package com.example.webclient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WebclientApplicationTests {

	@Autowired
	TodoRetriever retriever;

	@Test
	void contextLoads() {
	}

	@Test
	public void readAllTodos() {

		var todos = retriever.readAll();

		assertNotNull(todos);
		assertNotEquals(0, todos.size());

	}

	@Test
	public void readSingleTodo() {

		var todo = retriever.readSingle(1);

		assertNotNull(todo);
		assertEquals(1, todo.id());

	}

	@Test
	public void createTodo() {

		var todo = retriever
				.create(
						new Todo(
								10,
								10,
								"To test the app",
								false));

		assertNotNull(todo);
		assertEquals("To test the app", todo.title());

	}

	@Test
	public void deleteTodo() {

		var response = retriever.delete(1);

		assertTrue(response.getStatusCode().is2xxSuccessful());

	}

	@Test
	public void updateTodo() {

		var todo = retriever
				.update(new Todo(1, 1, "To test the app", false));

		assertNotNull(todo);
		assertEquals("To test the app", todo.title());
		
	}

}
