package com.hewuzhe.ui.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.hewuzhe.R;
import com.hewuzhe.model.Address;
import com.hewuzhe.model.Dojo;
import com.hewuzhe.model.PrivateTrainerList;
import com.hewuzhe.presenter.DojoRecommendPresenter;
import com.hewuzhe.presenter.PrivateTrainerListPresenter;
import com.hewuzhe.ui.adapter.DojoRecommendAdapter;
import com.hewuzhe.ui.adapter.GridItemDecoration;
import com.hewuzhe.ui.adapter.PrivateTrainerListAdapter;
import com.hewuzhe.ui.adapter.PtlListAdapter;
import com.hewuzhe.ui.base.SwipeRecycleViewActivity;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.view.DojoRecommendView;
import com.hewuzhe.view.PrivateTrainerListView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by ninos on 2016/5/2.
 */
public class DojoRecommend2Activity extends SwipeRecycleViewActivity<DojoRecommendPresenter, DojoRecommendAdapter, Dojo> implements AdapterView.OnItemClickListener, DojoRecommendView, LocationSource, AMapLocationListener {
    @Bind(R.id.tv_chengshi)
    TextView tv_chengshi;   //城市
    @Bind(R.id.tv_juli)
    TextView tv_juli;       //距离
    @Bind(R.id.tv_tuijian)
    TextView tv_tuijian;    //推荐
    @Bind(R.id.pop_title)
    LinearLayout pop_title;    //推荐
    @Bind(R.id.user_address)
    TextView user_address;    //定位当前地址
    @Bind(R.id.img_address)
    ImageView img_address;    //定位当前地址
    @Bind(R.id.img_search)
    ImageView img_search;    //定位当前地址

    ListView classification_one;
    /**
     * 一级分类
     */ //@Bind(R.id.classification_one)
    ListView classification_two;
    /**
     * 二级分类
     */ //@Bind(R.id.classification_two)
    private PtlListAdapter<Address> OneAdapter;
    private PtlListAdapter<Address> TwoAdapter;

    private int m_screen_width;
    /**
     * 手机屏幕宽度
     */
    private int cityCode = 0;       //城市code
    private int cityId=0;           //城市id
    private int switchnumber = 0;
    private int districts = 0;        //县区code
    private int isrecommend = 0;      //是否推荐0：全部 1：推荐 2：不推荐

    View ptl_list;              //弹框属性
    LinearLayout click_hide;    //弹框属性
    PopupWindow ptl_pop;         //弹框属性

    private GridLayoutManager gridLayoutManager;
    private GridItemDecoration decoration;

    /**
     * 定位所需
     */
    private String _cityName = "";
    private String _address = "";
    private double _Lat = 0;
    private double _Lng = 0;
    private int length = 5000;
    private String searchString="";
    private int flg=2;


    private AMap aMap;
    private MapView mapView;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    @Override
    protected DojoRecommendAdapter provideAdapter() {
        decoration = new GridItemDecoration(10, 1);
        recyclerView.addItemDecoration(decoration);
        return new DojoRecommendAdapter(getContext(),this);
    }

    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        gridLayoutManager = new GridLayoutManager(getContext(), 2);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == adapter.getItemCount() - 1) {
                    return 2;
                }
                return 2;
            }
        });
        layoutManager = gridLayoutManager;
        return layoutManager;
    }

    @Override
    protected CharSequence provideTitle() {
        return "场馆";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_private_trainer_list2;
    }

    @Override
    public void initListeners() {

        initPopData();

        classification_one.setOnItemClickListener(this);
        classification_two.setOnItemClickListener(this);
    }

    @Override
    public DojoRecommendPresenter createPresenter() {
        return new DojoRecommendPresenter();
    }

    @Override
    public void onItemClick(View view, int pos, Dojo item) {
        startActivity(DojoDetail2Activity.class, new Bun().putInt("id", item.Id).ok());
    }

    @Override
    public void setNodata(int recordcount) {

    }

    @Override
    public String[] getStringData() {
        String []s = {_Lat+"",_Lng+"",_cityName,cityId+"",length+"",searchString,isrecommend+"",flg+""};
        return s;
    }

    @Override
    public void bindList(ArrayList<Address> classifications) {
        if (switchnumber == 0) {
            switch (presenter.getCurrentList()) {
                case 0:/**一级分类列表*/
//                    Address address = new Address();
//                    address.Name = "全部";
//                    address.Code = "0";
//                    classifications.set(0, address);
                    OneAdapter = new PtlListAdapter<Address>(getContext(),
                            classifications, R.layout.item_classification_one, 0, m_screen_width);
                    classification_one.setAdapter(OneAdapter);
                    TwoAdapter = new PtlListAdapter<Address>(getContext(),
                            new ArrayList<Address>(), R.layout.item_classification_one, 1, m_screen_width);
                    classification_two.setAdapter(TwoAdapter);
                    changeTelPopupWindowState();
                    break;
                case 1:/**二级分类列表*/
                    TwoAdapter = new PtlListAdapter<Address>(getContext(),
                            classifications, R.layout.item_classification_one, 1, m_screen_width);
                    classification_two.setAdapter(TwoAdapter);
                    presenter.currentList = 0;
                    break;
                default:
                    break;
            }
        } else if (switchnumber == 1) {
            switch (presenter.getCurrentList()) {
                case 0:/**一级分类列表*/
                    Address address = new Address();
                    address.Name = "附近";
                    address.Code = "0";
                    classifications.set(0, address);
                    OneAdapter = new PtlListAdapter<Address>(getContext(),
                            classifications, R.layout.item_classification_one, 0, m_screen_width);
                    classification_one.setAdapter(OneAdapter);
                    TwoAdapter = new PtlListAdapter<Address>(getContext(),
                            new ArrayList<Address>(), R.layout.item_classification_one, 1, m_screen_width);
                    classification_two.setAdapter(TwoAdapter);
                    changeTelPopupWindowState();
                    break;
                case 1:/**二级分类列表*/
                    TwoAdapter = new PtlListAdapter<Address>(getContext(),
                            classifications, R.layout.item_classification_one, 1, m_screen_width);
                    classification_two.setAdapter(TwoAdapter);
                    presenter.currentList = 0;
                    break;
                default:
                    break;
            }
        } else if (switchnumber == 2) {

        }
    }

    @Override
    public Integer getData() {
        return null;
    }

    @Override
    public void bindData(ArrayList<Dojo> data) {
        bd(data);
    }
    public void initPages(){
        page = 1;
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
        LayoutInflater ptl = LayoutInflater.from(this);
        // 引入窗口配置文件 - 即弹窗的界面
        ptl_list = ptl.inflate(R.layout.ptl_recycler_list, null);
        click_hide = (LinearLayout) ptl_list.findViewById(R.id.click_hide);
        classification_one = (ListView) ptl_list.findViewById(R.id.classification_one);
        classification_two = (ListView) ptl_list.findViewById(R.id.classification_two);


        /**获取手机屏幕宽度*/
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        m_screen_width = metrics.widthPixels;
    }

    private void initPopData() {
        ptl_list.setFocusableInTouchMode(true);
        // PopupWindow实例化
        ptl_pop = new PopupWindow(ptl_list, ViewGroup.LayoutParams.MATCH_PARENT,
                StringUtil.getScreenHeight(this) / 5 * 3, true);
        /**
         * PopupWindow 设置
         */
        // 设置PopupWindow显示和隐藏时的动画
//        ptl_pop.setAnimationStyle(R.style.MenuAnimationFade);
        /**
         * 改变背景可拉的弹出窗口。后台可以设置为null。 这句话必须有，否则按返回键popwindow不能消失 或者加入这句话
         * ColorDrawable dw = new
         * ColorDrawable(-00000);pop.setBackgroundDrawable(dw);
         */
        ptl_pop.setBackgroundDrawable(new BitmapDrawable());

    }


    /**
     * 改变 PopupWindow 的显示和隐藏
     */
    private void changeTelPopupWindowState() {
        if (ptl_pop.isShowing()) {
            // 隐藏窗口，如果设置了点击窗口外消失，则不需要此方式隐藏
            ptl_pop.dismiss();
        } else {
            int location[] = new int[2];         //获取布局的location
            pop_title.getLocationInWindow(location);
            // 弹出窗口显示内容视图,默认以锚定视图的左下角为起点，这里为点击按钮
            ptl_pop.showAtLocation(pop_title, Gravity.NO_GRAVITY, location[0], location[1] + pop_title.getHeight());

        }
    }

    /**
     * 定位
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1025 && resultCode == 2048) {
            _address = data.getBundleExtra("data").getString("address");
            _Lat = Double.parseDouble(data.getBundleExtra("data").getString("lat"));
            _Lng = Double.parseDouble(data.getBundleExtra("data").getString("lng"));
            length = data.getBundleExtra("data").getInt("length");
            user_address.setText(_address);
            tv_chengshi.setText("默认");
            tv_juli.setText("默认");
            flg = 2;
            initPages();
            presenter.getData(page,count);
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
        _cityName = aMapLocation.getCity();
//        sheng = aMapLocation.getProvince();
        user_address.setText(_address);
        tv_chengshi.setText(aMapLocation.getCity());
        presenter.getData(page,count);
        mlocationClient.stopLocation();
    }

    /**
     * 所有点击事件
     */
    @OnClick(R.id.tv_chengshi)
    public void chengshiClick() {
        switchnumber = 0;
        classification_two.setVisibility(View.VISIBLE);
        presenter.getProvinces();/**初始化*/
    }


    @OnClick(R.id.tv_tuijian)
    public void tuijianClick() {
        switchnumber = 2;
        classification_two.setVisibility(View.GONE);
        ArrayList<Address> list = new ArrayList<>();
        Address address = new Address();
        address.Name = "全部";
        address.Code = "0";
        list.add(address);
        Address address1 = new Address();
        address1.Name = "推荐";
        address1.Code = "1";
        list.add(address1);
        OneAdapter = new PtlListAdapter<Address>(getContext(),
                list, R.layout.item_classification_one, 0, m_screen_width);
        classification_one.setAdapter(OneAdapter);
        changeTelPopupWindowState();
        /**初始化*/
    }


    @OnClick(R.id.tv_juli)
    public void tv_juliClick() {
        switchnumber = 1;
        classification_two.setVisibility(View.VISIBLE);
        if (cityCode == 0) {
            ArrayList<Address> list = new ArrayList<>();
            Address address = new Address();
            address.Name = "附近";
            address.Code = "0";
            list.add(address);
            OneAdapter = new PtlListAdapter<Address>(getContext(),
                    list, R.layout.item_classification_one, 0, m_screen_width);
            classification_one.setAdapter(OneAdapter);
            changeTelPopupWindowState();
            return;
        }
        presenter.getDistricts(cityCode);/**初始化*/

    }

    @OnClick(R.id.img_search)
    public void searchClick(){
        String []n= getStringData();
        startActivity(new Intent(this, SearchDojoActivity.class).putExtra("data",getStringData()));
    }

    @OnClick(R.id.img_address)
    public void addressClick(){
        startActivityForResult(new Intent(this,
                GDMapLocationActivity.class).putExtra("lat", _Lat).putExtra("lng", _Lng).putExtra("length", length) ,1025);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (switchnumber == 0) {
            Address classification;
            switch (parent.getId()) {
                case R.id.classification_one:
                    /**点击一级分类*/
                    classification = OneAdapter.getItem(position);
                    if (classification.Name.trim().equals("台湾省") || classification.Name.trim().equals("香港特别行政区") || classification.Name.trim().equals("澳门特别行政区")) {
                        cityCode = Integer.parseInt(classification.Code);
                        cityId = classification.Id;
                        tv_chengshi.setText(classification.Name);
                        ptl_pop.dismiss();
                        flg = 0;
                        initPages();
                        presenter.getData(page,count);
                        break;
                    }
                    OneAdapter.setSelectedPosition(position);
                    presenter.getCitys(Integer.parseInt(classification.Code), 1);
                    break;
                case R.id.classification_two:
                    /**点击二级分类*/
                    classification = TwoAdapter.getItem(position);
                    TwoAdapter.setSelectedPosition(position);
                    cityCode = Integer.parseInt(classification.Code);
                    cityId = classification.Id;
                    tv_chengshi.setText(classification.Name);
                    tv_juli.setText("默认");
                    flg = 0;
                    initPages();
                    presenter.getData(page,count);
                    ptl_pop.dismiss();
//                presenter.getSmallCategory(classification.Id, 2);
                    break;
                default:
                    break;
            }
        } else if (switchnumber == 1) {
            Address classification;
            switch (parent.getId()) {
                case R.id.classification_one:
                    /**点击一级分类*/
                    classification = OneAdapter.getItem(position);
                    if (!classification.Name.trim().equals("附近")) {
                        districts = classification.Id;
                        tv_juli.setText(classification.Name);
                        ptl_pop.dismiss();
                        flg = 1;

                        cityId = districts;
                        initPages();
                        presenter.getData(page,count);



                        break;
                    }
                    OneAdapter.setSelectedPosition(position);
                    ArrayList<Address> list = new ArrayList<>();
                    for (int i = 0; i < 5; i++) {
                        Address classificationnew = new Address();
                        classificationnew.Name = (i + 1) * 10 + "千米";
                        classificationnew.topc = (i + 1) * 10000 + "";
                        list.add(classificationnew);
                    }
                    presenter.currentList = 1;
                    bindList(list);
//                    presenter.getCitys(Integer.parseInt(classification.Code), 1);
                    break;
                case R.id.classification_two:
                    /**点击二级分类*/
                    classification = TwoAdapter.getItem(position);
                    TwoAdapter.setSelectedPosition(position);
                    length = Integer.parseInt(classification.topc);
                    tv_juli.setText(classification.Name);
                    flg = 2;
                    tv_chengshi.setText("默认");
                    initPages();
                    presenter.getData(page,count);
                    ptl_pop.dismiss();
//                presenter.getSmallCategory(classification.Id, 2);
                    break;
                default:
                    break;
            }
        } else if (switchnumber == 2) {
            Address classification;
            switch (parent.getId()) {
                case R.id.classification_one:
                    /**点击一级分类*/
                    classification = OneAdapter.getItem(position);
                    isrecommend = Integer.parseInt(classification.Code);
                    tv_tuijian.setText(classification.Name);
                    initPages();
                    presenter.getData(page,count);
                    ptl_pop.dismiss();
                    break;
                default:
                    break;
            }
        }
    }
}
