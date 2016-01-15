package com.hewuzhe.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by xianguangjin on 16/1/4.
 */
public class Plan implements Parcelable {


    /**
     * Id : 172
     * Title : g'ji
     * Content : çå¯å¦å¦å¦å¦
     * PublishTime : 2016-01-04 15:12:00
     * StartTime : 2016-01-04 15:13:05
     * EndTime : 2016-02-03 15:13:05
     * ImageList : []
     */

    public int Id;
    public String Title;
    public String Content;
    public String PublishTime;
    public String StartTime = "";
    public String EndTime = "";
    public ArrayList<UploadImage> ImageList = new ArrayList<>();
    public int catId = -1;

    public boolean isChecked = false;
    public boolean isNeedShow = false;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.Id);
        dest.writeString(this.Title);
        dest.writeString(this.Content);
        dest.writeString(this.PublishTime);
        dest.writeString(this.StartTime);
        dest.writeString(this.EndTime);
        dest.writeList(this.ImageList);
        dest.writeInt(this.catId);
        dest.writeByte(isChecked ? (byte) 1 : (byte) 0);
        dest.writeByte(isNeedShow ? (byte) 1 : (byte) 0);
    }

    public Plan() {
    }

    protected Plan(Parcel in) {
        this.Id = in.readInt();
        this.Title = in.readString();
        this.Content = in.readString();
        this.PublishTime = in.readString();
        this.StartTime = in.readString();
        this.EndTime = in.readString();
        this.ImageList = new ArrayList<UploadImage>();
        in.readList(this.ImageList, UploadImage.class.getClassLoader());
        this.catId = in.readInt();
        this.isChecked = in.readByte() != 0;
        this.isNeedShow = in.readByte() != 0;
    }

    public static final Creator<Plan> CREATOR = new Creator<Plan>() {
        public Plan createFromParcel(Parcel source) {
            return new Plan(source);
        }

        public Plan[] newArray(int size) {
            return new Plan[size];
        }
    };


    public String getCateName() {
        switch (this.catId) {
            case 48:
                return "年计划";
            case 49:
                return "季计划";
            case 50:
                return "月计划";

            case 51:
                return "周计划";

        }

        return "";
    }
}
