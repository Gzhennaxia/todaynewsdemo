package com.todaynews.ssm2;

import com.todaynews.ssm2.bean.User;
import com.todaynews.ssm2.dao.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @Description:
 * @Author: LiBo
 * @Date: 2018/4/20下午 01:02
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Ssm2Application.class)
@WebAppConfiguration
public class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void testInsertUser(){
        User user = new User();
        user.setUsername("zhen");
        user.setPassword("666666");
        user.setHeadUrl("aaaaaaaaa");
        int ret = userMapper.insertUser(user);

        System.out.println("ret = " + ret);
    }

    @Test
    public void test(){
        int ret = userMapper.isExistUsername("gzhennaxia@163.com");
        System.out.println("ret = " + ret);
    }
}
