package com.todaynews.ssm2.asyncevent;

/**
 * @Description:
 * @Author: LiBo
 * @Date: 2018/4/23下午 06:53
 */
public enum EntityType {

    NEWS(0),
    USER(1);

    private int value;

    EntityType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
