package com.group18.medical_scheduler.exceptions;

public record ErrorMessage(
		String errorName,
		String message,
		String origin) {
	
	public ErrorMessage(final String errorName, final String message) {
		this(errorName, message, "N/A");
	}
	
	public ErrorMessage(final Exception e) {
		this(e, e.getMessage());
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
