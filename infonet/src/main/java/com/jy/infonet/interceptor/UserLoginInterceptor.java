package com.jy.infonet.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 检查用户是否登陆
 */
public class UserLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Integer uId = (Integer) request.getSession().getAttribute("uId");
        if (uId==null){
            response.sendRedirect("login");
            return false;
        }
        return true;
    }
}
