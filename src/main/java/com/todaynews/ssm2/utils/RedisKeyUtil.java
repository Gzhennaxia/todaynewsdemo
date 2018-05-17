package com.todaynews.ssm2.utils;

/**
 * @Description:
 * @Author: LiBo
 * @Date: 2018/4/22下午 11:15
 */
public class RedisKeyUtil {

    private static String SPLIT = ":";
    private static String BIZ_LIKE = "LIKE";
    private static String BIZ_DISLIKE = "DISLIKE";
    private static String EVENT_QUEUE_KEY = "ASYNC_EVENT_KEY";

    //获取likeKey:如在newsId为2上的咨询点赞后会产生key: LIKE:ENTITY_NEWS:2(即LIKE:entityType:entityId)
    public static String getLikeKey(int entityId, int entityType) {
        return BIZ_LIKE +
                SPLIT + String.valueOf(entityType) +
                SPLIT + String.valueOf(entityId);
    }

    //获取disLikeKey:如在newsId为2上的资讯取消点赞后会产生key: DISLIKE:ENTITY_NEWS:2(即DISLIKE:entityType:entityId)
    public static String getDisLikeKey(int entityId, int entityType) {
        return BIZ_DISLIKE +
                SPLIT + String.valueOf(entityType) +
                SPLIT + String.valueOf(entityId);
    }

    //获取事件队列的key（即list集合的表名）
    public static String getEventQueueKey() {
        return EVENT_QUEUE_KEY;
    }
}
