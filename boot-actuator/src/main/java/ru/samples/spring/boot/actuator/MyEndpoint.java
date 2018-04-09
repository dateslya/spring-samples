package ru.samples.spring.boot.actuator;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
@Endpoint(id = "my")
public class MyEndpoint {

    @ReadOperation
    public Map get() {
        return Collections.singletonMap("result", "ok");
    }
}
