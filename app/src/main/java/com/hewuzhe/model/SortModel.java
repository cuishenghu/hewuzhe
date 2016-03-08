package com.hewuzhe.model;

/**
 * Created by Administrator on 2016/3/4 0004.
 */
public class SortModel {

    private String name;   //显示的数值
    private String sortLetters;  //显示数据拼音的首字母
    private String id;  // Id
    private String state;  // 通讯录状态
    private String phone;  // 手机号

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
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
