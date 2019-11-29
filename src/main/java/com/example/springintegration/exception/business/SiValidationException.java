package com.example.springintegration.exception.business;

import com.example.springintegration.exception.SiBusinessException;

public class SiValidationException extends SiBusinessException {

    public SiValidationException() {
        super();
    }

    public SiValidationException(String msg) {
        super(msg);
    }

    public SiValidationException(String msg, Throwable t) {
        super(msg, t);
    }

}
