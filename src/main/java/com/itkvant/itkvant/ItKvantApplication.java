package com.itkvant.itkvant;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.itkvant.itkvant", "com.itkvant.itkvant.service.quizService", "com.itkvant.itkvant.repository.repoQuiz"})
public class ItKvantApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItKvantApplication.class, args);
	}

}
