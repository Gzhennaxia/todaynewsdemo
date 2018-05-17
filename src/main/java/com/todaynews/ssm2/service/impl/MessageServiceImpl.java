package com.todaynews.ssm2.service.impl;

import com.todaynews.ssm2.bean.Message;
import com.todaynews.ssm2.dao.ConversationMapper;
import com.todaynews.ssm2.dao.MessageMapper;
import com.todaynews.ssm2.service.ConversationService;
import com.todaynews.ssm2.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description:
 * @Author: LiBo
 * @Date: 2018/4/21下午 05:21
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    ConversationMapper conversationMapper;

    @Override
    public int insertMessage(Message message) {
        return messageMapper.insertMessage(message);
    }

    @Override
    public List<Message> findAllMessageByUserIdAndConversationId(Integer userId, int conversationId) {
        return messageMapper.findAllMessageByUserIdAndConversationId(userId, conversationId);
    }

    @Transactional
    @Override
    public int updateMessageStateById(Message message, int conversationId) {
        return messageMapper.updateStateById(message);
    }


}
