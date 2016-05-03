package com.hewuzhe.view;

import com.hewuzhe.model.Dojo;
import com.hewuzhe.model.PrivateTrainerList;
import com.hewuzhe.view.base.GetView;
import com.hewuzhe.view.base.ListView;
import com.hewuzhe.view.base.LoadMoreView;

/**
 * Created by ninos on 2016/5/3.
 */
public interface SearchDojoView extends GetView<Dojo>,ListView<Dojo>,LoadMoreView {
    String []getStringData();
}
