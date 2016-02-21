package com.hewuzhe.view;

import com.hewuzhe.model.Product;
import com.hewuzhe.view.base.GetView;
import com.hewuzhe.view.base.ListView;
import com.hewuzhe.view.base.LoadMoreView;
import com.hewuzhe.view.common.AreaView;

/**
 * Created by zycom on 2016/1/23.
 */
public interface ProductListView  extends GetView<Product>,ListView<Product>,LoadMoreView {
    void updateItem(boolean b, int pos);
}
