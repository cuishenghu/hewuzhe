package com.hewuzhe.view;

import com.hewuzhe.model.ShopCar;
import com.hewuzhe.view.base.GetView;
import com.hewuzhe.view.base.ListView;
import com.hewuzhe.view.base.LoadMoreView;

/**
 * Created by zycom on 2016/2/2.
 */
public interface ShopCarView extends GetView<ShopCar>,ListView<ShopCar>,LoadMoreView {
    void updateItem(boolean b, int pos);
    void GetPostageState(double state);
}

