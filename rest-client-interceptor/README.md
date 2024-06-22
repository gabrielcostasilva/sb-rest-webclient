# REST CLIENT TEST EXAMPLE
This project exemplifies an interceptor along with a Webclient. This project is based on [Dan Vega's](https://www.youtube.com/@DanVega) [Youtube video](https://youtu.be/nedhXAU8U4s?si=vRr2IesoWDggYa88).

## Project Overview
There are two main changes to the [original project](../rest-client-test/).

First, we create a class that has the interceptor behaviour [`PostInterceptor`](./src/main/java/com/example/restclienttest/PostInterceptor.java). This is a traditional Spring Bean, apart from extending [`org.springframework.http.client.ClientHttpRequestInterceptor`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/client/ClientHttpRequestInterceptor.html) class. 

This functional interface bring the method `intercept`, which does the magic, as follows:

```java
(...)

public ClientHttpResponse intercept(
    HttpRequest request, 
    byte[] body, 
    ClientHttpRequestExecution execution)
        throws IOException {

            log.info("Interceptor in action: " + request.getURI());  // (1)

            request
                .getHeaders()
                .add("x-request-id", "An interceptor in action"); // (2)
    
        return execution.execute(request, body);
}

(...)
```

The code snippet above just add a header to the request as a way to show changing the request (2), but also print out a log, as a way to show extracting information from a request (1).

Second, we add the interceptor to the RestClient, in the [`PostClient`](./src/main/java/com/example/restclienttest/PostClient.java) Bean.