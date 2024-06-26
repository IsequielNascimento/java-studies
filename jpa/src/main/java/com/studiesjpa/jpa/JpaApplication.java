package com.studiesjpa.jpa;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
import org.springframework.context.annotation.Bean;

import com.studiesjpa.jpa.repositories.AuthorRepository;

@SpringBootApplication
public class JpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AuthorRepository repository) {

		return args -> {

		};
	}

}
