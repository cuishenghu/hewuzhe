package com.hewuzhe.model;

/**
 * Created by xianguangjin on 15/12/31.
 */
public class MegaGame {


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
}
