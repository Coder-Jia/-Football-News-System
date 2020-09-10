package com.jy.infonet.dao;

import com.jy.infonet.entity.News;
import com.jy.infonet.entity.NewsExample;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

public interface NewsMapper {
    long countByExample(NewsExample example);

    int deleteByExample(NewsExample example);

    int deleteByPrimaryKey(Integer newsId);

    int insert(News record);

    int insertSelective(News record);

    List<News> selectByExample(NewsExample example);

    List<News> selectByExampleWithType(NewsExample example);

    News selectByPrimaryKey(Integer newsId);

    News selectByPrimaryKeyWithType(Integer newsId);

    int updateByExampleSelective(@Param("record") News record, @Param("example") NewsExample example);

    int updateByExample(@Param("record") News record, @Param("example") NewsExample example);

    int updateByPrimaryKeySelective(News record);

    int updateByPrimaryKey(News record);

    //用于查询用户是否点赞过
    @Select("SELECT user_id FROM news_like_user WHERE news_id=#{newsId}")
    public Set<Integer> newsLikedUsers(@Param("newsId")int newsId);

    //插入【用户-资讯】点赞情况中间表记录
    @Insert("INSERT INTO news_like_user(news_id,user_id) VALUES(#{newsId},#{userId})")
    public Integer insertNewsLikeUser(@Param("userId") int userId, @Param("newsId")int newsId);

    //删除【用户-资讯】点赞情况中间表记录
    @Delete("DELETE FROM news_like_user WHERE news_id=#{newsId} AND user_id=#{userId}")
    public Integer deleteNewsLikeUser(@Param("userId") int userId, @Param("newsId")int newsId);

    //查询【用户-资讯】点赞情况中间表记录：判断某用户是否已点赞
    @Select("SELECT COUNT(1) FROM news_like_user WHERE news_id=#{newsId} AND user_id=#{userId}")
    public Integer isLikeUser(@Param("newsId")int newsId,@Param("userId") int userId );
}