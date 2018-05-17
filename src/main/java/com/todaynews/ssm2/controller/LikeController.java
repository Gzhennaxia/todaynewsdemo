package com.todaynews.ssm2.controller;

import com.todaynews.ssm2.asyncevent.EntityType;
import com.todaynews.ssm2.bean.User;
import com.todaynews.ssm2.service.LikeService;
import com.todaynews.ssm2.service.NewsService;
import com.todaynews.ssm2.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpSession;

/**
 * @Description:
 * @Author: LiBo
 * @Date: 2018/4/23上午 01:17
 */
@Controller
public class LikeController {

    @Autowired
    LikeService likeService;

    @Autowired
    NewsService newsService;

    //private static final String ENTITY_TYPE = "ENTITY_NEWS";

    //点赞
    @RequestMapping(path = "/like", method = RequestMethod.POST)
    @ResponseBody
    public Result like(Integer newsId, HttpSession session) {

        Result result = new Result();

        //当前用户
        User user = (User) session.getAttribute("user");

        //如果还没登陆
        if (user == null) {
            result.setCode(-1);
            return result;
        }

        int likeStatus = likeService.getLikeStatus(user.getId(), EntityType.NEWS.getValue(), newsId);

        if (likeStatus == 1) {
            //已经点过赞了！
            result.setCode(-1);
        } else {
            int likeCount = likeService.like(user.getId(), EntityType.NEWS.getValue(), newsId);

            //更新点赞数
            result.setCode(0);
            result.setMsg(String.valueOf(likeCount));

            //更新MySQL数据库
            int ret = newsService.updateLikeCountByNewsId(newsId, likeCount);
        }

        /*int ret = newsService.addLikeCountByNewsId(newsId);
        if (ret == 1) {
            result.setCode(0);
        } else {
            result.setCode(-1);
            result.setMsg("点赞失败！");
        }*/

        return result;
    }


    //点踩
    @RequestMapping(path = "/dislike", method = RequestMethod.POST)
    @ResponseBody
    public Result disLike(Integer newsId, HttpSession session) {

        Result result = new Result();

        //当前用户
        User user = (User) session.getAttribute("user");

        //如果还没登陆
        if (user == null) {
            result.setCode(-1);
            return result;
        }

        int likeStatus = likeService.getLikeStatus(user.getId(), EntityType.NEWS.getValue(), newsId);

        if (likeStatus == -1) {
            //已经点过踩了！
            result.setCode(-1);
        } else {
            int likeCount = likeService.disLike(user.getId(), EntityType.NEWS.getValue(), newsId);

            //更新点赞数
            result.setCode(0);
            result.setMsg(String.valueOf(likeCount));

            //更新MySQL数据库
            int ret = newsService.updateLikeCountByNewsId(newsId, likeCount);
        }

        /*int ret = newsService.addLikeCountByNewsId(newsId);
        if (ret == 1) {
            result.setCode(0);
        } else {
            result.setCode(-1);
            result.setMsg("点赞失败！");
        }*/

        return result;
    }

}
