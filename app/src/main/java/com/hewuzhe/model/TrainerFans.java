package com.hewuzhe.model;

/**
 * Created by Administrator on 2016/3/14 0014.
 */
public class TrainerFans {
private int Id;
    private String PhotoPath;//教练的头像
    private String Phone;//教练的联系电话
    private String NicName;//教练的昵称
    private int Distance;//教练的距离
//    private String imagePath;//,报名/关注/粉丝的头像,视频列表图片
//    private String username;//视频列表里的视频名称,报名/关注/粉丝的姓名
//    private String avatar;//视频列表里的头像
//    private String nickname;//视频列表里的昵称
//    private int commentNum;//视频列表里的评论数
//    private String publishDate;//视频列表里的发布时间

    public TrainerFans(int id, String photoPath, String phone, String nicName, int distance) {
        Id = id;
        PhotoPath = photoPath;
        Phone = phone;
        NicName = nicName;
        Distance = distance;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getPhotoPath() {
        return PhotoPath;
    }

    public void setPhotoPath(String photoPath) {
        PhotoPath = photoPath;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getNicName() {
        return NicName;
    }

    public void setNicName(String nicName) {
        NicName = nicName;
    }

    public int getDistance() {
        return Distance;
    }

    public void setDistance(int distance) {
        Distance = distance;
    }
}
