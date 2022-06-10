package ru.samples.spring.boot.data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
    private final Optional<PersonProcessor> personProcessor;

    @Transactional
    public Optional<Person> findAndProcessPerson(Long id) {
        Optional<Person> person = personRepository.findById(id);
        personProcessor.ifPresent(p -> p.process(person));
        return person;
    }
}
