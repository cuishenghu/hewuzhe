package com.hewuzhe.ui.activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.view.MyCommonTitle;
import com.loopj.android.http.RequestParams;

import java.text.DateFormat;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/1/26 0026.
 */
public class MyScoretDuiHuanSuccessInfoActivity extends BaseActivity2 {

    private MyCommonTitle myCommonTitle;
    @Bind(R.id.viewflow)
    ViewFlow mViewFlow;
    @Bind(R.id.viewflowindic)
    CircleFlowIndicator mFlowIndicator;
    @Bind(R.id.framelayout)
    FrameLayout framelayout;
    private TextView tv_product_name, tv_product_score, tv_product_person, tv_product_remain, tv_end_time, tv_content, tv_check_logtic;
    private ProductScore product;
    private String productId;
    private int mCurrPos;
    private ViewFlipper notice_vf;
    ArrayList<String> imageUrlList = new ArrayList<String>();
    ArrayList<String> linkUrlArray = new ArrayList<String>();
    ArrayList<String> titleList = new ArrayList<String>();
    private String liveryNo;
    private String billDetailId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        liveryNo=getIntent().getStringExtra("LiveryNo");//物流编号为空时显示正在发货,否则为查看物流
        billDetailId=getIntent().getStringExtra("billDetailId");//兑换成功后传过来的ID
        product = (ProductScore) getIntent().getSerializableExtra("product");
        setContentView(R.layout.activity_point_exchange_success_info);
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
        tv_end_time = (TextView) findViewById(R.id.tv_end_time);//商品到期时间
        tv_content = (TextView) findViewById(R.id.tv_content);//商品介绍内容
        tv_check_logtic = (TextView) findViewById(R.id.pro_buy_now);//查看物流

        setListener(tv_check_logtic);


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
                /**
                 * 物流编号为空时显示正在发货,否则为查看物流
                 */
                if(StringUtil.isEmpty(liveryNo)){
                    AlertDialog.Builder builder=new Builder(MyScoretDuiHuanSuccessInfoActivity.this);
                    builder.setTitle("温馨提示");
                    builder.setMessage("该商品还未发货!");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }else{
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.kuaidi100.com/index_all.html?type="+product.getLiveryType()+"&postid="+product.getLiveryNo())));
                }
                break;
        }
    }

    /**
     * 请求数据
     */
    public void requestData() {
        RequestParams params = new RequestParams();
        params.put("productId", product.getProductId() == null ? product.getId() : product.getProductId());//    productId 产品ID
        params.put("userId", new SessionUtil(MyScoretDuiHuanSuccessInfoActivity.this).getUserId());//    userId 用户ID
        params.put("billDetailId", billDetailId==null?product.getBillDetailId():billDetailId);// 兑换记录的ID
        HttpUtils.getChangeInfo(new HttpErrorHandler() {
            @Override
            public void onRecevieSuccess(JSONObject json) {
//              data":{"Id":6,"CreditTotal":0,"Name":"测试产品以","Content":"测试产品一详情描述","CreditNum":3,"StockNum":0}}
                JSONObject jsonObject = json.getJSONObject(UrlContants.jsonData);
                tv_product_name.setText(jsonObject.getString("Name"));
                tv_product_score.setText(jsonObject.getString("CreditTotal") + "积分");
                tv_product_person.setText(jsonObject.getString("CreditNum") + "人已兑换");
                tv_product_remain.setText("剩余：" + jsonObject.getString("StockNum")+"件");
                tv_content.setText(jsonObject.getString("Content"));
                tv_end_time.setText("兑换时间："+jsonObject.getString("OperateTime"));

            }
        }, params);

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
