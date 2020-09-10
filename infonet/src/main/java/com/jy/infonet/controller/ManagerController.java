package com.jy.infonet.controller;

import com.jy.infonet.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 管理员
 */
@Controller
public class ManagerController {
    @Autowired
    private ManagerService service;

    //管理员登陆
    @PostMapping("/mana")
    public String loginNewsManagerServlet(@RequestParam("name")String name, @RequestParam("pwd")String pwd, HttpServletRequest req){
        int result = service.loginManager(name,pwd);
        HttpSession session = req.getSession();
        if(result==1){
            session.setAttribute("mname",name);
            session.setAttribute("error_login",null);
            return "redirect:/news";
        }else{
            if(result==-1){
                session.setAttribute("error_login","管理员不存在");
            }else{
                session.setAttribute("error_login","密码错误");
            }
            return "redirect:/login_mana";
        }
    }

    //注销
    @GetMapping("/mana")
    public String logoutNewsManagerServlet(HttpServletRequest request){
        request.getSession().invalidate();
        return "mana/loginMana";
    }

}
