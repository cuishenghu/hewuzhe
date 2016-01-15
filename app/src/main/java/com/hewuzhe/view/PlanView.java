package com.hewuzhe.view;

import com.hewuzhe.model.Plan;
import com.hewuzhe.ui.inter.OnReceiveListener;
import com.hewuzhe.view.base.GetView;
import com.hewuzhe.view.base.ListView;
import com.hewuzhe.view.base.LoadMoreView;
import com.hewuzhe.view.common.SwipeRefreshView;

/**
 * Created by xianguangjin on 16/1/4.
 */
public interface PlanView extends ListView<Plan>, SwipeRefreshView, GetView<Integer>,LoadMoreView, OnReceiveListener<Integer, Boolean> {

}
