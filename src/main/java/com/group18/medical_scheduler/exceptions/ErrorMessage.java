package com.group18.medical_scheduler.exceptions;

public record ErrorMessage(
		String errorName,
		String message,
		String origin) {
	
	public ErrorMessage(final Exception e) {
		this(
			e.getClass().getSimpleName(), 
			e.getMessage(), 
			(e.getStackTrace().length > 0) ? e.getStackTrace()[0].toString() : "");
	}
	
	public ErrorMessage(final Exception e, final String message) {
		this(
			e.getClass().getSimpleName(), 
			message, 
			(e.getStackTrace().length > 0) ? e.getStackTrace()[0].toString() : "");
	}
	
	@Override
	public String toString() {
		
		return "Error. \n" + 
			"Error name: " + errorName + "\n " +
			"Message: " + message +  "\n " +
			"Origin: " + origin;
	}
}
