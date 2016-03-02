package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hewuzhe.R;
import com.hewuzhe.model.LiveVideo;
import com.hewuzhe.presenter.LiveVideoPresenter;
import com.hewuzhe.ui.adapter.LiveContentAdapter;
import com.hewuzhe.ui.base.SwipeRecycleViewActivity;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.view.LiveVideoView;

import java.util.ArrayList;

/**
 * 视频直播列表
 * Created by csh on 2016/2/20.
 */
public class LiveVideoListActivity extends SwipeRecycleViewActivity<LiveVideoPresenter, LiveContentAdapter, LiveVideo> implements LiveVideoView {

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        presenter.getData(page, count);

    }

    @Override
    protected LiveContentAdapter provideAdapter() {
        return new LiveContentAdapter(getContext());
    }

    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    protected String provideTitle() {
        return "直播列表";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_live_list_video;
    }

    @Override
    public void initListeners() {

    }

    @Override
    public LiveVideoPresenter createPresenter() {
        return new LiveVideoPresenter();
    }

    @Override
    public void bindData(ArrayList<LiveVideo> data) {
        if (page == 1) {
            adapter.data.clear();
        }
        adapter.data.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int pos, LiveVideo item) {
        startActivity(LiveVideoActivity.class, new Bun().putInt("Id", item.Id).ok());
    }

    @Override
    public void setData(LiveVideo liveVideo) {}
}
