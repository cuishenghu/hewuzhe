package com.hewuzhe.view;

import com.hewuzhe.model.MegaGameVideo;
import com.hewuzhe.model.MegaGameVideosRequest;
import com.hewuzhe.view.base.GetView;
import com.hewuzhe.view.base.ListView;
import com.hewuzhe.view.base.LoadMoreView;
import com.hewuzhe.view.common.SwipeRefreshView;

/**
 * Created by xianguangjin on 16/1/14.
 */
public interface MegaGameVideosView extends SwipeRefreshView, ListView<MegaGameVideo>, LoadMoreView, GetView<MegaGameVideosRequest> {
}
