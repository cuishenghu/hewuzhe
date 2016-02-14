package com.hewuzhe.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xianguangjin on 15/12/29.
 */
public class Comment implements Parcelable{


    public int Id;
    public int CommentedId;
    public String CommentedNicName;
    public String CommentedPhotoPath;
    public int CommenterId;
    public String CommenterNicName;
    public String CommenterPhotoPath;
    public String PublishTime;
    public String Content;
    public int MessageId;
    public int ParentId;


    /**
     * MessageAndVideoTitle : 测试状态
     * MessageAndVideoImagePath :
     * MessageAndVideoContent : 测试状态
     * NicName : 盖世英雄
     * Phone : 18354450969
     * PhotoPath : UpLoad/Photo/9d41d534-ade1-4453-9e5f-3d1814298596.jpg
     * IsDirty : true
     * MessageAndVideoTitle_IsDirty : false
     * MessageAndVideoImagePath_IsDirty : false
     * MessageAndVideoContent_IsDirty : false
     * NicName_IsDirty : false
     * Phone_IsDirty : false
     * PhotoPath_IsDirty : false
     * CommentedNicName_IsDirty : false
     * CommentedPhotoPath_IsDirty : false
     * Title :
     * ImagePath :
     * OperateTime : /Date(1451614735530)/
     * Id_IsDirty : true
     * Title_IsDirty : true
     * ImagePath_IsDirty : true
     * Content_IsDirty : true
     * PublishTime_IsDirty : true
     * OperateTime_IsDirty : true
     * MessageId_IsDirty : true
     * CommenterId_IsDirty : true
     * ParentId_IsDirty : true
     * CommentedId_IsDirty : true
     */

    public String MessageAndVideoTitle;
    public String MessageAndVideoImagePath;
    public String MessageAndVideoContent;
    public String NicName;
    public String Phone;
    public String PhotoPath;
    public boolean IsDirty;
    public boolean MessageAndVideoTitle_IsDirty;
    public boolean MessageAndVideoImagePath_IsDirty;
    public boolean MessageAndVideoContent_IsDirty;
    public boolean NicName_IsDirty;
    public boolean Phone_IsDirty;
    public boolean PhotoPath_IsDirty;
    public boolean CommentedNicName_IsDirty;
    public boolean CommentedPhotoPath_IsDirty;
    public String Title;
    public String ImagePath;
    public String OperateTime;
    public boolean Id_IsDirty;
    public boolean Title_IsDirty;
    public boolean ImagePath_IsDirty;
    public boolean Content_IsDirty;
    public boolean PublishTime_IsDirty;
    public boolean OperateTime_IsDirty;
    public boolean MessageId_IsDirty;
    public boolean CommenterId_IsDirty;
    public boolean ParentId_IsDirty;
    public boolean CommentedId_IsDirty;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.Id);
        dest.writeInt(this.CommentedId);
        dest.writeString(this.CommentedNicName);
        dest.writeString(this.CommentedPhotoPath);
        dest.writeInt(this.CommenterId);
        dest.writeString(this.CommenterNicName);
        dest.writeString(this.CommenterPhotoPath);
        dest.writeString(this.PublishTime);
        dest.writeString(this.Content);
        dest.writeInt(this.MessageId);
        dest.writeInt(this.ParentId);
        dest.writeString(this.MessageAndVideoTitle);
        dest.writeString(this.MessageAndVideoImagePath);
        dest.writeString(this.MessageAndVideoContent);
        dest.writeString(this.NicName);
        dest.writeString(this.Phone);
        dest.writeString(this.PhotoPath);
        dest.writeByte(IsDirty ? (byte) 1 : (byte) 0);
        dest.writeByte(MessageAndVideoTitle_IsDirty ? (byte) 1 : (byte) 0);
        dest.writeByte(MessageAndVideoImagePath_IsDirty ? (byte) 1 : (byte) 0);
        dest.writeByte(MessageAndVideoContent_IsDirty ? (byte) 1 : (byte) 0);
        dest.writeByte(NicName_IsDirty ? (byte) 1 : (byte) 0);
        dest.writeByte(Phone_IsDirty ? (byte) 1 : (byte) 0);
        dest.writeByte(PhotoPath_IsDirty ? (byte) 1 : (byte) 0);
        dest.writeByte(CommentedNicName_IsDirty ? (byte) 1 : (byte) 0);
        dest.writeByte(CommentedPhotoPath_IsDirty ? (byte) 1 : (byte) 0);
        dest.writeString(this.Title);
        dest.writeString(this.ImagePath);
        dest.writeString(this.OperateTime);
        dest.writeByte(Id_IsDirty ? (byte) 1 : (byte) 0);
        dest.writeByte(Title_IsDirty ? (byte) 1 : (byte) 0);
        dest.writeByte(ImagePath_IsDirty ? (byte) 1 : (byte) 0);
        dest.writeByte(Content_IsDirty ? (byte) 1 : (byte) 0);
        dest.writeByte(PublishTime_IsDirty ? (byte) 1 : (byte) 0);
        dest.writeByte(OperateTime_IsDirty ? (byte) 1 : (byte) 0);
        dest.writeByte(MessageId_IsDirty ? (byte) 1 : (byte) 0);
        dest.writeByte(CommenterId_IsDirty ? (byte) 1 : (byte) 0);
        dest.writeByte(ParentId_IsDirty ? (byte) 1 : (byte) 0);
        dest.writeByte(CommentedId_IsDirty ? (byte) 1 : (byte) 0);
    }

    public Comment() {
    }

    protected Comment(Parcel in) {
        this.Id = in.readInt();
        this.CommentedId = in.readInt();
        this.CommentedNicName = in.readString();
        this.CommentedPhotoPath = in.readString();
        this.CommenterId = in.readInt();
        this.CommenterNicName = in.readString();
        this.CommenterPhotoPath = in.readString();
        this.PublishTime = in.readString();
        this.Content = in.readString();
        this.MessageId = in.readInt();
        this.ParentId = in.readInt();
        this.MessageAndVideoTitle = in.readString();
        this.MessageAndVideoImagePath = in.readString();
        this.MessageAndVideoContent = in.readString();
        this.NicName = in.readString();
        this.Phone = in.readString();
        this.PhotoPath = in.readString();
        this.IsDirty = in.readByte() != 0;
        this.MessageAndVideoTitle_IsDirty = in.readByte() != 0;
        this.MessageAndVideoImagePath_IsDirty = in.readByte() != 0;
        this.MessageAndVideoContent_IsDirty = in.readByte() != 0;
        this.NicName_IsDirty = in.readByte() != 0;
        this.Phone_IsDirty = in.readByte() != 0;
        this.PhotoPath_IsDirty = in.readByte() != 0;
        this.CommentedNicName_IsDirty = in.readByte() != 0;
        this.CommentedPhotoPath_IsDirty = in.readByte() != 0;
        this.Title = in.readString();
        this.ImagePath = in.readString();
        this.OperateTime = in.readString();
        this.Id_IsDirty = in.readByte() != 0;
        this.Title_IsDirty = in.readByte() != 0;
        this.ImagePath_IsDirty = in.readByte() != 0;
        this.Content_IsDirty = in.readByte() != 0;
        this.PublishTime_IsDirty = in.readByte() != 0;
        this.OperateTime_IsDirty = in.readByte() != 0;
        this.MessageId_IsDirty = in.readByte() != 0;
        this.CommenterId_IsDirty = in.readByte() != 0;
        this.ParentId_IsDirty = in.readByte() != 0;
        this.CommentedId_IsDirty = in.readByte() != 0;
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        public Comment createFromParcel(Parcel source) {
            return new Comment(source);
        }

        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
