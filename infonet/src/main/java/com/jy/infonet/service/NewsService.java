package com.jy.infonet.service;

import com.jy.infonet.dao.NewsMapper;
import com.jy.infonet.entity.News;
import com.jy.infonet.entity.NewsExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface NewsService {
    //获取所有资讯
    public List<News> getNews();
    //根据条件选项获取所有资讯
    public List<News> getNewsWithExample(Integer option);
    //根据类别id获得所有资讯
    public List<News> getAllNews(Integer tid);
    //根据资讯id获取资讯
    public News getNewsById(Integer nid);
    //获取热点和最新资讯
    public List<News> getHomeList();
    //搜索栏：关键词搜索资讯
    public Map<String,Object> getSearchByKeyword(String keyword, int currentPage);
    //根据id删除资讯
    public String deleteNewsById(Integer nid);
    //增加资讯
    public String addNews(News news, MultipartFile file);
    //根据主键更新资讯
    public String updateNewsById(News news,MultipartFile file);
    //返回资讯和具体资讯内容
    News getNewsWithContentById(Integer newsID);
    //浏览量增加
    public void countBrowse(Integer nid);
    //评论数增加
    public void countComment(Integer nid);
    //点赞数+1 / -1
    public void countNewsLike(Integer userId,Integer nid, Integer change);
    //判断用户是否点赞了资讯
    public boolean isLike(Integer newsId,Integer userId);

    //获取某类别资讯总数
    public int getTotalByTypeId(Integer tid);
}
