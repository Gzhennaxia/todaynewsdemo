package com.todaynews.ssm2.asyncevent;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: LiBo
 * @Date: 2018/4/23下午 05:27
 */
public class EventModel {

    //事件类型
    private EventType eventType;

    //事件的发起者id（谁点的赞）
    private int actorId;

    //事件的接收者id（点的赞是点给谁的）
    private int ownerId;

    //事件发生在哪类实体上（新闻/评论）
    private EntityType entityType;

    //事件发生的实体id
    private int entityId;

    //额外信息
    Map<String, Object> extInf = new HashMap<String, Object>();

    public EventModel() {
    }

    public EventModel(EventType eventType, int actorId, int ownerId, EntityType entityType, int entityId) {
        this.eventType = eventType;
        this.actorId = actorId;
        this.ownerId = ownerId;
        this.entityType = entityType;
        this.entityId = entityId;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public int getActorId() {
        return actorId;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public Map<String, Object> getExtInf() {
        return extInf;
    }

    public void setExtInf(Map<String, Object> extInf) {
        this.extInf = extInf;
    }
}
