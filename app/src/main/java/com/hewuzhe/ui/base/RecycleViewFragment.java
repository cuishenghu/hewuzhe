package com.hewuzhe.ui.base;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hewuzhe.R;
import com.hewuzhe.presenter.base.RefreshAndLoadMorePresenter;
import com.hewuzhe.ui.adapter.base.BaseAdapter;
import com.hewuzhe.ui.inter.OnItemClickListener;
import com.hewuzhe.view.base.LoadMoreView;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by zycom on 2016/3/31.
 */
public abstract class RecycleViewFragment<P extends RefreshAndLoadMorePresenter, A extends BaseAdapter, M> extends ToolBarFragment<P> implements OnItemClickListener<M>, LoadMoreView {

    @Bind(R.id.recycler_view)
    public RecyclerView recyclerView;
    public A adapter;
    public RecyclerView.LayoutManager layoutManager;

    public int page = 1;
    public int count = 10;

    @Override
    protected void initThings(View view) {
        super.initThings(view);
        this.layoutManager = provideLayoutManager();
        recyclerView.setLayoutManager(layoutManager);

        this.adapter = provideAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(this);
        if (layoutManager instanceof LinearLayoutManager) {
            recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
//                    int lastPos = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
//                    if (lastPos >= adapter.data.size() - 1) {
//                        snb("加载更多", recyclerView);
//                    }

                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    onscroll(recyclerView, dx, dy);
                    if (canScrollDown(recyclerView)) {
                    } else {
                        loadMore();
                    }
                }
            });

        }
    }


    private boolean canScrollDown(RecyclerView recyclerView) {
        return ViewCompat.canScrollVertically(recyclerView, 1);
    }

    /**
     * RecycleView监听函数
     *
     * @param recyclerView
     * @param dx
     * @param dy
     */
    public void onscroll(RecyclerView recyclerView, int dx, int dy) {


    }

    /**
     * @return 提供Adapter
     */
    protected abstract A provideAdapter();

    /**
     * @return 提供LayoutManager
     */
    protected abstract RecyclerView.LayoutManager provideLayoutManager();


    /**
     * 基本的绑定数据
     *
     * @param data
     */
    public void bd(ArrayList<M> data) {
        if (page == 1) {
            adapter.addDatas(data);
        } else {
            adapter.addMore(data);
        }

    }
//
//    @Override
//    public void requestDataRefresh() {
//        super.requestDataRefresh();
//        page = 1;
//        presenter.getData(page, count);
//    }

    @Override
    public void loadMore() {
        if (adapter.getStatus() == BaseAdapter.STATUS_HASMORE) {
            page++;
            presenter.getData(page, count);
            adapter.loading();
        }
    }


    @Override
    public void hasMore() {
        adapter.hasMore();
    }

    @Override
    public void noMore() {
        adapter.noMore();
    }

    @Override
    public void loadMore(String tip) {
        if (adapter.getStatus() == BaseAdapter.STATUS_HASMORE) {
            page++;
            presenter.getData(page, count);
            adapter.loading(tip);
        }
    }

    @Override
    public void hasMore(String tip) {
        adapter.hasMore(tip);
    }

    @Override
    public void noMore(String tip) {
        adapter.noMore(tip);
    }


}
