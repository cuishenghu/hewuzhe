package com.hewuzhe.model;

import java.util.List;

/**
 * Created by xianguangjin on 16/1/18.
 */
public class Charge {


    /**
     * id : ch_mnTSuL0WH44Ca5aHy5qv50mH
     * object : charge
     * created : 1453094944
     * livemode : true
     * paid : false
     * refunded : false
     * app : app_qfD04CjHGu54aXXv
     * channel : alipay
     * order_no : 2016011800000036
     * client_ip : 127.0.0.1
     * amount : 120000
     * amount_settle : 120000
     * currency : cny
     * subject : 2
     * body : æ ¸æ­¦çä¼åè´­ä¹°
     * extra : {}
     * time_paid : null
     * time_expire : 1453181344
     * time_settle : null
     * transaction_no : null
     * refunds : {"object":"list","url":"/v1/charges/ch_mnTSuL0WH44Ca5aHy5qv50mH/refunds","has_more":false,"data":[]}
     * amount_refunded : 0
     * failure_code : null
     * failure_msg : null
     * metadata : {}
     * credential : {"object":"credential","alipay":{"orderInfo":"service=\"mobile.securitypay.pay\"&_input_charset=\"utf-8\"&notify_url=\"https%3A%2F%2Fapi.pingxx.com%2Fnotify%2Fcharges%2Fch_mnTSuL0WH44Ca5aHy5qv50mH\"&partner=\"2088121648755575\"&out_trade_no=\"2016011800000036\"&subject=\"2\"&body=\"æ ¸æ­¦ç\u009d\u0080ä¼\u009aå\u0091\u0098è´­ä¹°\"&total_fee=\"1200.00\"&payment_type=\"1\"&seller_id=\"2088121648755575\"&it_b_pay=\"2016-01-19 13:29:04\"&sign=\"FvFmQpnT5jIVWLUo926E%2Bp470asHRlMIxIe%2FqOCfgerYgqp0vUFRF%2B%2FfhrkzyLrBFNpQx8kOZfy1igEvBn3cD86BBknEMND%2BKjMjPPZ5QaSnBpWsnY%2Blx2np3pqv163w3skdsWe9v1pTfoHSlzlhEq3PJeeZgYggbiKM9KtqRyI%3D\"&sign_type=\"RSA\""}}
     * description : null
     */

    public String id;
    public String object;
    public int created;
    public boolean livemode;
    public boolean paid;
    public boolean refunded;
    public String app;
    public String channel;
    public String order_no;
    public String client_ip;
    public int amount;
    public int amount_settle;
    public String currency;
    public String subject;
    public String body;
    public ExtraEntity extra;
    public Object time_paid;
    public int time_expire;
    public Object time_settle;
    public Object transaction_no;
    /**
     * object : list
     * url : /v1/charges/ch_mnTSuL0WH44Ca5aHy5qv50mH/refunds
     * has_more : false
     * data : []
     */

    public RefundsEntity refunds;
    public int amount_refunded;
    public Object failure_code;
    public Object failure_msg;
    public MetadataEntity metadata;
    /**
     * object : credential
     * alipay : {"orderInfo":"service=\"mobile.securitypay.pay\"&_input_charset=\"utf-8\"&notify_url=\"https%3A%2F%2Fapi.pingxx.com%2Fnotify%2Fcharges%2Fch_mnTSuL0WH44Ca5aHy5qv50mH\"&partner=\"2088121648755575\"&out_trade_no=\"2016011800000036\"&subject=\"2\"&body=\"æ ¸æ­¦ç\u009d\u0080ä¼\u009aå\u0091\u0098è´­ä¹°\"&total_fee=\"1200.00\"&payment_type=\"1\"&seller_id=\"2088121648755575\"&it_b_pay=\"2016-01-19 13:29:04\"&sign=\"FvFmQpnT5jIVWLUo926E%2Bp470asHRlMIxIe%2FqOCfgerYgqp0vUFRF%2B%2FfhrkzyLrBFNpQx8kOZfy1igEvBn3cD86BBknEMND%2BKjMjPPZ5QaSnBpWsnY%2Blx2np3pqv163w3skdsWe9v1pTfoHSlzlhEq3PJeeZgYggbiKM9KtqRyI%3D\"&sign_type=\"RSA\""}
     */

    public CredentialEntity credential;
    public Object description;

    public static class ExtraEntity {
    }

    public static class RefundsEntity {
        public String object;
        public String url;
        public boolean has_more;
        public List<?> data;
    }

    public static class MetadataEntity {
    }

    public static class CredentialEntity {
        public String object;
        /**
         * orderInfo : service="mobile.securitypay.pay"&_input_charset="utf-8"&notify_url="https%3A%2F%2Fapi.pingxx.com%2Fnotify%2Fcharges%2Fch_mnTSuL0WH44Ca5aHy5qv50mH"&partner="2088121648755575"&out_trade_no="2016011800000036"&subject="2"&body="æ ¸æ­¦çä¼åè´­ä¹°"&total_fee="1200.00"&payment_type="1"&seller_id="2088121648755575"&it_b_pay="2016-01-19 13:29:04"&sign="FvFmQpnT5jIVWLUo926E%2Bp470asHRlMIxIe%2FqOCfgerYgqp0vUFRF%2B%2FfhrkzyLrBFNpQx8kOZfy1igEvBn3cD86BBknEMND%2BKjMjPPZ5QaSnBpWsnY%2Blx2np3pqv163w3skdsWe9v1pTfoHSlzlhEq3PJeeZgYggbiKM9KtqRyI%3D"&sign_type="RSA"
         */

        public AlipayEntity alipay;

        public static class AlipayEntity {
            public String orderInfo;
        }
    }

}
