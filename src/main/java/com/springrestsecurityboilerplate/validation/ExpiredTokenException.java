package com.springrestsecurityboilerplate.validation;

import java.util.Date;

@SuppressWarnings("serial")
public class ExpiredTokenException extends Throwable {

    public ExpiredTokenException(final Date date) {
        super("Expired Token - This token was valid until  " + date);
    }

}
