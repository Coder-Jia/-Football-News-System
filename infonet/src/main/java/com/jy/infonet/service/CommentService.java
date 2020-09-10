package com.jy.infonet.service;

import com.jy.infonet.entity.Comment;

import java.util.List;

public interface CommentService {
    //获取资讯所有最热评论
    public List<Comment> getHotCommentsByNewsId(Integer newsId);
    //更新评论点赞数
    public String updateCommentLike(Integer uId,Integer cid, Integer like);
    //增加评论
    public Comment addComment(Comment comment);
}
