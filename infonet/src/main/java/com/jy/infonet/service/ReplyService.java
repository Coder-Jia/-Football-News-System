package com.jy.infonet.service;

import com.jy.infonet.entity.Reply;

import java.util.List;

public interface ReplyService {

    //获取某评论所有回复
    public List<Reply> getReplysByComId(Integer comID);
    //添加回复
    public boolean addReply(Reply reply);

}
