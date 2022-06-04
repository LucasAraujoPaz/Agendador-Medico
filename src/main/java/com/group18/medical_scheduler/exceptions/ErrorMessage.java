package com.group18.medical_scheduler.exceptions;

public class ErrorMessage {
	
	public final String errorName;
	public final String message;
	public final String origin;
	
	public ErrorMessage(final Exception e) {
		this.errorName = e.getClass().getSimpleName();
		this.message = e.getMessage();
		this.origin = (e.getStackTrace().length > 0) ?
			e.getStackTrace()[0].toString() : "";
	}
	
	public ErrorMessage(final Exception e, final String message) {
		this.errorName = e.getClass().getSimpleName();
		this.message = message;
		this.origin = (e.getStackTrace().length > 0) ? 
			e.getStackTrace()[0].toString() : "";
	}
	
	@Override
	public String toString() {
		
		return "Error. \n" + 
			"Error name: " + errorName + "\n " +
			"Message: " + message +  "\n " +
			"Origin: " + origin;
	}
}
