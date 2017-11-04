package com.springrestsecurityboilerplate.validation;

@SuppressWarnings("serial")
public class UsernameExistsException extends Throwable {

    public UsernameExistsException(final String username) {
        super("There is an account with that username: " + username);
    }

}