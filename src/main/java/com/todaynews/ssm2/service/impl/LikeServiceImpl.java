package com.todaynews.ssm2.service.impl;

import com.todaynews.ssm2.asyncevent.EntityType;
import com.todaynews.ssm2.asyncevent.EventModel;
import com.todaynews.ssm2.asyncevent.EventProducer;
import com.todaynews.ssm2.asyncevent.EventType;
import com.todaynews.ssm2.dao.NewsMapper;
import com.todaynews.ssm2.service.LikeService;
import com.todaynews.ssm2.utils.JedisAdapter;
import com.todaynews.ssm2.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: LiBo
 * @Date: 2018/4/23上午 12:47
 */
@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    JedisAdapter jedisAdapter;

    @Autowired
    NewsMapper newsMapper;

    @Autowired
    EventProducer eventProducer;

    //判断指定用户对该新闻的喜欢状态（1代表点赞，0代表没点赞也没点踩，-1代表点踩）
    @Override
    public int getLikeStatus(int userId, int entityType, int entityId) {

        //生成指定新闻对应的likeKey和disLikeKey（相当于关系数据库中指定新闻的点赞表和点踩表的表名）
        String likeKey = RedisKeyUtil.getLikeKey(entityId, entityType);
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityId, entityType);

        //判断值为userId 的用户是否在key为listKey 的集合中
        if(jedisAdapter.sismember(likeKey, String.valueOf(userId))){
            return 1;
        }
        return jedisAdapter.sismember(disLikeKey, String.valueOf(userId)) ? -1: 0;
    }

    //点赞
    public int like(int userId, int entityType, int entityId){

        //获取key:   LIKE:ENTITY_NEWS:2
        String likeKey = RedisKeyUtil.getLikeKey(entityId, entityType);

        //在点赞集合中添加当前操作用户的userId(即当前用户点赞后，被点赞新闻的like集合中就会加上一个点赞用户的id)
        jedisAdapter.sadd(likeKey, String.valueOf(userId));

        //从点踩集合中删除该用户id
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityId, entityType);
        jedisAdapter.srem(disLikeKey, String.valueOf(userId));

    //////////////////////////////基于redis消息队列实现异步操作/////////////////////////////

        //构造事件模型
        int ownerId = newsMapper.findOwnerIdByNewsId(entityId);
        EventModel eventModel = new EventModel(EventType.LIKE, userId, ownerId, EntityType.NEWS, entityId);

        //将事件汇报给事件处理器,由事件处理器提交到事件队列中
        boolean ret = eventProducer.fireEvent(eventModel);

    //////////////////////////////基于redis消息队列实现异步操作/////////////////////////////

        //返回点赞数量
        return (int) jedisAdapter.scard(likeKey);
    }

    //点踩
    public int disLike(int userId, int entityType, int entityId){

        //获取key:   DISLIKE:ENTITY_NEWS:2
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityId, entityType);

        //在点踩集合中添加当前操作用户的userId(即当前用户点踩后，被点踩新闻的disLike集合中就会加上一个点踩用户的id)
        jedisAdapter.sadd(disLikeKey, String.valueOf(userId));

        //从点赞集合中删除该用户id
        String likeKey = RedisKeyUtil.getLikeKey(entityId, entityType);
        jedisAdapter.srem(likeKey, String.valueOf(userId));

        //返回点赞数量
        return (int) jedisAdapter.scard(likeKey);
    }
}
