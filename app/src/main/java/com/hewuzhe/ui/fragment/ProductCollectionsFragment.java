package com.hewuzhe.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hewuzhe.R;
import com.hewuzhe.model.ArticleCollection;
import com.hewuzhe.model.Product;
import com.hewuzhe.model.ProductCollection;
import com.hewuzhe.presenter.ProductCollectionPresenter;
import com.hewuzhe.ui.activity.ProductInfoActivity;
import com.hewuzhe.ui.adapter.ProductCollectionAdapter;
import com.hewuzhe.ui.base.SwipeRecycleViewFragment;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.NU;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.ProductCollectionView;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by zycom on 2016/2/20.
 */
public class ProductCollectionsFragment extends SwipeRecycleViewFragment<ProductCollectionPresenter, ProductCollectionAdapter, ProductCollection> implements ProductCollectionView {


    public static ProductCollectionsFragment newInstance() {
        return new ProductCollectionsFragment();
    }

    public ProductCollectionsFragment() {
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
    protected ProductCollectionAdapter provideAdapter() {
        return new ProductCollectionAdapter(getActivity(),presenter,recyclerView);
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
    public ProductCollectionPresenter createPresenter() {
        return new ProductCollectionPresenter();
    }


    /**
     * @return 提供LayoutManager
     */
    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getContext());
    }


    @Override
    public void bindData(ArrayList<ProductCollection> data) {

        bd(data);
    }

    /**
     * @param view
     * @param pos
     * @param item
     */
    @Override
    public void onItemClick(View view, int pos, ProductCollection item) {

    }

    @Override
    public Integer getData() {
        return new SessionUtil(getContext()).getUserId();
    }

    @Override
    public void onReceive(Integer msg) {


    }

    /**
     * 获取当前编辑状态
     *
     * @return
     */
    @Override
    public Boolean getMsg() {
        return NU.isNull(adapter) ? false : adapter.getCheckShowStatus();
    }

    @Override
    public void collectAndOther() {
        adapter.showCheck();
    }

}
