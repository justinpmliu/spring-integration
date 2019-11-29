package com.example.springintegration.exception;

public class SiBusinessException extends Exception{

    public SiBusinessException() {
        super();
    }

    public SiBusinessException(String msg) {
        super(msg);
    }

    public SiBusinessException(String msg, Throwable t) {
        super(msg, t);
    }

}
