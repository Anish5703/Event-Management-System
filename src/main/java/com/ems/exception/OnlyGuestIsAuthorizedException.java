package com.ems.exception;

public class OnlyGuestIsAuthorizedException extends RuntimeException{
	
	public OnlyGuestIsAuthorizedException() 
	{
		super("Only Guest User Are Allowed");
	}

}
