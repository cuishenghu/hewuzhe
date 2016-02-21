package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hewuzhe.R;
import com.hewuzhe.model.ProductComment;
import com.hewuzhe.presenter.ProductCommentPresenter;
import com.hewuzhe.ui.adapter.ProductCommentAdapter;
import com.hewuzhe.ui.base.RecycleViewActivity;
import com.hewuzhe.view.ProductCommentView;

import java.util.ArrayList;

/**
 * Created by zycom on 2016/2/20.
 */
public class ProductCommentActivity extends RecycleViewActivity<ProductCommentPresenter, ProductCommentAdapter, ProductComment> implements ProductCommentView {


    @Override
    public ProductComment getData() {
        return null;
    }

    @Override
    public void bindData(ArrayList<ProductComment> data) {
        bd(data);
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);

        presenter.getData(getIntent().getIntExtra("productId",0),page, count);
    }

    @Override
    protected ProductCommentAdapter provideAdapter() {
        return new ProductCommentAdapter(getContext(), presenter);
    }

    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    protected CharSequence provideTitle() {
        return "商品评价";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_product_comment;
    }

    @Override
    public void initListeners() {

    }

    @Override
    public ProductCommentPresenter createPresenter() {
        return new ProductCommentPresenter();
    }

    @Override
    public void onItemClick(View view, int pos, ProductComment item) {

    }
}
