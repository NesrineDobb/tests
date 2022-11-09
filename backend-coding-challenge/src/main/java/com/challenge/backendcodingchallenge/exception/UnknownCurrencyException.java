package com.challenge.backendcodingchallenge.exception;

public class UnknownCurrencyException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnknownCurrencyException() {
		super("can not retrieve exchange rate: currency not supported.");
	}

}
