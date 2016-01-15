package com.hewuzhe.model;

import java.util.ArrayList;

/**
 * Created by xianguangjin on 15/12/25.
 */
public class Article {


    /**
     * Id : 136
     * Title : æµè¯ç¶æ
     * Content : æµè¯ç¶æ
     * LikeNum : 0
     * FavoriteNum : 0
     * PublishTime : 2015-12-31 19:23:13
     * ImagePath :
     */

    public int Id;
    public String Title;
    public String Content;
    public int LikeNum;
    public int FavoriteNum;
    public String PublishTime;
    public String ImagePath;


    /**
     * Category : å¬çæå
     * PicList : [{"Id":27,"ImagePath":"UpLoad/Image/f81479f6-1a38-4e9c-a27c-77d1559be701.png","PictureUrl":""}]
     * CommentNum : 0
     */

    public String Category;
    public int CommentNum;
    /**
     * Id : 27
     * ImagePath : UpLoad/Image/f81479f6-1a38-4e9c-a27c-77d1559be701.png
     * PictureUrl :
     */

    public ArrayList<Pic> PicList;



}
