package com.hewuzhe.model;

/**
 * Created by Administrator on 2016/3/4 0004.
 */
public class SortModel {

    private String name;   //显示的数值
    private String sortLetters;  //显示数据拼音的首字母
    private String Id;  // Id
    private String IsFriend;  // 通讯录状态
    private String Phone;  // 手机号

    public SortModel() {
    }

    public SortModel(String name, String sortLetters, String id, String isFriend, String phone) {
        this.name = name;
        this.sortLetters = sortLetters;
        Id = id;
        IsFriend = isFriend;
        Phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getIsFriend() {
        return IsFriend;
    }

    public void setIsFriend(String isFriend) {
        IsFriend = isFriend;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
