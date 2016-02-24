package com.hewuzhe.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hewuzhe.model.UP;
import com.hewuzhe.model.User;

import java.util.ArrayList;
import java.util.List;

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
//      KLog.json("getUserInfoStr", userInfo);
        return userInfo;
    }

    public void putUserInfoStr(String userInfoStr) {
        SharedPreferences share = context.getSharedPreferences("user", 0);
        SharedPreferences.Editor editor = share.edit();
        editor.putString("userInfo", userInfoStr);
//        KLog.json("putUserInfoStr", userInfoStr);
        editor.commit();
    }

    public void putUser(User user) {
        SharedPreferences share = context.getSharedPreferences("user", 0);
        SharedPreferences.Editor editor = share.edit();
        editor.putString("userInfo", new Gson().toJson(user));
//      KLog.json("putUser", new Gson().toJson(user));
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
//          KLog.json("getUser", userInfo);
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


    /**
     * @param up 登录数据保存
     */
    public void putUP(UP up) {
        ArrayList<UP> ups = getUPs();
        if (ups == null) {
            ups = new ArrayList<>();
        }
        if (!contains(ups, up)) {
            ups.add(up);
            SharedPreferences share = context.getSharedPreferences("user", 0);
            SharedPreferences.Editor editor = share.edit();
            editor.putString("up", new Gson().toJson(ups));
            editor.commit();
        }

    }

    public void removeUp(UP up) {
        ArrayList<UP> ups = getUPs();
        if (ups == null) {
            ups = new ArrayList<>();
        }

        for (UP up1 : ups) {
            if (up1.userName.equals(up.userName)) {
                ups.remove(up1);
            }
        }

        SharedPreferences share = context.getSharedPreferences("user", 0);
        SharedPreferences.Editor editor = share.edit();
        editor.putString("up", new Gson().toJson(ups));
        editor.commit();
    }

    private boolean contains(ArrayList<UP> ups, UP up) {
        for (UP up1 : ups) {
            if (up1.userName.equals(up.userName)) {
                return true;
            }
        }
        return false;
    }


    /**
     * @return 获取登录数据
     */
    public ArrayList<UP> getUPs() {
        SharedPreferences share = context.getSharedPreferences("user", 0);
        String up = share.getString("up", "");
        if ("".equals(up)) {
            return null;
        } else {
            Gson gson = new Gson();
            return gson.fromJson(up, new TypeToken<List<UP>>() {
            }.getType());
        }
    }


    /**
     * @return
     */
    public boolean isLogin() {
        return StringUtil.isEmpty(getUserInfoStr()) ? false : true;
    }

}
