package com.hewuzhe.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.hewuzhe.model.User;
import com.socks.library.KLog;

/**
 * Created by xianguangjin on 15/7/1.
 */
public class SessionUtil {

    private final Context context;

    public SessionUtil(Context context) {
        this.context = context;

    }

    public String getUserInfoStr() {
        SharedPreferences share = context.getSharedPreferences("user", 0);
        String userInfo = share.getString("userInfo", "");
        KLog.json("getUserInfoStr", userInfo);
        return userInfo;

    }


    public void putUserInfoStr(String userInfoStr) {
        SharedPreferences share = context.getSharedPreferences("user", 0);
        SharedPreferences.Editor editor = share.edit();
        editor.putString("userInfo", userInfoStr);
        KLog.json("putUserInfoStr", userInfoStr);
        editor.commit();
    }

    public void putUser(User user) {
        SharedPreferences share = context.getSharedPreferences("user", 0);
        SharedPreferences.Editor editor = share.edit();
        editor.putString("userInfo", new Gson().toJson(user));
        KLog.json("putUser", new Gson().toJson(user));
        editor.commit();
    }


    public void removeUserInfoStr() {
        SharedPreferences share = context.getSharedPreferences("user", 0);
        SharedPreferences.Editor editor = share.edit();
        editor.putString("userInfo", "");
        editor.commit();
    }


    public User getUser() {
        SharedPreferences share = context.getSharedPreferences("user", 0);
        String userInfo = share.getString("userInfo", "");
        if ("".equals(userInfo)) {
            return null;
        } else {
            Gson gson = new Gson();
            KLog.json("getUser", userInfo);
            return gson.fromJson(userInfo, User.class);
        }
    }


    public int getUserId() {
        User user = getUser();
        if (user != null) {
            return user.Id;
        }
        return 0;
    }

    public boolean isLogin() {
        return StringUtil.isEmpty(getUserInfoStr()) ? false : true;
    }

}
