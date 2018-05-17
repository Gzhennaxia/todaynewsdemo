package com.todaynews.ssm2.asyncevent;

import com.alibaba.fastjson.JSONObject;
import com.todaynews.ssm2.utils.JedisAdapter;
import com.todaynews.ssm2.utils.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: LiBo
 * @Date: 2018/4/23下午 07:06
 */
@Service
public class EventProducer {

    @Autowired
    JedisAdapter jedisAdapter;

    private static final Logger logger = LoggerFactory.getLogger(EventProducer.class);

    //将事件提交到事件队列中
    public boolean fireEvent(EventModel eventModel) {

        try {
            //序列化(将事件模型对象转化为json数据字符串)
            String jsonString = JSONObject.toJSONString(eventModel);

            //获取事件队列的key（即list集合的表名）
            String eventQueueKey = RedisKeyUtil.getEventQueueKey();

            //放入事件队列中
            jedisAdapter.lpush(eventQueueKey, jsonString);
            return true;
        } catch (Exception e) {
            logger.error("EventProducer fireEvent 异常 ：" + e.getMessage());
            return false;
        }

    }
}
