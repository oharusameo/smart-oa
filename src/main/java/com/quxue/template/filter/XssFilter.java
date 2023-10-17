package com.quxue.template.filter;

import com.quxue.template.wrapper.XssHttpServletRequestWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class XssFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //将原始的请求对象进行包装，传递下面的处理链路
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        //增强Servlet的request的能力，让其具有处理XSS攻击的能力
        XssHttpServletRequestWrapper wrapper = new XssHttpServletRequestWrapper(request);
        //传递的是已经增强了的request对象
        filterChain.doFilter(wrapper, servletResponse);
    }
}
