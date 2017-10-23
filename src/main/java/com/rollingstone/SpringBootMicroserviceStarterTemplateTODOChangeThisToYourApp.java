package com.rollingstone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration // Sprint Boot Auto Configuration
@ComponentScan(basePackages = "com.rollingstone")

public class SpringBootMicroserviceStarterTemplateTODOChangeThisToYourApp {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMicroserviceStarterTemplateTODOChangeThisToYourApp.class, args);
	}
}
