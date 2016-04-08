package com.hewuzhe.ui.http;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by csh on 15-7-21.
 */
public class HttpUtils {

    private HttpUtils() {

    }

    private static AsyncHttpClient client = new AsyncHttpClient();

    static {
        // // 使用默认的 cacheThreadPool
        client.setTimeout(150000);
        client.setConnectTimeout(150000);
        client.setMaxConnections(20);
        client.setResponseTimeout(200000);
    }

    /*获取主页推荐分类 */
    public static void getRecommendProduct(AsyncHttpResponseHandler handler, RequestParams params) {
        client.get(UrlContants.getUrl(UrlContants.GETRECOMMENDPRODUCT), params, handler);
    }

    /*取消订单 */
    public static void cancleOrder(AsyncHttpResponseHandler handler, RequestParams params) {
        client.get(UrlContants.getUrl(UrlContants.CANCLEORDER), params, handler);
    }

    /*获取积分兑换的商品列表 */
    public static void getProductForDuihuan(AsyncHttpResponseHandler handler, RequestParams params) {
        client.get(UrlContants.getUrl(UrlContants.PRODUCTFORDUIHUANLIST), params, handler);
    }

    /*根据用户ID分页查询兑换记录 */
    public static void getProductDuihuanList(AsyncHttpResponseHandler handler, RequestParams params) {
        client.get(UrlContants.getUrl(UrlContants.PRODUCTDUIHUANLIST), params, handler);
    }

    /*立即兑换 */
    public static void submitChangeNow(AsyncHttpResponseHandler handler, RequestParams params) {
        client.get(UrlContants.getUrl(UrlContants.CHANGENOW), params, handler);
    }

    /*兑换详情 */
    public static void getChangeInfo(AsyncHttpResponseHandler handler, RequestParams params) {
        client.get(UrlContants.getUrl(UrlContants.CHANGEINFO), params, handler);
    }

    /*确认收货 */
    public static void confirmReceived(AsyncHttpResponseHandler handler, RequestParams params) {
        client.get(UrlContants.getUrl(UrlContants.CONFIRMRECEIVED), params, handler);
    }

    /*评价订单 */
    public static void submitCommentOrder(AsyncHttpResponseHandler handler, RequestParams params) {
        client.get(UrlContants.getUrl(UrlContants.COMMENTORDER), params, handler);
    }

    /*获取订单 根据用户ID分页查询订单 */
    public static void getOrderByUserId(AsyncHttpResponseHandler handler, RequestParams params) {
        client.get(UrlContants.getUrl(UrlContants.GETORDERBYUSERID), params, handler);
    }

    /* 根据订单ID分页查看订单商品 */
    public static void getProductByOrderNo(AsyncHttpResponseHandler handler, RequestParams params) {
        client.get(UrlContants.getUrl(UrlContants.SELECTPRODUCTBYORDERNO), params, handler);
    }

    /* 获取charge */
    public static void confirmSubmitCharge(AsyncHttpResponseHandler handler, RequestParams params) {
        client.get(UrlContants.getUrl(UrlContants.GETCHARGE), params, handler);
    }

    /* 根据地址ID设置默认收货地址 */
    public static void getAddressByAreaId(AsyncHttpResponseHandler handler, RequestParams params) {
        client.get(UrlContants.getUrl(UrlContants.GETADDRESSBYAREAID), params, handler);
    }

    /* 查询默认收货地址，若没有默认收货地址，则返回第一个收货地址 */
    public static void selectDefaultAddress(AsyncHttpResponseHandler handler, RequestParams params) {
        client.get(UrlContants.getUrl(UrlContants.SELECTDEFAULTADDRESS), params, handler);
    }


    /* 根据用户id查找用户,用于更新用户信息 */
    public static void getUserInfo(AsyncHttpResponseHandler handler, RequestParams params) {
        client.get(UrlContants.getUrl(UrlContants.GETUSERINFO), params, handler);
    }

    /* 删除订单 */
    public static void deleteOrder(AsyncHttpResponseHandler handler, RequestParams params) {
        client.get(UrlContants.getUrl(UrlContants.DELETEORDER), params, handler);
    }

    /* 购物车结算 */
    public static void submitBasket(AsyncHttpResponseHandler handler, RequestParams params) {
        client.get(UrlContants.getUrl(UrlContants.SUBMITBASKTE), params, handler);
    }

    /* 立即购买 */
    public static void buyNow(AsyncHttpResponseHandler handler, RequestParams params) {
        client.get(UrlContants.getUrl(UrlContants.BUYNOW), params, handler);
    }

    /* 获取被屏蔽信息的舞友 */
    public static void getShieldNewsFriend(AsyncHttpResponseHandler handler, RequestParams params) {
        client.get(UrlContants.getUrl(UrlContants.SHIELDNEWSFRIEND), params, handler);
    }

    /* 获取被屏蔽信息的舞友 */
    public static void getBannarList(AsyncHttpResponseHandler handler, RequestParams params) {
        client.get(UrlContants.getUrl(UrlContants.BANNARLIST), params, handler);
    }

    /* 根据用户ID，查询订单个数 */
    public static void getSelectBillCount(AsyncHttpResponseHandler handler, RequestParams params) {
        client.get(UrlContants.getUrl(UrlContants.SELECTBILLCOUNT), params, handler);
    }

    /* 通讯录匹配，未注册不返回，注册之后判断是否为好友 */
    public static void contactsMatch(AsyncHttpResponseHandler handler, RequestParams params) {
        client.post(UrlContants.getUrl(UrlContants.CONTACTSMATCH), params, handler);
    }

    /* 添加武友 */
    public static void addFriend(AsyncHttpResponseHandler handler, RequestParams params) {
        client.post(UrlContants.getUrl(UrlContants.SAVEFRIEND), params, handler);
    }

    /* 根据教练的ID查询教练信息 */
    public static void getTrainerById(AsyncHttpResponseHandler handler, RequestParams params) {
        client.post(UrlContants.getUrl(UrlContants.SELECTTEACHERBYID), params, handler);
    }

    /* 根据私教ID获取私教视频 */
    public static void getVideoByTeacherId(AsyncHttpResponseHandler handler, RequestParams params) {
        client.post(UrlContants.getUrl(UrlContants.SELECTVIDEOBYTEACHERID), params, handler);
    }

    /* 根据私教ID获取私教课程 */
    public static void getLessonByTeacherId(AsyncHttpResponseHandler handler, RequestParams params) {
        client.post(UrlContants.getUrl(UrlContants.SELECTLESSONBYTEACHERID), params, handler);
    }

    /* 根据私教ID获取私教关注 */
    public static void getGuanZhuByTeacherId(AsyncHttpResponseHandler handler, RequestParams params) {
        client.post(UrlContants.getUrl(UrlContants.SELECTGUANZHUBYTEACHERID), params, handler);
    }

    /* 根据私教ID获取私教粉丝 */
    public static void getFenSiByTeacherId(AsyncHttpResponseHandler handler, RequestParams params) {
        client.post(UrlContants.getUrl(UrlContants.SELECTFENSIBYTEACHERID), params, handler);
    }

    /* 根据id判断是否为武友 true:是 false:不是 */
    public static void isWuYou(AsyncHttpResponseHandler handler, RequestParams params) {
        client.post(UrlContants.getUrl(UrlContants.ISWUYOU), params, handler);
    }

    /* 关注私教*/
    public static void guanzhuTeacher(AsyncHttpResponseHandler handler, RequestParams params) {
        client.post(UrlContants.getUrl(UrlContants.GUANZHUTEACHER), params, handler);
    }

    /* 取消关注私教*/
    public static void cguanzhuTeacher(AsyncHttpResponseHandler handler, RequestParams params) {
        client.post(UrlContants.getUrl(UrlContants.CGUANZHUTEACHER), params, handler);
    }

    /* 分页查询报名记录*/
    public static void selectJoinRecord(AsyncHttpResponseHandler handler, RequestParams params) {
        client.post(UrlContants.getUrl(UrlContants.SELECTJOINRECORD), params, handler);
    }

    /* 获取关于我们*/
    public static void getAboutUs(AsyncHttpResponseHandler handler) {
        client.post(UrlContants.getUrl(UrlContants.GETABOUTUS), handler);
    }
}
