package com.todaynews.ssm2.asyncevent;

/**
 * @Description:
 * @Author: LiBo
 * @Date: 2018/4/23下午 05:31
 */
public enum EventType {

    LIKE(0),
    COMMENT(1);

    private int value;

    EventType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
