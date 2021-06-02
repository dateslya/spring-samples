package ru.samples.spring.cloud.feign;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@EnableFeignClients
@SpringBootApplication
public class Application {

    @Autowired
    private HandleClient handleClient;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("/handle")
    public void handle(@RequestParam String message) throws InterruptedException {
        int sec = new Random().nextInt(2);
        log.info("Handle '{}', sec {}", message, sec);
        TimeUnit.SECONDS.sleep(sec);
    }

    @GetMapping("/forward")
    public void forward(@RequestParam String message) {
        log.info("Forward '{}'", message);
        handleClient.handle(message);
    }

    @FeignClient(name = "handle-client", fallbackFactory = HandleClientFallbackFactory.class)
    public interface HandleClient {
        @GetMapping("/handle")
        void handle(@RequestParam("message") String message);
    }

    @Component
    public static class HandleClientFallback implements HandleClient {
        @Override
        public void handle(String message) {
            log.info("Fallback '{}'", message);
        }
    }

    @Component
    static class HandleClientFallbackFactory implements FallbackFactory<HandleClient> {
        @Override
        public HandleClient create(final Throwable cause) {
            return new HandleClient() {
                @Override
                public void handle(String message) {
                    log.info("Fallback '{}', reason was '{}'", message, cause);
                }
            };
        }
    }
}
