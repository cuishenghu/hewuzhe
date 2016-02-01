package com.hewuzhe.view;

import com.hewuzhe.model.Video;
import com.hewuzhe.view.base.GetView;
import com.hewuzhe.view.base.ListView;
import com.hewuzhe.view.base.LoadMoreView;
import com.hewuzhe.view.common.SwipeRefreshView;

/**
 * Created by xianguangjin on 16/1/31.
 */

public interface SearchVideosView extends ListView<Video>, LoadMoreView, GetView<String>, SwipeRefreshView {


    void showNoData(boolean isShow, String tip);
}
