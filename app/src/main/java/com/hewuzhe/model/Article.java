package com.hewuzhe.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by xianguangjin on 15/12/25.
 */
public class Article implements Parcelable {


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
    /**
     * VisitNum : 41
     * IsLike : false
     * IsFavorite : true
     * IsRepeat : false
     */

    public int VisitNum;
    public boolean IsLike;
    public boolean IsFavorite;
    public boolean IsRepeat;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.Id);
        dest.writeString(this.Title);
        dest.writeString(this.Content);
        dest.writeInt(this.LikeNum);
        dest.writeInt(this.FavoriteNum);
        dest.writeString(this.PublishTime);
        dest.writeString(this.ImagePath);
        dest.writeString(this.Category);
        dest.writeInt(this.CommentNum);
        dest.writeTypedList(PicList);
        dest.writeInt(this.VisitNum);
        dest.writeByte(IsLike ? (byte) 1 : (byte) 0);
        dest.writeByte(IsFavorite ? (byte) 1 : (byte) 0);
        dest.writeByte(IsRepeat ? (byte) 1 : (byte) 0);
    }

    public Article() {
    }

    protected Article(Parcel in) {
        this.Id = in.readInt();
        this.Title = in.readString();
        this.Content = in.readString();
        this.LikeNum = in.readInt();
        this.FavoriteNum = in.readInt();
        this.PublishTime = in.readString();
        this.ImagePath = in.readString();
        this.Category = in.readString();
        this.CommentNum = in.readInt();
        this.PicList = in.createTypedArrayList(Pic.CREATOR);
        this.VisitNum = in.readInt();
        this.IsLike = in.readByte() != 0;
        this.IsFavorite = in.readByte() != 0;
        this.IsRepeat = in.readByte() != 0;
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        public Article createFromParcel(Parcel source) {
            return new Article(source);
        }

        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
}
