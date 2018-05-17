package com.todaynews.ssm2.vo;

import com.todaynews.ssm2.bean.News;
import com.todaynews.ssm2.bean.User;

/**
 * @Description:
 * @Author: LiBo
 * @Date: 2018/4/20上午 10:43
 */
public class Vo {

    News news;
    User user;
    int like;

    public Vo() {
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }
}
