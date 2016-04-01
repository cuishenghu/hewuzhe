package com.hewuzhe.model;

import java.util.ArrayList;

/**
 * Created by zycom on 2016/3/29.
 */
public class ChatList {
    public int Id;
    public String Content;
    public String PublishTime;
    public String NicName;
    public String Description;
    public ArrayList<Pic> PicList;
    public ArrayList<Comment> ComList;
    public String VideoPath;
    public String ImagePath;
    public String VideoDuration;
    public String PhotoPath;
    public int LikeNum;
    public int UserId;
    public boolean IsLike;
    public boolean IsFavorite;
    public boolean IsRepeat;
    public boolean IsFriend;
}
