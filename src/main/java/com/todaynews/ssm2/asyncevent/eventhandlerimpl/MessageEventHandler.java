package com.todaynews.ssm2.asyncevent.eventhandlerimpl;

import com.todaynews.ssm2.asyncevent.EventHandler;
import com.todaynews.ssm2.asyncevent.EventModel;
import com.todaynews.ssm2.asyncevent.EventType;
import com.todaynews.ssm2.bean.News;
import com.todaynews.ssm2.bean.User;
import com.todaynews.ssm2.service.NewsService;
import com.todaynews.ssm2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: LiBo
 * @Date: 2018/4/23下午 08:53
 */
@Component
public class MessageEventHandler implements EventHandler {

    @Autowired
    UserService userService;

    @Autowired
    NewsService newsService;

    //关注的事件类型
    List<EventType> careEventTypes;

    //初始化careEventTypes
    public MessageEventHandler() {
        careEventTypes = new ArrayList<>();
        careEventTypes.add(EventType.LIKE);
    }

    @Override
    public void registerCareEventType(EventType eventType) {
        careEventTypes.add(eventType);
    }

    @Override
    public List<EventType> getCareEventType() {
        return careEventTypes;
    }

    @Override
    public void doHandler(EventModel eventModel) {

        User actor = userService.findUserById(eventModel.getActorId());
        News news = newsService.findNewsById(eventModel.getEntityId());

        //系统（id为0）给被点赞人的人发一个栈内信，告诉他他的哪个文章被谁点赞了！
        String content = "您好，" +
                actor.getUsername() +
                "给您分享的新闻《" +
                "<a href=" + "/news/"+ news.getId() + ">"+ news.getTitle() +"</a>" +
                "》点了一个赞";
        userService.addMessage(0, eventModel.getOwnerId(), content);

    }
}
