package com.ems.exception;

public class OnlyHostIsAuthorizedException extends RuntimeException{

	public OnlyHostIsAuthorizedException()
	{
		super("This Page or feature are only available for hosts");
	}
	
}
