package com.hewuzhe.ui.activity;

import com.hewuzhe.R;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.base.ToolBarActivity;

public class SubConversationListActivtiy extends ToolBarActivity {

    /**
     * @return 提供LayoutId
     */
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_sub_conversation_list_activtiy;
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {

    }

    /**
     * 绑定Presenter
     */
    @Override
    public BasePresenterImp createPresenter() {
        return null;
    }

    /**
     * @return 提供标题
     */
    @Override
    protected String provideTitle() {
        return "群聊";
    }
}
