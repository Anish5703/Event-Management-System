package com.ems.exception;

public class OwnerAuthorizationFailedException extends RuntimeException{
	
	public OwnerAuthorizationFailedException()
	{
		super("Only the original creator is allowed to perform this action");
	}

}
