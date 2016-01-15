package com.hewuzhe.view.base;

import java.util.ArrayList;

/**
 * Created by xianguangjin on 15/12/25.
 *
 * @param <T> RecycleView中Item的Model
 */
public interface ListView<T> extends BaseView {

    void bindData(ArrayList<T> data);


}
