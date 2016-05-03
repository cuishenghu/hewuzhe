package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.model.Dojo;
import com.hewuzhe.presenter.SearchDojiPresenter;
import com.hewuzhe.ui.adapter.DojoRecommendAdapter;
import com.hewuzhe.ui.adapter.GridItemDecoration;
import com.hewuzhe.ui.base.RecycleViewActivity;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.view.SearchDojoView;
import com.hewuzhe.view.SearchPtlView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by ninos on 2016/5/3.
 */
public class SearchDojoActivity extends RecycleViewActivity<SearchDojiPresenter, DojoRecommendAdapter, Dojo> implements SearchDojoView {

    @Bind(R.id.edt_search_content)
    TextView edt_search_content;    //定位当前地址

    @Bind(R.id.img_search)
    ImageView img_search;    //定位当前地址

    private int cityId=0;           //城市id
    private int isrecommend = 0;      //是否推荐0：全部 1：推荐 2：不推荐

    private String _cityName = "";
    private double _Lat = 0;
    private double _Lng = 0;
    private int length = 5000;
    private String searchString="";
    private int flg=2;

    String []data;

    private GridLayoutManager gridLayoutManager;
    private GridItemDecoration decoration;

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        data = getIntent().getStringArrayExtra("data");
        presenter.getData(page,count);

    }

    @Override
    protected DojoRecommendAdapter provideAdapter() {
        decoration = new GridItemDecoration(10, 1);
        recyclerView.addItemDecoration(decoration);
        return new DojoRecommendAdapter(getContext(),this);
    }

    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        gridLayoutManager = new GridLayoutManager(getContext(), 2);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == adapter.getItemCount() - 1) {
                    return 2;
                }
                return 2;
            }
        });
        layoutManager = gridLayoutManager;
        return layoutManager;
    }

    @Override
    protected CharSequence provideTitle() {
        return "场馆搜索";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.search_new_list;
    }

    @Override
    public void initListeners() {

    }

    @Override
    public SearchDojiPresenter createPresenter() {
        return new SearchDojiPresenter();
    }

    @Override
    public Dojo getData() {
        return null;
    }

    @Override
    public void bindData(ArrayList<Dojo> data) {
        bd(data);
    }

    @Override
    public void onItemClick(View view, int pos, Dojo item) {
        startActivity(PrivateTrainerInfoActivity.class, new Bun().putInt("id", item.Id).ok());
    }

    @Override
    public String[] getStringData() {
        String []s = data;
        return s;
    }

    @OnClick(R.id.img_search)
    public void searchClick(){
        data[5]=edt_search_content.getText().toString();
        page = 1;
        presenter.getData(page,count);
    }

}
