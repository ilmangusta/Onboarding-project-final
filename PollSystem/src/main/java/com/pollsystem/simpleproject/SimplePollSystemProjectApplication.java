package com.pollsystem.simpleproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SimplePollSystemProjectApplication {


	public static void main(String[] args) {

		SpringApplication.run(SimplePollSystemProjectApplication.class, args);
		System.out.println("Context successfully deployed");
	}


}
