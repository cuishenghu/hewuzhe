package com.hewuzhe.model;

/**
 * Created by xianguangjin on 15/12/14.
 */
public class User {


    public int Id = -1;
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
    /**
     * RemarkName : é¾åé£é¸
     * IsShield : true
     * IsShieldNews : false
     */

    public String RemarkName;
    public boolean IsShield;
    public boolean IsShieldNews;


    public boolean isVip() {
        return IsPay;
    }


}


