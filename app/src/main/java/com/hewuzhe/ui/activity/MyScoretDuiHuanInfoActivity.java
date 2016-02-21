package com.hewuzhe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.alibaba.fastjson.JSONObject;
import com.hewuzhe.R;
import com.hewuzhe.banner.CircleFlowIndicator;
import com.hewuzhe.banner.ImagePagerAdapter;
import com.hewuzhe.banner.ViewFlow;
import com.hewuzhe.model.ProductScore;
import com.hewuzhe.ui.base.BaseActivity2;
import com.hewuzhe.ui.http.HttpErrorHandler;
import com.hewuzhe.ui.http.HttpUtils;
import com.hewuzhe.ui.http.UrlContants;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.Tools;
import com.hewuzhe.view.MyCommonTitle;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/1/26 0026.
 */
public class MyScoretDuiHuanInfoActivity extends BaseActivity2 {

    private MyCommonTitle myCommonTitle;
    @Bind(R.id.viewflow)
    ViewFlow mViewFlow;
    @Bind(R.id.viewflowindic)
    CircleFlowIndicator mFlowIndicator;
    @Bind(R.id.framelayout)
    FrameLayout framelayout;
    private TextView tv_product_name, tv_product_score, tv_product_person, tv_product_remain, tv_content, tv_submit;
    private ProductScore product;
    private String myScore;
    private int mCurrPos;
    private ViewFlipper notice_vf;
    ArrayList<String> imageUrlList = new ArrayList<String>();
    ArrayList<String> linkUrlArray = new ArrayList<String>();
    ArrayList<String> titleList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myScore = (String) getIntent().getSerializableExtra("myScore");//此积分是用户积分-兑换需要的积分的值
        product = (ProductScore) getIntent().getSerializableExtra("product");
        setContentView(R.layout.activity_point_exchange_info);
        ButterKnife.bind(this);

        initView();
        requestData();
    }

    /**
     * 初始化
     */
    public void initView() {
        myCommonTitle = (MyCommonTitle) findViewById(R.id.aci_mytitle);
        myCommonTitle.setTitle("兑换记录");
        tv_product_name = (TextView) findViewById(R.id.tv_product_name);//商品名称
        tv_product_score = (TextView) findViewById(R.id.tv_product_need_score);//商品兑换需要的积分
        tv_product_person = (TextView) findViewById(R.id.tv_product_person);//商品已经兑换的人数
        tv_product_remain = (TextView) findViewById(R.id.tv_product_remain_num);//商品的余量
        tv_content = (TextView) findViewById(R.id.tv_content);//商品介绍内容
        tv_submit = (TextView) findViewById(R.id.pro_buy_now);//立即兑换

        //banner轮播图
//        imageUrlList.add("http://image17-c.poco.cn/mypoco/myphoto/20160108/21/5598520120160108215204039_640.jpg");
        imageUrlList.add(UrlContants.IMAGE_URL + product.getImagePath());

        linkUrlArray.add("");
        linkUrlArray.add("");

        titleList.add("图片信息");
        titleList.add("图片信息");

        ViewGroup.LayoutParams para;
        para = framelayout.getLayoutParams();

        initBanner(imageUrlList);

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.pro_buy_now:
                startActivity(new Intent(MyScoretDuiHuanInfoActivity.this, OrderConfirmSecondActivity.class).putExtra("product", product));
                finish();
                break;
        }
    }

    /**
     * 请求数据
     */
    public void requestData() {
        RequestParams params = new RequestParams();
        params.put("productId", product.getId());//    productId 产品ID
        params.put("userId", new SessionUtil(MyScoretDuiHuanInfoActivity.this).getUserId());//    userId 用户ID
        params.put("billDetailId", "0");// 兑换记录的ID
        HttpUtils.getChangeInfo(new HttpErrorHandler() {
            @Override
            public void onRecevieSuccess(JSONObject json) {
//              data":{"Id":6,"CreditTotal":0,"Name":"测试产品以","Content":"测试产品一详情描述","CreditNum":3,"StockNum":0}}
                JSONObject jsonObject = json.getJSONObject(UrlContants.jsonData);
                tv_product_name.setText(jsonObject.getString("Name"));
                tv_product_score.setText(jsonObject.getString("CreditTotal") + "积分");
                tv_product_person.setText(jsonObject.getString("CreditNum") + "人已兑换");
                tv_product_remain.setText("剩余：" + jsonObject.getString("StockNum") + "件");
                tv_content.setText(jsonObject.getString("Content"));

            }
        }, params);
        /**
         * 判断用户积分<商品兑换所需要的积分或者商品库存不足,兑换按钮背景为灰色,且不能使用
         */
        String num=product.getStockNum();
        String need_score=tv_product_score.getText().toString();
        if (Integer.parseInt(myScore) < Integer.parseInt(need_score.substring(0, need_score.length()-2))) {
            Tools.toast(MyScoretDuiHuanInfoActivity.this, "您的积分不足!");
            tv_submit.setBackgroundResource(R.color.colorBg);
            tv_submit.setText("积分不足");
        } else if (Integer.parseInt(product.getStockNum()) < 1) {
            Tools.toast(MyScoretDuiHuanInfoActivity.this, "您兑换的商品库存不足!");
            tv_submit.setBackgroundResource(R.color.colorBg);
        } else {
            setListener(tv_submit);
        }
    }

    private void moveNext() {
        setView(this.mCurrPos, this.mCurrPos + 1);
        this.notice_vf.setInAnimation(this, R.anim.in_bottomtop);
        this.notice_vf.setOutAnimation(this, R.anim.out_bottomtop);
        this.notice_vf.showNext();
    }

    private void setView(int curr, int next) {

        View noticeView = getLayoutInflater().inflate(R.layout.notice_item, null);
        TextView notice_tv = (TextView) noticeView.findViewById(R.id.notice_tv);
        if ((curr < next) && (next > (titleList.size() - 1))) {
            next = 0;
        } else if ((curr > next) && (next < 0)) {
            next = titleList.size() - 1;
        }
        notice_tv.setText(titleList.get(next));
        notice_tv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
            }
        });
        if (notice_vf.getChildCount() > 1) {
            notice_vf.removeViewAt(0);
        }
        notice_vf.addView(noticeView, notice_vf.getChildCount());
        mCurrPos = next;

    }

    private void initBanner(ArrayList<String> imageUrlList) {

        mViewFlow.setAdapter(new ImagePagerAdapter(this, imageUrlList, linkUrlArray, titleList).setInfiniteLoop(true));
        mViewFlow.setmSideBuffer(imageUrlList.size()); // 实际图片张数，
        // 我的ImageAdapter实际图片张数为3

        mViewFlow.setFlowIndicator(mFlowIndicator);
        mViewFlow.setTimeSpan(4500);
        mViewFlow.setSelection(imageUrlList.size() * 1000); // 设置初始位置
        mViewFlow.startAutoFlowTimer(); // 启动自动播放
    }
}
