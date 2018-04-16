package com.springrestsecurityboilerplate.validation;

@SuppressWarnings("serial")
public class EmailExistsException extends Throwable {

	public EmailExistsException(final String email) {
		super("There is an account with that email address: " + email);
	}

}
