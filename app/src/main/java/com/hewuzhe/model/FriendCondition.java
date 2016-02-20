package com.hewuzhe.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;

/**
 * Created by xianguangjin on 15/12/31.
 */
public class FriendCondition extends DataSupport implements Parcelable {

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
    @Column(ignore = true)
    public int Id;
    public int fId = 0;
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
    public int UserId = 0;
    public boolean IsFavorite;
    public boolean IsRepeat;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.Id);
        dest.writeString(this.Content);
        dest.writeString(this.PublishTime);
        dest.writeString(this.NicName);
        dest.writeString(this.VideoPath);
        dest.writeString(this.ImagePath);
        dest.writeString(this.VideoDuration);
        dest.writeString(this.PhotoPath);
        dest.writeInt(this.LikeNum);
        dest.writeByte(IsLike ? (byte) 1 : (byte) 0);
        dest.writeList(this.PicList);
        dest.writeList(this.ComList);
        dest.writeByte(isShowingAll ? (byte) 1 : (byte) 0);
        dest.writeInt(this.UserId);
        dest.writeByte(IsFavorite ? (byte) 1 : (byte) 0);
        dest.writeByte(IsRepeat ? (byte) 1 : (byte) 0);
    }

    public FriendCondition() {
    }

    protected FriendCondition(Parcel in) {
        this.Id = in.readInt();
        this.Content = in.readString();
        this.PublishTime = in.readString();
        this.NicName = in.readString();
        this.VideoPath = in.readString();
        this.ImagePath = in.readString();
        this.VideoDuration = in.readString();
        this.PhotoPath = in.readString();
        this.LikeNum = in.readInt();
        this.IsLike = in.readByte() != 0;
        this.PicList = new ArrayList<Pic>();
        in.readList(this.PicList, Pic.class.getClassLoader());
        this.ComList = new ArrayList<Comment>();
        in.readList(this.ComList, Comment.class.getClassLoader());
        this.isShowingAll = in.readByte() != 0;
        this.UserId = in.readInt();
        this.IsFavorite = in.readByte() != 0;
        this.IsRepeat = in.readByte() != 0;
    }

    public static final Creator<FriendCondition> CREATOR = new Creator<FriendCondition>() {
        public FriendCondition createFromParcel(Parcel source) {
            return new FriendCondition(source);
        }

        public FriendCondition[] newArray(int size) {
            return new FriendCondition[size];
        }
    };


    public String getPhotoPath() {
        return PhotoPath;
    }

    public void setPhotoPath(String photoPath) {
        PhotoPath = photoPath;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getfId() {
        return fId;
    }

    public void setfId(int fId) {
        this.fId = fId;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getPublishTime() {
        return PublishTime;
    }

    public void setPublishTime(String publishTime) {
        PublishTime = publishTime;
    }

    public String getNicName() {
        return NicName;
    }

    public void setNicName(String nicName) {
        NicName = nicName;
    }

    public String getVideoPath() {
        return VideoPath;
    }

    public void setVideoPath(String videoPath) {
        VideoPath = videoPath;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getVideoDuration() {
        return VideoDuration;
    }

    public void setVideoDuration(String videoDuration) {
        VideoDuration = videoDuration;
    }

    public int getLikeNum() {
        return LikeNum;
    }

    public void setLikeNum(int likeNum) {
        LikeNum = likeNum;
    }

    public boolean isIsLike() {
        return IsLike;
    }

    public void setIsLike(boolean isLike) {
        IsLike = isLike;
    }

    public ArrayList<Pic> getPicList() {
        return PicList;
    }

    public void setPicList(ArrayList<Pic> picList) {
        PicList = picList;
    }

    public ArrayList<Comment> getComList() {
        return ComList;
    }

    public void setComList(ArrayList<Comment> comList) {
        ComList = comList;
    }

    public boolean isShowingAll() {
        return isShowingAll;
    }

    public void setIsShowingAll(boolean isShowingAll) {
        this.isShowingAll = isShowingAll;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public boolean isIsFavorite() {
        return IsFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        IsFavorite = isFavorite;
    }

    public boolean isIsRepeat() {
        return IsRepeat;
    }

    public void setIsRepeat(boolean isRepeat) {
        IsRepeat = isRepeat;
    }

    public static Creator<FriendCondition> getCREATOR() {
        return CREATOR;
    }
}
