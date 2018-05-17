package com.todaynews.ssm2.service;

import com.todaynews.ssm2.bean.User;
import com.todaynews.ssm2.utils.Result;

/**
 * @Description:
 * @Author: LiBo
 * @Date: 2018/4/20上午 11:32
 */
public interface UserService {
    Result registerVerify(User user);

    int insertUser(User user);

    Result loginVerify(User user);

    User findUserByUsernameAndPassword(User user);

    User findUserById(Integer uid);

    User findUserByUsername(String username);

    Result addMessage(int userFromId, int userToId, String content);
}
