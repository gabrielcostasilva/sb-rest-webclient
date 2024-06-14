package com.example.restclienttest;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.ClientHttpRequestInterceptor;

@Component
public class PostClient {

    private final RestClient restClient;

    public PostClient(RestClient.Builder builder, ClientHttpRequestInterceptor postInterceptor) {
        this.restClient = builder
                            .baseUrl("https://jsonplaceholder.typicode.com")
                            .requestInterceptor(postInterceptor)
                            .build();

    }

    public List<Post> findAll() {
        return restClient
                    .get()
                    .uri("/posts")
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});

    }
}
