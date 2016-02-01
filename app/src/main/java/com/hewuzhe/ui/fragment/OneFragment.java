package com.hewuzhe.ui.fragment;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hewuzhe.R;
import com.hewuzhe.model.Video;
import com.hewuzhe.presenter.OneFragmentPresenter;
import com.hewuzhe.ui.adapter.VideoAdapter;
import com.hewuzhe.ui.base.SwipeRecycleViewFragment;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.view.OneFragmentView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class OneFragment extends SwipeRecycleViewFragment<OneFragmentPresenter, VideoAdapter, Video> implements OneFragmentView {

    private String path;

    public static OneFragment newInstance(String path) {
        OneFragment oneFragment = new OneFragment();
        oneFragment.setArguments(new Bun().putString("path", path).ok());
        return oneFragment;
    }

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void initListeners() {


    }

    @Override
    protected void initThings(View view) {
        super.initThings(view);
        path = getArguments().getString("path");
        presenter.getData(page, count);
    }

    /**
     * @return 提供Adapter
     */
    @Override
    protected VideoAdapter provideAdapter() {
        return new VideoAdapter(getActivity());
    }

    /**
     * @return 提供LayoutManager
     */
    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }


    /**
     * @return 提供LayoutId
     */
    @Override
    public int provideLayoutId() {
        return R.layout.fragment_one;
    }

    /**
     * 绑定Presenter
     */
    @Override
    public OneFragmentPresenter createPresenter() {
        return new OneFragmentPresenter();
    }


    @Override
    public void bindData(ArrayList<Video> data) {
        bd(data);
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
    public String getData() {
        return path;
    }
}
