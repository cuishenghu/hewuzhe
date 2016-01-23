package com.hewuzhe.ui.cons;

import android.graphics.Color;

/**
 * Created by xianguangjin on 15/12/10.
 */
public class C {

    /**
     * URL
     */
    public static final String BASE_URL = "http://115.28.67.86:8033/";

    /**
     * COLOR
     */
    public final static int COLOR_BG = Color.parseColor("#3e3e40");
    public final static int COLOR_YELLOW = Color.parseColor("#ef9c00");

    /**
     * 拍照*选择照片
     */
    public static final int OK = 200;
    public static final int TAKE_PIC = 20001;
    public static final int CHOOSE_PIC = 20002;
    public static final int CROP = 20003;


    /**
     * MOB
     */
    public static final String MOB_KEY = "d5572cbfc8b7";
    public static final String MOB_SECRET = "04e92d2c19317b1ab64cd60d5b571b28";
    public static final String DEFAULT_COUNTRY_ID = "86";


    /**
     * 选择视频和拍摄视频
     */
    public static final int CHOOSE_VIDEO = 20004;
    public static final int TAKE_VIDEO = 20005;

    /**
     * AdapterViewType
     */
    public static final int VIEW_TYPE_HEADER = 0;
    public static final int VIEW_TYPE_FOOTER = -1;
    public static final int VIEW_TYPE_NORMAL = 1;


    /**
     * RequestCode
     */
    public static final int REQUEST_SELECT_CATE = 20006;


    /**
     * ResultCode
     */
    public static final int RESULT_ONE = 0;
    public static final int RESULT_TWO = 1;
    public static final int RESULT_THREE = 2;

    /**
     * 支付
     */
    public static final int REQUEST_CODE_PAYMENT = 20007;

    /**
     * 银联支付渠道
     */
    public static final String CHANNEL_UPACP = "upacp";
    /**
     * 微信支付渠道
     */
    public static final String CHANNEL_WECHAT = "wx";
    /**
     * 支付支付渠道
     */
    public static final String CHANNEL_ALIPAY = "alipay";
    /**
     * 百度支付渠道
     */
    public static final String CHANNEL_BFB = "bfb";
    /**
     * 京东支付渠道
     */
    public static final String CHANNEL_JDPAY_WAP = "jdpay_wap";

    /**
     * 标识哪一个
     */
    public static final int WHITCH_DEFAUT = 0;
    public static final int WHITCH_ONE = 1;
    public static final int WHITCH_TWO = 2;


    public static final String WHITCH = "whitch";

    public static final Integer MSG_DEFAUT = 0;
    public static final Integer MSG_ONE = 1;
    public static final Integer MSG_TWO = 2;


    /**
     * 性别
     */
    public static final int GENDER_MALE = 1;
    public static final int GENDER_FEMALE = 2;
    public static final int GENDER_UN_KNOWN = 0;

    public static final String CACHE_DIR_PATH = "/hewuzhe/cache";
    public static final int MSG_CLOSE_GROUP_CONDITION = 20000;
    public static final int MSG_REFRESH_MEGA_GAME_DETAIL = 20001;
    public static final int MSG_UNREAD_COUNT = 20002;
    public static final int MSG_REFRESH_PLAN_LIST = 20003;
    public static final int MSG_CLOSE_FRIEND_PROFILE = 20004;
}
