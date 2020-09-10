package com.jy.infonet.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 资讯列表页拦截器 ： 若没有查询到内容，返回主页
 */
public class ListPageInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getAttribute("page")==null){
            response.sendRedirect("homePage");
            return false;
        }
        return true;
    }
}
