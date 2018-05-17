package com.todaynews.ssm2.controller;

import com.todaynews.ssm2.bean.News;
import com.todaynews.ssm2.bean.User;
import com.todaynews.ssm2.service.NewsService;
import com.todaynews.ssm2.service.UserService;
import com.todaynews.ssm2.vo.Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: LiBo
 * @Date: 2018/4/19下午 10:45
 */
@Controller
public class HomeController {

    @Autowired
    NewsService newsService;

    @Autowired
    UserService userService;

    //主页
    @RequestMapping("/home")
    public String test(Integer pop, Model model, HttpSession session){

        //主页vos
        List<Vo> vos = new ArrayList<Vo>();


        //查找news
        List<News> newsList = newsService.findAllNews();

        //填充到Vo中
        for (News news : newsList) {
            Vo vo = new Vo();
            vo.setNews(news);
           /* User user = userService.findUserById(news.getUid());
            vo.setUser(user);*/
            vo.setLike(news.getLikeCount());
            vos.add(vo);
        }

        //测试用例
        /*News testNews = new News();
        testNews.setCreateDate(new Date());
        testNews.setTitle("测试用例");
        testNews.setLikeCount(12);
        testNews.setLink("www.baidu.com");
        testNews.setCommentCount(312);

        User testUser = new User();
        testUser.setUsername("zhen");

        Vo testVo = new Vo();
        testVo.setNews(testNews);
        testVo.setUser(testUser);

        vos.add(testVo);*/


        model.addAttribute("vos", vos);

        //登陆按钮
        if (pop == null){
            model.addAttribute("pop", 0);
        }else {
            //从新闻详情页的登录后才能评论按钮传过来的。
            model.addAttribute("pop", pop);
        }
        User user = (User) session.getAttribute("user");
        if (user != null){
            model.addAttribute("user", user);
            model.addAttribute("pop", 0);
        }

        model.addAttribute("date", new Date());

        return "/home";
    }

}
