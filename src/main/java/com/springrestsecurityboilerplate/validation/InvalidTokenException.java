package com.springrestsecurityboilerplate.validation;

@SuppressWarnings("serial")
public class InvalidTokenException extends Throwable {

    public InvalidTokenException(final String message) {
        super("Invalid Token: " + message);
    }

}