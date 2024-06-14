package com.example.restclienttest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestClientTest(PostClient.class)
class PostClientTest {

    @Autowired
    MockRestServiceServer server;

    @Autowired
    PostClient postClient;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldFindAllPosts() throws JsonProcessingException {

        //given
        List<Post> data = List.of(
            new Post(1, 1, "Can you guess?", "Yes, hello world, baby!"), new Post(2, 1, "Test everything ...", "... that matters!"));

        // when
        this.server
            .expect(
                MockRestRequestMatchers.requestTo("https://jsonplaceholder.typicode.com/posts"))
            .andRespond(
                MockRestResponseCreators.withSuccess(
                    objectMapper.writeValueAsString(data), 
                    MediaType.APPLICATION_JSON));

        // then
        List<Post> posts = postClient.findAll();
        assertEquals(2, posts.size());
    }
    
}
