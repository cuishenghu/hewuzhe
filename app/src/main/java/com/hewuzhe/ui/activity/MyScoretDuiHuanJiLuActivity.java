package com.hewuzhe.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.hewuzhe.R;
import com.hewuzhe.model.ProductScore;
import com.hewuzhe.ui.adapter.DuiHuanProductListAdapter;
import com.hewuzhe.ui.base.BaseActivity2;
import com.hewuzhe.ui.http.EntityHandler;
import com.hewuzhe.ui.http.HttpUtils;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.MyCommonTitle;
import com.hewuzhe.view.XListView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.widget.AdapterView.OnItemClickListener;

import com.hewuzhe.view.XListView.IXListViewListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/26 0026.
 */
public class MyScoretDuiHuanJiLuActivity extends BaseActivity2 implements IXListViewListener, OnItemClickListener {

    private MyCommonTitle myCommonTitle;
    private XListView mListView;
    private DuiHuanProductListAdapter productAdapter;
    private List<ProductScore> products = new ArrayList<ProductScore>();
    //    private int[] pic = {R.drawable.icon_item_pic, R.drawable.icon_item_pic, R.drawable.icon_item_pic, R.drawable.icon_item_pic};
//    private String data[][] = {{"太极拳门票一张", "积分兑换：800", "兑换成功"}, {"螳螂拳门票一张", "积分兑换：500", "兑换成功"}, {"猴拳门票一张", "积分兑换：500", "兑换成功"}, {"咏春拳门票一张", "积分兑换：500", "兑换成功"}};
//    private SimpleAdapter simpleAdapter = null;
//    private List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    private String score = "20000";
    private int pageNo = 0;//页码
    private int pageSum = 10;//每页条数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_exchange);

        initView();
        requestData();
        initData();
    }

    /**
     * 初始化
     */
    public void initView() {
        myCommonTitle = (MyCommonTitle) findViewById(R.id.aci_mytitle);
        myCommonTitle.setTitle("兑换记录");

        mListView = (XListView) findViewById(R.id.list_duihuan_jilu);//listView
        mListView.setDividerHeight(0);
        mListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
        mListView.setXListViewListener(this);
        mListView.setOnItemClickListener(this);
//        mListView = (XListView) findViewById(R.id.list_duihuan_jilu);//listView
//        for (int i = 0; i < data.length; i++) {
//            Map<String, String> map = new HashMap<String, String>();
//            map.put("pic", String.valueOf(this.pic[i]));
//            map.put("name", this.data[i][0]);
//            map.put("score", this.data[i][1]);
//            map.put("state", this.data[i][2]);
//            this.list.add(map);
//        }
//
//        this.simpleAdapter = new SimpleAdapter(this, list, R.layout.item_duihuan_jilu, new String[]{"pic", "name", "score", "state"},
//                new int[]{R.id.img_product_pic, R.id.tv_product_name, R.id.tv_product_need_score, R.id.tv_product_state});
//        mListView.setAdapter(simpleAdapter);
    }

    /**
     * 请求数据
     */
    private void requestData() {
        RequestParams params = new RequestParams();
        params.put("startRowIndex", pageNo * pageSum);// 开始行索引
        params.put("maximumRows", pageSum);//每页条数
        params.put("userId", new SessionUtil(MyScoretDuiHuanJiLuActivity.this).getUserId());//兑换状态
        params.put("state", "2");//兑换状态
        params.put("proNum", "1");// 兑换个数
        HttpUtils.getProductDuihuanList(res_getProductForDuihuanList, params);
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

    public void initData() {
        productAdapter = new DuiHuanProductListAdapter(MyScoretDuiHuanJiLuActivity.this, products);
        mListView.setAdapter(productAdapter);

    }

    /**
     * 上拉刷新
     */
    @Override
    public void onRefresh() {
        pageNo = 0;
        requestData();
        onLoad();
    }

    /**
     * 下拉加载
     */
    @Override
    public void onLoadMore() {
        pageNo += 1;
        requestData();
        onLoad();

    }

    /**
     * 停止刷新/加载
     */
    public void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime("刚刚");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(MyScoretDuiHuanJiLuActivity.this, MyScoretDuiHuanSuccessInfoActivity.class).putExtra("product", products.get(position-1)).putExtra("LiveryNo",products.get(position-1).getLiveryNo()));
    }
}
