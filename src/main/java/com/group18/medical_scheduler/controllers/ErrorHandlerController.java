package com.group18.medical_scheduler.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.group18.medical_scheduler.exceptions.ErrorMessage;

@RestController
@ControllerAdvice
public class ErrorHandlerController implements ErrorController {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorMessage handleValidationExceptions(
			final MethodArgumentNotValidException exception) {
	    
	    final String message = exception.getBindingResult().getAllErrors()
	    	.stream()
    		.map(error -> {
    			final String fieldName = error instanceof FieldError fieldError ? 
    					fieldError.getField() + " " : "";
    	
    			final String errorMessage = error.getDefaultMessage();
        
    			return fieldName + errorMessage;
    		})
    		.reduce((a, b) -> a + "\n" + b)
    		.orElse("");
	    
	    return new ErrorMessage(exception, message);
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus
	public ErrorMessage sendErrorMessage(
			final HttpServletRequest request, 
			final Exception exception) {
	    
		exception.printStackTrace();
		
	    return new ErrorMessage(exception);
    }
}
