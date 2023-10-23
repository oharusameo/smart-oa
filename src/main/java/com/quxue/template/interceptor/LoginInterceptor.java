package com.quxue.template.interceptor;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.quxue.template.common.annotation.RequireLogin;
import com.quxue.template.common.utils.JWTUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Resource
    private JWTUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            if (method.isAnnotationPresent(RequireLogin.class)) {//如果方法贴上了@RequireLogin，则需要做token校验
                String token = request.getHeader("token");
                if (StringUtils.isBlank(token)) {
                    return false;
                }
                jwtUtils.verifyToken(token);
            }
            return true;
        }
        return true;
    }
//        AuthContextHolder.setUserId(Integer.valueOf(jwtUtils.getUserIdFromToken(token)));

}
