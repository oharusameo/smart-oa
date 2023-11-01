package com.quxue.template.common.utils;

import org.springframework.stereotype.Component;

@Component
public class AuthContextHolder {
    private static ThreadLocal<Integer> userIdThreadLocal = new ThreadLocal<>();

    public static void setUserId(Integer userId) {
        userIdThreadLocal.set(userId);
    }

    public static Integer getUserId() {
        return userIdThreadLocal.get();
    }

    public static void remove() {
        userIdThreadLocal.remove();
    }
}
