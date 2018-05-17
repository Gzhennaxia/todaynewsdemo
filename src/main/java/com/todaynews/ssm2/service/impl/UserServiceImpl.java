package com.todaynews.ssm2.service.impl;

import com.todaynews.ssm2.bean.Conversation;
import com.todaynews.ssm2.bean.Message;
import com.todaynews.ssm2.bean.User;
import com.todaynews.ssm2.dao.ConversationMapper;
import com.todaynews.ssm2.dao.MessageMapper;
import com.todaynews.ssm2.dao.UserMapper;
import com.todaynews.ssm2.service.UserService;
import com.todaynews.ssm2.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Description:
 * @Author: LiBo
 * @Date: 2018/4/20上午 11:33
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    
    @Autowired
    MessageMapper messageMapper;
    
    @Autowired
    ConversationMapper conversationMapper;

    @Override
    public Result registerVerify(User user) {

        Result result = new Result();

        int isExist = userMapper.isExistUsername(user.getUsername());

        result.setCode(isExist);
        if (isExist > 0){
            result.setMsgname("该邮箱已注册！");
        }

        return result;
    }

    @Override
    public int insertUser(User user) {
        return userMapper.insertUser(user);
    }

    @Override
    public Result loginVerify(User user) {

        Result result = new Result();

        int isExistUsername = userMapper.isExistUsername(user.getUsername());
        User user1 = userMapper.findUserByUsernameAndPassword(user);
        if (isExistUsername > 0){
            if (user1 == null){
                result.setCode(-1);
                result.setMsgpwd("用户名或密码错误!");
            }else {
                result.setCode(0);
            }
        }else {
            result.setCode(-1);
            result.setMsgname("用户名不存在！");
        }

        return result;
    }

    @Override
    public User findUserByUsernameAndPassword(User user) {
        return userMapper.findUserByUsernameAndPassword(user);
    }

    @Override
    public User findUserById(Integer id) {
        return userMapper.findUserById(id);
    }

    @Override
    public User findUserByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }

    @Transactional
    @Override
    public Result addMessage(int userFromId, int userToId, String content) {

        Result result = new Result();
        
    ///////////////////处理私信相关业务/////////////////////////

        //发送者所拥有的记录
        Message senderMessage = new Message();
        senderMessage.setUserId(userFromId);
        senderMessage.setFriendId(userToId);
        senderMessage.setFromId(userFromId);
        senderMessage.setToId(userToId);


        //接收者所拥有的记录
        Message receiverMessage = new Message();
        receiverMessage.setUserId(userToId);
        receiverMessage.setFriendId(userFromId);
        receiverMessage.setFromId(userFromId);
        receiverMessage.setToId(userToId);

        //发送内容
        senderMessage.setContent(content);
        receiverMessage.setContent(content);

        //发送时间
        Date createdDate = new Date();
        senderMessage.setCreatedDate(createdDate);
        receiverMessage.setCreatedDate(createdDate);

        //私信状态:1/2/3---未读/已读/删除
        senderMessage.setState(2);
        receiverMessage.setState(1);
        
    ///////////////////处理会话相关业务/////////////////////////
        
        //发送者所拥有的会话
        Conversation conversationFrom = 
                conversationMapper.findConversationByUserIdAndFriendId(userFromId, userToId);
        int retConversationFrom;
        if (conversationFrom == null || conversationFrom.getState() == 1){
            //首次会话或者删除上次会话后的新一轮会话

            conversationFrom = new Conversation();

            conversationFrom.setUserId(userFromId);
            conversationFrom.setFriendId(userToId);

            conversationFrom.setEarliestMessageDate(createdDate);
            conversationFrom.setLatestMessageContent(content);
            conversationFrom.setMessageCount(1);
            conversationFrom.setUnreadCount(0);
            conversationFrom.setState(0);
            retConversationFrom = conversationMapper.insertConversation(conversationFrom);

            //获取新创建的会话
            conversationFrom = conversationMapper.findConversationByUserIdAndFriendId(userFromId, userToId);
        }/*else if (conversationFrom.getState() == 1){
            //删除上次会话后的新一轮会话

            conversationFrom.setState(0);
            conversationFrom.setUnreadCount(1);
            conversationFrom.setMessageCount(1);
            conversationFrom.setLatestMessageContent(content);
            conversationFrom.setEarliestMessageDate(createdDate);
        }*/else {
            //更新已有会话的相关信息
            conversationFrom.setLatestMessageContent(content);
            //conversationFrom.setUnreadCount(conversationFrom.getUnreadCount() + 1);
            conversationFrom.setMessageCount(conversationFrom.getMessageCount() + 1);
            retConversationFrom = conversationMapper.updateByPrimaryKeySelective(conversationFrom);
        }
        
        
        //接收者所拥有的会话
        Conversation conversationTo = 
                conversationMapper.findConversationByUserIdAndFriendId(userToId, userFromId);
        int retConversationTo;
        if (conversationTo == null || conversationTo.getState() == 1){
            //首次会话或者删除上次会话后的新一轮会话

            conversationTo = new Conversation();

            conversationTo.setUserId(userToId);
            conversationTo.setFriendId(userFromId);

            conversationTo.setEarliestMessageDate(createdDate);
            conversationTo.setLatestMessageContent(content);
            conversationTo.setMessageCount(1);
            conversationTo.setUnreadCount(1);
            conversationTo.setState(0);
            retConversationTo = conversationMapper.insertConversation(conversationTo);

            //获取新创建的会话
            conversationTo = conversationMapper.findConversationByUserIdAndFriendId(userToId, userFromId);
        }/*else if (conversationFrom.getState() == 1){
            //删除上次会话后的新一轮会话

            conversationFrom.setState(0);
            conversationFrom.setUnreadCount(1);
            conversationFrom.setMessageCount(1);
            conversationFrom.setLatestMessageContent(content);
            conversationFrom.setEarliestMessageDate(createdDate);
        }*/else {
            //更新已有会话的相关信息
            conversationTo.setLatestMessageContent(content);
            conversationTo.setUnreadCount(conversationTo.getUnreadCount() + 1);
            conversationTo.setMessageCount(conversationTo.getMessageCount() + 1);
            retConversationTo = conversationMapper.updateByPrimaryKeySelective(conversationTo);
        }

        //将私信存入数据库
        senderMessage.setConversationId(conversationFrom.getId());
        int retMessageFrom = messageMapper.insertMessage(senderMessage);

        receiverMessage.setConversationId(conversationTo.getId());
        int retMessageTo = messageMapper.insertMessage(receiverMessage);

        if (retMessageFrom == 1 && retMessageTo == 1
                && retConversationFrom == 1 && retConversationTo == 1) {
            result.setCode(0);
        } else {
            result.setCode(1);
            result.setMsg("插入失败！");
        }

        return result;

    }
}
