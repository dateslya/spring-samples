package ru.samples.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.integration.splitter.DefaultMessageSplitter;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.core.BeanFactoryMessageChannelDestinationResolver;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@EnableKafka
@EnableScheduling
@EnableIntegration
@SpringBootApplication
public class Application {

    private int index = 0;
    private static final int COUNT = 3;

    @Autowired
    private MessagingTemplate messagingTemplate;
    @Autowired
    private RecordQueue recordQueue;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Scheduled(initialDelay = 5_000, fixedDelay = 15_000)
    public void putMessage() {
        String message = IntStream.range(index, index + COUNT)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(","));
        log.info("Put: {}", message);
        recordQueue.put(message);
        index += COUNT;
    }

    @MessagingGateway
    interface RecordQueue {
        @Gateway(requestChannel = "inputFlow.input")
        void put(Object payload);
    }

    @Bean
    public IntegrationFlow inputFlow(KafkaTemplate<?, ?> kafkaTemplate) {
        DefaultMessageSplitter splitter = new DefaultMessageSplitter();
        splitter.setDelimiters(",");
        return f -> f
                .log("split")
                .split(splitter)
                .log("message")
                .handle((MessageHandler) kafkaTemplate::send);
    }

    @Bean
    public IntegrationFlow kafkaFlow(
            ConsumerFactory<?, ?> consumerFactory,
            ProducerFactory<?, ?> producerFactory) {
        return IntegrationFlows
                .from(Kafka.messageDrivenChannelAdapter(consumerFactory, "kafka.input"))
                .log("kafka")
                .handle(Kafka.outboundChannelAdapter(producerFactory).topic("kafka.output"))
                .get();
    }

    @Bean
    public MessagingTemplate messagingTemplate() {
        MessagingTemplate template = new MessagingTemplate();
        template.setDestinationResolver(new BeanFactoryMessageChannelDestinationResolver());
        return template;
    }

    @KafkaListener(topics = "kafka.output")
    public void listenKafkaOutput(Message<?> message) {
        messagingTemplate.send("outputFlow.input", message);
    }

    @Bean
    public IntegrationFlow outputFlow() {
        return f -> f
                .log("aggregate")
                .aggregate()
                .log("release");
    }
}
