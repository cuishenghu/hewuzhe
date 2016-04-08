package com.hewuzhe.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hewuzhe.R;
import com.hewuzhe.model.Address;
import com.hewuzhe.model.Site;
import com.hewuzhe.model.TrainerLessonInfo;
import com.hewuzhe.model.TrainerLessonTwo;
import com.hewuzhe.presenter.TrainerLessonPresenter;
import com.hewuzhe.ui.base.ToolBarActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widget.GlideCircleTransform;
import com.hewuzhe.ui.widgeter.OnWheelChangedListener;
import com.hewuzhe.ui.widgeter.WheelView;
import com.hewuzhe.ui.widgeter.adapters.ArrayWheelAdapter;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.utils.TimeUtil;
import com.hewuzhe.view.CircleImageView;
import com.hewuzhe.view.TrainerLessonView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import framework.picker.DatePicker;

/**
 * Created by zycom on 2016/3/18.
 */
public class TrainerLessonTwoActivity extends ToolBarActivity<TrainerLessonPresenter> implements TrainerLessonView, OnWheelChangedListener {
    @Bind(R.id.product_title)
    TextView product_title;
    @Bind(R.id.user_name)
    TextView user_name;
    @Bind(R.id.user_content)
    TextView user_content;
    @Bind(R.id.head_portrait)
    CircleImageView head_portrait;
    @Bind(R.id.usr_name)
    TextView usr_name;
    @Bind(R.id.usr_phone)
    TextView usr_phone;
    @Bind(R.id.usr_true_name_et)
    EditText usr_true_name_et;
    @Bind(R.id.usr_true_phone_et)
    EditText usr_true_phone_et;
    @Bind(R.id.tv_province)
    TextView tv_province;
    @Bind(R.id.usr_adds)
    TextView usr_adds;
    @Bind(R.id.submit_usrinfo)
    LinearLayout submit_usrinfo;
    @Bind(R.id.baoming_back)
    LinearLayout baoming_back;
    private int gender;
    @Bind(R.id.cb_male)
    CheckBox cb_male;
    @Bind(R.id.cb_female)
    CheckBox cb_female;
    @Bind(R.id.sub_info)
    TextView sub_info;
    //城市选择========================================
    private Site site;
    private ArrayList<Address> provinces, citys;
    private Dialog dialog;
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private TrainerLessonInfo trainerLessonInfo;

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
    private String mCurrentDistrictName = "";
    /**
     * 当前区----区域ID
     */
    protected String mCurrentAreaId = "";

    private ArrayList<String> arrayList;
    //end城市选择========================================
    @Override
    protected CharSequence provideTitle() {
        return "报名详情";
    }

    @Override
    public boolean canAction() {
        return true;
    }

    @Override
    protected void action() {
        startActivity(MySignLessonListActivity.class);
        finish();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_trainer_lesson_t;
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        arrayList = getIntent().getStringArrayListExtra("data");
        presenter.SelectLessonById(Integer.parseInt(arrayList.get(0)));
        presenter.GetJoinLessonRecordByUserIdAndLessonId(Integer.parseInt(arrayList.get(0)));
        usr_name.setText(new SessionUtil(this).getUser().NicName);
        usr_phone.setText(new SessionUtil(this).getUser().Phone);
        //查省
        presenter.getProvince();
    }

    private String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto;} body{ color:#ffffff; background-color: #3e3e40;font-size:large;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
//
    }

    @Override
    public void initListeners() {

    }

    @Override
    public TrainerLessonPresenter createPresenter() {
        return new TrainerLessonPresenter();
    }

    @Override
    public void bindData(TrainerLessonTwo trainerLessonTwo) {
        product_title.setText(trainerLessonTwo.Title);
        user_name.setText(arrayList.get(1));
        user_content.setText(arrayList.get(2));
        Picasso.with(this)
                .load(C.BASE_URL + arrayList.get(3))
                .placeholder(R.mipmap.img_avatar)
                .resize(50, 50)
                .error(R.mipmap.img_avatar)
                .centerCrop()
                .into(head_portrait); //imagview 布局

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

    @OnCheckedChanged(R.id.cb_female)
    public void femaleClick(CompoundButton compoundButton, boolean b) {
        if (b) {
            if (cb_male.isChecked()) {
                cb_male.setChecked(false);
            }
        } else {
            if (!cb_male.isChecked()) {
                cb_male.setChecked(true);
            }
        }
    }

    @OnCheckedChanged(R.id.cb_male)
    public void maleClick(CompoundButton compoundButton, boolean b) {
        if (b) {
            if (cb_female.isChecked()) {
                cb_female.setChecked(false);
            }
        } else {
            if (!cb_female.isChecked()) {
                cb_female.setChecked(true);
            }
        }
    }


    @OnClick(R.id.baoming_back)
    public void backClick() {
        startActivity(new Intent(TrainerLessonTwoActivity.this, TrainerLessonActivity.class).
                putStringArrayListExtra("data", arrayList));
    }

    @OnClick(R.id.tv_province)
    public void province() {
        showDatePicker();
    }

    private void showDatePicker() {
        DatePicker picker = new DatePicker(this);
        picker.setRange(1900, 2016);//年份范围

        int year = Integer.parseInt("1990");
        int month = Integer.parseInt("01");
        int day = Integer.parseInt("01");

        picker.setSelectedItem(year, month, day);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                String birthDay = year + "-" + month + "-" + day;
                tv_province.setText(TimeUtil.timeHaved(birthDay) + "");
            }
        });
        picker.show();
    }

    @OnClick(R.id.usr_adds)
    public void addsClick() {
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
                    int end = countyCodes.length() == 0 ? 0 : (countyCodes.length() - 1);
                    mCurrentAreaId = countyCodes.substring(0, end).split(",")[0];
                }
            }
        }
        initPopWindowForCitys();
        dialog.show();
    }

    @Override
    public void setDistricts(ArrayList<Address> countys) {
        for (Address city : citys) {
            StringBuffer countyNames = new StringBuffer();
            StringBuffer countyIds = new StringBuffer();
            for (Address county : countys) {
                if (county.Code.startsWith(city.Code)) {
                    // 遍历市下面所有区/县的数据
                    countyNames.append(county.Name + ",");
                    countyIds.append(county.Id + ",");
                }
            }
            // 市-区/县的数据，保存到mDistrictDatasMap
//            Log.e(city.Name+","+countyNames.length(), city.Name);
            int end = countyNames.length() == 0 ? 0 : (countyNames.length() - 1);
            mDistrictDatasMap.put(city.Name, countyNames.substring(0, end).split(","));
            mDistrictAreaIdsMap.put(city.Name, countyIds.toString());
        }
        StringBuffer provinceNames = new StringBuffer();
        mProvinceDatas = new String[provinces.size()];
        for (int i = 0; i < provinces.size(); i++) {
            // 遍历所有省的数据
            mProvinceDatas[i] = provinces.get(i).Name;
            StringBuffer cityNames = new StringBuffer();
            for (Address city : citys) {
                if (city.Code.startsWith(provinces.get(i).Code)) {
                    // 遍历省下面的所有市的数据
                    cityNames.append(city.Name + ",");
                }
            }
            // 省-市的数据，保存到mCitisDatasMap
//            Log.e(provinces.get(i).Name+","+cityNames.length(), provinces.get(i).Name);
            int end = cityNames.length() == 0 ? 0 : (cityNames.length() - 1);
            mCitisDatasMap.put(provinces.get(i).Name, cityNames.substring(0, end).split(","));
        }
    }

    @Override
    public void bindInfo(TrainerLessonInfo trainerLessonInfo) {
        this.trainerLessonInfo = trainerLessonInfo;
        usr_true_name_et.setText(trainerLessonInfo.Name);
        usr_true_phone_et.setText(trainerLessonInfo.Phone);
        tv_province.setText(trainerLessonInfo.Age+"");
        if(trainerLessonInfo.Sex==0){
            cb_male.setChecked(true);
            cb_female.setChecked(false);
        }else{
            cb_male.setChecked(false);
            cb_female.setChecked(true);
        }
        usr_adds.setText(trainerLessonInfo.AreaName);
        sub_info.setText("取消报名");
        submit_usrinfo.setBackgroundResource(R.color.grey_text);

    }

    @Override
    public void finishing() {
        finish();
    }

    private void initPopWindowForCitys() {
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

    private void showSelectedResult() {
        StringBuffer str = new StringBuffer();
        str.append(StringUtil.isEmpty(mCurrentProviceName) ? "" : (mCurrentProviceName + "/"));
        str.append(StringUtil.isEmpty(mCurrentCityName) ? "" : (mCurrentCityName + "/"));
        str.append(StringUtil.isEmpty(mCurrentDistrictName) ? "" : (mCurrentDistrictName));

        usr_adds.setText(str.toString());
//        site.AreaId = mCurrentAreaId;
//        toast("当前选中:" + mCurrentProviceName + "," + mCurrentCityName + "," + mCurrentDistrictName + "," + mCurrentAreaId);
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
        } else {
            mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this, new String[]{""}));
            mViewDistrict.setCurrentItem(0);
            mCurrentCityName = "";
            mCurrentDistrictName = "";
            for (Address province : provinces) {
                if (province.Name.equals(mCurrentProviceName)) {
                    mCurrentAreaId = province.Id + "";
                }
            }
        }
    }

    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);

        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
        mViewDistrict.setCurrentItem(0);
        if (!StringUtil.isEmpty(areas[0])) {
            updateCounty();
        } else {
            mCurrentDistrictName = "";
            for (Address city : citys) {
                if (city.Name.equals(mCurrentCityName)) {
                    mCurrentAreaId = city.Id + "";
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
        int end = countyCodes.length() == 0 ? 0 : (countyCodes.length() - 1);
        mCurrentAreaId = countyCodes.substring(0, end).split(",")[pCurrent];
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

    @OnClick(R.id.submit_usrinfo)
    public void submitClick() {
        if(sub_info.getText().equals("取消报名")){
            presenter.CancelJoinLessonRecordById(trainerLessonInfo.Id,this.getContext());
        }else {
            String trueName = usr_true_name_et.getText().toString();
            String truePhone = usr_true_phone_et.getText().toString();
            String age = tv_province.getText().toString();
            int f = cb_male.isChecked() ? 0 : 1;
            String adds = usr_adds.getText().toString();

            if (trueName.equals("") || truePhone.equals("") || age.equals("") || adds.equals("")) {
                Toast.makeText(this, "请确保所有信息填写正确！请重新填写。", Toast.LENGTH_SHORT).show();
                return;
            }
            presenter.JoinLessonByLessonId(submit_usrinfo, Integer.parseInt(arrayList.get(0)), trueName, truePhone, Integer.parseInt(age), f, mCurrentAreaId, this.getContext());
        }
    }

}
