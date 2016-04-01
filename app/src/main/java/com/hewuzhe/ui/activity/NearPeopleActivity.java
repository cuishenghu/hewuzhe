package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.hewuzhe.R;
import com.hewuzhe.model.NearPeople;
import com.hewuzhe.presenter.NearPeoplePresenter;
import com.hewuzhe.ui.adapter.NearPeopleAdapter;
import com.hewuzhe.ui.base.RecycleViewForListActivity;
import com.hewuzhe.view.NearPeopleView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import materialdialogs.MaterialDialog;

/**
 * Created by zycom on 2016/3/31.
 */
public class NearPeopleActivity extends RecycleViewForListActivity<NearPeoplePresenter, NearPeopleAdapter, NearPeople> implements NearPeopleView,LocationSource,AMapLocationListener {
    public double lat;
    public double lng;
    public int age=0;
    public int sex=0;

    @Bind(R.id.np_sex) TextView np_sex;
    @Bind(R.id.np_age) TextView np_age;

    private String _address = "";

    private AMap aMap;
    private MapView mapView;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    @OnClick(R.id.np_sex)
    public void sexClick(){
        new MaterialDialog.Builder(this)
                .title("请选择性别")
                .items(R.array.gender)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        np_sex.setText(text);
                        sex = which;
                        mlocationClient.startLocation();
                    }
                })
                .show();
    }

    @OnClick(R.id.np_age)
    public void ageClick(){
        new MaterialDialog.Builder(this)
                .title("请选择年龄")
                .items(R.array.age)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        np_age.setText(text);
                        age = which;
                        mlocationClient.startLocation();
                    }
                })
                .show();
    }
    @Override
    protected NearPeopleAdapter provideAdapter() {
        return new NearPeopleAdapter(getContext(),presenter);
    }

    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    protected CharSequence provideTitle() {
        return "附近人";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_near_people;
    }

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
    protected void onResume() {
        super.onResume();
        presenter.getData(page,count,1,lat,lng,2000,age,sex);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public NearPeoplePresenter createPresenter() {
        return new NearPeoplePresenter();
    }

    @Override
    public void onItemClick(View view, int pos, NearPeople item) {

    }

    @Override
    public void bindNearPeople(ArrayList<NearPeople> nearPeoples) {
        bd(nearPeoples);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        lng = aMapLocation.getLongitude();
        lat = aMapLocation.getLatitude();
        _address = aMapLocation.getAddress();

        presenter.getData(page,count,1,lat,lng,2000,age,sex);
        mlocationClient.stopLocation();
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
}
