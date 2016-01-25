package com.hewuzhe.model;

import java.util.List;

/**
 * Created by xianguangjin on 16/1/18.
 */
public class Charge {


    /**
     * id : ch_KK84e5G8mD8K0Kib10bzLe50
     * object : charge
     * created : 1453691799
     * livemode : true
     * paid : false
     * refunded : false
     * app : app_qfD04CjHGu54aXXv
     * channel : wx
     * order_no : 2016012500000028
     * client_ip : 127.0.0.1
     * amount : 120000
     * amount_settle : 120000
     * currency : cny
     * subject : 46
     * body : 1
     * extra : {}
     * time_paid : null
     * time_expire : 1453698999
     * time_settle : null
     * transaction_no : null
     * refunds : {"object":"list","url":"/v1/charges/ch_KK84e5G8mD8K0Kib10bzLe50/refunds","has_more":false,"data":[]}
     * amount_refunded : 0
     * failure_code : null
     * failure_msg : null
     * metadata : {}
     * credential : {"object":"credential","wx":{"appId":"wxb1fb6e1f1f47c07f","partnerId":"1299611201","prepayId":"wx20160125111640a754abd0300938887671","nonceStr":"06125c6f8bca44c46ba8f2e51805694b","timeStamp":1453691800,"packageValue":"Sign=WXPay","sign":"5A0886D2DFC5C771FAB28924C4F1CF3B"}}
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
     * url : /v1/charges/ch_KK84e5G8mD8K0Kib10bzLe50/refunds
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
     * wx : {"appId":"wxb1fb6e1f1f47c07f","partnerId":"1299611201","prepayId":"wx20160125111640a754abd0300938887671","nonceStr":"06125c6f8bca44c46ba8f2e51805694b","timeStamp":1453691800,"packageValue":"Sign=WXPay","sign":"5A0886D2DFC5C771FAB28924C4F1CF3B"}
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
         * appId : wxb1fb6e1f1f47c07f
         * partnerId : 1299611201
         * prepayId : wx20160125111640a754abd0300938887671
         * nonceStr : 06125c6f8bca44c46ba8f2e51805694b
         * timeStamp : 1453691800
         * packageValue : Sign=WXPay
         * sign : 5A0886D2DFC5C771FAB28924C4F1CF3B
         */

        public WxEntity wx;

        public static class WxEntity {
            public String appId;
            public String partnerId;
            public String prepayId;
            public String nonceStr;
            public int timeStamp;
            public String packageValue;
            public String sign;
        }
    }
}
