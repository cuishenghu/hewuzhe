package com.hewuzhe.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by xianguangjin on 15/12/29.
 */
public class Comment extends DataSupport implements Parcelable {


    @Column(ignore = true)
    public int Id;

    public int fId = 0;
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

    public String MessageAndVideoTitle;
    public String MessageAndVideoImagePath;
    public String MessageAndVideoContent;
    public String NicName;
    public String Phone;
    public String PhotoPath;
    public String Title;
    public String ImagePath;
    public String OperateTime;


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
        dest.writeString(this.Title);
        dest.writeString(this.ImagePath);
        dest.writeString(this.OperateTime);
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
        this.Title = in.readString();
        this.ImagePath = in.readString();
        this.OperateTime = in.readString();
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        public Comment createFromParcel(Parcel source) {
            return new Comment(source);
        }

        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getCommentedId() {
        return CommentedId;
    }

    public void setCommentedId(int commentedId) {
        CommentedId = commentedId;
    }

    public String getCommentedNicName() {
        return CommentedNicName;
    }

    public void setCommentedNicName(String commentedNicName) {
        CommentedNicName = commentedNicName;
    }

    public String getCommentedPhotoPath() {
        return CommentedPhotoPath;
    }

    public void setCommentedPhotoPath(String commentedPhotoPath) {
        CommentedPhotoPath = commentedPhotoPath;
    }

    public int getCommenterId() {
        return CommenterId;
    }

    public void setCommenterId(int commenterId) {
        CommenterId = commenterId;
    }

    public String getCommenterNicName() {
        return CommenterNicName;
    }

    public void setCommenterNicName(String commenterNicName) {
        CommenterNicName = commenterNicName;
    }

    public String getCommenterPhotoPath() {
        return CommenterPhotoPath;
    }

    public void setCommenterPhotoPath(String commenterPhotoPath) {
        CommenterPhotoPath = commenterPhotoPath;
    }

    public String getPublishTime() {
        return PublishTime;
    }

    public void setPublishTime(String publishTime) {
        PublishTime = publishTime;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public int getMessageId() {
        return MessageId;
    }

    public void setMessageId(int messageId) {
        MessageId = messageId;
    }

    public int getParentId() {
        return ParentId;
    }

    public void setParentId(int parentId) {
        ParentId = parentId;
    }

    public String getMessageAndVideoTitle() {
        return MessageAndVideoTitle;
    }

    public void setMessageAndVideoTitle(String messageAndVideoTitle) {
        MessageAndVideoTitle = messageAndVideoTitle;
    }

    public String getMessageAndVideoImagePath() {
        return MessageAndVideoImagePath;
    }

    public void setMessageAndVideoImagePath(String messageAndVideoImagePath) {
        MessageAndVideoImagePath = messageAndVideoImagePath;
    }

    public String getMessageAndVideoContent() {
        return MessageAndVideoContent;
    }

    public void setMessageAndVideoContent(String messageAndVideoContent) {
        MessageAndVideoContent = messageAndVideoContent;
    }

    public String getNicName() {
        return NicName;
    }

    public void setNicName(String nicName) {
        NicName = nicName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPhotoPath() {
        return PhotoPath;
    }

    public void setPhotoPath(String photoPath) {
        PhotoPath = photoPath;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getOperateTime() {
        return OperateTime;
    }

    public void setOperateTime(String operateTime) {
        OperateTime = operateTime;
    }
}
