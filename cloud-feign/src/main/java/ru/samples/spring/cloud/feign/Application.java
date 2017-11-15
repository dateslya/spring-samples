package ru.samples.spring.cloud.feign;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
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
        log.info("Handle '{}'", message);
        TimeUnit.SECONDS.sleep(new Random().nextInt(5));
    }

    @GetMapping("/forward")
    public void forward(@RequestParam String message) {
        log.info("Forward '{}'", message);
        handleClient.handle(message);
    }

    @FeignClient("handle-client")
    public interface HandleClient {
        @GetMapping("/handle")
        void handle(@RequestParam("message") String message);
    }
}
