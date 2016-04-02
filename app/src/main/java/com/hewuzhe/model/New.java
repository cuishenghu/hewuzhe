package com.hewuzhe.model;

/**
 * Created by xianguangjin on 15/12/22.
 */
public class New {

    /**
     * Id : 13
     * Title : 野心视频
     * ImagePath : UpLoad/Image/2357e785-390f-43d5-9cc0-56e5d4a82def.JPG
     * Content : 野心视频描述
     * PublishTime : 1900-01-01 00:00:00
     * LikeNum : 0
     * FavoriteNum : 0
     * IsFree : false
     */

    public int Id;
    public String MatchImage;
    public String Title;
    public String ImagePath;
    public String Content;
    public String PublishTime;
    public String VideoName;
    public String ImageName;
    public int LikeNum;
    public int FavoriteNum;
    public boolean IsFree = true;
    public String MessageCame;//来源
    public String Path;
    public String Name;



    /**
     * VideoPath : UpLoad/Video/14468618701471.mp4
     * VideoDuration :
     * UserId : 0
     * UserNicName : 系统上传
     * PhotoPath : 系统上传
     * VisitNum : 0
     * CommentNum : 0
     */

    public String VideoPath;
    public String VideoDuration;
    public int UserId;
    public String UserNicName;
    public String PhotoPath;
    public int VisitNum;
    public int CommentNum;
    /**
     * NicName : hdhdjsjsnnsn
     */

    public String NicName;

    public String IsOriginal;


    /**
     * Islike : false
     */

    public boolean Islike = false;
    public boolean IsFavorite = false;
    public int IsGuanzhu;


    /**
     * MessageId : 101
     * OperateTime : 2015-12-29 17:30:19
     * RepeatNum : 4
     * IsRecommend : false
     * IsLike : true
     * IsRepeat : true
     */

    public int MessageId;
    public String OperateTime;
    public int RepeatNum;
    public boolean IsRecommend;
    public boolean IsLike;
    public boolean IsRepeat = false;


    /**
     * UpLoadType : 0
     */

    public int UpLoadType = 0;//0是你选择手机中的视频
    public int PublisherId;
    public boolean isChecked = false;

}
