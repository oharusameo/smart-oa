package com.quxue.template.interceptor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.quxue.template.common.annotation.RequireLogin;
import com.quxue.template.common.utils.JWTUtils;
import com.quxue.template.common.utils.TokenUtils;
import com.quxue.template.domain.pojo.Tenant;
import com.quxue.template.mapper.TenantMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;


@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Resource
    private JWTUtils jwtUtils;
    @Resource
    private TenantMapper tenantMapper;
    @Resource
    private TokenUtils tokenUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Class<?> clazz = handlerMethod.getBeanType();
            Method method = handlerMethod.getMethod();
            //如果方法或类上贴上了@RequireLogin，则需要做token校验
            if (method.isAnnotationPresent(RequireLogin.class) || clazz.isAnnotationPresent(RequireLogin.class)) {
                String token = request.getHeader("token");
                jwtUtils.verifyToken(token);

                String tenantId = tokenUtils.getTenantIdFromHeader();
                Map<String, String> map = tenantMapper.selectExpire(tenantId);
                String userStatus = map.get("us");
                String tenantStatus = map.get("ts");
                //如果用户和租户的状态都为正常才放行
                return !("0".equals(userStatus) || "0".equals(tenantStatus));
            }
            return true;
        }
        return true;
    }
//        AuthContextHolder.setUserId(Integer.valueOf(jwtUtils.getUserIdFromToken(token)));

}
