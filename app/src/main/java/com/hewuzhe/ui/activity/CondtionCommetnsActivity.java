package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hewuzhe.R;
import com.hewuzhe.model.ConditionComment;
import com.hewuzhe.presenter.ConditionCommentsPresenter;
import com.hewuzhe.ui.adapter.MyConditionCommentAdapter;
import com.hewuzhe.ui.base.SwipeRecycleViewActivity;
import com.hewuzhe.view.ConditionCommentsView;

import java.util.ArrayList;

public class CondtionCommetnsActivity extends SwipeRecycleViewActivity<ConditionCommentsPresenter, MyConditionCommentAdapter, ConditionComment> implements ConditionCommentsView {

    /**
     * @return 提供LayoutId
     */
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_condtion_commetns;
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

        presenter.getData(page, count);
    }

    /**
     * 绑定Presenter
     */
    @Override
    public ConditionCommentsPresenter createPresenter() {
        return new ConditionCommentsPresenter();
    }

    /**
     * @return 提供Adapter
     */
    @Override
    protected MyConditionCommentAdapter provideAdapter() {
        return new MyConditionCommentAdapter(getContext());
    }

    /**
     * @return 提供LayoutManager
     */
    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    /**
     * @return 提供标题
     */
    @Override
    protected String provideTitle() {
        return "朋友圈的评论提示";
    }

    @Override
    public void bindData(ArrayList<ConditionComment> data) {

        bd(data);
    }

    /**
     * @param view
     * @param pos
     * @param item
     */
    @Override
    public void onItemClick(View view, int pos, ConditionComment item) {


    }
}
