package com.jy.infonet.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登陆之后不进入login 和register 页面
 */
public class LoginPageInteceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //未登陆，放行，进入登陆页面
        if(request.getSession().getAttribute("uname")==null){
            return true;
        }else {
            response.sendRedirect("homePage");
            return false;
        }
    }
}
