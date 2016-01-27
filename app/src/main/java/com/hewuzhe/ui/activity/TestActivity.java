package com.hewuzhe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.base.ToolBarActivity;

import butterknife.Bind;
import cn.jpush.android.api.JPushInterface;

public class TestActivity extends ToolBarActivity {

    @Bind(R.id.tv_content)
    TextView _TvContent;

    /**
     * @return 提供LayoutId
     */
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_announce_detail;
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {

    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        Intent intent = getIntent();
        if (null != intent) {
            Bundle bundle = getIntent().getExtras();
            String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            String content = bundle.getString(JPushInterface.EXTRA_ALERT);
            tvTitle.setText(title);
            _TvContent.setText(content);
        }
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
        return "系统通知";
    }

}
