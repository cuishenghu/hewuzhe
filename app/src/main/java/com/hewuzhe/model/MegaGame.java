package com.hewuzhe.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xianguangjin on 15/12/31.
 */
public class MegaGame implements Parcelable {


    public static final int STATUS_READY = 1;
    public static final int STATUS_ING = 2;
    public static final int STATUS_FINISHED = 3;
    /**
     * Id : 1
     * EnrollTimeEnd : 1900-01-01 00:00:00
     * EnrollTimeStart : 1900-01-01 00:00:00
     * Introduction : æè¿å¤§èµç¬¬ä¸å±
     * MatchAddress :
     * MatchHost :
     * MatchImage : UpLoad/Image/4e4c0d25-d4bc-44b6-a87b-44f46910ffc6.png
     * MatchRule :
     * MatchTimeEnd : 1900-01-01 00:00:00
     * MatchTimeStart : 1900-01-01 00:00:00
     * Name : æè¿å¤§èµç¬¬ä¸å±
     */

    public int Id;
    public String EnrollTimeEnd;
    public String EnrollTimeStart;
    public String Introduction;
    public String MatchAddress;
    public String MatchHost;
    public String MatchImage;
    public String MatchRule;
    public String MatchTimeEnd;
    public String MatchTimeStart;
    public String Name;
    public int status;


    /**
     * IsJoin : true
     * Phone : 15265104981
     */

    public boolean IsJoin;
    public String Phone;
    /**
     * Lat : 35.091954
     * Lng : 118.408965
     */

    public String Lat;
    public String Lng;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.Id);
        dest.writeString(this.EnrollTimeEnd);
        dest.writeString(this.EnrollTimeStart);
        dest.writeString(this.Introduction);
        dest.writeString(this.MatchAddress);
        dest.writeString(this.MatchHost);
        dest.writeString(this.MatchImage);
        dest.writeString(this.MatchRule);
        dest.writeString(this.MatchTimeEnd);
        dest.writeString(this.MatchTimeStart);
        dest.writeString(this.Name);
        dest.writeInt(this.status);
        dest.writeByte(IsJoin ? (byte) 1 : (byte) 0);
        dest.writeString(this.Phone);
        dest.writeString(this.Lat);
        dest.writeString(this.Lng);
    }

    public MegaGame() {
    }

    protected MegaGame(Parcel in) {
        this.Id = in.readInt();
        this.EnrollTimeEnd = in.readString();
        this.EnrollTimeStart = in.readString();
        this.Introduction = in.readString();
        this.MatchAddress = in.readString();
        this.MatchHost = in.readString();
        this.MatchImage = in.readString();
        this.MatchRule = in.readString();
        this.MatchTimeEnd = in.readString();
        this.MatchTimeStart = in.readString();
        this.Name = in.readString();
        this.status = in.readInt();
        this.IsJoin = in.readByte() != 0;
        this.Phone = in.readString();
        this.Lat = in.readString();
        this.Lng = in.readString();
    }

    public static final Creator<MegaGame> CREATOR = new Creator<MegaGame>() {
        public MegaGame createFromParcel(Parcel source) {
            return new MegaGame(source);
        }

        public MegaGame[] newArray(int size) {
            return new MegaGame[size];
        }
    };
}

