package com.hewuzhe.ui.fragment;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.hewuzhe.model.OrderNumber;
import com.hewuzhe.ui.activity.OrderConfirmFirstActivity;
import com.hewuzhe.ui.activity.OrderDetailsActivity;
import com.hewuzhe.ui.adapter.OrderGroupAdapter;
import com.hewuzhe.ui.http.EntityHandler;
import com.hewuzhe.ui.http.HttpUtils;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.MyRequestDailog;
import com.hewuzhe.view.XListView.IXListViewListener;

import android.widget.AdapterView.OnItemClickListener;

import com.hewuzhe.model.OrderChild;
import com.hewuzhe.model.OrderGroup;
import com.hewuzhe.view.XListView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment implements IXListViewListener {

    private static int pageSum = 5;// perpage默认每页显示5条信息
    private int pageNo = 0;// 当前显示的页面
    private int mType = 1;// 1、待付款 2、待发货 3、待收货 4、已收货
    private ArrayList<OrderNumber> orders = new ArrayList<OrderNumber>();
    private OrderGroupAdapter orderGroupAdapter;
    private int groupCount;
    private int sumtiaoshu = 0;
    private XListView mListView;
    private Handler mHandler;
    private RequestParams params;

    /**
     * @param type // 1、待付款 2、待发货 3、待收货 4、已收货
     * @return 实例化收入列表
     */
    public static OrderFragment getInstance(int type) {
        OrderFragment fragment = new OrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;

    }

    /**
     * 初始化页面
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mListView = new XListView(getActivity(), null);
        mListView.setLayoutParams(params);
        mListView.setDividerHeight(0);
        mListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
        mListView.setXListViewListener(this);
        return mListView;

    }

    /**
     * 配置页面参数
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mType = getArguments().getInt("type");
        mHandler = new Handler();
        orderGroupAdapter = new OrderGroupAdapter(getActivity(), orders, mType);
        mListView.setAdapter(orderGroupAdapter);
//        mListView.setOnItemClickListener(this);
//        initData();
//        requestData();
    }
    @Override
    public void onResume() {
        super.onResume();
        requestData();
    }

    /**
     * 请求服务器数据
     */
    private void requestData() {
        params = new RequestParams();
        MyRequestDailog.showDialog(getActivity(), "正在加载");
        params.put("startRowIndex", pageNo * pageSum);//开始行索引
        params.put("maximumRows", pageSum);//每页条数
        params.put("userId", new SessionUtil(getActivity()).getUserId());//用户ID   由于自己ID没有订单,现在传2,此ID为李发起的ID.待修改成自己的ID=============================================================================
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
            orderGroupAdapter.notifyDataSetChanged();
        }
    };

    public void initData() {

//		for (int i = 0; i < orders.size(); i++) {
//			sumtiaoshu = sumtiaoshu + orders.get(i).getOrderLists().size();//商品总条数
//		}
//		Tools.toast(getActivity(), "" + sumtiaoshu);
//
//		  orderGroupAdapter = new OrderGroupAdapter(getActivity(), orders, mType);
//        mListView.setAdapter(orderGroupAdapter);
//
//		groupCount = mListView.getCount();
//		for (int i = 0; i < groupCount; i++) {
//			mListView.expandGroup(i);
//		}
        ///////////////////////////////////////////////////////////////////////////////////////////

    }

    /**
     * 下拉刷新
     */
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

    /**
     * 上拉加载
     */
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

    /**
     * 结束加载/刷新
     */
    public void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime("刚刚");
    }

}
