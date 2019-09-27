package com.xqx.travel.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 解决全站乱码问题，处理所有的请求
 */
@WebFilter("/*")
public class CharchaterFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse rep, FilterChain filterChain) throws IOException, ServletException {
        // 获取请求
        HttpServletRequest request = (HttpServletRequest)req;
        // 获取请求方法名
        String method = request.getMethod();
        // 判断请求方法是否为post
        if ("post".equals(method)){
            // 解决post请求中中文乱码问题
            request.setCharacterEncoding("utf-8");
        }
        // 处理响应乱码问题
        HttpServletResponse response = (HttpServletResponse) rep;
        response.setContentType("text/html;charset=utf-8");
        filterChain.doFilter(request,response);
    }


    @Override
    public void destroy() {

    }
}
