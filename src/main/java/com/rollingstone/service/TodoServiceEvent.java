package com.rollingstone.service;

import org.springframework.context.ApplicationEvent;
import com.rollingstone.domain.Person;

/**
 * This is an optional class used in publishing application events. This can be
 * used to inject events into the Spring Boot audit management endpoint.
 */

//TODO Replace All Todos with your Domains Object
public class TodoServiceEvent extends ApplicationEvent {
	Person eventTodo;
	String eventType;

	public TodoServiceEvent(Object source, String eventType, Person todo) {
		super(source);
		this.eventType = eventType;
		this.eventTodo = todo;
	}

	public String toString() {
		return "My TodoService Event";
	}

	public Person getEventTodo() {
		return eventTodo;
	}

	public void setEventTodo(Person eventTodo) {
		this.eventTodo = eventTodo;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
}
