package ru.samples.spring.cloud.feign;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

@WireMockTest(httpPort = 8080)
@SpringBootTest
class HelloClientTest {

    @Autowired
    HelloClient helloClient;

    @Test
    void helloTest() {

        stubFor(get("/hello?name=Bob").willReturn(ok()));

        helloClient.hello("Bob");
    }
}