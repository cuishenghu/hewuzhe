package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hewuzhe.R;
import com.hewuzhe.model.TeamIntroduce;
import com.hewuzhe.presenter.TeamIntroducePresenter;
import com.hewuzhe.ui.base.ToolBarActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.view.TeamIntroduceView;

import butterknife.Bind;

public class TeamIntroduceActivity extends ToolBarActivity<TeamIntroducePresenter> implements TeamIntroduceView {


    @Bind(R.id.img)
    ImageView _Img;
    @Bind(R.id.tv_content)
    TextView _TvContent;

    /**
     * @return 提供标题
     */
    @Override
    protected String provideTitle() {
        return "战队介绍";
    }

    /**
     * @return 提供LayoutId
     */
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_team_introduce;
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        presenter.getData();

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
    public TeamIntroducePresenter createPresenter() {
        return new TeamIntroducePresenter();
    }

    @Override
    public Integer getData() {
        return getIntentData().getInt("id");
    }

    @Override
    public void setData(TeamIntroduce teamIntroduce) {

        Glide.with(getContext())
                .load(C.BASE_URL + teamIntroduce.ImagePath)
                .fitCenter()
                .crossFade()
                .placeholder(R.mipmap.img_bg_videio)
                .into(_Img);

        _TvContent.setText(teamIntroduce.TeamIntroduce);
    }

}
