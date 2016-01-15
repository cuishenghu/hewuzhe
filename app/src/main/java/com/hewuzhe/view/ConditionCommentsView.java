package com.hewuzhe.view;

import com.hewuzhe.model.ConditionComment;
import com.hewuzhe.view.base.ListView;
import com.hewuzhe.view.base.LoadMoreView;
import com.hewuzhe.view.common.SwipeRefreshView;

/**
 * Created by xianguangjin on 16/1/15.
 */
public interface ConditionCommentsView extends ListView<ConditionComment>, LoadMoreView,SwipeRefreshView {
}
