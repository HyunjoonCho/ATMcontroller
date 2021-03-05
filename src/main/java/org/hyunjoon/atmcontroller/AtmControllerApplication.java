package org.hyunjoon.atmcontroller;

import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "org.hyunjoon.atmcontroller")
public class AtmControllerApplication {

	public static void main(String[] args) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(AtmControllerApplication.class);
		builder.web(WebApplicationType.SERVLET);
		builder.bannerMode(Banner.Mode.OFF);

		builder.build().run(args);
	}

}
