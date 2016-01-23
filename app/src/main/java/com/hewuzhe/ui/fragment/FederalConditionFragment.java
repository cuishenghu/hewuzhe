package com.hewuzhe.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hewuzhe.R;
import com.hewuzhe.model.Article;
import com.hewuzhe.presenter.ArticlesPresenter;
import com.hewuzhe.ui.activity.FederalConditionDetailActivity;
import com.hewuzhe.ui.adapter.ArticlesAdapter;
import com.hewuzhe.ui.base.SwipeRecycleViewFragment;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.view.ArticlesView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FederalConditionFragment extends SwipeRecycleViewFragment<ArticlesPresenter, ArticlesAdapter, Article> implements ArticlesView {


    private static final int CORRPERATION = 0;
    private static final int CONDITION = 1;
    private int catId;
    private int flag;

    public static FederalConditionFragment newInstance(Bundle args) {
        FederalConditionFragment fragment = new FederalConditionFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public FederalConditionFragment() {
        // Required empty public constructor
    }


    @Override
    public void initListeners() {

    }

    @Override
    protected void initThings(View view) {
        super.initThings(view);
        catId = getArguments().getInt("catid");
        if (catId == 76 || catId == 77) {
            //招聘合作
            flag = CORRPERATION;
        } else if (catId >= 57 && catId <= 61) {
            //联盟动态
            flag = CONDITION;
        } else if (catId >= 72 && catId <= 75) {

        }

        getArticles();
    }

    private void getArticles() {
        switch (flag) {
            case CORRPERATION:
                presenter.getArticlesTwo(catId, page, count);
                break;
            case CONDITION:
                presenter.getData(page, count);
                break;
        }
    }

    /**
     * @return 提供Adapter
     */
    @Override
    protected ArticlesAdapter provideAdapter() {
        return new ArticlesAdapter(getContext());
    }

    /**
     * @return 提供LayoutManager
     */
    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    }

    /**
     * @return 提供LayoutId
     */
    @Override
    public int provideLayoutId() {
        return R.layout.fragment_federal_condition;
    }

    /**
     * 绑定Presenter
     */
    @Override
    public ArticlesPresenter createPresenter() {
        return new ArticlesPresenter();
    }

    @Override
    public void bindData(ArrayList<Article> data) {

        if (page == 1) {
            adapter.addDatas(data);
        } else {
            adapter.addMore(data);
        }
    }

    /**
     * @param view
     * @param pos
     * @param item
     */
    @Override
    public void onItemClick(View view, int pos, Article item) {
        startActivity(FederalConditionDetailActivity.class, new Bun().putString("title", getArguments().getString("name")).putInt("id", item.Id).ok());
    }

    @Override
    public Integer getData() {
        return catId;
    }
}
