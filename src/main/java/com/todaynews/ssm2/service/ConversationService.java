package com.todaynews.ssm2.service;

import com.todaynews.ssm2.bean.Conversation;

import java.util.List;

/**
 * @Description:
 * @Author: LiBo
 * @Date: 2018/4/21下午 09:56
 */
public interface ConversationService {
    List<Conversation> findAllValidConversationByUserId(Integer userId);

    int updateUnreadCountById(int conversationId);
}
