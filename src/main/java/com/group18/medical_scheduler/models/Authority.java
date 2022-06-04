package com.group18.medical_scheduler.models;

public enum Authority {
	USER,
	ADMINISTRATOR;

	// For Annotations
	public static final String 
		_USER = "'USER'",
		_ADMINISTRATOR = "'ADMINISTRATOR'",
		_BEGIN = "hasAnyAuthority({",
		_END = "})";
}