package com.hewuzhe.ui.activity;

import android.view.View;
import android.widget.Button;

import com.hewuzhe.R;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.base.ToolBarActivity;

import butterknife.Bind;

public class MemberActivity extends ToolBarActivity {


    @Bind(R.id.btn_to_member)
    Button _BtnToMember;

    /**
     * @return 提供标题
     */
    @Override
    protected String provideTitle() {
        return "会员详情";
    }

    /**
     * @return 提供LayoutId
     */
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_member;
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {
        _BtnToMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MemberBuyActivity.class);
            }
        });

    }

    /**
     * 绑定Presenter
     */
    @Override
    public BasePresenterImp createPresenter() {
        return null;
    }

}
