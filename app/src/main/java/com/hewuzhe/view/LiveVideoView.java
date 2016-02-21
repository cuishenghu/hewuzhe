package com.hewuzhe.view;

import com.hewuzhe.model.LiveVideo;
import com.hewuzhe.view.base.ListView;
import com.hewuzhe.view.base.LoadMoreView;
import com.hewuzhe.view.base.SetView;
import com.hewuzhe.view.common.SwipeRefreshView;

/**
 * Created by xianguangjin on 16/1/26.
 */
public interface LiveVideoView extends SetView<LiveVideo>,LoadMoreView,ListView<LiveVideo>,SwipeRefreshView {


}
