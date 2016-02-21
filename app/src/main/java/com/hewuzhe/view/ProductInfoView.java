package com.hewuzhe.view;

import com.hewuzhe.model.Product;
import com.hewuzhe.view.base.BaseView;
import com.hewuzhe.view.base.GetView;

import java.util.ArrayList;

/**
 * Created by zycom on 2016/1/23.
 */
public interface ProductInfoView extends GetView<Product> {
    void bindData(Product data);
    void GetPostageState(double state);
}
