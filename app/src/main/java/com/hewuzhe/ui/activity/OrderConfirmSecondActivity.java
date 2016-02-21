package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.hewuzhe.R;
import com.hewuzhe.model.ProductScore;
import com.hewuzhe.ui.base.BaseActivity2;
import com.hewuzhe.ui.http.HttpErrorHandler;
import com.hewuzhe.ui.http.HttpUtils;
import com.hewuzhe.ui.http.UrlContants;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.utils.Tools;
import com.hewuzhe.view.MyCommonTitle;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Administrator on 2016/1/25 0025.
 */
public class OrderConfirmSecondActivity extends BaseActivity2 {
    private MyCommonTitle myCommonTitle;
    private LinearLayout ll_money, ll_score;
    private TextView tv_username, tv_mobile, tv_address, tv_order_sum, tv_total_price, tv_submit, tv_postage_price, tv_postage, tv_my_score;
    private TextView tv_need_score, tv_product_name, tv_product_price, tv_product_sum;
    private ImageView img_product_pic;
    private String score;//用户积分>>>>>>>>从积分兑换商品跳转过来传用户积分,显示积分兑换功能,隐藏支付宝和微信支付功能
    private ProductScore product;

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
        tv_username = (TextView) findViewById(R.id.tv_username);//收货人
        tv_mobile = (TextView) findViewById(R.id.tv_mobile);//收货人手机
        tv_address = (TextView) findViewById(R.id.tv_address);//收货地址

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

        setListener(tv_submit);
        /**
         * ListView自适应高度
         */
//        StringUtil.listAutoHeight(order_list);
    }

    private void initData() {
//        tv_username.setText("");
//        tv_mobile.setText("");
//        tv_address.setText("");
        tv_order_sum.setText("共" + product.getNumber() + "件商品");
        tv_total_price.setText("总金额：¥0.00");
        tv_my_score.setText(new SessionUtil(OrderConfirmSecondActivity.this).getUser().Credit + "分");//用户积分
        ImageLoader.getInstance().displayImage(StringUtil.toString(UrlContants.IMAGE_URL + product.getImagePath(), "http://"), img_product_pic);
        tv_product_name.setText(product.getName());//兑换商品名称
        tv_product_price.setText(product.getPrice());//兑换商品价格
        tv_product_sum.setText(product.getNumber());//兑换商品数量=========================此参数不正确,需要服务器修改
        tv_need_score.setText(product.getCreditTotal()+"分");//兑换商品需要的积分
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
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
        params.put("description", "");// 订单备注 (因为页面此项传空字符串)
        HttpUtils.submitChangeNow(new HttpErrorHandler() {
            @Override
            public void onRecevieSuccess(JSONObject json) {
                Tools.toast(OrderConfirmSecondActivity.this, "兑换成功");
                finish();
            }
        }, params);

    }
}
