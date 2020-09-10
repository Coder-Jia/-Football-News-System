package com.jy.infonet.dao;

import com.jy.infonet.entity.Comment;
import com.jy.infonet.entity.CommentExample;
import org.apache.ibatis.annotations.Param;


import java.util.List;

public interface CommentMapper {
    long countByExample(CommentExample example);

    int deleteByExample(CommentExample example);

    int deleteByPrimaryKey(Integer commentId);

    int insert(Comment record);

    int insertSelective(Comment record);

    List<Comment> selectByExample(CommentExample example);

    List<Comment> selectByExampleWithUser(CommentExample example);

    Comment selectByPrimaryKey(Integer commentId);

    Comment selectByPrimaryKeyWithUser(Integer commentId);

    int updateByExampleSelective(@Param("record") Comment record, @Param("example") CommentExample example);

    int updateByExample(@Param("record") Comment record, @Param("example") CommentExample example);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    List<Comment> selectByExampleWithReplyCount(CommentExample example);
}