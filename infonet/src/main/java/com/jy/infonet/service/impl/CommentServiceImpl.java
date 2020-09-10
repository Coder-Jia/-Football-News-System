package com.jy.infonet.service.impl;

import com.jy.infonet.dao.CommentMapper;
import com.jy.infonet.entity.Comment;
import com.jy.infonet.entity.CommentExample;
import com.jy.infonet.service.CommentService;
import com.jy.infonet.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentDao;
    @Autowired
    private NewsService newsService;

    //获取评论
    @Override
    public List<Comment> getHotCommentsByNewsId(Integer newsId) {
        CommentExample example = new CommentExample();
        example.createCriteria().andNewsIdEqualTo(newsId);
        List<Comment> comments = commentDao.selectByExampleWithReplyCount(example);
        return comments;
    }


    @Override//更新评论赞
    public String updateCommentLike(Integer uId,Integer cid, Integer like) {
        Comment comment = commentDao.selectByPrimaryKey(cid);
        if (uId == comment.getUserId()) return "不要给自己点赞噢！";
        comment.setCommentLike(comment.getCommentLike()+like);
        int count = commentDao.updateByPrimaryKeySelective(comment);
        return count==1?"Like success":"Like fault";
    }


    @Override//添加评论
    public Comment addComment(Comment comment ) {
        //获取当前时间
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date uDate = null;
        try {
            uDate = df.parse(df.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        java.sql.Timestamp date = new java.sql.Timestamp(uDate.getTime());
        comment.setCommentDate(date);
        if( commentDao.insertSelective(comment)==1 ){
            newsService.countComment(comment.getNewsId());//资讯评论数+1
            return comment;
        }
        return null;
    }
}
