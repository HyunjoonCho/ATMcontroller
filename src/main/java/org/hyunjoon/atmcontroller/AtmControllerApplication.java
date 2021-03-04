package org.hyunjoon.atmcontroller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "org.hyunjoon.atmcontroller")
public class AtmControllerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtmControllerApplication.class, args);
	}

}
