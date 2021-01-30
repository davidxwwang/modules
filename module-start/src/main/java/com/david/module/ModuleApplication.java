package com.david.module;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

//@SpringBootApplication(scanBasePackages = {}, exclude = {})
@SpringBootApplication(scanBasePackages = "com.david.module")

// http://localhost:{server.port} 就可以看见Eureka界面了
//@EnableEurekaServer
@EnableDiscoveryClient
public class ModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModuleApplication.class, args);
	}

}
