package com.jy.infonet.service.impl;


import com.jy.infonet.dao.NetuserMapper;
import com.jy.infonet.entity.Netuser;
import com.jy.infonet.entity.NetuserExample;
import com.jy.infonet.service.NetusersService;
import com.jy.infonet.util.PwdEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NetusersServiceImpl implements NetusersService {
    @Autowired
    private NetuserMapper userDao;

    @Override
    public Netuser getUserByPhone(String phone) {
        NetuserExample example = new NetuserExample();
        NetuserExample.Criteria criteria = example.createCriteria();
        criteria.andUserPhoneEqualTo(phone);
        List<Netuser> netusers = userDao.selectByExample(example);
        if(netusers.size()>0){
            return netusers.get(0);
        }else{
            return null;
        }
    }

    @Override
    public String getUserNameById(int id) {
        String userName = userDao.selectByPrimaryKey(id).getUserName();
        return userName;
    }

    @Override
    public int loginUser(String phone, String pwd) {
        Netuser netuser = getUserByPhone(phone);
        if(netuser==null){
            return -1;//用户手机号错误
        }else if(!PwdEncoderUtil.matches(pwd,netuser.getUserPwd())){
            return 0;//密码错误
        }else{
            return netuser.getUserId();//正确
        }
    }

    @Override
    public String registerUser(Netuser netuser) {
        if(getUserByPhone(netuser.getUserPhone())!=null){
            return "手机号已注册";
        }else{
            int count = userDao.insertSelective(netuser);
            if(count==1){
                return "注册成功";
            }else{
                return "异常";
            }
        }
    }

}
