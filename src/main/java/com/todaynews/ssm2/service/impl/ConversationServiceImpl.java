package com.todaynews.ssm2.service.impl;

import com.todaynews.ssm2.bean.Conversation;
import com.todaynews.ssm2.dao.ConversationMapper;
import com.todaynews.ssm2.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description:
 * @Author: LiBo
 * @Date: 2018/4/21下午 09:57
 */
@Service
public class ConversationServiceImpl implements ConversationService {

    @Autowired
    ConversationMapper conversationMapper;

    @Override
    public List<Conversation> findAllValidConversationByUserId(Integer userId) {
        return conversationMapper.findAllValidConversationByUserId(userId);
    }

    @Transactional
    @Override
    public int updateUnreadCountById(int conversationId) {
        return conversationMapper.updateUnreadCountById(conversationId);
    }
}
