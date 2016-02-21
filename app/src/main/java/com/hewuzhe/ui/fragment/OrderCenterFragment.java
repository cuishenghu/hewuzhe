package com.hewuzhe.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.hewuzhe.model.OrderChild;
import com.hewuzhe.model.OrderGroup;
import com.hewuzhe.ui.activity.OrderDetailsActivity;
import com.hewuzhe.ui.adapter.OrderCenterAdapter;
import com.hewuzhe.utils.Tools;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

public class OrderCenterFragment extends Fragment {

    private static int PERPAGE = 5;// perpage默认每页显示10条信息
    private int nowpage = 1;// 当前显示的页面
    private int mType = 1;// 1、待付款 2、待发货 3、待收货 4、已收货
    private List<OrderGroup> orders = new ArrayList<OrderGroup>();
    private OrderCenterAdapter orderCenterAdapter;
    private int groupCount;
    private int sumtiaoshu = 0;
    private ExpandableListView mListView;
    private Handler mHandler;
    private RequestParams params;
    private OrderChild orderChild;

    /**
     * @param type // 1、待付款 2、待发货 3、待收货 4、已收货
     * @return 实例化收入列表
     */
    public static OrderCenterFragment getInstance(int type) {
        OrderCenterFragment fragment = new OrderCenterFragment();
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
        mListView = new ExpandableListView(getActivity(), null);
        mListView.setDividerHeight(0);
        mListView.setLayoutParams(params);
        mListView.setGroupIndicator(null);
        /**
         * 进入订单详情界面
         */
        mListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                startActivity(new Intent(getActivity(), OrderDetailsActivity.class).putExtra("order", orders.get(groupPosition)).putExtra("mType", mType).putExtra("groupPosition", groupPosition));
//                context.startActivity(new Intent(context, OrderAssessActivity.class).putExtra("order", orders.get(groupPosition)).putExtra("mType", mType).putExtra("groupPosition",groupPosition));

                return true;
            }
        });
//		mListView.setPullRefreshEnable(true);
//		mListView.setPullLoadEnable(true);
//		mListView.setXListViewListener(this);
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
//		orderCenterAdapter = new OrderCenterAdapter(getActivity(), orders, mType,sumtiaoshu);
//
//        mListView.setAdapter(orderCenterAdapter);

        initData();
//		requestData();

    }

    /**
     * 请求服务器数据
     */
    private void requestData() {
        params = new RequestParams();
        params.put("nowpage", nowpage);
        params.put("perpage", PERPAGE);

    }

    public void initData() {
//		for (int i = 0; i < orders.size(); i++) {
//			sumtiaoshu = sumtiaoshu + orders.get(i).getOrderLists().size();//商品总条数
//		}
//		Tools.toast(getActivity(), "" + sumtiaoshu);
//
//		orderCenterAdapter = new OrderCenterAdapter(getActivity(), orders, mType, sumtiaoshu);
//		mListView.setAdapter(orderCenterAdapter);
//
//		groupCount = mListView.getCount();
//		for (int i = 0; i < groupCount; i++) {
//			mListView.expandGroup(i);
//		}
        ///////////////////////////////////////////////////////////////////////////////////////////
        List<OrderChild> list1 = new ArrayList<OrderChild>();
        list1.add(new OrderChild("1子id", "1子name", "规格：S/白色", "100", "100"));
        list1.add(new OrderChild("2子id", "2子name", "规格：S/白色", "100", "100"));
        list1.add(new OrderChild("3子id", "3子name", "规格：S/白色", "100", "100"));

        OrderGroup orderGroup1 = new OrderGroup("1组id", "1组name", list1);
        orders.add(orderGroup1);

        List<OrderChild> list2 = new ArrayList<OrderChild>();
        list2.add(new OrderChild("4子id", "4子name", "规格：S/黑色", "100", "100"));
        list2.add(new OrderChild("5子id", "5子name", "规格：S/黑色", "100", "100"));

        OrderGroup groupItem2 = new OrderGroup("2组id", "2组name", list2);
        orders.add(groupItem2);

        List<OrderChild> list3 = new ArrayList<OrderChild>();
        list3.add(new OrderChild("6子id", "6子name", "规格：S/红色", "100", "100"));
        list3.add(new OrderChild("7子id", "7子name", "规格：S/红色", "100", "100"));
        list3.add(new OrderChild("8子id", "8子name", "规格：S/红色", "100", "100"));

        OrderGroup groupItem3 = new OrderGroup("3组id", "3组name", list3);
        orders.add(groupItem3);

        for (int i = 0; i < orders.size(); i++) {
            sumtiaoshu = sumtiaoshu + orders.get(i).getOrderLists().size();//商品总条数
        }
        Tools.toast(getActivity(), "" + sumtiaoshu);
        orderCenterAdapter = new OrderCenterAdapter(getActivity(), orders, mType, sumtiaoshu);
        mListView.setAdapter(orderCenterAdapter);
        orderCenterAdapter.notifyDataSetChanged();

        groupCount = mListView.getCount();
        for (int i = 0; i < groupCount; i++) {
            mListView.expandGroup(i);

        }
    }


//	/**
//	 * 下拉刷新
//	 */
//	@Override
//	public void onRefresh() {
//		mHandler.postDelayed(new Runnable() {
//
//			@Override
//			public void run() {
//				nowpage = 1;
//				requestData();
//				onLoad();
//			}
//		}, 1000);
//	}
//
//	/**
//	 * 上拉加载
//	 */
//	@Override
//	public void onLoadMore() {
//		mHandler.postDelayed(new Runnable() {
//
//			@Override
//			public void run() {
//				nowpage += 1;
//				requestData();
//				onLoad();
//			}
//		}, 1000);
//	}
//
//	/**
//	 * 结束加载/刷新
//	 */
//	public void onLoad() {
//		mListView.stopRefresh();
//		mListView.stopLoadMore();
//		mListView.setRefreshTime("刚刚");
//	}
}
