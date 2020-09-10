package com.jy.infonet.service.impl;

import com.jy.infonet.dao.ManagerMapper;
import com.jy.infonet.entity.Manager;
import com.jy.infonet.entity.ManagerExample;
import com.jy.infonet.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    private ManagerMapper managerDao;

    @Override
    public int loginManager(String name, String pwd) {
        ManagerExample example = new ManagerExample();
        example.createCriteria().andManagerNameEqualTo(name);
        List<Manager> managers = managerDao.selectByExample(example);
        if (managers.size() == 0){
            return -1;//管理员不存在
        }else if(!managers.get(0).getManagerPwd().equals(pwd)){
            return 0;//密码错误
        }else{
            return 1;
        }
    }
}
