package ru.samples.spring.boot.actuator;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class MyInfoContributor implements InfoContributor {
    public void contribute(Info.Builder builder) {
        builder.withDetail("myInfo", Collections.singletonMap("myProp", "1"));
    }
}
