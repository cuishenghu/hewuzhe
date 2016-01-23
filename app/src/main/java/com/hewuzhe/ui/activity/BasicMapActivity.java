package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.hewuzhe.R;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.base.ToolBarActivity;
import com.hewuzhe.utils.StringUtil;

import butterknife.Bind;


public class BasicMapActivity extends ToolBarActivity {


    @Bind(R.id.bmapView)
    MapView mMapView;

    private String lat = "";
    private String lng = "";
    private String address = "";
    private BaiduMap mBaiduMap;
    private TextView tvDesc;
    private TextView title;
    private String name = "";

    /**
     * @return 提供标题
     */
    @Override
    protected String provideTitle() {
        return getIntentData().getString("title");
    }

    @Override
    public void beforeSetCView(Bundle savedInstanceState) {
        super.beforeSetCView(savedInstanceState);

        SDKInitializer.initialize(getApplicationContext());

    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);


        try {
            lat = getIntentData().getString("lat");
            lng = getIntentData().getString("lng");
            address = getIntentData().getString("address");
            name = getIntentData().getString("name");

            mBaiduMap = mMapView.getMap();


            LatLng ll = new LatLng(Double.parseDouble(lat),
                    Double.parseDouble(lng));


            MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(ll, 15);
            mBaiduMap.animateMapStatus(u);


            //构建Marker图标
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.mipmap.icon_loc);
            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(ll)
                    .icon(bitmap);
            //在地图上添加Marker，并显示
            mBaiduMap.addOverlay(option);

            View infoAddress = getLayoutInflater().inflate(R.layout.info_address, null);
            title = (TextView) infoAddress.findViewById(R.id.tv_title);
            tvDesc = (TextView) infoAddress.findViewById(R.id.tv_desc);

            if (StringUtil.isEmpty(name)) {
                title.setVisibility(View.GONE);
            } else {
                title.setText(name);
            }

            tvDesc.setText(address);
            //创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
            InfoWindow mInfoWindow = new InfoWindow(infoAddress, ll, -47);
            //显示InfoWindow
            mBaiduMap.showInfoWindow(mInfoWindow);
        } catch (Exception e) {

        }
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
        mMapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
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
        mBaiduMap.setMyLocationEnabled(false);
        if (mMapView != null) {
            mMapView.onDestroy();
            mMapView = null;
        }
        super.onDestroy();
    }


}
