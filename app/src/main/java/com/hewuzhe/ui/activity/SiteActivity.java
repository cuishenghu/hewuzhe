package com.hewuzhe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.hewuzhe.R;
import com.hewuzhe.model.Address;
import com.hewuzhe.model.Site;
import com.hewuzhe.presenter.SitePresenter;
import com.hewuzhe.ui.adapter.SiteAdapter;
import com.hewuzhe.ui.base.RecycleViewActivity;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.SiteView;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by csh on 2016/02/01.
 * 收货地址列表
 */
public class SiteActivity extends RecycleViewActivity<SitePresenter, SiteAdapter, Site>
        implements SiteView,View.OnClickListener{
    @Bind(R.id.submit_area)
    Button submitArea;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_site;
    }

    @Override
    protected String provideTitle() {
        return "地址管理";
    }

    @Override
    public SitePresenter createPresenter() {
        return new SitePresenter();
    }

    @Override
    protected SiteAdapter provideAdapter() {
        return new SiteAdapter(getContext(), presenter, getIntent().getIntExtra("sel", 0));
    }

    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    public void initListeners() {
        submitArea.setOnClickListener(this);
        if(tvAction != null)
            tvAction.setText("编辑");
        presenter.getData(page, count);
    }

    @Override
    public void bindData(ArrayList<Site> data) {
        adapter.data.clear();
        adapter.data.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            presenter.getData(page, count);
        }
    }

    @Override
    public void onClick(View v) {
        /**新增地址*/
        startActivityForResult(SiteAddActivity.class, RESULT_FIRST_USER);
    }

    @Override
    public void onItemClick(View view, int pos, Site item) {
        Bundle mBundle = getIntentData();
        mBundle.putSerializable("site", item);
        startActivityForResult(SiteAddActivity.class, mBundle, RESULT_FIRST_USER);
    }

    @Override
    public Integer getData() {
        return new SessionUtil(getContext()).getUserId();
    }

    @Override
    public void setData(Site site) {}

    @Override
    public void setProvinces(ArrayList<Address> address) {

    }

    @Override
    public void setCitys(ArrayList<Address> address) {

    }

    @Override
    public void setDistricts(ArrayList<Address> address) {

    }
}
