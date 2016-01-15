package com.hewuzhe.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

/**
 * Created by xianguangjin on 15/12/30.
 */
public class UploadImage implements Parcelable{

    /**
     * ImageName : UpLoad/Photo/d57961a7-6efe-456c-81e6-37120be71131.jpg
     */
    @Nullable
    public String ImageName;

    @Nullable
    public String ImagePath;

    @Nullable
    public int Id;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ImageName);
        dest.writeString(this.ImagePath);
        dest.writeInt(this.Id);
    }

    public UploadImage() {
    }

    protected UploadImage(Parcel in) {
        this.ImageName = in.readString();
        this.ImagePath = in.readString();
        this.Id = in.readInt();
    }

    public static final Creator<UploadImage> CREATOR = new Creator<UploadImage>() {
        public UploadImage createFromParcel(Parcel source) {
            return new UploadImage(source);
        }

        public UploadImage[] newArray(int size) {
            return new UploadImage[size];
        }
    };
}
