package com.hewuzhe.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xianguangjin on 15/12/14.
 */
public class User implements Parcelable {


    public int Id=-1;
    public String UserName;
    public String NicName;
    public String RealName;
    public String Phone;
    public String Token;
    public int Rank;
    public String UserRankName;
    public int Credit;
    public int CreditTotal;
    public String PhotoPath;
    public int TeamId;
    public String TeamName;
    public String HomeAddress;
    public String HomeCode;
    public String HomeAreaprovinceName;
    public String HomeAreaCityName;
    public String HomeAreaCountyName;
    public int HomeAreaId;
    public int Sexuality;
    public int Height;
    public int Weight;
    public int Experience;
    public String Birthday;
    public boolean IsClose;
    public boolean IsPay;
    public String OverTime;



    public String PassWord;
    /**
     * Description : lll
     */

    public String Description;
    /**
     * IsShieldComment : false
     */

    public boolean IsShieldComment;


    public boolean isVip() {
        return IsPay;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.Id);
        dest.writeString(this.UserName);
        dest.writeString(this.NicName);
        dest.writeString(this.RealName);
        dest.writeString(this.Phone);
        dest.writeString(this.Token);
        dest.writeInt(this.Rank);
        dest.writeString(this.UserRankName);
        dest.writeInt(this.Credit);
        dest.writeInt(this.CreditTotal);
        dest.writeString(this.PhotoPath);
        dest.writeInt(this.TeamId);
        dest.writeString(this.TeamName);
        dest.writeString(this.HomeAddress);
        dest.writeString(this.HomeCode);
        dest.writeString(this.HomeAreaprovinceName);
        dest.writeString(this.HomeAreaCityName);
        dest.writeString(this.HomeAreaCountyName);
        dest.writeInt(this.HomeAreaId);
        dest.writeInt(this.Sexuality);
        dest.writeInt(this.Height);
        dest.writeInt(this.Weight);
        dest.writeInt(this.Experience);
        dest.writeString(this.Birthday);
        dest.writeByte(IsClose ? (byte) 1 : (byte) 0);
        dest.writeByte(IsPay ? (byte) 1 : (byte) 0);
        dest.writeString(this.OverTime);
        dest.writeString(this.PassWord);
        dest.writeString(this.Description);
        dest.writeByte(IsShieldComment ? (byte) 1 : (byte) 0);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.Id = in.readInt();
        this.UserName = in.readString();
        this.NicName = in.readString();
        this.RealName = in.readString();
        this.Phone = in.readString();
        this.Token = in.readString();
        this.Rank = in.readInt();
        this.UserRankName = in.readString();
        this.Credit = in.readInt();
        this.CreditTotal = in.readInt();
        this.PhotoPath = in.readString();
        this.TeamId = in.readInt();
        this.TeamName = in.readString();
        this.HomeAddress = in.readString();
        this.HomeCode = in.readString();
        this.HomeAreaprovinceName = in.readString();
        this.HomeAreaCityName = in.readString();
        this.HomeAreaCountyName = in.readString();
        this.HomeAreaId = in.readInt();
        this.Sexuality = in.readInt();
        this.Height = in.readInt();
        this.Weight = in.readInt();
        this.Experience = in.readInt();
        this.Birthday = in.readString();
        this.IsClose = in.readByte() != 0;
        this.IsPay = in.readByte() != 0;
        this.OverTime = in.readString();
        this.PassWord = in.readString();
        this.Description = in.readString();
        this.IsShieldComment = in.readByte() != 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}


