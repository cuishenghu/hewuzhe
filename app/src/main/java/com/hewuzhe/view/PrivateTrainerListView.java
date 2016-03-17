package com.hewuzhe.view;

import com.hewuzhe.model.PrivateTrainerList;
import com.hewuzhe.view.base.GetView;
import com.hewuzhe.view.base.ListView;
import com.hewuzhe.view.base.LoadMoreView;
import com.hewuzhe.view.common.SwipeRefreshView;

/**
 * Created by zycom on 2016/3/16.
 */
public interface PrivateTrainerListView extends LoadMoreView, GetView<Integer>,SwipeRefreshView,ListView<PrivateTrainerList> {
    void setNodata(int recordcount);
    String []getStringData();
}
