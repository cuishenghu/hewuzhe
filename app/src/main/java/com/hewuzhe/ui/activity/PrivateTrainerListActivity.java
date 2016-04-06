package com.hewuzhe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.hewuzhe.R;
import com.hewuzhe.model.Dojo;
import com.hewuzhe.model.PrivateTrainerList;
import com.hewuzhe.presenter.DojoRecommendPresenter;
import com.hewuzhe.presenter.PrivateTrainerListPresenter;
import com.hewuzhe.ui.App;
import com.hewuzhe.ui.adapter.DojoRecommendAdapter;
import com.hewuzhe.ui.adapter.PrivateTrainerListAdapter;
import com.hewuzhe.ui.adapter.base.BaseAdapter;
import com.hewuzhe.ui.base.SwipeRecycleViewActivity;
import com.hewuzhe.ui.base.SwipeRecycleViewNoMoreActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.inter.OnLocListener;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.view.DojoRecommendView;
import com.hewuzhe.view.PrivateTrainerListView;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by zycom on 2016/3/14.
 */
public class PrivateTrainerListActivity extends SwipeRecycleViewNoMoreActivity<PrivateTrainerListPresenter, PrivateTrainerListAdapter, PrivateTrainerList> implements PrivateTrainerListView{

    @Bind(R.id.lay_city)
    LinearLayout _LayCity;
    @Bind(R.id.lay_no_loc)
    LinearLayout _LayNoLoc;


    private int cityId = -1;

    private String _cityName = "临沂";
    private double _Lat = 34;
    private double _Lng = 120;
    private int length=2000;

    private String classes;


    /**
     * @return 提供标题
     */
    @Override
    protected String provideTitle() {
        return "临沂";
    }

    /**
     * @return 提供LayoutId
     */
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_private_trainer_list;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * @param savedInstanceState
     */
    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        classes = getIntentData().getString("classes");
        _Lat = Double.parseDouble(getIntentData().getString("lat"));
        _Lng = Double.parseDouble(getIntentData().getString("lng"));
        _cityName = getIntentData().getString("title");
        length = getIntentData().getInt("length");
        tvTitle.setText(_cityName);
        presenter.getData(_cityName, _Lat + "", _Lng + "",classes,length, page, count);
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {

    }

    /**
     * 绑定Presenter
     */
    @Override
    public PrivateTrainerListPresenter createPresenter() {
        return new PrivateTrainerListPresenter();
    }

    /**
     * @return 提供Adapter
     */
    @Override
    protected PrivateTrainerListAdapter provideAdapter() {
        return new PrivateTrainerListAdapter(getContext(),presenter);
    }

    /**
     * @return 提供LayoutManager
     */
    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new GridLayoutManager(this,2);
    }


    @Override
    public void bindData(ArrayList<PrivateTrainerList> data) {
        bd(data);
    }


    /**
     * @param view
     * @param pos
     * @param item
     */
    @Override
    public void onItemClick(View view, int pos, PrivateTrainerList item) {
        startActivity(PrivateTrainerInfoActivity.class, new Bun().putInt("Id", item.Id).ok());
    }

    /**
     *
     */
    @Override
    public void requestDataRefresh() {
        if (cityId != -1) {
            super.requestDataRefresh();
        } else {
            page = 1;
            presenter.getData(_cityName, _Lat + "", _Lng + "",classes,length, page, count);
        }

    }

    @Override
    public void loadMore() {
        if (cityId != -1) {
            super.loadMore();
        }
        if (adapter.getStatus() == BaseAdapter.STATUS_HASMORE) {
            page++;
            presenter.getData(_cityName, _Lat + "", _Lng + "",classes,length, page, count);
            adapter.loading();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == C.RESULT_TWO) {
            tvTitle.setText(_cityName);
            cityId = -1;
            presenter.getData(_cityName, _Lat + "", _Lng + "",classes,length, page, count);

        }
    }


    @Override
    public Integer getData() {
        return cityId;
    }

    @Override
    public String []getStringData() {
        String []s = {_cityName,_Lat+"",_Lng+"",classes,length+""};
        return s;
    }



    @Override
    public void setNodata(int recordcount) {
        if (recordcount <= 0) {
            _LayNoLoc.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            _LayNoLoc.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

    }


}
