package com.jy.infonet.controller;

import com.github.pagehelper.PageHelper;
import com.jy.infonet.entity.Reply;
import com.jy.infonet.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 处理回复
 */
@Controller
public class ReplyController {
    @Autowired
    private ReplyService service;

    //获取回复
    @ResponseBody
    @GetMapping("/ReplyQueryByPageServlet")
    public List<Reply> ReplyQueryByPageServlet(@RequestParam("comment_id")Integer comment_id, @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize){
        PageHelper.startPage(1,pageSize);
        List<Reply> replys = service.getReplysByComId(comment_id);
        return replys;
    }

    @PostMapping("/reply")
    public String ReplyAddServlet(@RequestParam("referer")String referrer, Reply reply, RedirectAttributes redirectAttributes){
        if(service.addReply(reply))redirectAttributes.addAttribute("addCom_result","回复成功");
        else redirectAttributes.addAttribute("addCom_result","出错");
        return "redirect:"+referrer;
    }

}
