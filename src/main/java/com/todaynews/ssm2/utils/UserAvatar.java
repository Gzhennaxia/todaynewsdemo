package com.todaynews.ssm2.utils;

import java.util.Random;

/**
 * @Description:
 * @Author: LiBo
 * @Date: 2018/4/23下午 09:46
 */
public class UserAvatar {

    //随机指定一个用户头像
    public static String randAvatar() {

        int i = 1 + (int) (Math.random() * 7);
        String headUrl = "/images/avatar/avatar0" + i + ".jpg";

        return headUrl;
    }
}
