package com.todaynews.ssm2.controller;

import com.todaynews.ssm2.bean.News;
import com.todaynews.ssm2.bean.User;
import com.todaynews.ssm2.service.NewsService;
import com.todaynews.ssm2.service.UserService;
import com.todaynews.ssm2.utils.Result;
import com.todaynews.ssm2.utils.UserAvatar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @Description:
 * @Author: LiBo
 * @Date: 2018/4/20上午 09:34
 */
@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    NewsService newsService;


    //注册
    @RequestMapping("/reg")
    @ResponseBody
    public Result register(User user, HttpSession session) {

        Result result = userService.registerVerify(user);

        //信息有效则进行注册
        if (result.getCode() == 0) {
            user.setHeadUrl(UserAvatar.randAvatar());
            int ret = userService.insertUser(user);
            if (ret == 1) {
                user = userService.findUserByUsernameAndPassword(user);
                session.setAttribute("user", user);
            } else {
                result.setCode(-1);
            }
        }

        return result;
    }

    //登陆
    @RequestMapping("/login")
    @ResponseBody
    public Result login(User user, HttpSession session) {

        Result result = userService.loginVerify(user);
        user = userService.findUserByUsernameAndPassword(user);
        session.setAttribute("user", user);

        return result;
    }

    //个人中心
    @RequestMapping("/user/{id}")
    public String personalCenter(@PathVariable("id") int id, Model model) {

        User user1 = userService.findUserById(id);
        model.addAttribute("user1", user1);

        return "personal";
    }

    //分享
    @RequestMapping(path = "/user/addNews", method = RequestMethod.POST)
    @ResponseBody
    public Result addNews(News news, HttpSession session) {

        Result result = new Result();

        //提供者
        User user = (User) session.getAttribute("user");
        news.setCreateDate(new Date());
        news.setUid(user.getId());

        //插入该新闻
        int ret = newsService.insertNews(news);
        if (ret == 1) {
            result.setCode(0);
        }else {
            result.setCode(-1);
            result.setMsg("分享失败！");
        }

        return result;
    }

    //注销
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        //return "/home?pop=0";
        return "redirect:/home";
    }
}
