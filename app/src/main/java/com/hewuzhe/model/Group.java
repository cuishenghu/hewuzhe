package com.hewuzhe.model;

import android.content.Context;

import com.hewuzhe.utils.SessionUtil;

/**
 * Created by xianguangjin on 15/12/31.
 */
public class Group {

    public int citycode;
    public String name;


    public int Id;
    public String Name;
    public int AreaId;
    public String BuildTime;
    public String ImagePath;
    public String Address;

    public boolean isJoined(Context context) {
        return new SessionUtil(context).getUser().TeamId == Id;
    }
}
