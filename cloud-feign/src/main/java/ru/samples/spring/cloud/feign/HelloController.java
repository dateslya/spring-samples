package ru.samples.spring.cloud.feign;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HelloController implements Hello {
    @Override
    public void hello(String name) {
        log.info("Hello, {}!", name);
    }
}
