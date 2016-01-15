package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.base.ToolBarActivity;

import butterknife.Bind;

public class AnnounceDetailActivity extends ToolBarActivity {


    @Bind(R.id.tv_content)
    TextView _TvContent;

    /**
     * @return 提供标题
     */
    @Override
    protected String provideTitle() {
        return "公告详情";
    }

    /**
     * @return 提供LayoutId
     */
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_announce_detail;
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);

        _TvContent.setText(getIntentData().getString("content"));
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
}
