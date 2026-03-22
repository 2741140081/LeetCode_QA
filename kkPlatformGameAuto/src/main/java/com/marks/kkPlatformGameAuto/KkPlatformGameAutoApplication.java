package com.marks.kkPlatformGameAuto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class KkPlatformGameAutoApplication {

	public static void main(String[] args) {
//		SpringApplication.run(KkPlatformGameAutoApplication.class, args);
		SpringApplicationBuilder builder = new SpringApplicationBuilder(KkPlatformGameAutoApplication.class);
		builder.headless(false).run(args);
	}

}
