package com.ahmetkanat.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
//@ComponentScan(basePackages= {"com.ahmetkanat.webapp.controllers"})
@EnableAutoConfiguration
public class WebappApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebappApplication.class, args);
		System.out.println("AHMET");
		
	}

}
