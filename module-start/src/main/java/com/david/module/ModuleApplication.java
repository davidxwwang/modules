package com.david.module;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication(scanBasePackages = {}, exclude = {})
@SpringBootApplication(scanBasePackages = "com.david.module")
public class ModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModuleApplication.class, args);
	}

}
