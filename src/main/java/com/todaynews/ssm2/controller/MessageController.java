package com.todaynews.ssm2.controller;

import com.todaynews.ssm2.bean.Conversation;
import com.todaynews.ssm2.bean.Message;
import com.todaynews.ssm2.bean.User;
import com.todaynews.ssm2.service.ConversationService;
import com.todaynews.ssm2.service.MessageService;
import com.todaynews.ssm2.service.UserService;
import com.todaynews.ssm2.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: LiBo
 * @Date: 2018/4/21下午 05:22
 */
@Controller
public class MessageController {

    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    @Autowired
    ConversationService conversationService;

    //跳转到发私信的页面
    @RequestMapping("/user/tosendmsg")
    public String toSendMsg() {
        return "/sendmsg";
    }

    //发私信
    @RequestMapping(path = "/user/msg/addMessage", method = RequestMethod.POST)
    @ResponseBody
    public Result addMessage(String toName, String content, HttpSession session) {

        //发送者
        User userFrom = (User) session.getAttribute("user");
        //接收者
        User userTo = userService.findUserByUsername(toName);

        return userService.addMessage(userFrom.getId(), userTo.getId(), content);

    }

    //站内信页面
    @RequestMapping("/msg/list")
    public String toMessageList(Model model, HttpSession session){

        User user = (User) session.getAttribute("user");

        //查找该用户的所有有效会话
        List<Conversation> conversations = conversationService.findAllValidConversationByUserId(user.getId());
        if (conversations != null){
            model.addAttribute("conversations", conversations);
        }

        return "/letter";
    }

    //会话详情
    @RequestMapping("/msg/detail")
    public String toDetail(int conversationId, Model model, HttpSession session){

        User user = (User) session.getAttribute("user");

        //查找会话包含的所有私信
        List<Message> messages =
                messageService.findAllMessageByUserIdAndConversationId(user.getId(), conversationId);

        //将该会话所有的私信都改为已读，并把该会话的未读个数改为0
        for (Message message : messages) {
            if (message.getState() == 1){
                message.setState(2);
                int ret = messageService.updateMessageStateById(message, conversationId);
            }
        }
        conversationService.updateUnreadCountById(conversationId);


        model.addAttribute("messages", messages);

        return "letterDetail";
    }

}
