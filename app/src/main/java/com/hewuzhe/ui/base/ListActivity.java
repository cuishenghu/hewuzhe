package com.hewuzhe.ui.base;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.hewuzhe.R;
import com.hewuzhe.presenter.base.ListPresenter;
import com.hewuzhe.ui.adapter.base.BaseAdapter;
import com.hewuzhe.ui.inter.OnItemClickListener;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * SwipeRecycleViewActivity
 * Created by xianguangjin on 15/12/23.
 */
public abstract class ListActivity<P extends ListPresenter, A extends BaseAdapter, M> extends ToolBarActivity<P> implements OnItemClickListener<M> {

    @Bind(R.id.recycler_view)
    public RecyclerView recyclerView;
    public A adapter;
    public RecyclerView.LayoutManager layoutManager;

    public int page = 1;
    public int count = 10;

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);

        this.layoutManager = provideLayoutManager();
        recyclerView.setLayoutManager(provideLayoutManager());

        this.adapter = provideAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(this);

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


}
