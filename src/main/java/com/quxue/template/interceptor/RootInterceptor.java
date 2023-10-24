package com.quxue.template.interceptor;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.quxue.template.common.annotation.RequireRoot;
import com.quxue.template.common.utils.JWTUtils;
import com.quxue.template.exception.BusinessException;
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
            Class<?> clazz = handlerMethod.getBeanType();
            Method method = handlerMethod.getMethod();
            //如果方法或类贴上了@RequireRoot，则需要做token校验,查询是否root
            if (method.isAnnotationPresent(RequireRoot.class) || clazz.isAnnotationPresent(RequireRoot.class)) {
                String token = request.getHeader("token");
                jwtUtils.verifyToken(token);
                String userIdFromToken = jwtUtils.getUserIdFromToken(token);
                if (userService.rootVerify(userIdFromToken)) {
                    return true;
                }
                throw new BusinessException("非法访问，该接口仅允许管理员操作");
            }
            return true;//如果没有@RequireRoot.class注解，则放行
        }
        return true;
    }
}
