package com.hewuzhe.ui.activity;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.base.ToolBarActivity;

import butterknife.Bind;

public class SettingsActivity extends ToolBarActivity {

    @Bind(R.id.lay_friends_condition)
    LinearLayout _LayFriendsCondition;
    @Bind(R.id.lay_noti)
    TextView _LayNoti;
    @Bind(R.id.lay_settings)
    LinearLayout _LaySettings;
    @Bind(R.id.lay_sound)
    LinearLayout _LaySound;
    @Bind(R.id.lay_vibrate)
    LinearLayout _LayVibrate;
    @Bind(R.id.lay_shield_list)
    LinearLayout _LayShieldList;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_settings;
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

    @Override
    protected String provideTitle() {
        return "聊天通知设置";
    }
}
