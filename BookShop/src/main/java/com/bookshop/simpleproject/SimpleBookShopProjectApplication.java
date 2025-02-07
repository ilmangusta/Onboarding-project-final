package com.bookshop.simpleproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SimpleBookShopProjectApplication {


	public static void main(String[] args) {

		SpringApplication.run(SimpleBookShopProjectApplication.class, args);
		System.out.println("Context successfully deployed");
	}


}
