package com.quxue.template.interceptor;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.quxue.template.common.annotation.RequireRoot;
import com.quxue.template.common.utils.JWTUtils;
import com.quxue.template.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Slf4j
@Component
public class RootInterceptor implements HandlerInterceptor {
    @Resource
    private UserService userService;
    @Resource
    private JWTUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            if (method.isAnnotationPresent(RequireRoot.class)) {//如果方法贴上了@RequireRoot，则需要做token校验,查询是否root
                String token = request.getHeader("token");
                if (StringUtils.isBlank(token)) {
                    return false;
                }
                jwtUtils.verifyToken(token);
                String userIdFromToken = jwtUtils.getUserIdFromToken(token);
                return userService.rootVerify(userIdFromToken);
            }
            return true;//如果没有@RequireRoot.class注解，则放行
        }
        return true;
    }
}
