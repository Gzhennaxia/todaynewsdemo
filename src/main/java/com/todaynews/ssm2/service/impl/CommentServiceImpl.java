package com.todaynews.ssm2.service.impl;

import com.todaynews.ssm2.bean.Comment;
import com.todaynews.ssm2.dao.CommentMapper;
import com.todaynews.ssm2.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: LiBo
 * @Date: 2018/4/21上午 12:41
 */
@Service
public class CommentServiceImpl implements CommentService {


    @Autowired
    CommentMapper commentMapper;

    @Override
    public int insertComment(Comment comment) {
        return commentMapper.insertComment(comment);
    }
}
