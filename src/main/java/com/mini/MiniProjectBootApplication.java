package com.mini;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MiniProjectBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiniProjectBootApplication.class, args);
	}

}
