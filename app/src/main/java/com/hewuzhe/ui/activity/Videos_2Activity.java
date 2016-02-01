package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.hewuzhe.R;
import com.hewuzhe.model.Video;
import com.hewuzhe.presenter.Videos2Presenter;
import com.hewuzhe.ui.adapter.GridItemDecoration;
import com.hewuzhe.ui.adapter.Videos2Adapter;
import com.hewuzhe.ui.base.SwipeRecycleViewActivity;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.view.Videos2View;

import java.util.ArrayList;

import butterknife.Bind;

public class Videos_2Activity extends SwipeRecycleViewActivity<Videos2Presenter, Videos2Adapter, Video> implements Videos2View {


    private int catId;
    @Bind(R.id.img_search)
    ImageView imgSearch;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_videos_2;
    }

    /**
     * @param savedInstanceState 缓存数据
     *                           <p/>
     */
    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        catId = getIntent().getIntExtra("id", 0);
        refresh(true);
        presenter.getData(page, count);

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(SearchOnlineVideosActivity.class, new Bun().putString("title", "搜索").putInt("catId", catId).ok());
            }
        });
    }

    /**
     * 绑定Presenter
     */
    @Override
    public Videos2Presenter createPresenter() {
        return new Videos2Presenter();
    }


    /**
     * @return 提供Adapter
     */
    @Override
    protected Videos2Adapter provideAdapter() {
        recyclerView.addItemDecoration(new GridItemDecoration(10, 2));
        return new Videos2Adapter(getContext());
    }

    /**
     * @return 提供LayoutManager
     */
    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == adapter.getItemCount() - 1) {
                    return 2;
                }
                return 1;
            }
        });
        layoutManager = gridLayoutManager;

        return layoutManager;
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {


    }


    @Override
    protected String provideTitle() {
        return getIntent().getStringExtra("title");
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


    /**
     * 从数据源获取数据
     */
    @Override
    public void requestDataRefresh() {
        super.requestDataRefresh();
        page = 1;
        presenter.getData(page, count);
    }

    /**
     * @param view
     * @param pos
     * @param item
     */
    @Override
    public void onItemClick(View view, int pos, Video item) {
        if (item.UserId != 0) {
        }
    }

    @Override
    public Integer getData() {
        return catId;
    }
}
