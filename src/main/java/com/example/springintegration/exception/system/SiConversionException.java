package com.example.springintegration.exception.system;

import com.example.springintegration.exception.SiSystemException;

public class SiConversionException extends SiSystemException {

    public SiConversionException() {
        super();
    }

    public SiConversionException(String msg) {
        super(msg);
    }

    public SiConversionException(String msg, Throwable t) {
        super(msg, t);
    }
}
