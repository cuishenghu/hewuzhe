package com.hewuzhe.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.model.OrderNumber;
import com.hewuzhe.ui.adapter.OrderGroupAdapter2;
import com.hewuzhe.ui.base.BaseActivity2;
import com.hewuzhe.ui.http.EntityHandler;
import com.hewuzhe.ui.http.HttpUtils;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.MyCommonTitle;
import com.hewuzhe.view.MyRequestDailog;
import com.hewuzhe.view.XListView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/26 0026.
 */
public class OrderCenterActivity2 extends BaseActivity2 implements XListView.IXListViewListener {
    private static int pageSum = 5;// perpage默认每页显示5条信息
    private int pageNo = 0;// 当前显示的页面
    private int mType = 1;// 1、待付款 2、待发货 3、待收货 4、已收货
    private MyCommonTitle myCommonTitle;
    private RelativeLayout ll_wait_to_pay, ll_wait_to_send, ll_wait_to_receive, ll_received;
    private TextView tv_wait_to_pay_count, tv_wait_to_send_count, tv_wait_to_receive_count, tv_received_count;//统计订单数
    private TextView tv_wait_to_pay, tv_wait_to_send, tv_wait_to_receive, tv_received;//
    private XListView mListView;
    private OrderGroupAdapter2 orderGroupAdapter2;
    private ArrayList<OrderNumber> orders = new ArrayList<OrderNumber>();
    private Handler mHandler;
    private int i = 1;
    private RequestParams params;
    static Activity OrderCenterActivity2;
    private String recordcount, recordcount1, recordcount2, recordcount3, recordcount4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OrderCenterActivity2 = this;
        mType = getIntent().getIntExtra("mType", 0);
        setContentView(R.layout.activity_order_center2);
        initView();
        initData();
//        requestData();
    }

    /**
     * 初始化
     */
    public void initView() {
        myCommonTitle = (MyCommonTitle) findViewById(R.id.aci_mytitle);
        myCommonTitle.setTitle("确认订单");
        myCommonTitle.setListener(this, null, null);

        ll_wait_to_pay = (RelativeLayout) findViewById(R.id.ll_wait_to_pay);
        ll_wait_to_send = (RelativeLayout) findViewById(R.id.ll_wait_to_send);
        ll_wait_to_receive = (RelativeLayout) findViewById(R.id.ll_wait_to_receive);
        ll_received = (RelativeLayout) findViewById(R.id.ll_received);

//        tv_wait_to_pay_count = (TextView) findViewById(R.id.tv_wait_to_pay_count);//统计订单中心待付款数量
//        tv_wait_to_send_count = (TextView) findViewById(R.id.tv_wait_to_send_count);//统计订单中心已付款数量
//        tv_wait_to_receive_count = (TextView) findViewById(R.id.tv_wait_to_receive_count);//统计订单中心待收货数量
//        tv_received_count = (TextView) findViewById(R.id.tv_received_count);//统计订单中心已收货数量

        tv_wait_to_pay = (TextView) findViewById(R.id.tv_wait_to_pay);//统计订单中心待付款数量
        tv_wait_to_send = (TextView) findViewById(R.id.tv_wait_to_send);//统计订单中心已付款数量
        tv_wait_to_receive = (TextView) findViewById(R.id.tv_wait_to_receive);//统计订单中心待收货数量
        tv_received = (TextView) findViewById(R.id.tv_received);//统计订单中心已收货数量

        mListView = (XListView) findViewById(R.id.list_order);

        setListener(ll_wait_to_pay, ll_wait_to_send, ll_wait_to_receive, ll_received);

        mListView.setDividerHeight(0);
        mListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
        mListView.setXListViewListener(this);

        mHandler = new Handler();
        if (mType != 0) {
            requestDataByType();
        } else {
            mType = 1;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
//        getDataFromI(i);
//        requestRecordCountData(i);
        requestData(mType);
    }

    public void requestDataByType() {
        if (mType == 1) {
            tv_wait_to_pay.setTextColor(getResources().getColor(R.color.colorYellow));
            tv_wait_to_send.setTextColor(Color.WHITE);
            tv_received.setTextColor(Color.WHITE);
            tv_wait_to_receive.setTextColor(Color.WHITE);
//            tv_wait_to_pay.setSelected(true);
            initData();
//            requestData(mType);
        } else if (mType == 2) {
            tv_wait_to_send.setTextColor(getResources().getColor(R.color.colorYellow));
            tv_received.setTextColor(Color.WHITE);
            tv_wait_to_receive.setTextColor(Color.WHITE);
            tv_wait_to_pay.setTextColor(Color.WHITE);
//            tv_wait_to_send.setSelected(true);
            initData();
//            requestData(mType);
        } else if (mType == 3) {
            tv_wait_to_receive.setTextColor(getResources().getColor(R.color.colorYellow));
            tv_wait_to_pay.setTextColor(Color.WHITE);
            tv_wait_to_send.setTextColor(Color.WHITE);
            tv_received.setTextColor(Color.WHITE);
//            tv_wait_to_receive.setSelected(true);
            initData();
//            requestData(mType);
        } else if (mType == 4) {
            tv_received.setTextColor(getResources().getColor(R.color.colorYellow));
            tv_wait_to_receive.setTextColor(Color.WHITE);
            tv_wait_to_pay.setTextColor(Color.WHITE);
            tv_wait_to_send.setTextColor(Color.WHITE);
//            tv_received.setSelected(true);
            initData();
//            requestData(mType);
        }
    }

    private void initData() {
        orderGroupAdapter2 = new OrderGroupAdapter2(OrderCenterActivity2.this, orders, mType);
        mListView.setAdapter(orderGroupAdapter2);


    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.ll_wait_to_pay:
                mType = 1;
                tv_wait_to_pay.setTextColor(getResources().getColor(R.color.colorYellow));
                tv_wait_to_send.setTextColor(Color.WHITE);
                tv_received.setTextColor(Color.WHITE);
                tv_wait_to_receive.setTextColor(Color.WHITE);
//                tv_wait_to_pay.setSelected(true);
                initData();
                requestData(mType);
                break;
            case R.id.ll_wait_to_send:
                mType = 2;
                tv_wait_to_send.setTextColor(getResources().getColor(R.color.colorYellow));
                tv_received.setTextColor(Color.WHITE);
                tv_wait_to_receive.setTextColor(Color.WHITE);
                tv_wait_to_pay.setTextColor(Color.WHITE);
//                tv_wait_to_send.setSelected(true);
                initData();
                requestData(mType);
                break;
            case R.id.ll_wait_to_receive:
                mType = 3;
                tv_wait_to_receive.setTextColor(getResources().getColor(R.color.colorYellow));
                tv_wait_to_pay.setTextColor(Color.WHITE);
                tv_wait_to_send.setTextColor(Color.WHITE);
                tv_received.setTextColor(Color.WHITE);
//                tv_wait_to_receive.setSelected(true);
                initData();
                requestData(mType);
                break;
            case R.id.ll_received:
                mType = 4;
                tv_received.setTextColor(getResources().getColor(R.color.colorYellow));
                tv_wait_to_receive.setTextColor(Color.WHITE);
                tv_wait_to_pay.setTextColor(Color.WHITE);
                tv_wait_to_send.setTextColor(Color.WHITE);
//                tv_received.setSelected(true);
                initData();
                requestData(mType);
                break;
        }
    }

    public void requestData(final int mType) {
        params = new RequestParams();
        MyRequestDailog.showDialog(OrderCenterActivity2.this, "正在加载");
        params.put("startRowIndex", pageNo * pageSum);//开始行索引
        params.put("maximumRows", pageSum);//每页条数
        params.put("userId", new SessionUtil(OrderCenterActivity2.this).getUserId());//用户ID   由于自己ID没有订单,现在传2,此ID为李发起的ID.待修改成自己的ID=============================================================================
        params.put("proNum", 1000);//每个订单下显示商品个数
        params.put("state", mType);//订单状态 1：未付款 2：代发货 3：已发货 4：已签收 5：买家已评价 6：卖家已评价 7：已过期 8：全部 9:申请取消订单 10：已取消订单
        HttpUtils.getOrderByUserId(res_getOrderByUserId, params);
    }

    AsyncHttpResponseHandler res_getOrderByUserId = new EntityHandler<OrderNumber>(OrderNumber.class) {

        @Override
        public void onReadSuccess(List<OrderNumber> list) {
            MyRequestDailog.closeDialog();
            if (pageNo == 0) {
                orders.clear();
            }
            orders.addAll(list);
            orderGroupAdapter2.notifyDataSetChanged();
        }
    };

//    public void getDataFromI(int i) {
//        for (i = 1; i <= 4; i++) {
////            requestRecordCountData(i);
//            if (i == 1) {
//                tv_wait_to_pay.setTextColor(getResources().getColor(R.color.colorYellow));
//                tv_wait_to_send.setTextColor(Color.WHITE);
//                tv_received.setTextColor(Color.WHITE);
//                tv_wait_to_receive.setTextColor(Color.WHITE);
//                tv_wait_to_pay.setVisibility(View.VISIBLE);
//                tv_wait_to_pay.setText(recordcount);
//            }
//            if (i == 2) {
//                tv_wait_to_send.setTextColor(getResources().getColor(R.color.colorYellow));
//                tv_received.setTextColor(Color.WHITE);
//                tv_wait_to_receive.setTextColor(Color.WHITE);
//                tv_wait_to_pay.setTextColor(Color.WHITE);
//                tv_wait_to_send.setText(recordcount);
//            }
//            if (i == 3) {
//                tv_wait_to_receive.setTextColor(getResources().getColor(R.color.colorYellow));
//                tv_wait_to_pay.setTextColor(Color.WHITE);
//                tv_wait_to_send.setTextColor(Color.WHITE);
//                tv_received.setTextColor(Color.WHITE);
//                tv_wait_to_receive.setText(recordcount);
//            }
//            if (i == 4) {
//                tv_received.setTextColor(getResources().getColor(R.color.colorYellow));
//                tv_wait_to_receive.setTextColor(Color.WHITE);
//                tv_wait_to_pay.setTextColor(Color.WHITE);
//                tv_wait_to_send.setTextColor(Color.WHITE);
//                tv_received.setText(recordcount);
//            }
//        }
//
//    }

//    public void requestRecordCountData(final int i) {
//        params = new RequestParams();
//        params.put("startRowIndex", 0);//开始行索引
//        params.put("maximumRows", 1);//每页条数
//        params.put("userId", new SessionUtil(OrderCenterActivity2.this).getUserId());//用户ID   由于自己ID没有订单,现在传2,此ID为李发起的ID.待修改成自己的ID=============================================================================
//        params.put("proNum", 1);//每个订单下显示商品个数
//        params.put("state", i);//订单状态 1：未付款 2：代发货 3：已发货 4：已签收 5：买家已评价 6：卖家已评价 7：已过期 8：全部 9:申请取消订单 10：已取消订单
//        HttpUtils.getOrderByUserId(new HttpErrorHandler() {
//            @Override
//            public void onRecevieSuccess(JSONObject json) {
//                recordcount = json.getString("recordcount");
//            }
//        }, params);
//
//    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                pageNo = 0;
                requestData(mType);
                onLoad();
            }
        }, 1000);
    }

    /**
     * 上拉加载
     */
    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                pageNo += 1;
                requestData(mType);
                onLoad();
            }
        }, 1000);
    }

    /**
     * 结束加载/刷新
     */
    public void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime("刚刚");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 11) {
            if (resultCode == 99) {
                mType = data.getIntExtra("mType", 0);
                initView();
            }

        }
    }
}