package com.hewuzhe.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.DataSupport;

/**
 * Created by xianguangjin on 16/1/1.
 */
public class Pic extends DataSupport implements Parcelable {

    public int Id;

    public String ImagePath;
    public String PictureUrl;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.Id);
        dest.writeString(this.ImagePath);
        dest.writeString(this.PictureUrl);
    }

    public Pic() {
    }

    protected Pic(Parcel in) {
        this.Id = in.readInt();
        this.ImagePath = in.readString();
        this.PictureUrl = in.readString();
    }

    public static final Creator<Pic> CREATOR = new Creator<Pic>() {
        public Pic createFromParcel(Parcel source) {
            return new Pic(source);
        }

        public Pic[] newArray(int size) {
            return new Pic[size];
        }
    };

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getPictureUrl() {
        return PictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        PictureUrl = pictureUrl;
    }
}
