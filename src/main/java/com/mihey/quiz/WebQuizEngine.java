package com.mihey.quiz;

import com.mihey.quiz.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class WebQuizEngine {

	public static void main(String[] args) {

		SpringApplication.run(WebQuizEngine.class, args);

	}
}
