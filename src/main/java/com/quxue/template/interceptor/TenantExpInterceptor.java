package com.quxue.template.interceptor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quxue.template.common.annotation.RequireTenantActive;
import com.quxue.template.common.utils.JWTUtils;
import com.quxue.template.common.utils.TokenUtils;
import com.quxue.template.domain.pojo.Tenant;
import com.quxue.template.mapper.TenantMapper;
import jdk.nashorn.internal.parser.Token;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

@Component
public class TenantExpInterceptor implements HandlerInterceptor {

    @Resource
    private JWTUtils jwtUtils;
    @Resource
    private TenantMapper tenantMapper;



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Class<?> beanType = handlerMethod.getBeanType();
            Method method = handlerMethod.getMethod();
            if (method.isAnnotationPresent(RequireTenantActive.class) || beanType.isAnnotationPresent(RequireTenantActive.class)) {
                String tenantId = jwtUtils.getTenantIdFromToken(request.getHeader("token"));
                Map<String, String> map = tenantMapper.selectExpire(tenantId);
                String userStatus = map.get("us");
                String tenantStatus = map.get("ts");
                //如果用户和租户的状态都为正常才放行
                return !("0".equals(userStatus) || "0".equals(tenantStatus));
            }
        }
        return true;
    }


}
