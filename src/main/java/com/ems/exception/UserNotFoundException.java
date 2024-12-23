package com.ems.exception;

public class UserNotFoundException extends RuntimeException{

	public UserNotFoundException()
	{
		super("No User Login Found !! Login First!!!");
	}
}
