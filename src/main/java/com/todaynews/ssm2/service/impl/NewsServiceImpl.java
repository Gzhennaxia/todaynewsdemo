package com.todaynews.ssm2.service.impl;

import com.todaynews.ssm2.bean.News;
import com.todaynews.ssm2.dao.NewsMapper;
import com.todaynews.ssm2.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: LiBo
 * @Date: 2018/4/20下午 07:55
 */
@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    NewsMapper newsMapper;

    @Override
    public int insertNews(News news) {
        return newsMapper.insertNews(news);
    }

    @Override
    public List<News> findAllNews() {
        return newsMapper.findAllNews();
    }

    @Override
    public int addLikeCountByNewsId(int id) {
        return newsMapper.addLikeCountByNewsId(id);
    }

    @Override
    public News findNewsById(int id) {
        return newsMapper.findNewsById(id);
    }

    @Override
    public int addCommentCountByNewsId(Integer id) {
        return newsMapper.addCommentCountById(id);
    }

    @Override
    public int updateLikeCountByNewsId(Integer newsId, int likeCount) {
        return newsMapper.updateLikeCountByNewsId(newsId, likeCount);
    }
}
