package com.example.webclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@SpringBootApplication
public class WebclientApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebclientApplication.class, args);

	}

	@Bean
	TodoRetriever todoRetrieverService() {
		var client = RestClient.create("https://jsonplaceholder.typicode.com");
		HttpServiceProxyFactory factory = 
			HttpServiceProxyFactory
				.builderFor(
					RestClientAdapter.create(client))
				.build();

		return factory.createClient(TodoRetriever.class);

	}

}
