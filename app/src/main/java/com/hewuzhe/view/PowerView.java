package com.hewuzhe.view;

import com.hewuzhe.view.base.BaseView;

/**
 * Created by xianguangjin on 15/12/17.
 */
public interface PowerView extends BaseView {


    /**
     * 显示拍摄视频的PopView
     */
    void showPopView();

    /**
     * 销毁PopView
     */
    void dismissPopView();
}
