package com.quxue.template.exception;

public class SystemException extends RuntimeException {

    public SystemException(String message, Exception exception) {
        super(message, exception);
    }

    public SystemException(String message) {
        super(message);
    }
}
