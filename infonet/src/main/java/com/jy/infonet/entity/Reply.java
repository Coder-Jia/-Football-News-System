package com.jy.infonet.entity;

import java.util.Date;

public class Reply {
    private Integer replyId;

    private Integer commentId;

    private String replyContent;

    private Date replyDate;

    private Integer userId;

    private Netuser netuser;

    public Netuser getNetuser() {
        return netuser;
    }

    public void setNetuser(Netuser netuser) {
        this.netuser = netuser;
    }

    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent == null ? null : replyContent.trim();
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Reply{" +
                "replyId=" + replyId +
                ", commentId=" + commentId +
                ", replyContent='" + replyContent + '\'' +
                ", replyDate=" + replyDate +
                ", userId=" + userId +
                ", netuser=" + netuser +
                '}';
    }
}