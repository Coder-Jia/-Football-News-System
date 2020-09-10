package com.jy.infonet.service;

import com.jy.infonet.entity.Types;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface TypeService {
    //根据id得到类别
    public Types getTypeById(Integer tid);
    //获得所有类别
    public List<Types> getAllTypes();
    //根据id删除类别
    public String deleteTypeById(int tid,Integer op);
    //更新类别
    public String updateTypeById(Types type,MultipartFile file);
    //增加类别
    public String addType(MultipartFile file,Types type);
}
