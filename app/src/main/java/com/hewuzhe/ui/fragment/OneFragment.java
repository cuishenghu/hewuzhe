package com.hewuzhe.ui.fragment;


import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hewuzhe.R;
import com.hewuzhe.model.Video;
import com.hewuzhe.presenter.OneFragmentPresenter;
import com.hewuzhe.ui.activity.VideoDetail2Activity;
import com.hewuzhe.ui.adapter.GridItemDecoration;
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
    private boolean isChecked = false;

    private GridLayoutManager gridLayoutManager;
    private GridItemDecoration decoration;

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
        /**
         * 获取关注视频的列表和热门列表参数不一致(path一致,请求参数不一致),接口分开写
         * 因为刷新列表抽为公用只是getData,所以把判定条件写在presenter里面了(根据不同的path调用不同的接口)
         * 获取关注的视频
         */
//        if (path.equals("SelectGuanzhuVideoList")) {
//            presenter.SelectGuanzhuVideoList(page, count);
//        }else{
//            presenter.getData(page, count);
//        }
    }

    /**
     * @return 提供Adapter
     */
    @Override
    protected VideoAdapter provideAdapter() {
        decoration = new GridItemDecoration(10, 1);
        recyclerView.addItemDecoration(decoration);
        return new VideoAdapter(getActivity());
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
                if (position == adapter.getItemCount() - 1) {
                    return isChecked ? 1 : 2;
                }
                return 1;
            }
        });
        layoutManager = gridLayoutManager;
        return layoutManager;
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
        startActivity(VideoDetail2Activity.class, new Bun().putInt("Id", item.Id).ok());
        if (item.UserId != 0) {
        }
    }

    @Override
    public String getData() {
        return path;
    }

    public void changeSpanCount(boolean isChecked) {
        this.isChecked = isChecked;
        decoration.setSpanCount(isChecked ? 1 : 2);
        gridLayoutManager.setSpanCount(isChecked ? 1 : 2);
        adapter.changeViewHeight(isChecked);
        adapter.notifyDataSetChanged();
    }
}
