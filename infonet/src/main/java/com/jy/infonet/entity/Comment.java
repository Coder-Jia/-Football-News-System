package com.jy.infonet.entity;

import java.util.Date;

public class Comment {
    private Integer commentId;

    private Integer newsId;

    private Integer userId;

    private String commentContent;

    private Date commentDate;

    private Integer commentLike;

    private Netuser netuser;

    public Netuser getNetuser() {
        return netuser;
    }

    public void setNetuser(Netuser netuser) {
        this.netuser = netuser;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent == null ? null : commentContent.trim();
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public Integer getCommentLike() {
        return commentLike;
    }

    public void setCommentLike(Integer commentLike) {
        this.commentLike = commentLike;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", newsId=" + newsId +
                ", userId=" + userId +
                ", commentContent='" + commentContent + '\'' +
                ", commentDate=" + commentDate +
                ", commentLike=" + commentLike +
                ", netusers=" + netuser +
                '}';
    }
}