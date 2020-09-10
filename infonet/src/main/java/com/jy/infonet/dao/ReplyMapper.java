package com.jy.infonet.dao;

import com.jy.infonet.entity.Reply;
import com.jy.infonet.entity.ReplyExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReplyMapper {
    long countByExample(ReplyExample example);

    int deleteByExample(ReplyExample example);

    int deleteByPrimaryKey(Integer replyId);

    int insert(Reply record);

    int insertSelective(Reply record);

    List<Reply> selectByExample(ReplyExample example);

    List<Reply> selectByExampleWithUser(ReplyExample example);

    Reply selectByPrimaryKey(Integer replyId);

    Reply selectByPrimaryKeyWithUser(Integer replyId);

    int updateByExampleSelective(@Param("record") Reply record, @Param("example") ReplyExample example);

    int updateByExample(@Param("record") Reply record, @Param("example") ReplyExample example);

    int updateByPrimaryKeySelective(Reply record);

    int updateByPrimaryKey(Reply record);
}