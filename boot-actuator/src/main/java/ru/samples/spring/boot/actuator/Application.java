package ru.samples.spring.boot.actuator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.web.context.WebServerPortFileWriter;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.addListeners(
                new ApplicationPidFileWriter("myApp-pid.txt"),
                new WebServerPortFileWriter("myApp-port.txt"));
        springApplication.run(args);
    }
}
