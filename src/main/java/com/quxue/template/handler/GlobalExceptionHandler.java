package com.quxue.template.handler;

import cn.hutool.json.JSONUtil;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.quxue.template.domain.pojo.Result;
import com.quxue.template.exception.BusinessException;
import com.quxue.template.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;

import static cn.hutool.http.HttpStatus.*;


@RestControllerAdvice
@Component
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Result exceptionHandler(Exception exception) {

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
            log.error(e.getMessage());
            return Result.error(HTTP_UNAVAILABLE, e.getMessage());
        }
        if (exception instanceof SystemException) {
            SystemException e = (SystemException) exception;
            log.error(e.getMessage());
            return Result.error(HTTP_INTERNAL_ERROR, e.getMessage());
        }
        if (exception instanceof JWTDecodeException) {
            JWTDecodeException e = (JWTDecodeException) exception;
            log.error(e.getMessage());
            return Result.error(HTTP_UNAVAILABLE, "无效的令牌");
        }
        if (exception instanceof DuplicateKeyException) {
            DuplicateKeyException e = (DuplicateKeyException) exception;
            log.error(e.getMessage());
            return Result.error(HTTP_BAD_REQUEST, "该数据已存在，不允许重复添加");
        }
        exception.printStackTrace();
        log.error(exception.getMessage());
        return Result.error(HTTP_FORBIDDEN, "网络不稳定");
    }

}
