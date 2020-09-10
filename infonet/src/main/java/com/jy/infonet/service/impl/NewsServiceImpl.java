package com.jy.infonet.service.impl;

import com.github.pagehelper.PageHelper;
import com.jy.infonet.dao.NewsMapper;
import com.jy.infonet.entity.News;
import com.jy.infonet.entity.NewsExample;
import com.jy.infonet.service.NewsService;
import com.jy.infonet.util.ContentUtil;
import com.jy.infonet.util.ImageUtil;
import com.jy.infonet.util.JacksonUtil;
import com.jy.infonet.util.IndexUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsMapper newsDao;
    @Autowired
    private IndexUtil util;//lucene操作对象

    Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${NEWS.COUNT.BROWSE}")
    private String newsBrowseCounts;
    @Value("${NEWSID.LIKE.USERID}")
    private String news_like_user;
    @Value("${NEWSID.UNLIKE.USERID}")
    private String news_unlike_user;

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${upload.folder.cover}")
    private String UPLOADED_FOLDER_COVER ;//上传图片文件地址
    @Value("${upload.folder.content}")
    private String UPLOADED_FOLDER_CONTENT ;//上传资讯正文地址

    /**
     * 初始化设置序列化方式
     * PostConstruct 注释用于在依赖关系注入完成之后需要执行的方法上，以执行任何初始化
     */
    @PostConstruct
    public void init() {
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setValueSerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(redisSerializer);
        redisTemplate.setHashValueSerializer(redisSerializer);
    }

    @Override
    public List<News> getNews() {
        return newsDao.selectByExample(null);
    }

    @Override
    public List<News> getNewsWithExample(Integer option) {
        NewsExample example =  new NewsExample();
        if (option == 1) {example.setOrderByClause("(news_comment+news_like+news_browse) desc");}//根据热度降序排序
        else if (option == 2) {example.setOrderByClause("(news_comment+news_like+news_browse) asc");}//根据热度升序排序
        else if (option == 3) {example.setOrderByClause("news_date desc");}//根据时效降序排序
        else if (option == 4) {example.setOrderByClause("news_date asc");}//根据时效升序排序
        else if (option<0){
            option = Math.abs(option);
            example.createCriteria().andTypeIdEqualTo(option);
            logger.info("--根据类别ID（{}）查询资讯",option);
        }
        return newsDao.selectByExampleWithType(example);
    }

    @Override
    public List<News> getAllNews(Integer tid) {
        NewsExample example = new NewsExample();
        NewsExample.Criteria criteria = example.createCriteria();
        criteria.andTypeIdEqualTo(tid);
        return newsDao.selectByExampleWithType(example);
    }

    @Override
    public News getNewsById(Integer nid) {
        News news = newsDao.selectByPrimaryKeyWithType(nid);
        news.setNewsContent( ContentUtil.getInstance().dirContentName(UPLOADED_FOLDER_CONTENT,news.getNewsContent()) );
        return news;
    }

    // 读取资讯内容之后并返回
    public News getNewsWithContentById(Integer nid) {
        News news = newsDao.selectByPrimaryKeyWithType(nid);
        news.setNewsContent( ContentUtil.getInstance().downLoadContent( ContentUtil.getInstance().dirContentName(UPLOADED_FOLDER_CONTENT,news.getNewsContent()) ) );
        makeSureNewest(news);
        String cover = news.getNewsCover();
        news.setNewsCover("/cover/"+cover);
        return news;
    }

    //确保资讯的状态是当前时刻最新的一个版本
    void makeSureNewest(News news){
        HashSet<Integer> set = opsHashForGetUsersBynId(news_like_user, news.getNewsId());
        if (set!=null && !set.isEmpty()) news.setNewsLike(news.getNewsLike()+set.size());
        set = opsHashForGetUsersBynId(news_unlike_user,news.getNewsId());
        if (set!=null && !set.isEmpty()) news.setNewsLike(news.getNewsLike()-set.size());
        String browses = (String)redisTemplate.opsForHash().get(newsBrowseCounts, String.valueOf(news.getNewsId()));
        if (browses!=null) news.setNewsBrowse(news.getNewsBrowse()+Integer.valueOf(browses));
    }

    @Override
    public List<News> getHomeList() {
        NewsExample example = new NewsExample();
        example.setOrderByClause("news_date desc");
        PageHelper.startPage(1,10);
        List<News> list = newsDao.selectByExample(example);//最新

        PageHelper.startPage(1,10);
        example.setOrderByClause("news_browse+news_like+news_comment desc");
        List<News> hotList = newsDao.selectByExample(example);//热点
        for (News news:hotList){
            list.add(news);
        }
        return list;
    }

    @Override
    public String deleteNewsById(Integer nid) {
        //删除记录和资讯资源
        String cover = ImageUtil.getInstance().dirImageName(UPLOADED_FOLDER_COVER,newsDao.selectByPrimaryKey(nid).getNewsCover());
        String content = ContentUtil.getInstance().dirContentName(UPLOADED_FOLDER_CONTENT,newsDao.selectByPrimaryKey(nid).getNewsContent());
        ImageUtil.getInstance().deleteImage(cover);
        ImageUtil.getInstance().deleteImage(content);
        if (util.deleteIndex(nid) && newsDao.deleteByPrimaryKey(nid) == 1){
            return "删除成功";
        } else return "删除失败";
    }

    @Override
    public String addNews(News news, MultipartFile file) {
        if (news.getNewsTitle()==null || news.getNewsTitle().equals("")) return "资讯名不能为空！";
        String s = ContentUtil.getInstance().uploadContent(UPLOADED_FOLDER_CONTENT, news.getNewsContent());//上传资讯内容
        HashMap<String, String> map = ImageUtil.getInstance().uploadImage(UPLOADED_FOLDER_COVER, file);//上传图片
        if (!s.equals("上传异常") && !map.containsKey("error")){
            //数据库只保存简单文件名
            news.setNewsContent(ContentUtil.getInstance().noDirContentName(UPLOADED_FOLDER_CONTENT,s));
            news.setNewsCover(ImageUtil.getInstance().noDirImageName(UPLOADED_FOLDER_COVER,map.get("filename")));

            //得到java.sql.Date类型的date
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date uDate = null;
            try {
                uDate = df.parse(df.format(new Date()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            java.sql.Timestamp date = new java.sql.Timestamp(uDate.getTime());
            news.setNewsDate(date);
            int result = newsDao.insertSelective(news);
            boolean result_index = util.insertIndex(news);
            return (result==1 && result_index)?"上传成功":"error";
        }else return "上传资源失败！";

    }

    @Override
    public String updateNewsById(News news,MultipartFile file) {
        HashMap<String, String> map = null;
        String resource = ImageUtil.getInstance().dirImageName(UPLOADED_FOLDER_COVER,getNewsById(news.getNewsId()).getNewsCover());//获得原图片路径
        if ( !file.isEmpty() ){
            if (ImageUtil.isImage(file)) {
                //修改图片
                ImageUtil.getInstance().deleteImage( resource );//删除
                map = ImageUtil.getInstance().uploadImage(UPLOADED_FOLDER_COVER, file);//重传
                if (map.containsKey("error")) return "修改图片失败";
                resource = ImageUtil.getInstance().noDirImageName(UPLOADED_FOLDER_COVER,map.get("filename"));
            }else return "图片格式有误";
        }
        news.setNewsCover(resource);
        //上传资讯内容
        if ( ContentUtil.getInstance().updateContent( (resource = ContentUtil.getInstance().dirContentName(UPLOADED_FOLDER_CONTENT,getNewsById(news.getNewsId()).getNewsContent()) ), news.getNewsContent() ) ){
            news.setNewsContent(ContentUtil.getInstance().noDirContentName(UPLOADED_FOLDER_CONTENT,resource));
            //得到java.sql.Date类型的date
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date uDate = null;
            try {
                uDate = df.parse(df.format(new Date()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            java.sql.Timestamp date = new java.sql.Timestamp(uDate.getTime());
            news.setNewsDate(date);
            return newsDao.updateByPrimaryKeySelective(news)==1 && util.updateIndex(news.getNewsId(),news) ? "修改成功":"修改存在异常！";
        }else return "修改失败";
    }

    @Override//评论数增加
    public void countComment(Integer nid) {
        News news = newsDao.selectByPrimaryKey(nid);
        news.setNewsComment(news.getNewsComment()+1);
        newsDao.updateByPrimaryKey(news);
    }

    @Override//浏览量增加
    public void countBrowse(Integer nid) {
        redisTemplate.opsForHash().increment(newsBrowseCounts,String.valueOf(nid),1);
    }

    @Override//点赞数增加或减少
    public synchronized void countNewsLike(Integer userId,Integer nid,Integer change) {
        //获取文章点赞情况
        String result_like = (String) redisTemplate.opsForHash().get(news_like_user, String.valueOf(nid));
        HashSet<Integer> set_like = result_like == null?new HashSet<>(): JacksonUtil.deserializeToSet(result_like);
        //获取文章取消点赞情况
        String result_unlike = (String) redisTemplate.opsForHash().get(news_unlike_user, String.valueOf(nid));
        HashSet<Integer> set_unlike = result_unlike == null?new HashSet<>(): JacksonUtil.deserializeToSet(result_unlike);
        if (change==1) {
            if (!set_like.contains(userId)){//用户进行点赞 并且还未点赞过
                logger.info("==用户{}点赞了资讯{}",userId,nid);
                if (set_unlike.contains(userId)){
                    set_unlike.remove(userId);//说明用户即将取消数据库的点赞，撤销
                    if (set_unlike.isEmpty()) redisTemplate.opsForHash().delete(news_unlike_user,String.valueOf(nid));
                }else {
                    set_like.add(userId);
                    redisTemplate.opsForHash().put(news_like_user,String.valueOf(nid),JacksonUtil.serializeToString(set_like));
                }
            }else logger.info("==用户{}已经点赞过资讯{}",userId,nid);
        }else if(change==-1){
            /**取消点赞
             * mysql 点赞状态会显示在页面中，若已点赞则只能取消点赞，因此此时缓存中不可能有该用户的点赞记录
             * 若缓存中有，证明用户是在当前时间段点的赞，则直接在缓存中删去即可，稍后就不会写入数据库
             * 若没有，而且当前执行了点赞操作，证明是mysql中的点赞状态，因此记录在取消赞记录中，稍后写入数据库时进行更新
             */
            if (set_like.contains(userId)){
                logger.info("==用户{}取消点赞了资讯{}",userId,nid);
                set_like.remove(userId);
                if (set_like.isEmpty()){
                    redisTemplate.opsForHash().delete(news_like_user,String.valueOf(nid));
                    return ;
                }
                redisTemplate.opsForHash().put(news_like_user,String.valueOf(nid),JacksonUtil.serializeToString(set_like));
            }else{
                set_unlike.add(userId);
                redisTemplate.opsForHash().put(news_unlike_user,String.valueOf(nid),JacksonUtil.serializeToString(set_unlike));
            }
        }
    }

    @Override
    public boolean isLike(Integer newsId, Integer userId) {
        //判断用户是否为取消点赞
        HashSet<Integer> set = opsHashForGetUsersBynId(news_unlike_user,newsId);
        if ( set!=null && set.contains(userId) ) return false;
        //判断用户是否点赞
        set = opsHashForGetUsersBynId(news_like_user,newsId);
        boolean flag =  set!=null?set.contains(userId):false  ;
        //判断是否曾经点赞
        return  flag || newsDao.isLikeUser(newsId,userId)==1;
    }

    @Override
    public int getTotalByTypeId(Integer tid) {
        NewsExample example = new NewsExample();
        example.createCriteria().andTypeIdEqualTo(tid);
        return (int) newsDao.countByExample(example);
    }

    //用于操作hash数据类型 : key  :   value(childkey : set)
    HashSet<Integer> opsHashForGetUsersBynId(String key,Integer childkey){
        String result = (String) redisTemplate.opsForHash().get(key, String.valueOf(childkey));
        HashSet<Integer> set = result!=null?JacksonUtil.deserializeToSet(result):null;
        return set;
    }

    @Override
    public Map<String,Object> getSearchByKeyword(String keyword, int currentPage) {
        return util.IndexTextSearch(keyword,currentPage);
    }
}
