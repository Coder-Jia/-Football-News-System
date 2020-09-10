package com.jy.infonet.dao;

import com.jy.infonet.entity.Netuser;
import com.jy.infonet.entity.NetuserExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NetuserMapper {
    long countByExample(NetuserExample example);

    int deleteByExample(NetuserExample example);

    int deleteByPrimaryKey(Integer userId);

    int insert(Netuser record);

    int insertSelective(Netuser record);

    List<Netuser> selectByExample(NetuserExample example);

    Netuser selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") Netuser record, @Param("example") NetuserExample example);

    int updateByExample(@Param("record") Netuser record, @Param("example") NetuserExample example);

    int updateByPrimaryKeySelective(Netuser record);

    int updateByPrimaryKey(Netuser record);
}