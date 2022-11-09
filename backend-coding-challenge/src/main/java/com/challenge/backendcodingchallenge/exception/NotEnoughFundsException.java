package com.challenge.backendcodingchallenge.exception;

public class NotEnoughFundsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotEnoughFundsException(int accountId) {
        super("Not enough funds in account with id= " + accountId + ". The transfer has been aborted.");
    }
}
