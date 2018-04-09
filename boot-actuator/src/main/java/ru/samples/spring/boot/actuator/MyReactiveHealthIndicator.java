package ru.samples.spring.boot.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class MyReactiveHealthIndicator implements ReactiveHealthIndicator {
    public Mono<Health> health() {
        return Mono
                .just(Health.up().build())
                .onErrorReturn(Health.down().build());
    }
}
