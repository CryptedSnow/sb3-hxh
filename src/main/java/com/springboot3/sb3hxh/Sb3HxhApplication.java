package com.springboot3.sb3hxh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class Sb3HxhApplication {

	public static void main(String[] args) {
		SpringApplication.run(Sb3HxhApplication.class, args);
	}

	@GetMapping("/")
	public String begin(){
		return "/";
	}

}