package com.hewuzhe.ui.fragment;


import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hewuzhe.R;
import com.hewuzhe.model.Video;
import com.hewuzhe.presenter.VideoCollectionPresenter;
import com.hewuzhe.ui.activity.VideoDetail2Activity;
import com.hewuzhe.ui.activity.VideoDetailActivity;
import com.hewuzhe.ui.adapter.GridItemDecoration;
import com.hewuzhe.ui.adapter.Videos2Adapter;
import com.hewuzhe.ui.base.SwipeRecycleViewFragment;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.VideoCollectionsView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoCollectionsFragment extends SwipeRecycleViewFragment<VideoCollectionPresenter, Videos2Adapter, Video> implements VideoCollectionsView {


    public static VideoCollectionsFragment newInstance() {
        return new VideoCollectionsFragment();
    }

    public VideoCollectionsFragment() {
        // Required empty public constructor
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {


    }

    /**
     * @param view
     */
    @Override
    protected void initThings(View view) {
        super.initThings(view);
        recyclerView.addItemDecoration(new GridItemDecoration(10, 2));
        presenter.getData(page, count);
    }

    /**
     * @return 提供Adapter
     */
    @Override
    protected Videos2Adapter provideAdapter() {
        return new Videos2Adapter(getContext());
    }

    /**
     * @return 提供LayoutManager
     */
    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new GridLayoutManager(getContext(), 2);
    }

    /**
     * @return 提供LayoutId
     */
    @Override
    public int provideLayoutId() {
        return R.layout.fragment_video_collections;
    }

    /**
     * 绑定Presenter
     */
    @Override
    public VideoCollectionPresenter createPresenter() {
        return new VideoCollectionPresenter();
    }


    /**
     * @param view
     * @param pos
     * @param item
     */
    @Override
    public void onItemClick(View view, int pos, Video item) {
        if (item.PublisherId != 0) {
            startActivity(VideoDetail2Activity.class, new Bun().putInt("Id", item.MessageId).ok());
        } else {
            startActivity(VideoDetailActivity.class, new Bun().putInt("Id", item.MessageId).ok());
        }
    }

    @Override
    public void bindData(ArrayList<Video> data) {
        bd(data);
    }


    @Override
    public Integer getData() {
        return new SessionUtil(getContext()).getUserId();
    }
}
