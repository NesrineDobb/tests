package com.challenge.backendcodingchallenge.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.challenge.backendcodingchallenge.exception.EntityNotFoundException;
import com.challenge.backendcodingchallenge.exception.NotEnoughFundsException;
import com.challenge.backendcodingchallenge.exception.UnknownCurrencyException;

import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.http.HttpStatus;

@ControllerAdvice
public class ExceptionControllerAdvice {

	@ResponseBody
	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	Error entityNotFoundHandler(EntityNotFoundException ex) {
		return new Error("EntityNotFoundByIdException", ex.getMessage());
	}

	@ResponseBody
	@ExceptionHandler(NotEnoughFundsException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	Error notEnoughFundsHandler(NotEnoughFundsException ex) {
		return new Error("NotEnoughFundsException", ex.getMessage());
	}

	@ResponseBody
	@ExceptionHandler(UnknownCurrencyException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	Error notEnoughFundsHandler(UnknownCurrencyException ex) {
		return new Error("UnknownCurrencyException", ex.getMessage());
	}

	@Getter
	@NoArgsConstructor
	static class Error {

		private String name, message;

		Error(String name, String message) {
			this.name = name;
			this.message = message;
		}
	}
}
