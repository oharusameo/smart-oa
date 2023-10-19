package com.quxue.template.handler;

import cn.hutool.json.JSONUtil;
import com.quxue.template.domain.pojo.Result;
import com.quxue.template.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;

import static cn.hutool.http.HttpStatus.HTTP_BAD_REQUEST;
import static cn.hutool.http.HttpStatus.HTTP_FORBIDDEN;


@RestControllerAdvice
@Component
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Result exceptionHandler(Exception exception) {
        String message = exception.getMessage();
        System.out.println("message = " + message);
        if (exception instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException e = (MethodArgumentNotValidException) exception;
            //获取异常相应结果
            BindingResult bindingResult = e.getBindingResult();
            //获取多个字段的异常信息
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            HashMap<String, String> messages = new HashMap<>();
            for (FieldError error : fieldErrors) {
                messages.put(error.getField(), error.getDefaultMessage());
            }
            String json = JSONUtil.toJsonStr(messages);
            log.info(json);
            return Result.error(HTTP_BAD_REQUEST, json);

        }
        if (exception instanceof BusinessException) {
            BusinessException e = (BusinessException) exception;
            return Result.error(e.getMessage());
        }

        return Result.error(HTTP_FORBIDDEN, "网络不稳定");
    }

}
