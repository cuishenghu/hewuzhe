package com.hewuzhe.ui.activity;

import android.os.Bundle;

import com.hewuzhe.R;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.base.ToolBarActivity;


public class BasicMapActivity extends ToolBarActivity {


//    @Bind(R.id.bmapView)
//    MapView mMapView;

    private String lat = "";
    private String lng = "";
    private String address = "";
//    private BaiduMap mBaiduMap;

    /**
     * @return 提供标题
     */
    @Override
    protected String provideTitle() {
        return "地图";
    }

    @Override
    protected void onStart() {
        super.onStart();
//        SDKInitializer.initialize(getApplicationContext());
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        lat = getIntentData().getString("lat");
        lng = getIntentData().getString("lng");
        address = getIntentData().getString("address");

//        mBaiduMap = mMapView.getMap();

//        MyLocationData locData = new MyLocationData.Builder()
//                .accuracy(10)
//                         此处设置开发者获取到的方向信息，顺时针0-360
//                .direction(100).latitude(Double.parseDouble(lat))
//                .longitude(Double.parseDouble(lng)).build();
//        mBaiduMap.setMyLocationData(locData);
//
//        LatLng ll = new LatLng(Double.parseDouble(lng),
//                Double.parseDouble(lat));
//        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
//        mBaiduMap.animateMapStatus(u);
    }

    /**
     * @return 提供LayoutId
     */
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_basic_map;
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
    public BasePresenterImp createPresenter() {
        return null;
    }


    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
//        mMapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
//        mMapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mMapView.onDestroy();

//        mBaiduMap.setMyLocationEnabled(false);
//        mMapView.onDestroy();
//        mMapView = null;
    }


}
