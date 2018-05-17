package com.todaynews.ssm2.service;

import com.todaynews.ssm2.bean.News;

import java.util.List;

/**
 * @Description:
 * @Author: LiBo
 * @Date: 2018/4/20下午 07:55
 */
public interface NewsService {
    int insertNews(News news);

    List<News> findAllNews();

    int addLikeCountByNewsId(int id);

    News findNewsById(int id);

    int addCommentCountByNewsId(Integer newsId);

    int updateLikeCountByNewsId(Integer newsId, int likeCount);
}
