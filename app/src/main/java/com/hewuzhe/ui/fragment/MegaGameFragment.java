package com.hewuzhe.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hewuzhe.R;
import com.hewuzhe.model.MegaGame;
import com.hewuzhe.presenter.MegaGamePresenter;
import com.hewuzhe.ui.activity.MegaGameDetailActivity;
import com.hewuzhe.ui.activity.MegaGameVideosActivity;
import com.hewuzhe.ui.adapter.MegaGameAdapter;
import com.hewuzhe.ui.base.SwipeRecycleViewFragment;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.view.MegaGameView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MegaGameFragment extends SwipeRecycleViewFragment<MegaGamePresenter, MegaGameAdapter, MegaGame> implements MegaGameView {


    private String path;

    public static MegaGameFragment newInstance(Bundle args) {
        MegaGameFragment fragment = new MegaGameFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MegaGameFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initThings(View view) {
        super.initThings(view);
        path = getArguments().getString("path");
        refresh(true);
        presenter.getData(page, count);
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {


    }

    /**
     * @return 提供LayoutId
     */
    @Override
    public int provideLayoutId() {
        return R.layout.fragment_mega_game;
    }

    /**
     * 绑定Presenter
     */
    @Override
    public MegaGamePresenter createPresenter() {
        return new MegaGamePresenter();
    }

    @Override
    public void bindData(ArrayList<MegaGame> data) {
        bd(data);
    }


    /**
     * @return 提供Adapter
     */
    @Override
    protected MegaGameAdapter provideAdapter() {
        return new MegaGameAdapter(getContext());
    }

    /**
     * @return 提供LayoutManager
     */
    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    /**
     * @param view
     * @param pos
     * @param item
     */
    @Override
    public void onItemClick(View view, int pos, MegaGame item) {
        if (item.status == MegaGame.STATUS_READY) {
            startActivity(MegaGameDetailActivity.class, new Bun().putInt("id", item.Id).ok());
        } else if (item.status == MegaGame.STATUS_ING) {
            startActivity(MegaGameVideosActivity.class, new Bun().putInt("id", item.Id).putString("title", item.Name).ok());
        } else {
            startActivity(MegaGameDetailActivity.class, new Bun().putInt("id", item.Id).ok());
        }
    }

    @Override
    public String getData() {
        return path;
    }
}
