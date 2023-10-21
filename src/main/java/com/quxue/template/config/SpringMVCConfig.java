package com.quxue.template.config;

import com.quxue.template.interceptor.RootInterceptor;
import com.quxue.template.interceptor.VerifyTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class SpringMVCConfig implements WebMvcConfigurer {

    @Resource
    private VerifyTokenInterceptor verifyTokenInterceptor;
    @Resource
    private RootInterceptor rootInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(verifyTokenInterceptor)
                .addPathPatterns("/**/api/api/v1/user/**")
                .excludePathPatterns("/**/api/v1/user/createRootUser")
                .excludePathPatterns("/**/api/v1/user/login")
                .excludePathPatterns("/**/api/v1/user/register")
        ;
        //需要管理员身份的接口的拦截器
        registry.addInterceptor(rootInterceptor)
                .addPathPatterns("/**/api/api/v1/root/**")
//                .addPathPatterns("/**/api/v1/user/createCommonUser")
                .addPathPatterns("/**/createCommonUser")
        ;
    }
}
