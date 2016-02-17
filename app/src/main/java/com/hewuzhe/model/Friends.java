package com.hewuzhe.model;

import org.litepal.crud.DataSupport;

/**
 * Created by xianguangjin on 15/12/31.
 */
public class Friends extends DataSupport {
    public String topc;
    public int id;
    public int friendid;

    public int getFriendid() {
        return friendid;
    }

    public void setFriendid(int friendid) {
        this.friendid = friendid;
    }

    /**
     * NicName : å­åè¹
     * PhotoPath : UpLoad/Photo/qwreqwreqwqw.jpg
     * UserRankName :
     * Rank : 0
     * IsFriend : false
     */


    public String nicname;

    public String photopath;


    public int rank;

    public boolean isfriend;
    /**
     * TeamName : æ­¦å½æ´¾
     * HomeAreaCountyName :
     * HomeAreaCityName :
     * HomeAreaprovinceName :
     * WorkAreaCountyName :
     * WorkAreaCityName :
     * WorkAreaprovinceName :
     * ActivityAreaCountyName :
     * ActivityAreaCityName :
     * ActivityAreaprovinceName :
     * CountyName :
     * IsDirty : true
     * UserRankName_IsDirty : false
     * Rank_IsDirty : false
     * TeamName_IsDirty : false
     * HomeAreaCountyName_IsDirty : false
     * HomeAreaCityName_IsDirty : false
     * HomeAreaprovinceName_IsDirty : false
     * WorkAreaCountyName_IsDirty : false
     * WorkAreaCityName_IsDirty : false
     * WorkAreaprovinceName_IsDirty : false
     * ActivityAreaCountyName_IsDirty : false
     * ActivityAreaCityName_IsDirty : false
     * ActivityAreaprovinceName_IsDirty : false
     * CountyName_IsDirty : false
     * UserName : 1234
     * PassWord : C4CA4238A0B923820DCC509A6F75849B
     * RealName :
     * Phone : 1234
     * Email :
     * RongCloudName :
     * RongCloudPwd :
     * PingName :
     * PingPwd :
     * VideoName :
     * VideoPwd :
     * RankId : 0
     * Credit : 0
     * TeamId : 2
     * HomeAreaId : 0
     * WorkAreaId : 0
     * ActivityAreaId : 0
     * HomeAddress :
     * WorkAddress :
     * ActivityAddress :
     * Sexuality : 0
     * Height : 0
     * Weight : 0
     * Experience : 0
     * IsPay : false
     * PayTime : /Date(-2209017600000)/
     * OverTime : /Date(-2209017600000)/
     * CreditTotal : 0
     * RegTime : /Date(-2209017600000)/
     * IsClose : false
     * Birthday : /Date(-2209017600000)/
     * Description :
     * OpenId :
     * Id_IsDirty : true
     * UserName_IsDirty : true
     * PassWord_IsDirty : true
     * NicName_IsDirty : true
     * RealName_IsDirty : true
     * Phone_IsDirty : true
     * Email_IsDirty : true
     * RongCloudName_IsDirty : true
     * RongCloudPwd_IsDirty : true
     * PingName_IsDirty : true
     * PingPwd_IsDirty : true
     * VideoName_IsDirty : true
     * VideoPwd_IsDirty : true
     * RankId_IsDirty : true
     * Credit_IsDirty : true
     * PhotoPath_IsDirty : true
     * TeamId_IsDirty : true
     * HomeAreaId_IsDirty : true
     * WorkAreaId_IsDirty : true
     * ActivityAreaId_IsDirty : true
     * HomeAddress_IsDirty : true
     * WorkAddress_IsDirty : true
     * ActivityAddress_IsDirty : true
     * Sexuality_IsDirty : false
     * Height_IsDirty : false
     * Weight_IsDirty : false
     * Experience_IsDirty : false
     * IsPay_IsDirty : false
     * PayTime_IsDirty : false
     * OverTime_IsDirty : false
     * CreditTotal_IsDirty : false
     * RegTime_IsDirty : false
     * IsClose_IsDirty : false
     * Birthday_IsDirty : false
     * Description_IsDirty : false
     * OpenId_IsDirty : false
     */


    public String phone;

    public int teamid;
    /**
     * MemnerId : 24
     * JoinTime : /Date(1450339424453)/
     * IsTeamLeader : false
     * IsTeamManager : false
     * State : 2
     * MemnerId_IsDirty : true
     * JoinTime_IsDirty : true
     * IsTeamLeader_IsDirty : true
     * IsTeamManager_IsDirty : true
     * State_IsDirty : true
     */

    public int memnerid;

    public String jointime;
    /**
     * IsShield : false
     * UserId : 46
     */

    public boolean isshield;

    public int userid;
    /**
     * RemarkName : æ±å©ä¼
     */


    public String remarkname;


    public String getTopc() {
        return topc;
    }

    public void setTopc(String topc) {
        this.topc = topc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNicname() {
        return nicname;
    }

    public void setNicname(String nicname) {
        this.nicname = nicname;
    }

    public String getPhotopath() {
        return photopath;
    }

    public void setPhotopath(String photopath) {
        this.photopath = photopath;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean isfriend() {
        return isfriend;
    }
    public boolean isIsfriend() {
        return isfriend;
    }

    public void setIsfriend(boolean isfriend) {
        this.isfriend = isfriend;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getTeamid() {
        return teamid;
    }

    public void setTeamid(int teamid) {
        this.teamid = teamid;
    }

    public int getMemnerid() {
        return memnerid;
    }

    public void setMemnerid(int memnerid) {
        this.memnerid = memnerid;
    }

    public String getJointime() {
        return jointime;
    }

    public void setJointime(String jointime) {
        this.jointime = jointime;
    }

    public boolean isshield() {
        return isshield;
    }
    public boolean isIsshield() {
        return isshield;
    }

    public void setIsshield(boolean isshield) {
        this.isshield = isshield;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getRemarkname() {
        return remarkname;
    }

    public void setRemarkname(String remarkname) {
        this.remarkname = remarkname;
    }
}
