package com.hewuzhe.ui.activity;

import android.os.Bundle;

import com.hewuzhe.R;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.base.ToolBarActivity;

//import com.amap.api.maps2d.AMap;
//import com.amap.api.maps2d.CameraUpdate;
//import com.amap.api.maps2d.CameraUpdateFactory;
//import com.amap.api.maps2d.MapView;
//import com.amap.api.maps2d.model.LatLng;
//import com.amap.api.maps2d.model.Marker;
//import com.amap.api.maps2d.model.MarkerOptions;

public class BasicMapActivity extends ToolBarActivity {

//    @Bind(R.id.map)
//    MapView mapView;
//    private AMap aMap;
//    private LatLng position;
//    private String lat;
//    private String lng;
//    private String address;
//    private MarkerOptions markerOption;

    /**
     * @return 提供标题
     */
    @Override
    protected String provideTitle() {
        return "地图";
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
//        mapView.onCreate(savedInstanceState);// 必须要写
//        aMap = mapView.getMap();
//        lat = getIntentData().getString("lat");
//        lng = getIntentData().getString("lng");
//        address = getIntentData().getString("address");
//
//        position = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
//
//
//        MarkerOptions markerOptions = new MarkerOptions().anchor(0.5f, 0.5f)
//                .position(position).title(address)
//                .draggable(true);
//        Marker mark = aMap.addMarker(markerOptions);
//        mark.showInfoWindow();


//        markerOption = new MarkerOptions();
//        markerOption.position(C.XIAN);
//        markerOption.title("西安市").snippet("西安市：34.341568, 108.940174");
//        markerOption.draggable(true);
//        markerOption.icon(BitmapDescriptorFactory
//                .fromResource(R.drawable.arrow));
//        marker2 = aMap.addMarker(markerOption);
//        marker2.showInfoWindow();
//         marker旋转90度
//        marker2.setRotateAngle(90)
//

//
//        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(position, 15);
//        aMap.moveCamera(update);
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
//        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
//        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
//        mapView.onDestroy();
        super.onDestroy();
    }


}
