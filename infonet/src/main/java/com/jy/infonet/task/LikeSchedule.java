package com.jy.infonet.task;

import com.jy.infonet.dao.NewsMapper;
import com.jy.infonet.entity.News;
import com.jy.infonet.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 用于定时更新文章点赞总数和浏览量总数
 */
@Service
public class LikeSchedule {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private NewsMapper newsDao;

    @Resource
    private RedisTemplate redisTemplate;

    @Value("${NEWS.COUNT.BROWSE}")
    private String newsBrowseCounts;
    @Value("${NEWSID.LIKE.USERID}")
    private String news_like_user;
    @Value("${NEWSID.UNLIKE.USERID}")
    private String news_unlike_user;

    //点赞任务更新
    @Scheduled(cron="${like.cron}")
    public void saveLikeDataToMySQL(){
        logger.info( "==定时任务 更新点赞数到 mysql数据库 {} = 开始",LocalDateTime.now().format( DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss") ));
        Map<String,String> entries = redisTemplate.opsForHash().entries(news_like_user);
        if(!entries.isEmpty()){
            for (Map.Entry<String,String> entry : entries.entrySet()){
                Integer newsId = Integer.valueOf(entry.getKey());//获取资讯id
                HashSet<Integer> userIdSet = JacksonUtil.deserializeToSet(String.valueOf(entry.getValue()));//获取所有点赞过该资讯的用户id
                saveLike(newsId,userIdSet.size());//更新点赞数
                // 更新中间表
                saveUserLikeNews(userIdSet,newsId);
            }
            logger.info( "==定时任务 更新点赞数到 mysql数据库 {} = 完成",LocalDateTime.now().format( DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss") ));
        }else  logger.info( "==定时任务 更新点赞数到 mysql数据库 {} = 没有新信息需要写入",LocalDateTime.now().format( DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss") ));
    }

    //取消点赞任务更新
    @Scheduled(cron="${like.cron}")
    public void saveunLikeDataToMySQL(){
        logger.info( "==定时任务 更新取消点赞数到 mysql数据库 {} = 开始",LocalDateTime.now().format( DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss") ));
        Map<String,String> entries = redisTemplate.opsForHash().entries(news_unlike_user);
        if (!entries.isEmpty()){
            for (Map.Entry<String,String> entry : entries.entrySet()){
                Integer newsId = Integer.valueOf(entry.getKey());//获取资讯id
                HashSet<Integer> userIdSet = JacksonUtil.deserializeToSet(String.valueOf(entry.getValue()));//获取所有点赞过该资讯的用户id
                saveLike(newsId,-userIdSet.size());//更新减少点赞数
                // 更新中间表
                saveUserunLikeNews(userIdSet,newsId);
            }
            logger.info( "==定时任务 更新取消点赞数到 mysql数据库 {} = 完成",LocalDateTime.now().format( DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss") ));
        }else  logger.info( "==定时任务 更新取消点赞数到 mysql数据库 {} = 没有新信息需要写入",LocalDateTime.now().format( DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss") ));
    }

    //浏览量任务更新
    @Scheduled(cron="${browse.cron}")
    public void saveBrowseDataToMySQL(){
        logger.info( "==定时任务 写入浏览量到 mysql数据库 {} = 开始",LocalDateTime.now().format( DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss") ));
        Map<String, String> entries = redisTemplate.opsForHash().entries(newsBrowseCounts);
        if (!entries.isEmpty()){
            for (Map.Entry<String,String> entry : entries.entrySet()){
                Integer newsId = Integer.valueOf(entry.getKey());//获取资讯id
                Integer browseCount = Integer.valueOf( entry.getValue() );
                saveBrowse(newsId,browseCount);//更新浏览量
            }
            logger.info( "==定时任务 写入浏览量到 mysql数据库 {} = 完成",LocalDateTime.now().format( DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss") ));
        }else logger.info( "==定时任务 写入浏览量到 mysql数据库 {} = 没有新信息需要写入",LocalDateTime.now().format( DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss") ));
    }

    /**
     * 更新资讯点赞数
     * @param newsId
     * @param likeCount
     */
    public void saveLike(int newsId,int likeCount){
        News news ;
        if ( (news = newsDao.selectByPrimaryKey(newsId))!=null ){
               news.setNewsLike(news.getNewsLike()+likeCount);
               newsDao.updateByPrimaryKey(news);
               logger.info("--更新数据库资讯{}点赞数:{}",newsId,likeCount);
        }else logger.info("--更新数据库资讯{}点赞数:{}：资讯不存在",newsId,likeCount);
    }


    /**
     * 更新资讯浏览量
     * @param newsId
     * @param browseCount
     */
    public void saveBrowse(int newsId,int browseCount){
        News news ;
        if ( (news = newsDao.selectByPrimaryKey(newsId))!=null ){
            news.setNewsBrowse(news.getNewsBrowse()+browseCount);
            newsDao.updateByPrimaryKey(news);
            Long delete = redisTemplate.opsForHash().delete(newsBrowseCounts, String.valueOf(newsId));// #
            logger.info("--更新数据库资讯{}浏览量:{}  删除浏览量缓存数据:{}条",newsId,browseCount,delete);
        }else logger.info("--更新数据库资讯{}浏览量:{}:资讯不存在",newsId,browseCount);
    }

    /**
     * 记录用户点赞过的资讯情况
     * @param userId
     * @param newsId
     */
    public void saveUserLikeNews(Set<Integer> userId, int newsId){
        Set<Integer> users = newsDao.newsLikedUsers(newsId);
        for (Integer id:userId){
            if (!users.contains(id)){
                newsDao.insertNewsLikeUser(id,newsId);//插入中间表
            }
        }
        Long delete = redisTemplate.opsForHash().delete(news_like_user, String.valueOf(newsId));
        logger.info("--点赞 更新数据库资讯{}-用户表  删除缓存数据{}条",newsId,delete);
    }

    /**
     * 取消点赞
     * @param userId
     * @param newsId
     */
    public void saveUserunLikeNews(Set<Integer> userId, int newsId){
        Set<Integer> users = newsDao.newsLikedUsers(newsId);
        for (Integer id:userId){
            if (users.contains(id)){
                newsDao.deleteNewsLikeUser(id,newsId);//删除中间表记录
            }
        }
        Long delete = redisTemplate.opsForHash().delete(news_unlike_user, String.valueOf(newsId));
        logger.info("--取消点赞 更新数据库资讯{}-用户表  删除缓存数据{}条",newsId,delete);
    }


}
