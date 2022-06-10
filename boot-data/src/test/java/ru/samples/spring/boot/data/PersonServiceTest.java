package ru.samples.spring.boot.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

@DataJpaTest
class PersonServiceTest {

    @Autowired
    PersonService personService;

    @Sql("createPerson.sql")
    @Test
    void findAndProcessPerson() {
        Optional<Person> person = personService.findAndProcessPerson(1L);
        Assertions.assertEquals("Bob", person.get().getName());
    }

    @TestConfiguration
    static class Configuration {

        @Autowired
        PersonRepository personRepository;
        @MockBean
        PersonProcessor personProcessor;

        @Bean
        PersonService personService() {
            return new PersonService(personRepository, Optional.of(personProcessor));
        }
    }
}