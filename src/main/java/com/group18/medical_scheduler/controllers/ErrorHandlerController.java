package com.group18.medical_scheduler.controllers;

import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.group18.medical_scheduler.exceptions.ErrorMessage;

@RestController
@ControllerAdvice
public class ErrorHandlerController implements ErrorController {
	
	@RequestMapping("/error")
	public ResponseEntity<ErrorMessage> handleWhiteLabelErrorPage(
			final HttpServletResponse response) {
		
		final int statusCode = response.getStatus();
		final var status = HttpStatus.resolve(statusCode) == null ? 
				HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.resolve(statusCode);
		
		return new ResponseEntity<>(
				new ErrorMessage(
						status.getReasonPhrase(), 
						"Error " + statusCode + ": " + status.getReasonPhrase()), 
				status);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorMessage handleValidationExceptions(
			final MethodArgumentNotValidException exception) {
	    
		final Function<String, String> firstLetterToUpperCase = s ->
			s == null || s.length() <= 1 ? 
				s : s.substring(0, 1).toUpperCase() + s.substring(1);
		
		final String message = exception
			.getBindingResult()
			.getAllErrors()
			.stream()
			.reduce("",
				(text, error) -> {
					final String fieldName = error instanceof FieldError fieldError ? 
						firstLetterToUpperCase.apply(fieldError.getField()) + " " : "";
					
					final String errorMessage = error.getDefaultMessage();
					
					return text + fieldName + errorMessage + "\n";
				}, 
				(a, b) -> a + b);

	    return new ErrorMessage(exception, message);
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus
	public ErrorMessage handle(
			final HttpServletRequest request, 
			final HttpServletResponse response,
			final Exception exception) {
		
	    return new ErrorMessage(exception);
    }
}
