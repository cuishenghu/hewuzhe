package com.hewuzhe.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hewuzhe.R;
import com.hewuzhe.model.Address;
import com.hewuzhe.model.User;
import com.hewuzhe.presenter.ProfilePresenter;
import com.hewuzhe.ui.adapter.AddressAdapter;
import com.hewuzhe.ui.base.ToolBarActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widget.GlideCircleTransform;
import com.hewuzhe.ui.widget.YsnowDialog;
import com.hewuzhe.utils.FileUtil;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.TimeUtil;
import com.hewuzhe.view.ProfileView;

import java.util.ArrayList;

import butterknife.Bind;
import framework.picker.DatePicker;
import materialdialogs.MaterialDialog;

public class SignupProfileActivity extends ToolBarActivity<ProfilePresenter> implements ProfileView {

    @Bind(R.id.img_avatar)
    ImageView imgAvatar;
    @Bind(R.id.edt_username)
    EditText edtUsername;
    @Bind(R.id.tv_id)
    TextView tvId;
    @Bind(R.id.cb_male)
    CheckBox cbMale;
    @Bind(R.id.lay_male)
    LinearLayout layMale;
    @Bind(R.id.cb_female)
    CheckBox cbFemale;
    @Bind(R.id.lay_female)
    LinearLayout layFemale;
    @Bind(R.id.edt_height)
    EditText edtHeight;
    @Bind(R.id.lay_height)
    LinearLayout layHeight;
    @Bind(R.id.edt_weight)
    EditText edtWeight;
    @Bind(R.id.lay_weight)
    LinearLayout layWeight;
    @Bind(R.id.edt_age)
    EditText edtAge;
    @Bind(R.id.lay_age)
    LinearLayout layAge;
    @Bind(R.id.edt_long)
    EditText edtLong;
    @Bind(R.id.lay_long)
    LinearLayout layLong;
    @Bind(R.id.tv_province)
    TextView tvProvince;
    @Bind(R.id.tv_city)
    TextView tvCity;
    @Bind(R.id.tv_district)
    TextView tvDistrict;
    @Bind(R.id.edt_desc)
    EditText edtDesc;
    @Bind(R.id.tv_save)
    TextView tvSave;
    private User user;
    private YsnowDialog ysnowDialog;


    private AddressAdapter cityDialogAdapter;
    private AddressAdapter disctrictAdapter;
    private int mAreaId = -1;
    private int cityId;
    private int countyId;
    private int provinceId;
    private String birthDay;


    @Override
    protected int provideContentViewId() {
        return R.layout.activity_signup_profile;
    }

    @Override
    protected String provideTitle() {
        return "个人资料";
    }

    @Override
    public void initListeners() {
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAvatarUpdateDialog();
            }
        });
        cbFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (cbMale.isChecked()) {
                        cbMale.setChecked(false);
                    }
                } else {
                    if (!cbMale.isChecked()) {
                        cbMale.setChecked(true);
                    }
                }
            }
        });

        cbMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (cbFemale.isChecked()) {
                        cbFemale.setChecked(false);
                    }
                } else {
                    if (!cbFemale.isChecked()) {
                        cbFemale.setChecked(true);
                    }
                }
            }
        });

        layMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cbMale.setChecked(true);
            }
        });
        layFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cbFemale.setChecked(true);
            }
        });

        layHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtHeight.setSelection(edtHeight.getText().length());
                edtHeight.requestFocus();
                showInputMethod(edtHeight);

            }
        });
        layWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtWeight.setSelection(edtWeight.getText().length());
                edtWeight.requestFocus();
                showInputMethod(edtWeight);

            }
        });
        layAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });
        layLong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtLong.setSelection(edtLong.getText().length());
                edtLong.requestFocus();
                showInputMethod(edtLong);
            }
        });

        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.saveDataSignup(view);
            }
        });

        tvProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.getProvinces();
            }
        });

        tvCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showListDialog(cityDialogAdapter, new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        Address ad = cityDialogAdapter.dataList.get(which);
                        tvCity.setText(ad.Name);
                        cityId = Integer.parseInt(ad.Code);
                        presenter.getDistricts(cityId);
                        dialog.dismiss();
                    }
                });

            }
        });
        tvDistrict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showListDialog(disctrictAdapter, new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        Address ad = disctrictAdapter.dataList.get(which);
                        tvDistrict.setText(ad.Name);
                        countyId = Integer.parseInt(ad.Code);
                        mAreaId = ad.Id;
                        dialog.dismiss();
                    }
                });
            }
        });
    }


    private void showDatePicker() {
        DatePicker picker = new DatePicker(this);
        picker.setRange(1980, 2015);//年份范围
        String yearStr = user.Birthday.substring(0, 4);
        String monthStr = user.Birthday.substring(5, 7);
        String dayStr = user.Birthday.substring(8, 10);

        int year = Integer.parseInt(yearStr);
        int month = Integer.parseInt(monthStr);
        int day = Integer.parseInt(dayStr);

        picker.setSelectedItem(year, month, day);

        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                birthDay = year + "-" + month + "-" + day;
                user.Birthday = birthDay;
                edtAge.setText(TimeUtil.timeHaved(birthDay) + "");
            }
        });
        picker.show();
    }


    /**
     * @param savedInstanceState 缓存数据
     *                           <p/>
     *                           初始化一些事情
     */
    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);

        ViewCompat.setTransitionName(imgAvatar, "imgAvatar");
        tvAction.setText("保存");
        presenter.getUserData();

    }

    /**
     * 绑定Presenter
     */
    @Override
    public ProfilePresenter createPresenter() {
        return new ProfilePresenter();
    }


    @Override
    public void setData() {
        user = new SessionUtil(getContext()).getUser();
        tvId.setText(user.Phone);

        Glide.with(getContext())
                .load(C.BASE_URL + user.PhotoPath)
                .placeholder(R.mipmap.img_avatar)
                .crossFade()
                .centerCrop()
                .transform(new GlideCircleTransform(getContext()))
                .into(imgAvatar);

        edtUsername.setText(user.NicName);
        edtHeight.setText(user.Height + "");
        edtWeight.setText(user.Weight + "");
        edtAge.setText(user.Weight + "");
        edtLong.setText(user.Experience + "");
        edtDesc.setText(user.Description);

        checkGender(user.Sexuality);
    }

    @Override
    public User getData() {

        user.NicName = edtUsername.getText().toString().trim();
//        if (StringUtil.isEmpty(user.NicName)) {
//            edtUsername.setError("昵称不能为空");
//            return null;
//        }

        user.Sexuality = cbMale.isChecked() ? 1 : 2;

        String heightStr = edtHeight.getText().toString().trim();
        user.Height = Integer.parseInt(heightStr);

        String weightStr = edtWeight.getText().toString().trim();
        user.Weight = Integer.parseInt(weightStr);

        String ExperienceStr = edtLong.getText().toString().trim();
        user.Experience = Integer.parseInt(ExperienceStr);

//        String tStr = edtWeight.getText().toString().trim();
//        user. = Integer.parseInt(heightStr.substring(0, weightStr.length() - 3));
        user.Description = edtDesc.getText().toString().trim();

        user.HomeAreaId = mAreaId == -1 ? user.HomeAreaId : mAreaId;

        return user;

    }

    @Override
    public void checkGender(int gender) {

        if (gender == 1) {//男
            cbFemale.setChecked(false);
            cbMale.setChecked(true);
        } else if (gender == 2) {//女
            cbFemale.setChecked(true);
            cbMale.setChecked(false);
        }

    }

    @Override
    public void showAvatarUpdateDialog() {

        if (ysnowDialog == null) {
            ysnowDialog = new YsnowDialog(this);
        }
        ysnowDialog.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == C.TAKE_PIC) {
                ysnowDialog.goToCrop(C.CROP);
            } else if (requestCode == C.CROP) {
                Bundle bundle = data.getExtras();
                Bitmap bitmap = bundle.getParcelable("data");
                String fileName = FileUtil.getTempImage().getPath();
                presenter.saveImgAvatar(imgAvatar, bitmap, fileName);
            } else if (requestCode == C.CHOOSE_PIC) {
                Bundle extras2 = data.getExtras();
                if (extras2 != null) {
                    Bitmap yourSelectedImage = extras2.getParcelable("data");
                    presenter.saveImgAvatar(imgAvatar, yourSelectedImage, FileUtil.getTempImage().getPath());
                }
            }
        }
    }

    public void showInputMethod(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(v, InputMethodManager.SHOW_FORCED);
    }


    public void hideInputMethod(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }


    /**
     * 设置province
     *
     * @param address
     */
    @Override
    public void setProvinces(final ArrayList<Address> address) {
        showListDialog(new AddressAdapter(getContext(), address), new MaterialDialog.ListCallback() {
            @Override
            public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                Address ad = address.get(which);
                tvProvince.setText(ad.Name);
                provinceId = Integer.parseInt(ad.Code);
                presenter.getCitys(provinceId);
                dialog.dismiss();

            }
        });
    }

    /**
     * 设置城市
     *
     * @param address
     */
    @Override
    public void setCitys(ArrayList<Address> address) {
        if (address != null && address.size() > 0) {
            cityDialogAdapter = new AddressAdapter(getContext(), address);
            Address ad = address.get(0);
            tvCity.setText(ad.Name);
            cityId = Integer.parseInt(ad.Code);
            presenter.getDistricts(cityId);
        }

    }

    /**
     * 设置县区
     *
     * @param address
     */
    @Override
    public void setDistricts(ArrayList<Address> address) {
        if (address != null && address.size() > 0) {
            disctrictAdapter = new AddressAdapter(getContext(), address);
            tvDistrict.setText(address.get(0).Name);
        }
    }


    /**
     * 加载更多
     */
    @Override
    public void loadMore() {

    }

    @Override
    public void noMore() {

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
}
