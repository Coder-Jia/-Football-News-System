package com.jy.infonet.controller;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jy.infonet.entity.News;
import com.jy.infonet.entity.Types;
import com.jy.infonet.service.NewsService;
import com.jy.infonet.service.TypeService;
import com.jy.infonet.util.ContentUtil;
import com.jy.infonet.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理资讯
 */

@Controller
public class NewsController {
    @Autowired
    private NewsService service;
    @Autowired
    private TypeService typeService;

    //获取资讯列表
    @RequestMapping("/NewsListWithType")
    public String NewsListQueryServletk(@RequestParam(value = "currentPage",defaultValue = "1")Integer currentPage, @RequestParam(value = "TypeID",defaultValue = "2")Integer tid , Model model ){
        PageHelper.startPage(currentPage,5);
        List<News> news = service.getAllNews(tid);
        PageInfo<News> page = new PageInfo<News>(news);
        if (page.getSize()>0){
            Types types = page.getList().get(0).getTypes();
            types.setTypeCover("/infonet/images/"+types.getTypeCover());
            model.addAttribute("page",page);
        }
        return "view/list";
    }

    //获取资讯内容
    @GetMapping("/toContent/{newsId}")
    public String NewsContentServlet(@PathVariable("newsId")Integer newsID,@ModelAttribute("addCom_result")String addCom_result, Model model){
        News news = service.getNewsWithContentById(newsID);
        model.addAttribute("news",news);
        Object uId = null;
        if ( (uId = getSession().getAttribute("uId") ) != null ){//用户已经登陆，返回点赞状态
            model.addAttribute("liked",service.isLike(newsID,(Integer)uId));
        }
        if (!addCom_result.equals("")) model.addAttribute("addCom_result",addCom_result);
        return "view/text";
    }

    //获取热点资讯和获取最新资讯
    @RequestMapping("/NewsHomeServlet")
    public String NewsHomeServlet(Model model, HttpServletRequest request){
        model.addAttribute("homeNew",service.getHomeList().subList(0,10));
        model.addAttribute("homeHot",service.getHomeList().subList(10,20));
        return "view/homePage";
    }

    //获取管理员资讯列表
    @GetMapping("/news")
    public String NewsQueryServlet(@RequestParam(value = "option",defaultValue = "0") Integer option,@RequestParam(value = "currentPage",defaultValue = "1")Integer currentPage , Model model){
        PageHelper.startPage(currentPage,13);//一次返回15
        List<News> news ;
        if (option==0) news = service.getNews();
        else news = service.getNewsWithExample(option);
        PageInfo<News> page = new PageInfo<>(news);
        model.addAttribute("page",page);
        model.addAttribute("option",option);//将选项带回去
        return "mana/manaHome";
    }

    //根据id 查询新闻和种类，前往管理员更新
    @GetMapping("/newsById")
    public String NewsQueryById(@RequestParam("newsID")Integer newsID,@ModelAttribute(value = "message")String message, Model model){
        News news = service.getNewsById(newsID);
        news.setNewsContent(ContentUtil.getInstance().downLoadContent(news.getNewsContent()));
        List<Types> allTypes = typeService.getAllTypes();
        model.addAttribute("news",news);
        model.addAttribute("allTypes",allTypes);
        if (!message.equals(""))  model.addAttribute("message",message);
        return "mana/manageNews_add";
    }

    //上传资讯
    @PostMapping("/news")
    public String NewsAddServlet(@RequestParam("cover") MultipartFile file,
                                 News news,RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "操作有误：请添加一张资讯封面噢！");
            return "redirect:/uploadStatus";
        }
        String result = service.addNews(news, file);
        redirectAttributes.addFlashAttribute("message",result);
        if (result.equals("上传成功")) return "redirect:/newsById?newsID="+news.getNewsId();//跳向修改页面查看资讯新增结果
        return "redirect:/uploadStatus";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "/error/uploadStatus";
    }

    //更新资讯
    @PutMapping("/news")
    public String NewsUpdateServlet(@RequestParam("cover") MultipartFile file, News news,RedirectAttributes redirectAttributes) throws IOException {
        redirectAttributes.addFlashAttribute("message",service.updateNewsById(news,file));
        return "redirect:/newsById?newsID="+news.getNewsId();
    }

    //删除资讯
    @ResponseBody
    @DeleteMapping("/news")
    public String NewsDeleteServlet(@RequestParam("newsID")Integer newsID){
        return service.deleteNewsById(newsID);
    }


    @ResponseBody//浏览量更新
    @PostMapping(value = "/browse")
    public void NewsBrowseServlet(@RequestParam("newsID")Integer newsID){
        service.countBrowse(newsID);
    }

    @ResponseBody//点赞数更新：+1 / -1
    @PostMapping("/likeNews")
    public void NewsLikeServlet(@RequestParam(value = "userId",required = true)Integer userId,@RequestParam("newsID")Integer newsID, @RequestParam(value = "Change",defaultValue = "1")Integer Change){
        service.countNewsLike(userId,newsID,Change);
    }

    //关键字查询
    @GetMapping("/search")
    public ModelAndView NewsSearchServlet(@RequestParam("queryString")String queryString, @RequestParam(value = "currentPage",defaultValue = "1")Integer currentPage,@RequestParam(value = "isMana",defaultValue = "false")boolean isMana){
        ModelAndView modelAndView = null;
        if (!isMana) modelAndView = new ModelAndView("view/searchlist");
        else modelAndView = new ModelAndView("mana/manaHome_search");
        Map<String,Object> map = service.getSearchByKeyword(queryString, currentPage);
        if(map!=null){
            List<News> newsList = (List<News>) map.get("newsList");
            int totalPage = (int) map.get("totalPage");
            modelAndView.addObject("totalPage",totalPage);
            modelAndView.addObject("page",newsList);//page就是查询到的结果
            modelAndView.addObject("currentPage",currentPage);
            modelAndView.addObject("queryString",queryString);
        }
        return modelAndView;
    }


    //通过RequestContextHolder获取session对象
    public HttpSession getSession(){
        //获取到ServletRequestAttributes 里面有
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取到Request对象
        HttpServletRequest request = attrs.getRequest();
        //获取到Session对象
        HttpSession session = request.getSession();
        //获取到Response对象
        //HttpServletResponse response = attrs.getResponse();
        return session;
    }
}
