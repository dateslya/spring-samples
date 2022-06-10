package ru.samples.spring.boot.data;

import java.util.Optional;

public interface PersonProcessor {
    void process(Optional<Person> personOptional);
}
