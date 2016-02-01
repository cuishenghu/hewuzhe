package com.hewuzhe.model;

import java.util.ArrayList;

/**
 * Created by xianguangjin on 15/12/31.
 */
public class FriendCondition {


    /**
     * Id : 139
     * Content : 我是内容
     * PublishTime : 2016-01-01 11:28:05
     * NicName : 盖世英雄
     * PicList : [{"Id":28,"ImagePath":"UpLoad/Image/f81479f6-1a38-4e9c-a27c-77d1559be701.png","PictureUrl":""}]
     * ComList : [{"Id":54,"CommentedId":46,"CommentedNicName":"盖世英雄","CommentedPhotoPath":"UpLoad/Photo/9d41d534-ade1-4453-9e5f-3d1814298596.jpg","CommenterId":46,"Content":"你喜欢哥哥还是妹妹啊","NicName":"盖世英雄","ParentId":0,"MessageId":139,"PhotoPath":"UpLoad/Photo/9d41d534-ade1-4453-9e5f-3d1814298596.jpg","PublishTime":"2016-01-01 11:35:39"}]
     * VideoPath :
     * ImagePath :
     * VideoDuration :
     * PhotoPath : UpLoad/Photo/9d41d534-ade1-4453-9e5f-3d1814298596.jpg
     * LikeNum : 0
     * IsLike : false
     */

    public int Id;
    public String Content;
    public String PublishTime;
    public String NicName;
    public String VideoPath;
    public String ImagePath;
    public String VideoDuration;
    public String PhotoPath;
    public int LikeNum;
    public boolean IsLike;
    /**
     * Id : 28
     * ImagePath : UpLoad/Image/f81479f6-1a38-4e9c-a27c-77d1559be701.png
     * PictureUrl :
     */

    public ArrayList<Pic> PicList = new ArrayList<>();

    public ArrayList<Comment> ComList = new ArrayList<>();
    /**
     * 是否是显示全部评论状态
     */
    public boolean isShowingAll = false;
    /**
     * UserId : 2
     * IsFavorite : false
     * IsRepeat : true
     */
    public int UserId;
    public boolean IsFavorite;
    public boolean IsRepeat;
}
