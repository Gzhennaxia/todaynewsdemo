package com.todaynews.ssm2.asyncevent;

import com.alibaba.fastjson.JSON;
import com.todaynews.ssm2.utils.JedisAdapter;
import com.todaynews.ssm2.utils.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: LiBo
 * @Date: 2018/4/23下午 07:32
 */
@Service
public class EventConsumer implements InitializingBean, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);

    @Autowired
    JedisAdapter jedisAdapter;

    private ApplicationContext applicationContext;

    //事件处理器注册表（存储每种事件对应的所有事件处理器）
    private Map<EventType, List<EventHandler>> registerTable = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {

        //获取所有的事件处理器（事件处理器都实现了EventHandler这个接口）
        Map<String, EventHandler> eventHandlerMap = applicationContext.getBeansOfType(EventHandler.class);

        //构造事件处理器注册表
        if (eventHandlerMap != null){
            for (Map.Entry<String, EventHandler> entry : eventHandlerMap.entrySet()){

                //遍历每个EventHandler，将这个EventHandler放到注册表对应事件的事件处理器列表中
                EventHandler eventHandler = entry.getValue();

                //获取每个EventHandler所关注的事件类型
                List<EventType> eventTypes = eventHandler.getCareEventType();

                for (EventType eventType : eventTypes){
                    //初始化的时候是一个空的注册表（没有事件类型，也没有对应的事件处理器列表）
                    //所以先根据事件类型创建出空的事件处理器列表，在将对应的事件处理器放进去
                    if (!registerTable.containsKey(eventType)){
                        registerTable.put(eventType, new ArrayList<EventHandler>());
                    }
                    registerTable.get(eventType).add(eventHandler);
                }
            }
        }

        //启动线程从工作队列中取出事件进行处理
        new Thread(new Runnable() {
            @Override
            public void run() {

                //
                while (true){

                    //获取事件队列的key（即list集合的表名）
                    String eventQueueKey = RedisKeyUtil.getEventQueueKey();

                    //获取存储的(经过序列化的)事件模型
                    //假如在指定时间内没有任何元素被弹出，则返回一个 nil 和等待时长。
                    //反之，返回一个含有两个元素的列表，第一个元素是被弹出元素所属的 key ，第二个元素是被弹出元素的值。
                    List<String> brpop = jedisAdapter.brpop(0, eventQueueKey);

                    //用for循环的形式是为了避免空指针(队列中没有事件)异常
                    for (String jsonString : brpop) {
                        if (jsonString.equals(eventQueueKey)) {
                            continue;
                        }
                        EventModel eventModel = JSON.parseObject(jsonString, EventModel.class);

                        if (!registerTable.containsKey(eventModel.getEventType())) {
                            logger.error("不能识别的事件！");
                            continue;
                        }
                        //获取关注过该事件eventModel的handler,进行处理
                        for (EventHandler handler : registerTable.get(eventModel.getEventType())) {
                            handler.doHandler(eventModel);
                        }
                    }
                }
            }
        }).start();



    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
