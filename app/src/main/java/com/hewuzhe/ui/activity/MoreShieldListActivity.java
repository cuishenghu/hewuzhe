package com.hewuzhe.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.hewuzhe.R;
import com.hewuzhe.model.Friend;
import com.hewuzhe.model.Friend2;
import com.hewuzhe.model.ProductScore;
import com.hewuzhe.ui.adapter.DuiHuanProductListAdapter;
import com.hewuzhe.ui.adapter.MoreShieldListAdapter;
import com.hewuzhe.ui.base.BaseActivity2;
import com.hewuzhe.ui.http.EntityHandler;
import com.hewuzhe.ui.http.HttpErrorHandler;
import com.hewuzhe.ui.http.HttpUtils;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.MyCommonTitle;
import com.hewuzhe.view.MyRequestDailog;
import com.hewuzhe.view.XListView;
import com.hewuzhe.view.XListView.IXListViewListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/26 0026.
 */
public class MoreShieldListActivity extends BaseActivity2 {

    private MyCommonTitle myCommonTitle;
    private ListView mListView;
    private MoreShieldListAdapter moreShieldListAdapter;
    private List<Friend2> friends = new ArrayList<Friend2>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shield_list);

        initView();
        requestData();
    }

    /**
     * 初始化
     */
    public void initView() {
        myCommonTitle = (MyCommonTitle) findViewById(R.id.aci_mytitle);
        myCommonTitle.setTitle("屏蔽列表");

        mListView = (ListView) findViewById(R.id.list_shield);//listView
        mListView.setDividerHeight(0);
        mListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
//        mListView.setPullRefreshEnable(true);
//        mListView.setPullLoadEnable(true);
//        mListView.setXListViewListener(this);
//        mListView.setOnItemClickListener(this);

        moreShieldListAdapter = new MoreShieldListAdapter(MoreShieldListActivity.this, friends);
        mListView.setAdapter(moreShieldListAdapter);
    }
    /**
     * 请求数据
     */
    private void requestData() {
        RequestParams params = new RequestParams();
        MyRequestDailog.showDialog(MoreShieldListActivity.this,"正在加载");
        params.put("userId", new SessionUtil(MoreShieldListActivity.this).getUserId());//兑换状态
        HttpUtils.getShieldNewsFriend(res_getShieldNewsFriend,params);
    }
    AsyncHttpResponseHandler res_getShieldNewsFriend = new EntityHandler<Friend2>(Friend2.class) {
        @Override
        public void onReadSuccess(List<Friend2> list) {
            MyRequestDailog.closeDialog();
//            if (pageNo == 0) {
//                products.clear();
//            }
            friends.addAll(list);
            moreShieldListAdapter.notifyDataSetChanged();
        }
    };
//    /**
//     * 上拉刷新
//     */
//    @Override
//    public void onRefresh() {
//        pageNo = 0;
//        requestData();
//        onLoad();
//    }
//
//    /**
//     * 下拉加载
//     */
//    @Override
//    public void onLoadMore() {
//        pageNo += 1;
//        requestData();
//        onLoad();
//
//    }
//
//    /**
//     * 停止刷新/加载
//     */
//    public void onLoad() {
//        mListView.stopRefresh();
//        mListView.stopLoadMore();
//        mListView.setRefreshTime("刚刚");
//    }
//
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        startActivity(new Intent(MoreShieldListActivity.this, MyScoretDuiHuanSuccessInfoActivity.class).putExtra("product", products.get(position-1)));
//    }
}
