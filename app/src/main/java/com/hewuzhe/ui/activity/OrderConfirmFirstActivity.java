package com.hewuzhe.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hewuzhe.model.Site;
import com.hewuzhe.ui.adapter.OrderConfirmFirstAdaptet;
import com.hewuzhe.ui.http.UrlContants;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.MyRequestDailog;
import com.pingplusplus.android.PaymentActivity;

import android.content.ComponentName;

import com.hewuzhe.R;
import com.hewuzhe.model.OrderContent;
import com.hewuzhe.model.OrderNumber;
import com.hewuzhe.ui.adapter.OrderDetailsItemAdaptet;
import com.hewuzhe.ui.base.BaseActivity2;
import com.hewuzhe.ui.http.AbstractHttpHandler;
import com.hewuzhe.ui.http.HttpErrorHandler;
import com.hewuzhe.ui.http.HttpUtils;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.utils.Tools;
import com.hewuzhe.view.MyCommonTitle;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.protocol.HTTP;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/25 0025.
 */
public class OrderConfirmFirstActivity extends BaseActivity2 {
    private MyCommonTitle myCommonTitle;
    private LinearLayout ll_no_address, ll_address, ll_alipay, ll_weixin, ll_money, ll_score;
    private TextView tv_username, tv_mobile, tv_address, tv_order_sum, tv_total_price, tv_submit, tv_postage_price, tv_postage, tv_my_score;
    private EditText ed_content;
    private ImageView img_weixin, img_alipay;
    private ListView order_list;
    private String type;
    private String areaId;//订单中心传过来收货地址ID
    private String score;//用户积分>>>>>>>>从积分兑换商品跳转过来传用户积分,显示积分兑换功能,隐藏支付宝和微信支付功能
    private static final int REQUEST_CODE_PAYMENT = 1;
    private String billId;//此处为购物车结算传过来的订单ID
    private static int pageNo = 0;
    private int pageSum = 10;
    private String basketDeatilIdList = "";//订单列表的商品ID拼接成的字符串
    private String state;//标示1为立即购买传过来的,2为购物车结算传过来的

    //    private int[] pic = {R.drawable.icon_item_pic, R.drawable.icon_item_pic};
//    private String data[][] = {{"男士哑铃一对10公斤", "规格：S/红色", "¥50", "x1"}, {"男士哑铃一对10公斤", "规格：S/红色", "¥50", "x1"}};
//    private SimpleAdapter simpleAdapter = null;
//    private List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    private ArrayList<OrderContent> list = new ArrayList<OrderContent>();
    private String total_number;//订单商品数量
    private String total_price;//订单总价
    private Site site;
    private OrderContent orderContent;
    private double totlePrice = 0.00;
    private String deliveryId;//默认收货地址ID
    //    private String postageState;//邮费状态0包邮  -1货到付款  其他显示邮费金额
    private String liveryPrice;
    private double x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        list=(List<OrderContent>)getIntent().getSerializableExtra("list");
        liveryPrice = getIntent().getStringExtra("LiveryPrice");
        list = (ArrayList<OrderContent>) getIntent().getSerializableExtra("list");
        state = (String) getIntent().getStringExtra("state");
        areaId = (String) getIntent().getSerializableExtra("areaId");
        billId = (String) getIntent().getSerializableExtra("billId");

        score = (String) getIntent().getSerializableExtra("score");//积分兑换商品跳转传值
//        order =   (ArrayList<OrderContent>)getIntent().getSerializableExtra("order");//由订单中心的付款按钮传值过来的OrderGroup
        total_number = (String) getIntent().getSerializableExtra("number");
        total_price = (String) getIntent().getSerializableExtra("price");
        setContentView(R.layout.activity_order_confirm_first);

        initView();
        requestData();
    }

    /**
     * 初始化
     */
    public void initView() {
        myCommonTitle = (MyCommonTitle) findViewById(R.id.aci_mytitle);
        myCommonTitle.setTitle("确认订单");
        /**
         * 如果areaId不为空根据areaId查询收货人信息
         */
        if (!StringUtil.isEmpty(areaId)) {
            getReceiverInfo();
        }
        /**
         * 如果是兑换跳过来的 score有值,支付不显示,对应积分栏目显示
         */
        ll_money = (LinearLayout) findViewById(R.id.ll_money);
        ll_score = (LinearLayout) findViewById(R.id.ll_score);
        if (!StringUtil.isEmpty(score)) {
            ll_money.setVisibility(View.GONE);
            ll_score.setVisibility(View.VISIBLE);
        }
        ll_no_address = (LinearLayout) findViewById(R.id.ll_no_address);//,没有默认地址
        ll_address = (LinearLayout) findViewById(R.id.ll_address);//有地址

        tv_username = (TextView) findViewById(R.id.tv_username);//收货人
        tv_mobile = (TextView) findViewById(R.id.tv_mobile);//收货人手机
        tv_address = (TextView) findViewById(R.id.tv_address);//收货地址
        tv_postage_price = (TextView) findViewById(R.id.tv_postage_price);//上部邮费
        tv_order_sum = (TextView) findViewById(R.id.tv_order_sum);//订单数量
        tv_total_price = (TextView) findViewById(R.id.tv_total_price);//订单总价
        tv_postage = (TextView) findViewById(R.id.tv_postage);//底部邮寄费用
        tv_submit = (TextView) findViewById(R.id.tv_submit_btn);//提交
        ll_alipay = (LinearLayout) findViewById(R.id.ll_zhifubao);
        ll_weixin = (LinearLayout) findViewById(R.id.ll_weixin);
        tv_my_score = (TextView) findViewById(R.id.tv_my_score);//我的积分
        ed_content = (EditText) findViewById(R.id.ed_content);//确认订单的备注信息
        img_weixin = (ImageView) findViewById(R.id.img_select_weixni);//选择微信支付
        img_alipay = (ImageView) findViewById(R.id.img_select_alipay);//选择支付宝支付

        order_list = (ListView) findViewById(R.id.list_order);

        getDefaultAddress();// 查询默认收货地址，若没有默认收货地址，则返回第一个收货地址

        setListener(ll_no_address, ll_address, ll_alipay, ll_weixin, tv_submit);
//        int i = tv_total_price.getText().toString().indexOf("¥");
//        int j = tv_total_price.getText().toString().length();
//        String totalprice = tv_total_price.getText().toString().substring(tv_total_price.getText().toString().indexOf("¥")+1, tv_total_price.getText().toString().length());
//        if (Integer.parseInt(totalprice) != 0) {
//            setListener();
//        }

//        if (product != null) {
//            list = new ArrayList<OrderChild>();
//            OrderChild orderChild = new OrderChild();
//            orderChild.setId(product.getId());
//            orderChild.setName(product.getName());
//            orderChild.setOrderNum(product.getDuiHuanNum());
//            orderChild.setPrice2("00");
//            list.add(orderChild);
//        }
//        if (list != null) {
//            OrderConfirmFirstAdaptet orderDetailsItemAdaptet = new OrderConfirmFirstAdaptet(OrderConfirmFirstActivity.this, list);
//            order_list.setAdapter(orderDetailsItemAdaptet);
//        } else {
        OrderConfirmFirstAdaptet orderDetailsItemAdaptet = new OrderConfirmFirstAdaptet(OrderConfirmFirstActivity.this, list);
        order_list.setAdapter(orderDetailsItemAdaptet);
        /**
         * ListView自适应高度
         */
        StringUtil.listAutoHeight(order_list);
    }

    /**
     * 根据areaId查询收货人信息
     */
    public void getReceiverInfo() {
        RequestParams params = new RequestParams();
        MyRequestDailog.showDialog(OrderConfirmFirstActivity.this, "正在加载");
        params.put("id", areaId);
        params.put("userId", new SessionUtil(OrderConfirmFirstActivity.this).getUserId());
        HttpUtils.getAddressByAreaId(new HttpErrorHandler() {
            @Override
            public void onRecevieSuccess(JSONObject json) {
                MyRequestDailog.closeDialog();
                JSONObject jsonObject = json.getJSONObject("data");
                tv_username.setText(jsonObject.getString("ReceiverName"));
                tv_mobile.setText(jsonObject.getString("Phone"));
                tv_address.setText(jsonObject.getString("Area") + jsonObject.getString("Address"));
            }
        }, params);
    }

    /**
     * 查询默认收货地址，若没有默认收货地址，则返回第一个收货地址
     */
    public void getDefaultAddress() {
        RequestParams params = new RequestParams();
        params.put("userId", new SessionUtil(OrderConfirmFirstActivity.this).getUserId());
        HttpUtils.selectDefaultAddress(new HttpErrorHandler() {
            @Override
            public void onRecevieSuccess(JSONObject json) {
                MyRequestDailog.closeDialog();
//        "Id": 15,// "Address": "崇文门西大街",  "AreaId": 3,    "Code": "100010",   "IsDefault": true,
//         "Phone": "13911045678", "ReceiverName": "古风",     "UserId": 86,    "Area": "北京市/北京市/东城区"
                /**
                 * 当有地址时显示,若没有默认收货地址，则返回第一个收货地址data为JSONObject
                 * 当没有地址时返回值data是个空字符串""
                 */
                if (StringUtil.isEmpty(json.getString(UrlContants.jsonData))) {
                    getNewAddress();
                } else if (json.getJSONObject(UrlContants.jsonData) != null) {
                    JSONObject jsonObject = json.getJSONObject(UrlContants.jsonData);
                    tv_username.setText(jsonObject.getString("ReceiverName"));
                    tv_mobile.setText(jsonObject.getString("Phone"));
                    tv_address.setText(jsonObject.getString("Area") + jsonObject.getString("Address"));
                    deliveryId = jsonObject.getString("Id");
                }
            }
        }, params);
    }


    /**
     * 如果没有默认的地址走此处
     */
    public void getNewAddress() {
        if (StringUtil.isEmpty(tv_username.getText().toString())) {
            ll_no_address.setVisibility(View.VISIBLE);
            ll_address.setVisibility(View.GONE);
        } else {
            ll_no_address.setVisibility(View.GONE);
            ll_address.setVisibility(View.VISIBLE);
            tv_username.setText(site.ReceiverName);
            tv_mobile.setText(site.Phone);
            tv_address.setText(site.Area + site.Address);
        }
    }

    /**
     * 请求数据
     */
    public void requestData() {
        tv_postage_price.setText("邮费：¥" + liveryPrice);
        tv_postage.setText("邮费(¥" + liveryPrice + ")");
        DecimalFormat df = new DecimalFormat("#####0.00");
        if (StringUtil.isEmpty(total_number)) {
            int number = 0;
            for (int i = 0; i < list.size(); i++) {
                number += Integer.parseInt(list.get(i).getNumber());
            }
            tv_order_sum.setText("共" + number + "件商品");
        } else {
            tv_order_sum.setText("共" + Integer.parseInt(total_number) + "件商品");
        }
        if (StringUtil.isEmpty(total_price)) {
            double price = 0.00;
            int number = 0;
            for (int i = 0; i < list.size(); i++) {
                price = Double.parseDouble(list.get(i).getProductPriceTotalPrice());//单价
                number = Integer.parseInt(list.get(i).getNumber());//个数
                totlePrice += price * number;
            }
            try {
                x = Double.parseDouble(liveryPrice);
            } catch (Exception e) {

            }
            total_price = totlePrice + x + "";
            tv_total_price.setText("总金额：¥" + df.format(Double.parseDouble(total_price)));
        } else {
            tv_total_price.setText("总金额：¥" + df.format(Double.parseDouble(total_price)));
        }
//        int postage = Integer.parseInt(order.getPostage());
//        double postage = 0.00;
//        for (int i = 0; i < list.size(); i++) {
//            postageState = list.get(i).getLiveryPrice();
//            if (postageState.equals("0")) break;
//            if (postageState.equals("-1")) break;
//            list.get(i).getLiveryPrice();
//        }
        img_alipay.setSelected(true);
        type = "alipay";

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.ll_no_address:
                startActivityForResult(new Intent(OrderConfirmFirstActivity.this, SiteActivity.class), 11);
                break;
            case R.id.ll_address:
                startActivityForResult(new Intent(OrderConfirmFirstActivity.this, SiteActivity.class), 11);
                break;
            case R.id.ll_zhifubao:
                img_alipay.setSelected(true);
                img_weixin.setSelected(false);
                type = "alipay";
                break;
            case R.id.ll_weixin:
                img_alipay.setSelected(false);
                img_weixin.setSelected(true);
                type = "wx";
                break;
            case R.id.tv_submit_btn:
                if (StringUtil.isEmpty(deliveryId)) {
                    Tools.toast(OrderConfirmFirstActivity.this, "请先添加收货地址");
                    break;
                }
                DecimalFormat df = new DecimalFormat("#####0.00");
                if (df.format(Double.parseDouble(total_price)).equals("0.00")) {
                    break;
                }
                if (!StringUtil.isEmpty(state)) {
                    if (Integer.parseInt(state) == 2) {
//                    userId 用户ID
//                    deliveryId 收货地址ID
//                    description 订单备注
//                    basketDeatilIdList 购物车明细ID 用“&”拼接
                        for (int i = 0; i < list.size(); i++) {
                            String productId = list.get(i).getId();
                            basketDeatilIdList = basketDeatilIdList + productId + "&";
                        }
                        basketDeatilIdList = basketDeatilIdList.substring(0, basketDeatilIdList.length() - 1);
                        submitBasket();
                    } else if (Integer.parseInt(state) == 1) {
                        for (int i = 0; i < list.size(); i++) {
                            buyNow(i);
                        }
                    }
                } else {
//                    billId = order.getId();
                    confirmSubmitCharge(billId);
                }
                break;
        }
    }

    /**
     * 立即购买
     */
    private void buyNow(int i) {
        RequestParams params = new RequestParams();
        params.put("userId", new SessionUtil(OrderConfirmFirstActivity.this).getUserId());//用户ID
        params.put("productId", list.get(i).getId());//商品ID
        params.put("num", list.get(i).getNumber());//个数
        params.put("priceId", list.get(i).getProductPriceId());//  价格ID
        params.put("price", list.get(i).getProductPriceTotalPrice());// 商品单价
        params.put("deliveryId", deliveryId);
        params.put("description", ed_content.getText().toString());//订单备注
        HttpUtils.buyNow(new HttpErrorHandler() {
            @Override
            public void onRecevieSuccess(JSONObject json) {
                JSONObject jsonObject = json.getJSONObject(UrlContants.jsonData);
                billId = jsonObject.getString("id");
                confirmSubmitCharge(billId);
            }
        }, params);
    }

    /**
     * 购物车结算
     */
    private void submitBasket() {
        RequestParams params = new RequestParams();
        params.put("userId", new SessionUtil(OrderConfirmFirstActivity.this).getUserId());
        params.put("deliveryId", deliveryId);
        params.put("description", ed_content.getText().toString());
        params.put("basketDeatilIdList", basketDeatilIdList);
        HttpUtils.submitBasket(new HttpErrorHandler() {
            @Override
            public void onRecevieSuccess(JSONObject json) {
                JSONObject jsonObject = json.getJSONObject(UrlContants.jsonData);
                billId = jsonObject.getString("id");
                confirmSubmitCharge(billId);
            }
        }, params);
    }

    /**
     * 获取Charge
     */
    private void confirmSubmitCharge(String billId) {
        RequestParams params = new RequestParams();
        params.put("userid", new SessionUtil(OrderConfirmFirstActivity.this).getUserId());//用户ID====================    new SessionUtil(OrderConfirmFirstActivity.this).getUserId()
        params.put("channel", type);//支付方式
        String o = total_price;
        params.put("amount", (int) (Double.parseDouble(total_price) * 100));//金额
//                params.put("amount", 1);//金额
        params.put("description", "是电风扇的");//描述
        params.put("billId", billId);//订单号
        params.put("flg", "1");//flg 类型 0：充值会员 1：购买商品
        HttpUtils.confirmSubmitCharge(new AbstractHttpHandler() {

            @Override
            public void onJsonSuccess(JSONObject json) {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                super.onSuccess(statusCode, headers, responseBody);
                String failure_msg = null;
                JSONObject datas = null;
                try {
                    String responseString = new String(responseBody, HTTP.UTF_8);
                    JSONObject jsonData = JSONObject.parseObject(responseString);
                    datas = jsonData.getJSONObject("datas");
                    if (datas == null) {
                        Intent intent = new Intent();
                        String packageName = getPackageName();
                        ComponentName componentName = new ComponentName(packageName, packageName + ".wxapi.WXPayEntryActivity");
                        intent.setComponent(componentName);
                        intent.putExtra(PaymentActivity.EXTRA_CHARGE, responseString);
                        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
                    } else {
                        Tools.toast(OrderConfirmFirstActivity.this, "支付失败");
                    }

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "请求失败", Toast.LENGTH_LONG).show();
            }
        }, params);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //支付页面返回处理
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
            /* 处理返回值
             * "success" - payment succeed
             * "fail"    - payment failed
             * "cancel"  - user canceld
             * "invalid" - payment plugin not installed
             */
//                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
//                String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
//                showMsg(result, errorMsg, extraMsg);
                if (result.equals("success")) {
                    Tools.toast(this, "您已经付款成功");
                    startActivity(new Intent(OrderConfirmFirstActivity.this, OrderCenterActivity2.class).putExtra("mType", 2));
//                    reflush();
                    if (OrderCenterActivity2.OrderCenterActivity2 != null) {
                        OrderCenterActivity2.OrderCenterActivity2.finish();
                    }
                    finish();
                } else if (result.equals("fail")) {
                    Tools.toast(this, "支付失败，请重试！");
//                    setResult(RESULT_OK);
                    startActivity(new Intent(OrderConfirmFirstActivity.this, OrderCenterActivity2.class).putExtra("mType", 1));
                    if (OrderCenterActivity2.OrderCenterActivity2 != null) {
                        OrderCenterActivity2.OrderCenterActivity2.finish();
                    }
//                    reflush();
                    finish();
                } else if (result.equals("invalid")) {
                    Tools.toast(this, "支付失败，请重试！");
//                    setResult(99, new Intent().putExtra("mType", 2 + ""));
                    startActivity(new Intent(OrderConfirmFirstActivity.this, OrderCenterActivity2.class).putExtra("mType", 1));
                    if (OrderCenterActivity2.OrderCenterActivity2 != null) {
                        OrderCenterActivity2.OrderCenterActivity2.finish();
                    }
//                    reflush();
                    finish();
                } else if (result.equals("cancel")) {
                    Tools.toast(this, "取消支付！");
//                    setResult(RESULT_OK);
                    startActivity(new Intent(OrderConfirmFirstActivity.this, OrderCenterActivity2.class).putExtra("mType", 1));
                    if (OrderCenterActivity2.OrderCenterActivity2 != null) {
                        OrderCenterActivity2.OrderCenterActivity2.finish();
                    }
//                      reflush();
                    finish();
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    Tools.toast(this, "支付取消！");
//                    setResult(RESULT_OK);
                    startActivity(new Intent(OrderConfirmFirstActivity.this, OrderCenterActivity2.class).putExtra("mType", 1));
                    if (OrderCenterActivity2.OrderCenterActivity2 != null) {
                        OrderCenterActivity2.OrderCenterActivity2.finish();
                    }
//                    reflush();
                    finish();
                }
            }
        } else if (requestCode == 11) {
            if (resultCode == 9) {
                site = (Site) data.getSerializableExtra("site");
                tv_username.setText(site.ReceiverName);
                deliveryId = site.Id + "";
                getNewAddress();
            }
        }
    }

    public void reflush() {
        AlertDialog.Builder builder = new AlertDialog.Builder(OrderConfirmFirstActivity.this);
        builder.setMessage("取消成功，请刷新该页面查看剩余订单");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
