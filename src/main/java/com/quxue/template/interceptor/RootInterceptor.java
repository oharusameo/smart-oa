package com.quxue.template.interceptor;

import com.quxue.template.common.utils.JWTUtils;
import com.quxue.template.common.utils.TokenUtils;
import com.quxue.template.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class RootInterceptor implements HandlerInterceptor {
    @Resource
    private UserService userService;
    @Resource
    private JWTUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        String userIdFromToken = jwtUtils.getUserIdFromToken(token);
        return userService.rootVerify(userIdFromToken);
    }
}
