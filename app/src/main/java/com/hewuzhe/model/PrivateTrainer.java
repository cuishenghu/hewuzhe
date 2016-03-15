package com.hewuzhe.model;

/**
 * Created by Administrator on 2016/3/14 0014.
 */
public class PrivateTrainer {

    private String imagePath;//,报名/关注/粉丝的头像,视频列表图片
    private String username;//视频列表里的视频名称,报名/关注/粉丝的姓名
    private String avatar;//视频列表里的头像
    private String nickname;//视频列表里的昵称
    private int commentNum;//视频列表里的评论数
    private String publishDate;//视频列表里的发布时间

    public PrivateTrainer(String nickname, String publishDate, int commentNum, String avatar, String username, String imagePath) {
        this.publishDate = publishDate;
        this.commentNum = commentNum;
        this.avatar = avatar;
        this.username = username;
        this.imagePath = imagePath;
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
