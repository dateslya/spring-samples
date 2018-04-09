package ru.samples.spring.boot.actuator;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;

@Service
public class MyService {

    private final Counter counter;
    private final Timer timer;

    @Autowired
    public MyService(MeterRegistry meterRegistry) {
        this.counter = meterRegistry.counter("myCounter");
        this.timer = meterRegistry.timer("myTimer");
    }

    public Collection<String> get() {
        counter.increment();
        return timer.record(() -> Arrays.asList("1", "2", "3"));
    }
}
