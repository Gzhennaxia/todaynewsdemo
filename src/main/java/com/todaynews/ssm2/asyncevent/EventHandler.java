package com.todaynews.ssm2.asyncevent;

import java.util.List;

/**
 * @Description:
 * @Author: LiBo
 * @Date: 2018/4/23下午 07:41
 */
public interface EventHandler {

    //注册关注的事件
    void registerCareEventType(EventType eventType);

    //获取关注事件的类型
    List<EventType> getCareEventType();

    //处理事件
    void doHandler(EventModel eventModel);
}
