package com.secret;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GlobalsecretmanagerApplication {
	public static void main(String[] args) {
		SpringApplication.run(GlobalsecretmanagerApplication.class, args);
	}
}
