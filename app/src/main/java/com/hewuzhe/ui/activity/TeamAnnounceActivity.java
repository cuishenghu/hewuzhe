package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hewuzhe.R;
import com.hewuzhe.model.TeamAnnounce;
import com.hewuzhe.presenter.TreamAnnouncePresenter;
import com.hewuzhe.ui.adapter.TeamAnnounceAdapter;
import com.hewuzhe.ui.base.SwipeRecycleViewActivity;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.view.TreamAnnounceView;

import java.util.ArrayList;

public class TeamAnnounceActivity extends SwipeRecycleViewActivity<TreamAnnouncePresenter, TeamAnnounceAdapter, TeamAnnounce> implements TreamAnnounceView {

    @Override
    public Integer getData() {
        return getIntentData().getInt("id");
    }

    @Override
    public void bindData(ArrayList<TeamAnnounce> data) {
        bd(data);
    }

    /**
     * @return 提供Adapter
     */
    @Override
    protected TeamAnnounceAdapter provideAdapter() {
        return new TeamAnnounceAdapter(getContext());
    }


    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        presenter.getData(page, count);
    }

    /**
     * @return 提供LayoutManager
     */
    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    /**
     * @return 提供标题
     */
    @Override
    protected String provideTitle() {
        return "战队公告";
    }

    /**
     * @return 提供LayoutId
     */
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_team_announce;
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
    public TreamAnnouncePresenter createPresenter() {
        return new TreamAnnouncePresenter();
    }

    /**
     * @param view
     * @param pos
     * @param item
     */
    @Override
    public void onItemClick(View view, int pos, TeamAnnounce item) {

        startActivity(AnnounceDetailActivity.class, new Bun().putString("content", item.Content).ok());
    }
}
