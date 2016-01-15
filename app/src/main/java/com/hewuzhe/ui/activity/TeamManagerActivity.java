package com.hewuzhe.ui.activity;

import android.view.View;
import android.widget.LinearLayout;

import com.hewuzhe.R;
import com.hewuzhe.presenter.TeamManagerPresenter;
import com.hewuzhe.ui.base.ToolBarActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.view.TeamManagerView;

import butterknife.Bind;
import de.greenrobot.event.EventBus;

public class TeamManagerActivity extends ToolBarActivity<TeamManagerPresenter> implements TeamManagerView {

    @Bind(R.id.lay_exit)
    LinearLayout _LayExit;

    /**
     * @return 提供标题
     */
    @Override
    protected String provideTitle() {
        return "战队管理";
    }

    /**
     * @return 提供LayoutId
     */
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_team_manager;
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {
        _LayExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.quitGroup(getIntentData().getInt("id"));
            }
        });
    }

    /**
     * 绑定Presenter
     */
    @Override
    public TeamManagerPresenter createPresenter() {
        return new TeamManagerPresenter();
    }

    @Override
    public void updateItem(boolean b) {
        if (!b) {
            snb("退出战队成功", _LayExit);
            EventBus.getDefault().post(C.MSG_CLOSE_GROUP_CONDITION);
            finishActivity();
        }

    }

    @Override
    public Integer getData() {
        return null;
    }
}
