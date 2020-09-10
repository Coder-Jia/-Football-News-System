package com.jy.infonet.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jy.infonet.entity.Comment;
import com.jy.infonet.service.CommentService;
import com.jy.infonet.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 处理评论
 */
@Controller
public class CommentController {
    @Autowired
    private CommentService service;


    @ResponseBody//获得首页最热评论列表
    @GetMapping("/CommentQueryByPage")
    public PageInfo CommentQueryByPage(@RequestParam("newsID")Integer newsID, @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize){
        PageHelper.startPage(1,pageSize);
        List<Comment> list = service.getHotCommentsByNewsId(newsID);
        PageInfo<Comment> page = new PageInfo<>(list);
        return page;
    }

    @ResponseBody//评论点赞数增加
    @RequestMapping("/commentLike")
    public String CommentLikeServlet(@RequestParam("ID")Integer comID, @RequestParam(value = "like",defaultValue = "1")Integer like, HttpServletRequest request){
        Integer uId = (Integer) request.getSession().getAttribute("uId");
        return service.updateCommentLike(uId,comID, like);
    }


    @PostMapping("/comments")//添加评论
    public String CommentAddServlet(Comment comment, RedirectAttributes redirectAttributes){
        int nid = comment.getNewsId();
        comment = service.addComment(comment);
        redirectAttributes.addFlashAttribute("addCom_result", (comment!=null?"评论成功":"评论失败！") );
        redirectAttributes.addFlashAttribute("yourcomment",comment);
        return "redirect:toContent/"+nid;
    }

}
