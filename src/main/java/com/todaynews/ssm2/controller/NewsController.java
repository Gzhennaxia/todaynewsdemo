package com.todaynews.ssm2.controller;

import com.todaynews.ssm2.bean.Comment;
import com.todaynews.ssm2.bean.News;
import com.todaynews.ssm2.bean.User;
import com.todaynews.ssm2.service.NewsService;
import com.todaynews.ssm2.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @Description:
 * @Author: LiBo
 * @Date: 2018/4/20下午 10:39
 */
@Controller
public class NewsController {

    @Autowired
    NewsService newsService;

    //新闻详情页
    @RequestMapping("/news/{id}")
    public String newsDetail(@PathVariable("id")int id, Model model, HttpSession session){

        News news = newsService.findNewsById(id);
        model.addAttribute("news", news);

        User user = (User) session.getAttribute("user");
        if (user != null){
            model.addAttribute("user", user);
        }

        return "/detail";
    }

}
