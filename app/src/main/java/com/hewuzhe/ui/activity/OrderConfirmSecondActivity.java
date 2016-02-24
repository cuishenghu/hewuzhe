package com.hewuzhe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.hewuzhe.R;
import com.hewuzhe.model.ProductScore;
import com.hewuzhe.model.Site;
import com.hewuzhe.ui.base.BaseActivity2;
import com.hewuzhe.ui.http.HttpErrorHandler;
import com.hewuzhe.ui.http.HttpUtils;
import com.hewuzhe.ui.http.UrlContants;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.utils.Tools;
import com.hewuzhe.view.MyCommonTitle;
import com.hewuzhe.view.MyRequestDailog;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Administrator on 2016/1/25 0025.
 */
public class OrderConfirmSecondActivity extends BaseActivity2 {
    private MyCommonTitle myCommonTitle;
    private LinearLayout ll_money, ll_score, ll_no_address, ll_address;
    private TextView tv_username, tv_mobile, tv_address, tv_order_sum, tv_total_price, tv_submit, tv_postage_price, tv_postage, tv_my_score;
    private TextView tv_need_score, tv_product_name, tv_product_price, tv_product_sum;
    private ImageView img_product_pic;
    private String score;//用户积分>>>>>>>>从积分兑换商品跳转过来传用户积分,显示积分兑换功能,隐藏支付宝和微信支付功能
    private ProductScore product;
    private String deliveryId;//默认收货地址ID
    private Site site;
    private EditText ed_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        product = (ProductScore) getIntent().getSerializableExtra("product");//由订单中心的付款按钮传值过来的OrderGroup
        setContentView(R.layout.activity_order_confirm_second);

        initView();
        initData();
    }

    /**
     * 初始化
     */
    public void initView() {
        myCommonTitle = (MyCommonTitle) findViewById(R.id.aci_mytitle);
        myCommonTitle.setTitle("确认订单");
        /**
         * 如果是兑换跳过来的 score有值,支付不显示,对应积分栏目显示
         */
        ll_money = (LinearLayout) findViewById(R.id.ll_money);
        ll_score = (LinearLayout) findViewById(R.id.ll_score);

        ll_address = (LinearLayout) findViewById(R.id.ll_address);
        ll_no_address = (LinearLayout) findViewById(R.id.ll_no_address);
        tv_username = (TextView) findViewById(R.id.tv_username);//收货人
        tv_mobile = (TextView) findViewById(R.id.tv_mobile);//收货人手机
        tv_address = (TextView) findViewById(R.id.tv_address);//收货地址
        ed_content = (EditText) findViewById(R.id.ed_content);
        tv_product_name = (TextView) findViewById(R.id.tv_equip_name);//商品名称
        tv_product_price = (TextView) findViewById(R.id.tv_equip_price);//商品价格
        tv_product_sum = (TextView) findViewById(R.id.tv_equip_num);//商品订单数量
        img_product_pic = (ImageView) findViewById(R.id.img_equip_pic);//商品图片

        tv_my_score = (TextView) findViewById(R.id.tv_my_score);//我的积分
        tv_need_score = (TextView) findViewById(R.id.tv_need_score);//商品兑换需要积分

        tv_postage_price = (TextView) findViewById(R.id.tv_postage_price);//上部邮费
        tv_order_sum = (TextView) findViewById(R.id.tv_order_sum);//底部订单总数量
        tv_total_price = (TextView) findViewById(R.id.tv_total_price);//订单总价
        tv_postage = (TextView) findViewById(R.id.tv_postage);//底部邮寄费用
        tv_submit = (TextView) findViewById(R.id.tv_confirm_btn);//结算

        getDefaultAddress();// 查询默认收货地址，若没有默认收货地址，则返回第一个收货地址

        setListener(ll_no_address, ll_address, tv_submit);
        /**
         * ListView自适应高度
         */
//        StringUtil.listAutoHeight(order_list);
    }

    private void initData() {
//        tv_username.setText("");
//        tv_mobile.setText("");
//        tv_address.setText("");
        tv_order_sum.setText("共" + "1件商品");
        tv_total_price.setText("总积分：" + product.getCreditTotal());
        tv_my_score.setText(new SessionUtil(OrderConfirmSecondActivity.this).getUser().Credit + "分");//用户积分
        ImageLoader.getInstance().displayImage(StringUtil.toString(UrlContants.IMAGE_URL + product.getImagePath(), "http://"), img_product_pic);
        tv_product_name.setText(product.getName());//兑换商品名称
        tv_product_price.setText(product.getCreditTotal() + "积分");//兑换商品价格
        tv_product_sum.setText("x1");//兑换商品数量=========================此参数不正确,需要服务器修改
        tv_need_score.setText(product.getCreditTotal() + "分");//兑换商品需要的积分
        tv_postage_price.setText("邮费（到付）");
        tv_postage.setText("邮费（到付）");
    }

    /**
     * 查询默认收货地址，若没有默认收货地址，则返回第一个收货地址
     */
    public void getDefaultAddress() {
        RequestParams params = new RequestParams();
        params.put("userId", new SessionUtil(OrderConfirmSecondActivity.this).getUserId());
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

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.ll_no_address:
                startActivityForResult(new Intent(OrderConfirmSecondActivity.this, SiteActivity.class), 11);
                break;
            case R.id.ll_address:
                startActivityForResult(new Intent(OrderConfirmSecondActivity.this, SiteActivity.class), 11);
                break;
            case R.id.tv_confirm_btn:
                requestData();
                break;
        }
    }

    /**
     * 提交数据
     */
    private void requestData() {
        RequestParams params = new RequestParams();
        params.put("productId", product.getId());//产品ID
        params.put("num", "1");//兑换个数
        params.put("userId", new SessionUtil(OrderConfirmSecondActivity.this).getUserId());// 用户ID
        params.put("deliveryId", "1");//收货地址ID
        params.put("description", ed_content.getText().toString());// 订单备注 (因为页面此项传空字符串)
        HttpUtils.submitChangeNow(new HttpErrorHandler() {
            @Override
            public void onRecevieSuccess(JSONObject json) {
                Tools.toast(OrderConfirmSecondActivity.this, "兑换成功");
                finish();
            }
        }, params);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11) {
            if (resultCode == 9) {
                site = (Site) data.getSerializableExtra("site");
                tv_username.setText(site.ReceiverName);
                deliveryId = site.Id + "";
                getNewAddress();
            }
        }
    }
}
