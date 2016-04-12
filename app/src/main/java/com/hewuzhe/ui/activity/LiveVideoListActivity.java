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
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.TimeUtil;
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
        return "直播";
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
        if (!TimeUtil.timeComparedNow(item.TimeStart) && TimeUtil.timeComparedNow(item.TimeEnd)) {
            presenter.SelectVideoLive(item.Id);
        } else {
            startActivity(LiveVideoActivity.class, new Bun().putInt("Id", item.Id)
                    .putString("title", TimeUtil.timeComparedNow(item.TimeStart) ? "视频直播" : "直播回放").ok());
        }
    }

    @Override
    public void setData(LiveVideo liveVideo) {
        startActivityForResult(IjkVideoActicity.class, new Bun()
                .putString("title", liveVideo.Title)
                .putString("start", liveVideo.TimeStart)
                .putString("end", liveVideo.TimeEnd)
                .putString("content", liveVideo.Content)
                .putString("uid", liveVideo.LiveUId)
                .putString("vid", liveVideo.LiveVId).ok(), 1);
    }
}
