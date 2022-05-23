package ru.samples.spring.cloud.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "hello", url = "${hello.url}")
public interface HelloClient extends Hello {
}
