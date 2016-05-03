package com.hewuzhe.view;

import com.hewuzhe.model.Address;
import com.hewuzhe.model.Dojo;
import com.hewuzhe.view.base.GetView;
import com.hewuzhe.view.base.ListView;
import com.hewuzhe.view.base.LoadMoreView;
import com.hewuzhe.view.common.SwipeRefreshView;

import java.util.ArrayList;

/**
 * Created by xianguangjin on 15/12/25.
 */
public interface DojoRecommendView extends LoadMoreView, GetView<Integer>,SwipeRefreshView,ListView<Dojo> {
    String []getStringData();
    void bindList(ArrayList<Address> classifications);
    void setNodata(int recordcount);
}
