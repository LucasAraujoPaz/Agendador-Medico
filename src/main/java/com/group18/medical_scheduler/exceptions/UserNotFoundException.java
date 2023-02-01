package com.group18.medical_scheduler.exceptions;

public class UserNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public UserNotFoundException() {
		super("User not found.");
	}
}
