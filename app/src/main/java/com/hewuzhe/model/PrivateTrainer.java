package com.hewuzhe.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/14 0014.
 */
public class PrivateTrainer implements Serializable {
    private int Id;
    private String PhotoPath;//教练的头像
    private String Phone;//教练的联系电话
    private String NicName;//教练的昵称
    private int Distance;//教练的距离
    private String HomeAddress;//地址
    private String Speciality;//特长
    private boolean IsGuanzhu;//是否关注
//    private String imagePath;//,报名/关注/粉丝的头像,视频列表图片
//    private String username;//视频列表里的视频名称,报名/关注/粉丝的姓名
//    private String avatar;//视频列表里的头像
//    private String nickname;//视频列表里的昵称
//    private int commentNum;//视频列表里的评论数
//    private String publishDate;//视频列表里的发布时间


    public PrivateTrainer() {
    }

    public PrivateTrainer(boolean isGuanzhu,int id, String photoPath, String phone, String nicName, int distance,String homeAddress,String speciality) {
        Id = id;
        PhotoPath = photoPath;
        Phone = phone;
        NicName = nicName;
        Distance = distance;
        Speciality=speciality;
        HomeAddress=homeAddress;
        IsGuanzhu=isGuanzhu;
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

    public String getHomeAddress() {
        return HomeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        HomeAddress = homeAddress;
    }

    public boolean isGuanzhu() {
        return IsGuanzhu;
    }

    public void setIsGuanzhu(boolean isGuanzhu) {
        IsGuanzhu = isGuanzhu;
    }

    public String getSpeciality() {
        return Speciality;
    }

    public void setSpeciality(String speciality) {
        Speciality = speciality;
    }
}
