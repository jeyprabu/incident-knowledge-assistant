package com.enterprise.incident.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

	@Bean
	public WebClient ragWebClient() {
		return WebClient.builder().baseUrl("http://localhost:8000").build();
	}
}
