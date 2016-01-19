package com.hewuzhe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.hewuzhe.R;
import com.hewuzhe.model.Dojo;
import com.hewuzhe.presenter.DojoRecommendPresenter;
import com.hewuzhe.ui.App;
import com.hewuzhe.ui.adapter.DojoRecommendAdapter;
import com.hewuzhe.ui.adapter.base.BaseAdapter;
import com.hewuzhe.ui.base.SwipeRecycleViewActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.inter.OnLocListener;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.view.DojoRecommendView;

import java.util.ArrayList;

import butterknife.Bind;

public class DoJoRecommendActivity extends SwipeRecycleViewActivity<DojoRecommendPresenter, DojoRecommendAdapter, Dojo> implements DojoRecommendView, OnLocListener {

    @Bind(R.id.lay_city)
    LinearLayout _LayCity;
    @Bind(R.id.lay_no_loc)
    LinearLayout _LayNoLoc;
    private String name;
    private int id;

    private int cityId = -1;

    private String _cityName = "临沂";
    private double _Lat = 34;
    private double _Lng = 120;
    private LocationClient mLocationClient;

    public BDLocationListener myListener = new MyLocationListener();

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
        return R.layout.activity_do_jo_recommend;
    }

    @Override
    protected void onStart() {
        super.onStart();
        ((App) getApplication()).onLocListeners.add(this);
    }

    /**
     * @param savedInstanceState
     */
    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);

        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数

        tvTitle.setText("定位中...");
        showDialog("提示", "正在定位...");
        initLocation();
        mLocationClient.start();

        tvTitle.setText(_cityName);
        presenter.getData(_cityName, _Lat + "", _Lng + "", page, count);
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {
        _LayCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(CitySelectActivity.class, new Bun().putString("cityName", _cityName).ok(), C.REQUEST_SELECT_CATE);
            }
        });
    }

    /**
     * 绑定Presenter
     */
    @Override
    public DojoRecommendPresenter createPresenter() {
        return new DojoRecommendPresenter();
    }

    /**
     * @return 提供Adapter
     */
    @Override
    protected DojoRecommendAdapter provideAdapter() {
        return new DojoRecommendAdapter(getContext());
    }

    /**
     * @return 提供LayoutManager
     */
    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getContext());
    }


    @Override
    public void bindData(ArrayList<Dojo> data) {
        bd(data);
    }


    /**
     * @param view
     * @param pos
     * @param item
     */
    @Override
    public void onItemClick(View view, int pos, Dojo item) {
        startActivity(DojoDetailActivity.class, new Bun().putInt("id", item.Id).ok());
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
            presenter.getData(_cityName, _Lat + "", _Lng + "", page, count);
        }

    }

    @Override
    public void loadMore() {
        if (cityId != -1) {
            super.loadMore();
        }
        if (adapter.getStatus() == BaseAdapter.STATUS_HASMORE) {
            page++;
            presenter.getData(_cityName, _Lat + "", _Lng + "", page, count);
            adapter.loading();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == C.RESULT_ONE) {
            Bundle bun = data.getBundleExtra("data");
            if (bun != null) {

                name = bun.getString("name");
                id = bun.getInt("id");
                tvTitle.setText(name);
                page = 1;
                cityId = id;
                presenter.getData(page, count);
            }

        } else if (resultCode == C.RESULT_TWO) {
            tvTitle.setText(_cityName);
            presenter.getData(_cityName, _Lat + "", _Lng + "", page, count);

        }
    }


    @Override
    public Integer getData() {
        return cityId;
    }


    @Override
    protected void onStop() {
        ((App) getApplication()).onLocListeners.remove(this);
        if (mLocationClient != null) {
            mLocationClient.stop();

        }

        super.onStop();
    }


    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location != null) {
                _Lng = location.getLongitude();
                _Lat = location.getLatitude();
                _cityName = location.getCity();

                tvTitle.setText(_cityName);
                dismissDialog();

                presenter.getData(_cityName, _Lat + "", _Lng + "", page, count);
            } else {
                _LayNoLoc.setVisibility(View.VISIBLE);

            }
        }
    }


    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
//        int span = 1000;
//        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationClient != null) {
            mLocationClient.stop();
            mLocationClient = null;
        }
    }


}
