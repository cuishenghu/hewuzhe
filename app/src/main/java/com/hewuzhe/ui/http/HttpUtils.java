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

    /* 修改是否接收推送信息 */
    public static void changeTuiSong(AsyncHttpResponseHandler handler, RequestParams params) {
        client.get(UrlContants.getUrl(UrlContants.CHANGETUISONG), params, handler);
    }

    /* 根据用户ID，查询订单个数 */
    public static void getSelectBillCount(AsyncHttpResponseHandler handler, RequestParams params) {
        client.get(UrlContants.getUrl(UrlContants.SELECTBILLCOUNT), params, handler);
    }

}
