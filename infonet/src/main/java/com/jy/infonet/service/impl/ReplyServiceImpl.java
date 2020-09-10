package com.jy.infonet.service.impl;

import com.jy.infonet.dao.ReplyMapper;
import com.jy.infonet.entity.Reply;
import com.jy.infonet.entity.ReplyExample;
import com.jy.infonet.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService {
    @Autowired
    private ReplyMapper replyDao;

    @Override
    public List<Reply> getReplysByComId(Integer comID) {
        ReplyExample example = new ReplyExample();
        example.setOrderByClause("reply_date desc");
        example.createCriteria().andCommentIdEqualTo(comID);
        return replyDao.selectByExampleWithUser(example);
    }

    @Override
    public boolean addReply(Reply reply) {
        //获取当前时间
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date uDate = null;
        try {
            uDate = df.parse(df.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        java.sql.Timestamp date = new java.sql.Timestamp(uDate.getTime());
        reply.setReplyDate(date);
        int count = replyDao.insertSelective(reply);
        return count==1;
    }
}
