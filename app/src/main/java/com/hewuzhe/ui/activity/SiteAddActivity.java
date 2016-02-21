package com.hewuzhe.ui.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.model.Address;
import com.hewuzhe.model.Site;
import com.hewuzhe.presenter.SitePresenter;
import com.hewuzhe.ui.base.ToolBarActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widgeter.OnWheelChangedListener;
import com.hewuzhe.ui.widgeter.WheelView;
import com.hewuzhe.ui.widgeter.adapters.ArrayWheelAdapter;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.view.SiteView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import materialdialogs.DialogAction;
import materialdialogs.MaterialDialog;

/**
 * Created by Administrator on 2016/2/2.
 */
public class SiteAddActivity extends ToolBarActivity<SitePresenter>
        implements SiteView,View.OnClickListener,OnWheelChangedListener {
    @Bind(R.id.tv_people)
    EditText tvPeople;/**收货人*/
    @Bind(R.id.tv_phone)
    EditText tvPhone;/**联系电话*/
    @Bind(R.id.tv_city)
    EditText tvCity;/**省市区*/
    @Bind(R.id.tv_site)
    EditText tvSite;/**详细地址*/
    @Bind(R.id.tv_youbian)
    EditText tvYoubian;/**邮编号码*/
    @Bind(R.id.tv_default)
    LinearLayout tvDefault;/**设置默认*/
    @Bind(R.id.tv_default_ico)
    ImageView tvDefaultIco;/**默认图标*/
    @Bind(R.id.submit_area)
    Button submitArea;/**提交*/
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private TextView mBtnConfirm;

    private Site site;
    private ArrayList<Address> provinces,citys;
    private Dialog dialog;
    /**
     * 所有省
     */
    private String[] mProvinceDatas;
    /**
     * key - 省 value - 市
     */
    private Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * key - 市 values - 区
     */
    private Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();
    /**
     * key - 区 values - AreaId(区域ID)
     */
    private Map<String, String> mDistrictAreaIdsMap = new HashMap<String, String>();
    /**
     * 当前省的名称
     */
    private String mCurrentProviceName;
    /**
     * 当前市的名称
     */
    private String mCurrentCityName;
    /**
     * 当前区的名称
     */
    private String mCurrentDistrictName ="";
    /**
     * 当前区----区域ID
     */
    protected String mCurrentAreaId ="";
    /**
     * @return 右边按钮是否显示
     */
    @Override
    public boolean canAction() {
        return true;
    }

    @Override
    protected String provideTitle() {
        return site != null?"修改地址":"新增地址";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_site_add;
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        site = (Site)getIntentData().getSerializable("site");
        if (tvAction != null){
            tvAction.setText("删除");
            tvAction.setVisibility(site != null ? View.VISIBLE : View.GONE);
        }
        super.initThings(savedInstanceState);
        presenter.setSiteData(site);
        presenter.getProvince();
    }

    @Override
    public void initListeners() {
        tvDefault.setOnClickListener(this);
        submitArea.setOnClickListener(this);
        tvCity.setOnClickListener(this);
    }

    @Override
    public SitePresenter createPresenter() {
        return new SitePresenter();
    }

    @Override
    public void setData(Site site) {
        if(site != null){
            tvPeople.setText(site.ReceiverName);
            tvPhone.setText(site.Phone);
            tvCity.setText(site.Area);
            tvSite.setText(site.Address);
            tvYoubian.setText(site.Code);
            tvDefaultIco.setImageResource("true".equals(site.IsDefault) ? R.drawable.adress_choose : R.drawable.adress_choose_normal);
        }
    }

    /**
     * UserId获取当前用户Id
     */
    @Override
    public Integer getData() {
        return new SessionUtil(getContext()).getUserId();
    }

    /**
     * Toolbar右边按钮的点击事件
     */
    @Override
    protected void action() {
        new MaterialDialog.Builder(getContext())
                .title("删除地址?")
                .titleColor(Color.WHITE)
                .contentColor(Color.WHITE)
                .positiveColor(C.COLOR_YELLOW)
                .negativeColor(C.COLOR_YELLOW)
                .content("确定删除该地址信息吗？")
                .positiveText("确定")
                .negativeText("取消")
                .backgroundColor(C.COLOR_BG)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        presenter.delete(site.Id, tvAction);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View view) {
        site = site == null?new Site():site;
        if(view.getId() == R.id.tv_city){
            /**初始化默认选中的省、市、区*/
            if (mProvinceDatas.length > 0) {
                mCurrentProviceName = mProvinceDatas[0];
                String[] cityNames = mCitisDatasMap.get(mProvinceDatas[0]);
                if (cityNames.length > 0) {
                    mCurrentCityName = cityNames[0];
                    String[] countyNames = mDistrictDatasMap.get(cityNames[0]);
                    if (countyNames.length > 0) {
                        mCurrentDistrictName = countyNames[0];
                        String countyCodes = mDistrictAreaIdsMap.get(mCurrentCityName);
                        int end = countyCodes.length()==0?0:(countyCodes.length()-1);
                        mCurrentAreaId = countyCodes.substring(0,end).split(",")[0];
                    }
                }
            }
            initPopWindowForCitys();
            dialog.show();
        }else if(view.getId() == R.id.tv_default){
            /**设置是否为默认收货地址*/
            site.IsDefault = "true".equals(site.IsDefault)?"false":"true";
            tvDefaultIco.setImageResource("true".equals(site.IsDefault) ? R.drawable.adress_choose : R.drawable.adress_choose_normal);
        }else {
            /**点击提交按钮*/
            site.ReceiverName = getEditText(tvPeople);
            site.Phone = getEditText(tvPhone);
            site.Area = getEditText(tvCity);
            site.Address = getEditText(tvSite);
            site.Code = getEditText(tvYoubian);
            presenter.saveSite(site, tvAction);
        }
    }

    @Override
    public void bindData(ArrayList<Site> data) {
    }

    @Override
    public void loadMore() {
    }

    @Override
    public void noMore() {
        /**编辑地址*/
        this.setResult(RESULT_OK);
        this.finish();
    }

    @Override
    public void hasMore() {
    }

    @Override
    public void loadMore(String tip) {

    }

    @Override
    public void hasMore(String tip) {

    }

    @Override
    public void noMore(String tip) {

    }

    /**
     * 提交编辑地址信息
     * @param editText 控件
     * @return 是否为空
     */
    private String getEditText(EditText editText){
        String text = editText.getText().toString().trim();
        return StringUtil.isEmpty(text)?"":text;
    }

    /**
     * 选择地址确认
     */
    private void showSelectedResult() {
        StringBuffer str = new StringBuffer();
        str.append(StringUtil.isEmpty(mCurrentProviceName)?"":(mCurrentProviceName+"/"));
        str.append(StringUtil.isEmpty(mCurrentCityName)?"":(mCurrentCityName+"/"));
        str.append(StringUtil.isEmpty(mCurrentDistrictName)?"":(mCurrentDistrictName));

        tvCity.setText(str.toString());
        site.AreaId = mCurrentAreaId;
//        toast("当前选中:" + mCurrentProviceName + "," + mCurrentCityName + "," + mCurrentDistrictName + "," + mCurrentAreaId);
    }

    @Override
    public void setProvinces(ArrayList<Address> provinces) {
        this.provinces = provinces;
        presenter.getCity();
    }

    @Override
    public void setCitys(ArrayList<Address> citys) {
        this.citys = citys;
        presenter.getCounty();
    }

    @Override
    public void setDistricts(ArrayList<Address> countys) {
        for (Address city:citys) {
            StringBuffer countyNames = new StringBuffer();
            StringBuffer countyIds = new StringBuffer();
            for (Address county:countys) {
                if(county.Code.startsWith(city.Code)){
                    // 遍历市下面所有区/县的数据
                    countyNames.append(county.Name + ",");
                    countyIds.append(county.Id + ",");
                }
            }
            // 市-区/县的数据，保存到mDistrictDatasMap
//            Log.e(city.Name+","+countyNames.length(), city.Name);
            int end = countyNames.length()==0?0:(countyNames.length()-1);
            mDistrictDatasMap.put(city.Name, countyNames.substring(0, end).split(","));
            mDistrictAreaIdsMap.put(city.Name, countyIds.toString());
        }
        StringBuffer provinceNames = new StringBuffer();
        mProvinceDatas = new String[provinces.size()];
        for (int i=0; i< provinces.size(); i++) {
            // 遍历所有省的数据
            mProvinceDatas[i] = provinces.get(i).Name;
            StringBuffer cityNames = new StringBuffer();
            for (Address city:citys) {
                if(city.Code.startsWith(provinces.get(i).Code)){
                    // 遍历省下面的所有市的数据
                    cityNames.append(city.Name + ",");
                }
            }
            // 省-市的数据，保存到mCitisDatasMap
//            Log.e(provinces.get(i).Name+","+cityNames.length(), provinces.get(i).Name);
            int end = cityNames.length()==0?0:(cityNames.length()-1);
            mCitisDatasMap.put(provinces.get(i).Name, cityNames.substring(0, end).split(","));
        }
    }
    private void initPopWindowForCitys(){
        dialog = new Dialog(this, R.style.dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_select_city);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);

        mViewProvince = (WheelView) window.findViewById(R.id.id_province);
        mViewCity = (WheelView) window.findViewById(R.id.id_city);
        mViewDistrict = (WheelView) window.findViewById(R.id.id_district);
        // 添加change事件
        mViewProvince.addChangingListener(this);
        mViewCity.addChangingListener(this);
        mViewDistrict.addChangingListener(this);

        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(getContext(), mProvinceDatas));
        // 设置可见条目数量
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);
        TextView btn_cancel = (TextView) window.findViewById(R.id.btn_cancel);
        btn_cancel.getPaint().setFakeBoldText(true);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView btn_confirm = (TextView) window.findViewById(R.id.btn_confirm);
        btn_confirm.getPaint().setFakeBoldText(true);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showSelectedResult();
                dialog.dismiss();
            }
        });
        updateCities();
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        // TODO Auto-generated method stub
        if (wheel == mViewProvince) {
            updateCities();
        } else if (wheel == mViewCity) {
            updateAreas();
        } else if (wheel == mViewDistrict) {
            updateCounty();
        }
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);

        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
        mViewCity.setCurrentItem(0);
        if (!StringUtil.isEmpty(cities[0])) {
            updateAreas();
        }else{
            mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this, new String[]{""}));
            mViewDistrict.setCurrentItem(0);
            mCurrentCityName = "";
            mCurrentDistrictName = "";
            for (Address province:provinces) {
                if(province.Name.equals(mCurrentProviceName)){
                    mCurrentAreaId = province.Id+"";
                }
            }
        }
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);

        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
        mViewDistrict.setCurrentItem(0);
        if (!StringUtil.isEmpty(areas[0])) {
            updateCounty();
        }else{
            mCurrentDistrictName = "";
            for (Address city:citys) {
                if(city.Name.equals(mCurrentCityName)){
                    mCurrentAreaId = city.Id+"";
                }
            }
        }
    }

    /**
     * 更新当前的区信息
     */
    private void updateCounty() {
        int pCurrent = mViewDistrict.getCurrentItem();
        mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[pCurrent];
        String countyCodes = mDistrictAreaIdsMap.get(mCurrentCityName);
        int end = countyCodes.length()==0?0:(countyCodes.length()-1);
        mCurrentAreaId = countyCodes.substring(0,end).split(",")[pCurrent];
    }
}