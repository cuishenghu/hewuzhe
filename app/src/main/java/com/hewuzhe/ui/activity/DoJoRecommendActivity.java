package com.hewuzhe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

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

//import com.amap.api.location.AMapLocation;
//import com.amap.api.location.AMapLocationClient;
//import com.amap.api.location.AMapLocationClientOption;
//import com.amap.api.location.AMapLocationListener;

public class DoJoRecommendActivity extends SwipeRecycleViewActivity<DojoRecommendPresenter, DojoRecommendAdapter, Dojo> implements DojoRecommendView, OnLocListener {

    @Bind(R.id.lay_city)
    LinearLayout _LayCity;
    private String name;
    private int id;

    private int cityId = -1;

    private String _cityName = "临沂";
    private double _Lat=34;
    private double _Lng=120;
//    private AMapLocationClient locationClient;

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


//        locationClient = new AMapLocationClient(getApplicationContext());
//        locationClient.setLocationListener(this);

//        initLoc();


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
//        locationClient.stopLocation();
        super.onStop();
    }

//
//    @Override
//    public void onLocationChanged(AMapLocation aMapLocation) {
//        if (aMapLocation != null) {
//            if (aMapLocation.getErrorCode() == 0) {
//                //定位成功回调信息，设置相关消息
//                _Lat = aMapLocation.getLatitude();//获取纬度
//                _Lng = aMapLocation.getLongitude();//获取经度
//                _cityName = aMapLocation.getCity();
//
//                KLog.d(_cityName + _Lat + "---" + _Lng);
//
//                tvTitle.setText(_cityName);
//                presenter.getData(_cityName, _Lat + "", _Lng + "", page, count);
//
//            }
//        }
//    }


//    public void initLoc() {
//        //声明mLocationOption对象
//        AMapLocationClientOption mLocationOption = null;
////初始化定位参数
//        mLocationOption = new AMapLocationClientOption();
////设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
//        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
////设置是否返回地址信息（默认返回地址信息）
//        mLocationOption.setNeedAddress(true);
////设置是否只定位一次,默认为false
//        mLocationOption.setOnceLocation(true);
////设置是否强制刷新WIFI，默认为强制刷新
//        mLocationOption.setWifiActiveScan(true);
////设置是否允许模拟位置,默认为false，不允许模拟位置
//        mLocationOption.setMockEnable(false);
////设置定位间隔,单位毫秒,默认为2000ms
////        mLocationOption.setInterval(2000);
////给定位客户端对象设置定位参数
//        locationClient.setLocationOption(mLocationOption);
////启动定位
//
//        locationClient.startLocation();
//
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

//        locationClient.onDestroy();
    }


}
