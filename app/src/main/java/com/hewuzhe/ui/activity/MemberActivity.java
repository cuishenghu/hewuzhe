package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.presenter.MemberPresenter;
import com.hewuzhe.ui.base.ToolBarActivity;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.MemberView;

import butterknife.Bind;

public class MemberActivity extends ToolBarActivity<MemberPresenter> implements MemberView {


    @Bind(R.id.btn_to_member)
    Button _BtnToMember;
    @Bind(R.id.tv_over_time)
    TextView _TvOverTime;
    private boolean _IsVip;

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

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        _IsVip = new SessionUtil(getContext()).getUser().isVip();
        _BtnToMember.setText(_IsVip ? "点击续费" : "成为会员");
        _TvOverTime.setVisibility(_IsVip ? View.VISIBLE : View.INVISIBLE);

        if (_IsVip) {
            presenter.GetPayOverTime();
        }

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
    public MemberPresenter createPresenter() {
        return new MemberPresenter();
    }

    @Override
    public void setData(String s) {
        _TvOverTime.setText("到期时间：" + s);
    }
}
