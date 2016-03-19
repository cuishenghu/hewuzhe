package com.hewuzhe.view;

import com.hewuzhe.model.Video;
import com.hewuzhe.view.base.GetView;
import com.hewuzhe.view.base.ListView;
import com.hewuzhe.view.base.LoadMoreView;
import com.hewuzhe.view.common.SwipeRefreshView;

/**
 * Created by xianguangjin on 15/12/22.
 */
public interface Videos2View extends LoadMoreView, ListView<Video>, GetView<Integer>, SwipeRefreshView {

}
