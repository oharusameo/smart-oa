package com.quxue.template.exception;


import com.quxue.template.common.enums.FaceEnum;

public class BusinessException extends RuntimeException {
    private Integer statusCode;

    public Integer getStatusCode() {
        return statusCode;
    }

    public BusinessException(Integer statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public BusinessException(FaceEnum statusCode, String message) {
        super(message);
        this.statusCode = statusCode.getValue();
    }

    public BusinessException(String message) {
        super(message);
    }


    public BusinessException(String message, Exception e) {
        super(message, e);
    }


}
