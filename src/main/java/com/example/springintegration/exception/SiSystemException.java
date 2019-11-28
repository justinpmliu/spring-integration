package com.example.springintegration.exception;

public class SiSystemException extends RuntimeException {

    public SiSystemException() {
        super();
    }

    public SiSystemException(String msg) {
        super(msg);
    }

    public SiSystemException(String msg, Throwable t) {
        super(msg, t);
    }
}
