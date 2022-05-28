package com.bed.config;

import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 请求资源过滤器
 */
@Configuration
public class WebFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {


        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //过滤css请求、登录页面、注册页面的请求、以及登录接口、注册接口的请求
        if (request.getServletPath().matches("(/)||(/register)||(.*css$)||(/user/login)||(/user/register)")) {
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        //判断当前session中是否有account储存，没有储存的话重定向到登录页面登录
        if (request.getSession().getAttribute("account")==null){
            request.getSession().setAttribute("msg","<script>window.alert('请先登录账户')</script>");
            response.sendRedirect("/");
        } else filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
