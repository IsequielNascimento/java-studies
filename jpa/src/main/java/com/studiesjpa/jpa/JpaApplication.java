package com.studiesjpa.jpa;

import com.studiesjpa.jpa.models.Author;
import com.studiesjpa.jpa.repositories.AuthorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AuthorRepository repository) {

		return args -> {
			var author = Author.builder()
					.firstName("teste")
					.lastName("last teste")
					.age(90)
					.email("aaaaaaAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
					.build();
			repository.save(author);
		};
	}

}
