package com.springrestsecurityboilerplate.validation;

@SuppressWarnings("serial")
public class UserNameExistsException extends Throwable {

    public UserNameExistsException(final String message) {
        super(message);
    }

}