package com.hewuzhe.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.hewuzhe.R;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.base.ToolBarActivity;
import com.hewuzhe.ui.fragment.WarriorFragment;

import butterknife.Bind;
import io.rong.message.LocationMessage;


public class BasicLocationMapActivity extends ToolBarActivity {


    @Bind(R.id.bmapView)
    MapView mMapView;

    private String lat = "";
    private String lng = "";
    private String address = "";
    private BaiduMap mBaiduMap;
    private TextView tvDesc;
    private TextView title;
    private String name = "";
    private LocationClient mLocClient;
    private boolean isFirstLoc = true;

    public MyLocationListenner myListener = new MyLocationListenner();
    private MyLocationConfiguration.LocationMode mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
    BitmapDescriptor mCurrentMarker;
    private double _lat;
    private double _lng;
    private String _address;


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
    public boolean canAction() {
        return true;
    }

    @Override
    protected void action() {
        super.action();
        Uri uri = Uri.parse("http://api.map.baidu.com/staticimage/v2?ak=rs8pD43GeAkZpFHii4Z3PLr3&mcode=666666&center=" + _lng + "," + _lat + "&width=300&height=200&zoom=15");
        Log.d("BasicLocationMapActivit", uri.toString());
        WarriorFragment._LocationCallback.onSuccess(LocationMessage.obtain(_lat, _lng, _address, uri));
        finishActivity();
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);

        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setIsNeedAddress(true);
        mLocClient.setLocOption(option);
        mLocClient.start();

        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {

                toast("start");
            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {
                toast("change");

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                toast("end");

            }
        });

    }


    /**
     * @return 提供LayoutId
     */
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_location_map;
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
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }

            _lat = location.getLatitude();
            _lng = location.getLongitude();
            _address = location.getAddrStr() + "";


            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(0).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();

            mBaiduMap.setMyLocationData(locData);

            mCurrentMarker = BitmapDescriptorFactory
                    .fromResource(R.mipmap.icon_loc);
            MyLocationConfiguration config = new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker);
            mBaiduMap.setMyLocationConfigeration(config);

            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(u);
            }
        }


        public void onReceivePoi(BDLocation poiLocation) {


        }
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
