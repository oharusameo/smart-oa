package com.quxue.template.exception;


public class BusinessException extends RuntimeException {
    private Integer statusCode;

    public Integer getStatusCode() {
        return statusCode;
    }

    public BusinessException(Integer statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Exception e) {
        super(message, e);
    }


}
