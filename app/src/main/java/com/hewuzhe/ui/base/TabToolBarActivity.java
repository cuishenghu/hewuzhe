package com.hewuzhe.ui.base;

import android.os.Bundle;

/**
 * Created by xianguangjin on 15/12/17.
 */
public abstract class TabToolBarActivity extends ToolBarActivity {

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
