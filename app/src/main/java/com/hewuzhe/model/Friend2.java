package com.hewuzhe.model;


import java.io.Serializable;

/**
 * Created by xianguangjin on 15/12/31.
 */
public class Friend2 implements Serializable{
    private  int Id;
    private String NicName;
    private String PhotoPath;
    private boolean IsFriend;
    private int MemnerId;
    private String JoinTime;
    private boolean IsShield;
    private int UserId;
    private String RemarkName;

    public Friend2() {
    }

    public Friend2(int id, String nicName, String photoPath, boolean isFriend, int memnerId, String joinTime, boolean isShield, int userId, String remarkName) {
        Id = id;
        NicName = nicName;
        PhotoPath = photoPath;
        IsFriend = isFriend;
        MemnerId = memnerId;
        JoinTime = joinTime;
        IsShield = isShield;
        UserId = userId;
        RemarkName = remarkName;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNicName() {
        return NicName;
    }

    public void setNicName(String nicName) {
        NicName = nicName;
    }

    public String getPhotoPath() {
        return PhotoPath;
    }

    public void setPhotoPath(String photoPath) {
        PhotoPath = photoPath;
    }

    public boolean isFriend() {
        return IsFriend;
    }

    public void setIsFriend(boolean isFriend) {
        IsFriend = isFriend;
    }

    public int getMemnerId() {
        return MemnerId;
    }

    public void setMemnerId(int memnerId) {
        MemnerId = memnerId;
    }

    public String getJoinTime() {
        return JoinTime;
    }

    public void setJoinTime(String joinTime) {
        JoinTime = joinTime;
    }

    public boolean isShield() {
        return IsShield;
    }

    public void setIsShield(boolean isShield) {
        IsShield = isShield;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getRemarkName() {
        return RemarkName;
    }

    public void setRemarkName(String remarkName) {
        RemarkName = remarkName;
    }
}
