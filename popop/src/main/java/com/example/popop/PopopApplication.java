package com.example.popop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PopopApplication {

	public static void main(String[] args) {
		SpringApplication.run(PopopApplication.class, args);
	}

}
