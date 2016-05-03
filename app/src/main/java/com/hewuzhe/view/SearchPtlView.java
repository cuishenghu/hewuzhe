package com.hewuzhe.view;

import com.hewuzhe.model.PrivateTrainerList;
import com.hewuzhe.view.base.GetView;
import com.hewuzhe.view.base.ListView;
import com.hewuzhe.view.base.LoadMoreView;

/**
 * Created by ninos on 2016/5/3.
 */
public interface SearchPtlView  extends GetView<PrivateTrainerList>,ListView<PrivateTrainerList>,LoadMoreView {
    String []getStringData();
}
