package com.jy.infonet.entity;

import java.util.Date;

public class News {
    private Integer newsId;

    private String newsTitle;

    private String newsIntro;

    private Date newsDate;

    private Integer newsBrowse;

    private String newsCover;

    private Integer newsLike;

    private String newsContent;

    private Integer typeId;

    private Integer newsComment;

    private Types types;

    public Types getTypes() {
        return types;
    }

    public void setTypes(Types types) {
        this.types = types;
    }

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle == null ? null : newsTitle.trim();
    }

    public String getNewsIntro() {
        return newsIntro;
    }

    public void setNewsIntro(String newsIntro) {
        this.newsIntro = newsIntro == null ? null : newsIntro.trim();
    }

    public Date getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(Date newsDate) {
        this.newsDate = newsDate;
    }

    public Integer getNewsBrowse() {
        return newsBrowse;
    }

    public void setNewsBrowse(Integer newsBrowse) {
        this.newsBrowse = newsBrowse;
    }

    public String getNewsCover() {
        return newsCover;
    }

    public void setNewsCover(String newsCover) {
        this.newsCover = newsCover == null ? null : newsCover.trim();
    }

    public Integer getNewsLike() {
        return newsLike;
    }

    public void setNewsLike(Integer newsLike) {
        this.newsLike = newsLike;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent == null ? null : newsContent.trim();
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getNewsComment() {
        return newsComment;
    }

    public void setNewsComment(Integer newsComment) {
        this.newsComment = newsComment;
    }

    @Override
    public String toString() {
        return "News{" +
                "newsId=" + newsId +
                ", newsTitle='" + newsTitle + '\'' +
                ", newsIntro='" + newsIntro + '\'' +
                ", newsDate=" + newsDate +
                ", newsBrowse=" + newsBrowse +
                ", newsCover='" + newsCover + '\'' +
                ", newsLike=" + newsLike +
                ", newsContent='" + newsContent + '\'' +
                ", typeId=" + typeId +
                ", newsComment=" + newsComment +
                ", types=" + types +
                '}';
    }
}