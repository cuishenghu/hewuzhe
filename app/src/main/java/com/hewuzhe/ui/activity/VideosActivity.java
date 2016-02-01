package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.hewuzhe.R;
import com.hewuzhe.model.Video;
import com.hewuzhe.presenter.VideosPresenter;
import com.hewuzhe.ui.adapter.VideoAdapter;
import com.hewuzhe.ui.base.SwipeRecycleViewActivity;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.view.Videos2View;

import java.util.ArrayList;

import butterknife.Bind;

public class VideosActivity extends SwipeRecycleViewActivity<VideosPresenter, VideoAdapter, Video> implements Videos2View {


    private int id;

    @Bind(R.id.img_search)
    ImageView imgSearch;


    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        id = getIntent().getBundleExtra("data").getInt("id");
        presenter.getData(page, count);

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(SearchCatVideosActivity.class, new Bun().putString("title", "搜索").putInt("catId", id).ok());
            }
        });


    }

    /**
     * 绑定Presenter
     */
    @Override
    public VideosPresenter createPresenter() {
        return new VideosPresenter();
    }

    /**
     * @return 提供Adapter
     */
    @Override
    protected VideoAdapter provideAdapter() {
        return new VideoAdapter(getContext());
    }

    /**
     * @return 提供LayoutManager
     */
    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_videos;
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {

    }


    @Override
    protected String provideTitle() {

        return getIntent().getBundleExtra("data").getString("title");
    }


    /**
     * @param view
     * @param pos
     * @param item
     */
    @Override
    public void onItemClick(View view, int pos, Video item) {

        startActivity(VideoDetail2Activity.class, new Bun().putInt("Id", item.Id).ok());


        if (item.UserId != 0) {
        }
    }

    /**
     * @param videos 绑定recycleview数据
     */
    @Override
    public void bindData(ArrayList<Video> videos) {
        if (page == 1) {
            adapter.data.clear();
        }
        adapter.data.addAll(videos);
        adapter.notifyDataSetChanged();
    }

    @Override
    public Integer getData() {
        return id;
    }
}
