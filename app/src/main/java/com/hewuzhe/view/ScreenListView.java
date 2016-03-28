package com.hewuzhe.view;

import com.hewuzhe.model.ScreenList;
import com.hewuzhe.view.base.LoadMoreView;

import java.util.ArrayList;

/**
 * Created by zycom on 2016/3/23.
 */
public interface ScreenListView  extends LoadMoreView {
    void bindData(ArrayList<ScreenList> data);
}

