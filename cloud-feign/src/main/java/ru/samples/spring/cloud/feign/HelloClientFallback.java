package ru.samples.spring.cloud.feign;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HelloClientFallback implements HelloClient {
    @Override
    public void hello(String name) {
        log.info("Name={}", name);
    }
}
