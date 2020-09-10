package com.jy.infonet.task;

import com.jy.infonet.dao.TypesMapper;
import com.jy.infonet.entity.TypesExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 类别删除定时器
 */
@Service
public class TypeDeleteTask {

    @Autowired
    private TypesMapper typesMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());

    //将所有确认删除的类别删除
    @Scheduled(cron = "${delete.type.cron}")
    void deleteTypeFromMysqlPerDay(){
        TypesExample example = new TypesExample();
        example.createCriteria().andTypeDeletedEqualTo(1);
        int result = typesMapper.deleteByExample(example);
        logger.info("==定时任务 从数据库中删除类别{}项 ",result);
    }

}
