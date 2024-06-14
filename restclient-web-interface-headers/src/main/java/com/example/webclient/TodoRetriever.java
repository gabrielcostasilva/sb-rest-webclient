package com.example.webclient;

import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;

public interface TodoRetriever {

    @GetExchange("/todos")
    public List<Todo> readAll(@RequestHeader Map<String, String> authMap);

    @GetExchange("/todos/{id}")
	public Todo readSingle(@PathVariable Integer id, @RequestHeader Map<String, String> authMap);

    @PostExchange("/todos")
    public Todo create(@RequestBody Todo todo, @RequestHeader Map<String, String> authMap);

    @DeleteExchange("/todos/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id, @RequestHeader Map<String, String> authMap);

    @PutExchange("/todos/{id}")
    public Todo update(@PathVariable Integer id, @RequestBody Todo todo, @RequestHeader Map<String, String> authMap);

}
