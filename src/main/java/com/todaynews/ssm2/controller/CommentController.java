package com.todaynews.ssm2.controller;

import com.todaynews.ssm2.bean.Comment;
import com.todaynews.ssm2.bean.User;
import com.todaynews.ssm2.service.CommentService;
import com.todaynews.ssm2.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @Description:
 * @Author: LiBo
 * @Date: 2018/4/21上午 12:42
 */
@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    NewsService newsService;

    //添加评论
    @RequestMapping(path = "/addComment", method = RequestMethod.POST)
    public String addComment(Comment comment, HttpSession session){

        //评论者
        User user = (User) session.getAttribute("user");

        comment.setCreatedDate(new Date());
        comment.setUid(user.getId());

        //将评论插入数据库
        int ret = commentService.insertComment(comment);
        if (ret == 1){
            int ret1 = newsService.addCommentCountByNewsId(comment.getNewsId());
        }

        return "/news/" + comment.getNewsId();
    }
}
