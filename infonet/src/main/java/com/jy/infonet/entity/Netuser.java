package com.jy.infonet.entity;

public class Netuser {
    private Integer userId;

    private String userName;

    private String userPwd;

    private String userRealname;

    private String userSex;

    private String userPhone;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd == null ? null : userPwd.trim();
    }

    public String getUserRealname() {
        return userRealname;
    }

    public void setUserRealname(String userRealname) {
        this.userRealname = userRealname == null ? null : userRealname.trim();
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex == null ? null : userSex.trim();
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    @Override
    public String toString() {
        return "Netuser{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPwd='" + userPwd + '\'' +
                ", userRealname='" + userRealname + '\'' +
                ", userSex='" + userSex + '\'' +
                ", userPhone='" + userPhone + '\'' +
                '}';
    }
}