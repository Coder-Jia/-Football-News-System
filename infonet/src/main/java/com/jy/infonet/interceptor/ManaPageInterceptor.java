package com.jy.infonet.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 管理员只有登陆后才能进行各种管理
 */
public class ManaPageInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String mname = (String) session.getAttribute("mname");
        if (mname==null){
            response.sendRedirect("login_mana");
            return false;
        }
        return true;
    }
}
