package ru.samples.spring.cloud.feign;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface Hello {
    @GetMapping("/hello")
    void hello(@RequestParam("name") String name);
}
