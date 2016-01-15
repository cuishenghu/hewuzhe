package com.hewuzhe.view;

import com.hewuzhe.model.MegaGame;
import com.hewuzhe.view.base.GetView;
import com.hewuzhe.view.base.ListView;
import com.hewuzhe.view.base.LoadMoreView;
import com.hewuzhe.view.common.SwipeRefreshView;

/**
 * Created by xianguangjin on 15/12/31.
 */
public interface MegaGameView extends LoadMoreView,ListView<MegaGame>,SwipeRefreshView,GetView<String> {
}
