package com.hewuzhe.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.adhamenaya.listeners.OnItemClickListener;
import com.adhamenaya.views.BlockPattern;
import com.adhamenaya.views.MosaicLayout;
import com.hewuzhe.R;
import com.hewuzhe.model.StudyOnlineCatItem;
import com.hewuzhe.presenter.StudyOnlineFragPresenter;
import com.hewuzhe.ui.adapter.MyAdapter;
import com.hewuzhe.ui.base.ToolBarActivity;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.view.StudyOnlineFragView;

import java.util.ArrayList;

import butterknife.Bind;

public class CateSelectActivity extends ToolBarActivity<StudyOnlineFragPresenter> implements StudyOnlineFragView {

    @Bind(R.id.layout)
    MosaicLayout _Layout;


    BlockPattern.BLOCK_PATTERN pattern1[] = {BlockPattern.BLOCK_PATTERN.BIG, BlockPattern.BLOCK_PATTERN.BIG, BlockPattern.BLOCK_PATTERN.HORIZONTAL, BlockPattern.BLOCK_PATTERN.HORIZONTAL,
            BlockPattern.BLOCK_PATTERN.BIG, BlockPattern.BLOCK_PATTERN.BIG, BlockPattern.BLOCK_PATTERN.HORIZONTAL, BlockPattern.BLOCK_PATTERN.HORIZONTAL};

    BlockPattern.BLOCK_PATTERN pattern2[] = {BlockPattern.BLOCK_PATTERN.HORIZONTAL, BlockPattern.BLOCK_PATTERN.HORIZONTAL, BlockPattern.BLOCK_PATTERN.BIG, BlockPattern.BLOCK_PATTERN.BIG, BlockPattern.BLOCK_PATTERN.HORIZONTAL,
            BlockPattern.BLOCK_PATTERN.HORIZONTAL, BlockPattern.BLOCK_PATTERN.BIG, BlockPattern.BLOCK_PATTERN.BIG};

    /**
     * @return 提供LayoutId
     */
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_cate_select;
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {

    }


    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        presenter.GetChannel();
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
     * 绑定Presenter
     */
    @Override
    public StudyOnlineFragPresenter createPresenter() {
        return new StudyOnlineFragPresenter();
    }


    /**
     * @return 提供标题
     */
    @Override
    protected String provideTitle() {
        return "选择分类";
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

                intent = new Intent();
                intent.putExtra("data", new Bun().putInt("id", item.Id).putString("title", item.Name).ok());

                CateSelectActivity.this.setResult(RESULT_OK, intent);
                finishActivity();
            }
        });

    }
}
