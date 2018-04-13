package com.springrestsecurityboilerplate.validation;

@SuppressWarnings("serial")
public class AccountNotFoundException extends Throwable {

	public AccountNotFoundException(final String message) {
		super(message);
	}
}
