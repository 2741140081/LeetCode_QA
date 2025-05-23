package com.marks.redemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.marks.redemo")
@SpringBootApplication
public class RedemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedemoApplication.class, args);
	}

}
