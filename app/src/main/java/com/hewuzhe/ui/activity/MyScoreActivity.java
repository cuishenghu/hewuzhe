package com.hewuzhe.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.hewuzhe.R;
import com.hewuzhe.model.ProductScore;
import com.hewuzhe.model.User;
import com.hewuzhe.ui.adapter.DuiHuanProductAdapter;
import com.hewuzhe.ui.adapter.EquipmentRecommendAdapter;
import com.hewuzhe.ui.base.BaseActivity2;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.http.EntityHandler;
import com.hewuzhe.ui.http.HttpErrorHandler;
import com.hewuzhe.ui.http.HttpUtils;
import com.hewuzhe.ui.http.UrlContants;
import com.hewuzhe.ui.widget.GlideCircleTransform;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.utils.Tools;
import com.hewuzhe.view.CircleImageView;
import com.hewuzhe.view.MyCommonTitle;
import com.hewuzhe.view.XListView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/26 0026.
 */
public class MyScoreActivity extends BaseActivity2 implements XListView.IXListViewListener {

    private MyCommonTitle myCommonTitle;
    private TextView tv_username, tv_user_score, tv_duihuan_jilu, tv_duihuan;
    private CircleImageView img_avatar;
    private XListView mListView;
    private DuiHuanProductAdapter productAdapter;
    private List<ProductScore> products = new ArrayList<ProductScore>();
    private Handler mHandler;

    //    private int[] pic = {R.drawable.icon_item_pic, R.drawable.icon_item_pic, R.drawable.icon_item_pic, R.drawable.icon_item_pic};
//    private String data[][] = {{"太极拳门票一张", "积分兑换：800", "剩余：50"}, {"螳螂拳门票一张", "积分兑换：500", "剩余：50"}, {"猴拳门票一张", "积分兑换：500", "剩余：50"}, {"咏春拳门票一张", "积分兑换：500", "剩余：50"}};
//    private SimpleAdapter simpleAdapter = null;
//    private List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    private int pageNo = 0;//页码
    private int pageSum = 10;//每页条数
    private RequestParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_score);

        initView();
        updateUserInfo();
        requestData();
    }

    /**
     * 初始化
     */
    public void initView() {
        myCommonTitle = (MyCommonTitle) findViewById(R.id.aci_mytitle);
        myCommonTitle.setTitle("兑换");
        mHandler = new Handler();
        img_avatar = (CircleImageView) findViewById(R.id.img_avatar);//头像

        tv_username = (TextView) findViewById(R.id.tv_username);//用户昵称
//        tv_username.setText(new SessionUtil(MyScoreActivity.this).getUser().NicName);
        tv_user_score = (TextView) findViewById(R.id.tv_user_score);//用户积分
//        tv_user_score.setText("积分：" + new SessionUtil(MyScoreActivity.this).getUser().Credit);
        tv_duihuan_jilu = (TextView) findViewById(R.id.tv_duihuan_jilu);//兑换记录
        mListView = (XListView) findViewById(R.id.list_product_duihuan);//listView

        setListener(tv_duihuan_jilu);

        mListView.setDividerHeight(0);
        mListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
        mListView.setXListViewListener(MyScoreActivity.this);


        productAdapter = new DuiHuanProductAdapter(MyScoreActivity.this, products);
        mListView.setAdapter(productAdapter);
//        for (int i = 0; i < data.length; i++) {
//            Map<String, String> map = new HashMap<String, String>();
//            map.put("pic", String.valueOf(this.pic[i]));
//            map.put("name", this.data[i][0]);
//            map.put("score", this.data[i][1]);
//            map.put("num", this.data[i][2]);
//            this.list.add(map);
//        }
//
//        this.simpleAdapter = new SimpleAdapter(this, list, R.layout.item_duihuan_product, new String[]{"pic", "name", "score", "num"},
//                new int[]{R.id.img_product_pic, R.id.tv_product_name, R.id.tv_product_need_score, R.id.tv_product_remain_num});
//        product_list.setAdapter(simpleAdapter);
//        product_list.setOnItemClickListener(new OnItemClickListener() {
//                                                public void onItemClick(AdapterView adapter, View view, int position, long id) {
//                                                    startActivity(new Intent(MyScoreActivity.this, OrderConfirmFirstActivity.class).putExtra("product", list.get(position).getClass()));
//                                                }
//                                            }
//        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUserInfo();
    }
    /**
     * 更新用户信息
     */
    public void updateUserInfo(){
    params = new RequestParams();
    params.put("userid",new SessionUtil(MyScoreActivity.this).getUserId());
    HttpUtils.getUserInfo(new HttpErrorHandler() {
        @Override
        public void onRecevieSuccess(JSONObject json) {
            JSONObject jsonObject = json.getJSONObject(UrlContants.jsonData);
            tv_user_score.setText("积分：" + jsonObject.getString("Credit"));
            tv_username.setText(jsonObject.getString("NicName"));
            ImageLoader.getInstance().displayImage(StringUtil.toString(UrlContants.IMAGE_URL + jsonObject.getString("PhotoPath"), "http://"), img_avatar);

        }
    }, params);
    }
    /**
     * 请求数据获取兑换商品列表
     */
//    startRowIndex//    maximumRows //    search//    categoryId//    isPriceAsc//    isSalesAsc//    isCommentAsc
//    isNewAsc//    isCredit//    isRecommend
//"startRowIndex=0&maximumRows=15&search=&categoryId=0&isSalesAsc=0&isPriceAsc=0&isNewAsc=0&isCommentAsc=0&isCredit=1&isRecommend=0"
    public void requestData() {
        params = new RequestParams();
        params.put("startRowIndex", pageNo * pageSum);//开始行索引
        params.put("maximumRows", pageSum);//每页条数
        params.put("search", "");//搜索内容
        params.put("categoryId", "0");//类别ID 不按照类别搜索传0
        params.put("isPriceAsc", "0");//是否价格升序 0：默认 1：升序 2：降序
        params.put("isSalesAsc", "0");// 是否销量升序 0：默认 1：升序 2：降序
        params.put("isCommentAsc", "0");//是否好评升序 0：默认 1：升序 2：降序
        params.put("isCredit", "1");//是否可以兑换 0：购买 1：兑换
        params.put("isNewAsc", "0");//是否最新升序 0：默认 1：升序 2：降序
        params.put("isRecommend", "0");//是否推荐 0：默认 1：推荐商品 2：不推荐
        RequestParams params1 = new RequestParams();
        HttpUtils.getProductForDuihuan(res_getProductForDuihuanList, params);
    }

    AsyncHttpResponseHandler res_getProductForDuihuanList = new EntityHandler<ProductScore>(ProductScore.class) {
        @Override
        public void onReadSuccess(List<ProductScore> list) {
            if (pageNo == 0) {
                products.clear();
            }
            products.addAll(list);
            productAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_duihuan_jilu:
                startActivity(new Intent(MyScoreActivity.this, MyScoretDuiHuanJiLuActivity.class));
                break;

        }
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pageNo = 0;
                requestData();
                onLoad();
            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pageNo += 1;
                requestData();
                onLoad();
            }
        }, 1000);
    }

    private void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime("刚刚");
    }
}
