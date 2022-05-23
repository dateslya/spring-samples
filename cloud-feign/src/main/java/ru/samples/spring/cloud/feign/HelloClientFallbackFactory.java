package ru.samples.spring.cloud.feign;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HelloClientFallbackFactory implements FallbackFactory<HelloClient> {
    @Override
    public HelloClient create(Throwable cause) {
        return new HelloClient() {
            @Override
            public void hello(String name) {
                log.error("Name={}", name, cause);
            }
        };
    }
}
