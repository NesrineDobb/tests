package com.up42.codingchallenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class FeatureExceptionController {

	@ExceptionHandler(value = FeatureNotfoundException.class)
	public ResponseEntity<Object> exception(FeatureNotfoundException exception) {
		return new ResponseEntity<>("Feature not found", HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = InternalServerErrorException.class)
	public ResponseEntity<Object> exception(InternalServerErrorException exception) {
		return new ResponseEntity<>("The data file cannot be parsed", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
