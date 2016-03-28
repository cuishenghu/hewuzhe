package com.hewuzhe.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMap.OnCameraChangeListener;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.hewuzhe.R;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.utils.Tools;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GDMapLocationActivity extends Activity implements OnGeocodeSearchListener,OnCameraChangeListener{

    private ProgressDialog progDialog = null;
    private AMap aMap;
    private MapView mapView;
    private GeocodeSearch geocoderSearch;
    private String addressName = "";
    private String Lat;
    private String Lng;
    private Marker regeoMarker;
    private LatLonPoint latLonPoint;
    private RegeocodeQuery query;
    private float zoom = 15f;


    @Bind(R.id.gdm_newaddr)
    TextView gdm_newaddr;
    @Bind(R.id.gdm_back)
    TextView gdm_back;

    @OnClick(R.id.gdm_newaddr)
    public void newClick(){
        setResult(2048, new Intent().putExtra("data", new Bun().putString("address",addressName ).putString("lng", Lng).putString("lat",Lat ).ok()));
        finish();

    }

    @OnClick(R.id.gdm_back)
    public void backClick(){
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gdmap_location);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        double latitude = getIntent().getDoubleExtra("lat",35.106334);
        double longitude = getIntent().getDoubleExtra("lng", 118.356544);
        latLonPoint = new LatLonPoint(latitude, longitude);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        if (aMap == null) {
            aMap = mapView.getMap();
            regeoMarker = aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                    .icon(BitmapDescriptorFactory
                            .fromResource(R.mipmap.location_marker)));
            regeoMarker.showInfoWindow();// 设置默认显示一个infowinfow
            regeoMarker.setPositionByPixels(StringUtil.getScreenWidth(this)/2,(StringUtil.getScreenHeight(this)-StringUtil.dip2px(this,45))/2);
            aMap.setOnCameraChangeListener(this);// 对amap添加移动地图事件监听器
        }
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        progDialog = new ProgressDialog(this);

        getAddress(latLonPoint);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    /**
     * 响应地理编码
     */
    public void getLatlon(final String name) {
        showDialog();
        GeocodeQuery query = new GeocodeQuery(name, "010");// 第一个参数表示地址，第二个参数表示查询城市，中文或者中文全拼，citycode、adcode，
        geocoderSearch.getFromLocationNameAsyn(query);// 设置同步地理编码请求
    }

    /**
     * 响应逆地理编码
     */
    public void getAddress(final LatLonPoint latLonPoint) {
        showDialog();
        query = new RegeocodeQuery(latLonPoint, 200,
                GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
    }

    /**
     * 地理编码查询回调
     */
    @Override
    public void onGeocodeSearched(GeocodeResult result, int rCode) {
        dismissDialog();
        if (rCode == 1000) {
            if (result != null && result.getGeocodeAddressList() != null
                    && result.getGeocodeAddressList().size() > 0) {
            } else {
                Tools.toast(this, "对不起，没有搜索到相关数据！");
            }
        } else if (rCode == 27) {
            Tools.toast(this, "搜索失败,请检查网络连接！");
        } else if (rCode == 32) {
            Tools.toast(this, "key验证无效！");
        } else {
            Tools.toast(this, "未知错误，请稍后重试!错误码为" + rCode);
        }
    }

    /**
     * 逆地理编码回调
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        dismissDialog();
        if (rCode == 1000) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                Lng = result.getRegeocodeQuery().getPoint().getLongitude()+"";
                Lat = result.getRegeocodeQuery().getPoint().getLatitude()+"";

                addressName = result.getRegeocodeAddress().getFormatAddress().toString().replace(result.getRegeocodeAddress().getTownship(),"")
                        + "附近";
                aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude()), zoom));
                Tools.toast(this, addressName);
            } else {
                Tools.toast(this, "对不起，没有搜索到相关数据！");
            }
        } else if (rCode == 27) {
            Tools.toast(this, "搜索失败,请检查网络连接！");
        } else if (rCode == 32) {
            Tools.toast(this, "key验证无效！");
        } else {
            Tools.toast(this, "未知错误，请稍后重试!错误码为" + rCode);
        }
    }

    /**
     * 显示进度条对话框
     */
    public void showDialog() {
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(true);
        progDialog.setMessage("正在获取地址");
        progDialog.show();
    }

    /**
     * 隐藏进度条对话框
     */
    public void dismissDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        if(Math.abs(cameraPosition.target.latitude-latLonPoint.getLatitude())>0.0001
                || Math.abs(cameraPosition.target.longitude-latLonPoint.getLongitude())>0.0001){
            latLonPoint.setLatitude(cameraPosition.target.latitude);
            latLonPoint.setLongitude(cameraPosition.target.longitude);
            zoom = aMap.getCameraPosition().zoom;
            geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
        }
    }


}
