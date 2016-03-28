package com.hewuzhe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.hewuzhe.R;
import com.hewuzhe.model.ScreenList;
import com.hewuzhe.presenter.ScreenListPresenter;
import com.hewuzhe.ui.adapter.ScreenListAdapter;
import com.hewuzhe.ui.base.RecycleViewActivity;
import com.hewuzhe.ui.base.RecycleViewNoMoreActivity;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.view.ScreenListView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by zycom on 2016/3/23.
 */
public class ScreenListActivity extends RecycleViewNoMoreActivity<ScreenListPresenter, ScreenListAdapter, ScreenList> implements ScreenListView,LocationSource,AMapLocationListener {
    @Bind(R.id.s_location)
    RelativeLayout s_location;
    @Bind(R.id.user_address)
    TextView user_address;

    private String _cityName = "";
    private String _address = "";
    private double _Lat = 0;
    private double _Lng = 0;


    private AMap aMap;
    private MapView mapView;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        if (aMap == null) {
            aMap = mapView.getMap();
            aMap.setLocationSource(this);// 设置定位监听
            aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
            aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        }
    }

    @Override
    protected ScreenListAdapter provideAdapter() {
        return new ScreenListAdapter(this,presenter);
    }

    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    protected CharSequence provideTitle() {
        return "私教筛选";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_screen_list;
    }

    @Override
    public void initListeners() {

    }

    @Override
    public ScreenListPresenter createPresenter() {
        return new ScreenListPresenter();
    }

    @Override
    public void onItemClick(View view, int pos, ScreenList item) {
        startActivity(PrivateTrainerListActivity.class, new Bun().putInt("proid", item.Id).ok());

    }
    @OnClick(R.id.s_location)
    public void locationOnClick(){
        startActivityForResult(new Intent(this,
                GDMapLocationActivity.class).putExtra("lat", _Lat).putExtra("lng", _Lng), 1025);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1025&&resultCode==2048) {
            user_address.setText(data.getBundleExtra("data").getString("address"));
            _address = data.getBundleExtra("data").getString("address");
            _Lat = Double.parseDouble(data.getBundleExtra("data").getString("lat"));
            _Lng = Double.parseDouble(data.getBundleExtra("data").getString("lng"));
            presenter.SelectTeacherCateList(_Lat,_Lng,2000);
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        _Lng = aMapLocation.getLongitude();
        _Lat = aMapLocation.getLatitude();
        _address = aMapLocation.getAddress();
        user_address.setText(_address);
        presenter.SelectTeacherCateList(_Lat,_Lng,2000);
        mlocationClient.stopLocation();
    }

    @Override
    public void bindData(ArrayList<ScreenList> data) {
        bd(data);
    }
}
