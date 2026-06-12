package com.example.bdd;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

/** Relie Cucumber au contexte Spring Boot afin d'injecter les beans réels dans les steps. */
@CucumberContextConfiguration
@SpringBootTest
public class CucumberSpringConfiguration {
}
