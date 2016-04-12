package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.hewuzhe.R;
import com.hewuzhe.model.Video;
import com.hewuzhe.presenter.Videos2Presenter;
import com.hewuzhe.ui.adapter.GridItemDecoration;
import com.hewuzhe.ui.adapter.Videos3Adapter;
import com.hewuzhe.ui.base.SwipeRecycleViewActivity;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.view.Videos2View;

import java.util.ArrayList;

import butterknife.Bind;

public class Videos_2Activity extends SwipeRecycleViewActivity<Videos2Presenter, Videos3Adapter, Video> implements Videos2View {

    private int catId;
    @Bind(R.id.img_search)
    ImageView imgSearch;
    @Bind(R.id.swicth_button)
    CheckBox swicthButton;
    String where = "";
    String who="";

    private GridLayoutManager gridLayoutManager;
    private GridItemDecoration decoration;

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
        this.where = getIntent().getStringExtra("where");
        this.who=getIntent().getStringExtra("who");//有课程传值过来
        refresh(true);
        if (!StringUtil.isEmpty(where)) {
            if (where.equals("six"))
//                presenter.getData(page, count);
            presenter.SelectVideoByRecommendCategory(new SessionUtil(Videos_2Activity.this).getUserId(),page,count);

            if (where.equals("five"))//交流
                presenter.SelectVideoByCategory(page, count);
            /**
             * 搜索功能
             * 因为搜索点击事件放在视频的交流里面,因为课程和交流用的一个activity,现在把搜索点击事件移至initListener里面
             */
//            imgSearch.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    startActivity(SearchOnlineVideosActivity.class, new Bun().putString("title", "搜索").putInt("catId", catId).ok());
//                }
//            });
        } else {
            presenter.getData(page, count);
        }

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
    protected Videos3Adapter provideAdapter() {
        this.who=getIntent().getStringExtra("who");
        decoration = new GridItemDecoration(10, 1);
        recyclerView.addItemDecoration(decoration);
        return new Videos3Adapter(getContext(),who);
    }

    /**
     * @return 提供LayoutManager
     */
    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == adapter.getItemCount()-1) {
                    return swicthButton.isChecked() ? 1 :2;

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
        /**
         * 搜索按钮
         */
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(SearchOnlineVideosActivity.class, new Bun().putString("title", "搜索").putInt("catId", catId).ok());
            }
        });
        /**
         * 控制单双列显示按钮
         */
        swicthButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                decoration.setSpanCount(isChecked ? 1 : 2);
                gridLayoutManager.setSpanCount(isChecked ? 1 : 2);
                adapter.changeViewHeight(isChecked);
                adapter.notifyDataSetChanged();
            }
        });

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
        startActivity(VideoDetail2Activity.class, new Bun().putInt("Id", item.Id).ok());

    }

    @Override
    public Integer getData() {
        return catId;
    }
}
