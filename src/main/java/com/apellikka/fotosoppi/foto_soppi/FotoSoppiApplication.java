package com.apellikka.fotosoppi.foto_soppi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class FotoSoppiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FotoSoppiApplication.class, args);
	}

		@Bean
		WebMvcConfigurer corsConfigurer() {
			return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(@SuppressWarnings("null") CorsRegistry registry) {
				registry.addMapping("/images/**")
					.allowedOrigins("http://localhost:5173")
					.allowedMethods("GET", "OPTIONS");
			}
		};
	}
}
