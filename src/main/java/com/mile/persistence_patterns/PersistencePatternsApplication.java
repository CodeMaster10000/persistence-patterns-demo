package com.mile.persistence_patterns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class PersistencePatternsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersistencePatternsApplication.class, args);
	}

}
