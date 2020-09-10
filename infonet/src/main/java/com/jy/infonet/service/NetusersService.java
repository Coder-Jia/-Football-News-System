package com.jy.infonet.service;

import com.jy.infonet.entity.Netuser;

public interface NetusersService {
    //根据手机号查询用户
    public Netuser getUserByPhone(String phone);
    //根据id查询用户名
    public String getUserNameById(int id);
    //用户登录
    public int loginUser(String phone, String pwd);
    //用户注册
    public String registerUser(Netuser netuser);
}
