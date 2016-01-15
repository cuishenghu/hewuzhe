package com.hewuzhe.view;

import com.hewuzhe.model.ArticleCollection;
import com.hewuzhe.view.base.GetView;
import com.hewuzhe.view.base.ListView;
import com.hewuzhe.view.base.LoadMoreView;
import com.hewuzhe.view.common.SwipeRefreshView;

/**
 * Created by xianguangjin on 15/12/25.
 */
public interface ArticleCollectionsView extends LoadMoreView,ListView<ArticleCollection>,SwipeRefreshView,GetView<Integer> {


}
