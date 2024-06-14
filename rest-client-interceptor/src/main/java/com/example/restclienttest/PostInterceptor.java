package com.example.restclienttest;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

@Component
public class PostInterceptor 
    implements ClientHttpRequestInterceptor {

    private static final Logger log = LoggerFactory.getLogger(PostInterceptor.class);

    @Override
    public ClientHttpResponse intercept(
        HttpRequest request, 
        byte[] body, 
        ClientHttpRequestExecution execution)
            throws IOException {

                log.info("Interceptor in action: " + request.getURI());

                request
                    .getHeaders()
                    .add("x-request-id", "An interceptor in action");
        
            return execution.execute(request, body);
    }
    
}
