package com.todaynews.ssm2.service;

import com.todaynews.ssm2.bean.Message;

import java.util.List;

/**
 * @Description:
 * @Author: LiBo
 * @Date: 2018/4/21下午 05:21
 */
public interface MessageService {
    int insertMessage(Message message);

    List<Message> findAllMessageByUserIdAndConversationId(Integer userId, int conversationId);

    int updateMessageStateById(Message message, int conversationId);
}
