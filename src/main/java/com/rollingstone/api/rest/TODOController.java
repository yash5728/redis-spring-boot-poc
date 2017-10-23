package com.rollingstone.api.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rollingstone.domain.Person;
import com.rollingstone.domain.RedisItem;

import redis.clients.jedis.Jedis;
import com.rollingstone.exception.HTTP404Exception;
import com.rollingstone.service.TodoService;
import com.rollingstone.service.TodoServiceEvent;

@RestController
@RequestMapping(value = "/rsmortgage-TODOservice/v1/TODO")
public class TODOController extends AbstractRestHandler {

	/*
	 * This is the Public Facing API. Change this to your specific public facing REST API
	 */
	@Autowired
	private TodoService todoService;

	@RequestMapping(value = "", method = RequestMethod.POST, consumes = { "application/json",
			"application/xml" }, produces = { "application/json", "application/xml" })
	@ResponseStatus(HttpStatus.CREATED)
	public void createTODO(@RequestBody Person todo, HttpServletRequest request, HttpServletResponse response) {
		Person createdTODO = this.todoService.createTodo(todo);
		eventPublisher.publishEvent(new TodoServiceEvent(this, "TODOCreated", createdTODO));
		response.setHeader("Location", request.getRequestURL().append("/").append(createdTODO.getId()).toString());
	}

	@RequestMapping(value = "", method = RequestMethod.GET, produces = { "application/json", "application/xml" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Page<Person> getAllTodo(
			@RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
			@RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
			HttpServletRequest request, HttpServletResponse response) {
		return this.todoService.getAlltodos(page, size);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json", "application/xml" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Person getTodo(@PathVariable("id") Long id, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Person Todo = this.todoService.getTodo(id);
		checkResourceFound(Todo);
		return Todo;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = { "application/json",
			"application/xml" }, produces = { "application/json", "application/xml" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateTodo(@PathVariable("id") Long id, @RequestBody Person Todo, HttpServletRequest request,
			HttpServletResponse response) {
		checkResourceFound(this.todoService.getTodo(id));
		if (id != Todo.getId())
			throw new HTTP404Exception("ID doesn't match!");
		this.todoService.updateTodo(Todo);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = { "application/json",
			"application/xml" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteTodo(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
		checkResourceFound(this.todoService.getTodo(id));
		this.todoService.deleteTodo(id);
	}
	

	@RequestMapping(value = "/JEDIS/{key}", method = RequestMethod.GET, produces = { "application/json",
			"application/xml" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody RedisItem getJedis(@PathVariable("key") String key, HttpServletRequest request, HttpServletResponse response) {
		Jedis jedis = new Jedis("127.0.0.1",6379);
		String value = jedis.get(key);
		System.out.println(value);
		RedisItem item = new RedisItem(value);
		jedis.close();
		return item;
	}
}
