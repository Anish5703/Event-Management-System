package com.ems.exception;

public class EventNotFoundException extends RuntimeException{

	public EventNotFoundException()
	{
		super("No event exists with this id");
	}
}

