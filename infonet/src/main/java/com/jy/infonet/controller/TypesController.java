package com.jy.infonet.controller;

import com.jy.infonet.entity.RespBean;
import com.jy.infonet.entity.Types;
import com.jy.infonet.service.NewsService;
import com.jy.infonet.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
public class TypesController {
    @Autowired
    private TypeService service;
    @Autowired
    private NewsService newsService;

    //导航栏
    @ResponseBody
    @RequestMapping("/TypesForNav")
    public List<Types> TypeForNavServlet(){
        List<Types> allTypes = service.getAllTypes();
        return allTypes;
    }

    //管理员查询类别
    @GetMapping("/types")
    public String TypeQueryAllServlet(Model model, @RequestParam(value = "totype", defaultValue = "false") boolean ifType, @ModelAttribute("message") String message) {
        List<Types> allTypes = service.getAllTypes();
        model.addAttribute("allTypes", allTypes);
        if (!ifType) return "mana/manageNews_add";
        else{
            if (!message.equals("")) model.addAttribute("message",message);
            System.out.println(message);
            return "mana/manageTypeHome";
        }
    }

    //删除类别
    @ResponseBody
    @DeleteMapping("/types")
    public String TypeDeleteServlet(@RequestParam("typeID")Integer typeID,@RequestParam("op") Integer op){
        return service.deleteTypeById(typeID,op);
    }

    //增加类别
    @PostMapping("/types")
    public String TypeAddServlet(@RequestParam("cover") MultipartFile typeCover, Types type, RedirectAttributes redirectAttributes) throws IOException {
        if (type.getTypeId()!=null) redirectAttributes.addFlashAttribute("message",service.updateTypeById(type,typeCover));
        else redirectAttributes.addFlashAttribute("message",service.addType(typeCover,type));
        return "redirect:types?totype=true";
    }

    //根据类别获取资讯总数
    @ResponseBody
    @GetMapping("/typesOfnews")
    public int typesOfnews(@RequestParam("tid") Integer tid){
        return newsService.getTotalByTypeId(tid);
    }

}
