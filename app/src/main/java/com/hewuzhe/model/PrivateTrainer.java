package com.hewuzhe.model;

/**
 * Created by Administrator on 2016/3/14 0014.
 */
public class PrivateTrainer {

    private String avatar;
    private String username;

    public PrivateTrainer(String avatar, String username) {
        this.avatar = avatar;
        this.username = username;
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
}
