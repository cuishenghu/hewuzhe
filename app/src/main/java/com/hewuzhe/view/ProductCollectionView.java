package com.hewuzhe.view;

import com.hewuzhe.model.ArticleCollection;
import com.hewuzhe.model.Product;
import com.hewuzhe.model.ProductCollection;
import com.hewuzhe.ui.inter.OnReceiveListener;
import com.hewuzhe.view.base.GetView;
import com.hewuzhe.view.base.ListView;
import com.hewuzhe.view.base.LoadMoreView;
import com.hewuzhe.view.common.SwipeRefreshView;

/**
 * Created by zycom on 2016/2/20.
 */
public interface ProductCollectionView extends LoadMoreView,ListView<ProductCollection>,SwipeRefreshView,GetView<Integer>, OnReceiveListener<Integer, Boolean> {

    void collectAndOther();
}
