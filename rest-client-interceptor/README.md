# REST CLIENT TEST EXAMPLE
This project shows how to test REST APIs that uses `org.springframework.web.client.RestClient`. This project is fully based on [Dan Vega's](https://www.youtube.com/@DanVega) [Youtube video](https://youtu.be/jhhi03AIin4?si=ICEOEQWboz71AoZB).

## Project Overview
This project consists of two classes. [`Post`](./src/main/java/com/example/restclienttest/Post.java) is a Java _record_ that represents a single _Post_. [`PostClient`](./src/main/java/com/example/restclienttest/PostClient.java) is a REST Client implementation retrieving a list of _Post_ from a public API.

The REST Client uses `org.springframework.web.client.RestClient` to call the public API as simple as the code below: 

```java
// (... imports ...)

@Component
public class PostClient {

    private final RestClient restClient;

    public PostClient(RestClient.Builder builder) {
        this.restClient = builder
                            .baseUrl("https://jsonplaceholder.typicode.com")
                            .build(); // (1)

    }

    public List<Post> findAll() {
        return restClient
                    .get()
                    .uri("/posts")
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {}); // (2)

    }
}
```

In which:
- 1. Instantiates a `RestClient` targeting the `baseUrl`;
- 2. Sets the REST resource and how its body should be mapped to the Java object.

## The Test

One may test the code just by running it. However, this way the REST Client will make a real call to the API. This will be the case even if you create a unit test. 

An alternative is mocking it, so a call is simulated locally.

> You can know more about mocking by checking this [repository](https://github.com/gabrielcostasilva/sb-testing.git)

The testing code is as follows:

```java
// (... imports ...)

@RestClientTest(PostClient.class) //(1)
class PostClientTest {

    @Autowired
    MockRestServiceServer server; // (2)

    @Autowired
    PostClient postClient; // (3)

    @Autowired
    ObjectMapper objectMapper; // (4)

    @Test
    void shouldFindAllPosts() throws JsonProcessingException {

        //given
        List<Post> data = List.of(
            new Post(1, 1, "Can you guess?", "Yes, hello world, baby!"), new Post(2, 1, "Test everything ...", "... that matters!")); //(5)

        // when
        this.server
            .expect(
                MockRestRequestMatchers.requestTo("https://jsonplaceholder.typicode.com/posts"))
            .andRespond(
                MockRestResponseCreators.withSuccess(
                    objectMapper.writeValueAsString(data), 
                    MediaType.APPLICATION_JSON)); //(6)

        // then
        List<Post> posts = postClient.findAll();
        assertEquals(2, posts.size()); //(7)
    }
    
}

```

We start by annotating the class with `org.springframework.boot.test.autoconfigure.web.client.RestClientTest` (1). 

As many Spring annotations, this one simplifies a process - in this case, the REST Client testing process. In particular, it (i) applies only relevant configuration for the REST Client; and (ii) auto-configure a MockServer. 

Notice that we identify the class that is under test (`PostClient.class`).

Second, we create a `org.springframework.test.web.client.MockRestServiceServer` (2), which takes care of the whole simulation (_mocking_) further in the test (6). We highlight that `@RestClientTest` auto-configures the MockServer.

Next, we need a `PostClient` instance to test its methods, and `com.fasterxml.jackson.databind.ObjectMapper` to mapping the JSON onto a Java POJO (`Post`).

To test the code, Dan Vega structures the code in this interesting way: given, when & then. I like it!

So, _given_ a list of `Post` (5), _when_ this endpoint is called (6), _then_ check this assertion. Sounds easy, isn't it?

## Dependencies
This project _explicitly_ uses one single dependency: Web. However, it also carries `spring-boot-starter-test` otherwise, we could not test the code.