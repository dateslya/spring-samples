package ru.samples.spring.boot.actuator;

import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@Timed
@RestController
public class MyController {

    @Autowired
    private MyService myService;

    @GetMapping("/myController")
    public Collection<String> get() {
        return myService.get();
    }
}
