package com.quxue.template.handler;

import com.quxue.template.domain.pojo.Result;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {
    public Result exceptionHandler(Exception e){


       return null;
    }

}
