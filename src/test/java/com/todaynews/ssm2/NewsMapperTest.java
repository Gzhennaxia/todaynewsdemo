package com.todaynews.ssm2;

import com.todaynews.ssm2.bean.News;
import com.todaynews.ssm2.dao.NewsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: LiBo
 * @Date: 2018/4/20下午 08:06
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Ssm2Application.class)
@WebAppConfiguration
public class NewsMapperTest {

    @Autowired
    NewsMapper newsMapper;

    @Test
    public void testInsertNews(){
        News news = new News();
        news.setCreateDate(new Date());
        news.setTitle("测试用例");
        news.setLink("www.baidu.com");

        int ret = newsMapper.insertNews(news);
        System.out.println("ret = " + ret);
    }

    @Test
    public void testFindAllNews(){
        List<News> allNews = newsMapper.findAllNews();
        System.out.println("ret = " + allNews);
    }

    @Test
    public void test(){
        int ret = newsMapper.addLikeCountByNewsId(5);
        System.out.println("ret = " + ret);
    }
}
