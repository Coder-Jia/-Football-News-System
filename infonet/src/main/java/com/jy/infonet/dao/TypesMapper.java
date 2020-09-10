package com.jy.infonet.dao;

import com.jy.infonet.entity.Types;
import com.jy.infonet.entity.TypesExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TypesMapper {
    long countByExample(TypesExample example);

    int deleteByExample(TypesExample example);

    int deleteByPrimaryKey(Integer typeId);

    int insert(Types record);

    int insertSelective(Types record);

    List<Types> selectByExample(TypesExample example);

    Types selectByPrimaryKey(Integer typeId);

    int updateByExampleSelective(@Param("record") Types record, @Param("example") TypesExample example);

    int updateByExample(@Param("record") Types record, @Param("example") TypesExample example);

    int updateByPrimaryKeySelective(Types record);

    int updateByPrimaryKey(Types record);
}