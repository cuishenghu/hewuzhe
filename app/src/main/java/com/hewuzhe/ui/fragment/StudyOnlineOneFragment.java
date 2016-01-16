package com.hewuzhe.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.adhamenaya.listeners.OnItemClickListener;
import com.adhamenaya.views.BlockPattern;
import com.adhamenaya.views.MosaicLayout;
import com.hewuzhe.R;
import com.hewuzhe.model.StudyOnlineCatItem;
import com.hewuzhe.presenter.StudyOnlineFragPresenter;
import com.hewuzhe.ui.activity.Videos_2Activity;
import com.hewuzhe.ui.adapter.MyAdapter;
import com.hewuzhe.ui.base.BaseFragment;
import com.hewuzhe.view.StudyOnlineFragView;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudyOnlineOneFragment extends BaseFragment<StudyOnlineFragPresenter> implements StudyOnlineFragView {


    @Bind(R.id.layout)
    MosaicLayout _Layout;

    private int id;


    BlockPattern.BLOCK_PATTERN pattern1[] = {BlockPattern.BLOCK_PATTERN.BIG, BlockPattern.BLOCK_PATTERN.BIG, BlockPattern.BLOCK_PATTERN.HORIZONTAL, BlockPattern.BLOCK_PATTERN.HORIZONTAL,
            BlockPattern.BLOCK_PATTERN.BIG, BlockPattern.BLOCK_PATTERN.BIG, BlockPattern.BLOCK_PATTERN.HORIZONTAL, BlockPattern.BLOCK_PATTERN.HORIZONTAL};

    BlockPattern.BLOCK_PATTERN pattern2[] = {BlockPattern.BLOCK_PATTERN.HORIZONTAL, BlockPattern.BLOCK_PATTERN.HORIZONTAL, BlockPattern.BLOCK_PATTERN.BIG, BlockPattern.BLOCK_PATTERN.BIG, BlockPattern.BLOCK_PATTERN.HORIZONTAL,
            BlockPattern.BLOCK_PATTERN.HORIZONTAL, BlockPattern.BLOCK_PATTERN.BIG, BlockPattern.BLOCK_PATTERN.BIG};


    public static StudyOnlineOneFragment newInstance(Bundle args) {
        StudyOnlineOneFragment instance = new StudyOnlineOneFragment();
        args.putInt("id", args.getInt("id"));
        instance.setArguments(args);
        return instance;
    }

    public StudyOnlineOneFragment() {

    }


    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {

    }

    /**
     * 初始化一些事情
     *
     * @param view
     */
    @Override
    protected void initThings(View view) {
        id = getArguments().getInt("id");
        presenter.getCates(id);

        orderedSelectedPatterns();

    }

    private void randomAllPatters() {
        _Layout.chooseRandomPattern(true);

    }

    private void randomSelectedPatterns() {
        _Layout.addPattern(pattern1);
        _Layout.addPattern(pattern2);
        _Layout.chooseRandomPattern(true);

    }

    private void orderedSelectedPatterns() {
        _Layout.addPattern(pattern1);
        _Layout.addPattern(pattern2);
        _Layout.chooseRandomPattern(false);

    }

    /**
     * @return 提供LayoutId
     */
    @Override
    public int provideLayoutId() {
        return R.layout.fragment_study_online_one;
    }

    /**
     * 绑定Presenter
     */
    @Override
    public StudyOnlineFragPresenter createPresenter() {
        return new StudyOnlineFragPresenter();
    }


    @Override
    public void bindData(final ArrayList<StudyOnlineCatItem> data) {

        MyAdapter adapter = new MyAdapter(getContext());
        adapter.setData(data);
        _Layout.setAdapter(adapter);

        _Layout.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onClick(int position) {
                StudyOnlineCatItem item = data.get(position);
                Intent intent = new Intent(getActivity(), Videos_2Activity.class);
                intent.putExtra("title", item.Name);
                intent.putExtra("id", item.Id);
                getActivity().startActivity(intent);

            }
        });


    }
}