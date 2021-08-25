package com.david.module;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.ComponentScan;

import javax.sql.DataSource;

//@SpringBootApplication(scanBasePackages = {}, exclude = {})
@SpringBootApplication(scanBasePackages = "com.david.module")

// http://localhost:{server.port} 就可以看见Eureka界面了
//@EnableEurekaServer
@MapperScan("com.david.module.data.mysql.mapper")
//@EnableDiscoveryClient
public class ModuleApplication {


	public static void main(String[] args) {
		SpringApplication.run(ModuleApplication.class, args);
	}

}
