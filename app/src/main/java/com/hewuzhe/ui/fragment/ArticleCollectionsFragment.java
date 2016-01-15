package com.hewuzhe.ui.fragment;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hewuzhe.R;
import com.hewuzhe.model.ArticleCollection;
import com.hewuzhe.presenter.ArticleCollectionPresenter;
import com.hewuzhe.ui.activity.FederalConditionDetailActivity;
import com.hewuzhe.ui.adapter.ArticleAdapter;
import com.hewuzhe.ui.base.SwipeRecycleViewFragment;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.ArticleCollectionsView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleCollectionsFragment extends SwipeRecycleViewFragment<ArticleCollectionPresenter, ArticleAdapter, ArticleCollection> implements ArticleCollectionsView {


    public static ArticleCollectionsFragment newInstance() {
        return new ArticleCollectionsFragment();
    }

    public ArticleCollectionsFragment() {
        // Required empty public constructor

    }


    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {

    }

    @Override
    protected void initThings(View view) {
        super.initThings(view);

        presenter.getData(page, count);

    }

    /**
     * @return 提供Adapter
     */
    @Override
    protected ArticleAdapter provideAdapter() {
        return new ArticleAdapter(getActivity());
    }

    /**
     * @return 提供LayoutId
     */
    @Override
    public int provideLayoutId() {
        return R.layout.fragment_article_collections;
    }

    /**
     * 绑定Presenter
     */
    @Override
    public ArticleCollectionPresenter createPresenter() {
        return new ArticleCollectionPresenter();
    }


    /**
     * @return 提供LayoutManager
     */
    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getContext());
    }


    @Override
    public void bindData(ArrayList<ArticleCollection> data) {

        bd(data);
    }

    /**
     * @param view
     * @param pos
     * @param item
     */
    @Override
    public void onItemClick(View view, int pos, ArticleCollection item) {

        startActivity(FederalConditionDetailActivity.class, new Bun().putInt("id", item.Id).ok());
    }

    @Override
    public Integer getData() {
        return new SessionUtil(getContext()).getUserId();
    }
}
