package com.hewuzhe.view;

import com.hewuzhe.model.TeamAnnounce;
import com.hewuzhe.view.base.GetView;
import com.hewuzhe.view.base.ListView;
import com.hewuzhe.view.base.LoadMoreView;
import com.hewuzhe.view.common.SwipeRefreshView;

/**
 * Created by xianguangjin on 16/1/12.
 */
public interface TreamAnnounceView extends ListView<TeamAnnounce>, LoadMoreView, GetView<Integer>,SwipeRefreshView {
}
