package com.dolphinskart.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EmployeeRestExceptionHandler {
	@ExceptionHandler
	public ResponseEntity<EmployeeErrorResponse> handleException(EmployeeNotFoundException notFound) {
		EmployeeErrorResponse response = new EmployeeErrorResponse();
		response.setStatus(HttpStatus.NOT_FOUND.value());
		response.setMessage(notFound.getMessage());
		response.setTimeStamp(System.currentTimeMillis());

		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<EmployeeErrorResponse> handleException(Exception exc) {
		EmployeeErrorResponse response = new EmployeeErrorResponse();
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		response.setMessage(exc.getMessage());
		response.setTimeStamp(System.currentTimeMillis());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

	}
}
