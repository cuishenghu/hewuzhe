package com.hewuzhe.ui.base;

import android.os.Bundle;

import com.hewuzhe.presenter.base.BasePresenterImp;

/**
 * Created by xianguangjin on 15/12/17.
 */
public abstract class TabToolBarActivity<P extends BasePresenterImp> extends ToolBarActivity<P> {

    /**
     * 初始化Tabs
     */
    abstract public void initTabs();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTabs();

    }
}
