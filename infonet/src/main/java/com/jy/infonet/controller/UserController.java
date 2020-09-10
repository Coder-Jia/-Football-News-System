package com.jy.infonet.controller;

import com.jy.infonet.entity.Netuser;
import com.jy.infonet.entity.RespBean;
import com.jy.infonet.service.NetusersService;
import com.jy.infonet.util.PwdEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 处理用户
 */
@Controller
public class UserController {
    @Autowired
    private NetusersService service;


    //登录
    @RequestMapping("/loginUserServlet")
    public String loginUserServlet(@RequestParam("referer")String referrer,@RequestParam("uphone")String phone, @RequestParam("pwd")String pwd, Model model, HttpServletRequest request){
        int result = service.loginUser(phone, pwd);
        if(result>0){//登陆成功
            HttpSession session = request.getSession();
            Netuser user = service.getUserByPhone(phone);
            session.setAttribute("uname",user.getUserName());
            session.setAttribute("uId",user.getUserId());
            return "redirect:"+referrer;
        }else{//登陆失败
            model.addAttribute("referer",referrer);
            if(result==-1){
                model.addAttribute("error_phone","用户手机号不存在");
            }else{
                model.addAttribute("error_pwd","密码错误");
            }
            return "view/login";
        }
    }

    //注销
    @RequestMapping("/logoutUserServlet")
    public String logoutUserServlet(@RequestParam("referer")String referrer,HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:"+referrer;
    }

    //注册
    @ResponseBody
    @RequestMapping(value = "/registerUserServlet")
    public RespBean registerUserServlet(Netuser netuser){
        //用户密码加密
        netuser.setUserPwd(PwdEncoderUtil.encode(netuser.getUserPwd()));
        String result = service.registerUser(netuser);
        RespBean respBean = new RespBean(result, "register result");
        return respBean;
    }

}
