package com.example.webclient;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class TodoRetriever {

    private final RestClient restClient;

    public TodoRetriever() {
        restClient = RestClient
                            .builder()
                            .baseUrl("https://jsonplaceholder.typicode.com")
                            .build();

    }

    public List<Todo> readAll() {

		return restClient
                    .get()
                    .uri("/todos")
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<Todo>>() {});

	}

	public Todo readSingle(int id) {

        return restClient
                    .get()
                    .uri("/todos/{id}", id)
                    .retrieve()
                    .body(Todo.class);

	}

    public Todo create(Todo todo) {
        
        return restClient
                    .post()
                    .uri("/todos")
                    .body(todo)
                    .retrieve()
                    .body(Todo.class);
    }

    public ResponseEntity<Void> delete(int id) {
        
        return restClient
                    .delete()
                    .uri("/todos/{id}", id)
                    .retrieve()
                    .toBodilessEntity();
                    
    }

    public Todo update(Todo todo) {
        
        return restClient
                    .put()
                    .uri("/todos/{id}", todo.id())
                    .body(todo)
                    .retrieve()
                    .body(Todo.class);
    }
}
