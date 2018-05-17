package com.todaynews.ssm2.service;

/**
 * @Description:
 * @Author: LiBo
 * @Date: 2018/4/23上午 12:30
 */
public interface LikeService {

    //判断指定用户对该新闻的喜欢状态（1代表点赞，0代表没点赞也没点踩，-1代表点踩）
    public int getLikeStatus(int userId, int entityType, int entityId);

    //点赞
    public int like(int userId, int entityType, int entityId);

    //点踩
    public int disLike(int userId, int entityType, int entityId);

}
