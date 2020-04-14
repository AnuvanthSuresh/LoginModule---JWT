package com.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({ "com.stock.controllers","com.stock.utils","com.stock.services","com.stock.config"})
@EntityScan("com.stock.requests")
@EnableJpaRepositories("com.stock.jparepository")
public class LoginModuleJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginModuleJwtApplication.class, args);
	}

}
