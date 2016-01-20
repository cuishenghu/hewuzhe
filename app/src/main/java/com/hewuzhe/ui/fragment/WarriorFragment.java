package com.hewuzhe.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hewuzhe.R;
import com.hewuzhe.model.User;
import com.hewuzhe.model.Weather;
import com.hewuzhe.presenter.WarriorFragmentPresenter;
import com.hewuzhe.ui.App;
import com.hewuzhe.ui.activity.DoJoRecommendActivity;
import com.hewuzhe.ui.activity.FlyDreamActivity;
import com.hewuzhe.ui.activity.FriendProfileActivity;
import com.hewuzhe.ui.activity.IntegralActivity;
import com.hewuzhe.ui.activity.LiveVideoActivity;
import com.hewuzhe.ui.activity.MemberActivity;
import com.hewuzhe.ui.activity.MyCollectionsActivity;
import com.hewuzhe.ui.activity.ProfileActivity;
import com.hewuzhe.ui.activity.RecordActivity;
import com.hewuzhe.ui.activity.StrangerProfileSettingsActivity;
import com.hewuzhe.ui.activity.StudyOnlineActivity;
import com.hewuzhe.ui.activity.TrainActivity;
import com.hewuzhe.ui.base.ToolBarFragment;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widget.GlideCircleTransform;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.view.WarriorFragmentView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import okhttp3.Request;

//import com.amap.api.location.AMapLocation;
//import com.amap.api.location.AMapLocationClient;
//import com.amap.api.location.AMapLocationClientOption;
//import com.amap.api.location.AMapLocationListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class WarriorFragment extends ToolBarFragment<WarriorFragmentPresenter> implements WarriorFragmentView, RongIM.LocationProvider, RongIM.UserInfoProvider, RongIM.GroupInfoProvider, RongIM.ConversationBehaviorListener {

    @Bind(R.id.img_avatar)
    ImageView imgAvatar;
    @Bind(R.id.tv_fly_dream)
    TextView tvFlyDream;
    @Bind(R.id.tv_study_online)
    TextView tvStudyOnline;
    @Bind(R.id.tv_dojo_recommend)
    TextView tvDojoRecommend;
    @Bind(R.id.tv_my_collections)
    TextView tvMyCollections;
    @Bind(R.id.lay_live)
    LinearLayout layLive;
    @Bind(R.id.lay_train)
    LinearLayout layTrain;
    @Bind(R.id.lay_integral_one)
    LinearLayout getLayIntegralOne;
    @Bind(R.id.lay_level)
    LinearLayout layLevel;
    @Bind(R.id.lay_record)
    LinearLayout layRecord;
    @Bind(R.id.lay_integral)
    LinearLayout layIntegral;
    @Bind(R.id.tv_air_quality)
    TextView tvAirQuality;
    @Bind(R.id.tv_pm)
    TextView tvPm;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_temperature)
    TextView tvTemperature;
    @Bind(R.id.tv_username)
    TextView tvUsername;
    @Bind(R.id.tv_id)
    TextView tvId;
    @Bind(R.id.tv_level)
    TextView tvLevel;
    @Bind(R.id.tv_integral)
    TextView tvIntegral;
    @Bind(R.id.tv_level_name)
    TextView tvLevelName;
    private View rootView;
    private User user;
    private boolean isHasSetRongIM = false;
//    private AMapLocationClient locationClient;

    private String _cityName = "临沂市";
    private double _Lat;
    private double _Lng;
    private String cityId = "CN101120901";
    private String userId;


    public WarriorFragment() {
        // Required empty public constructor
    }


    @Override
    protected String provideTitle() {
        return "功夫派";
    }

    /**
     * 初始化一些事情
     *
     * @param v
     */
    @Override
    protected void initThings(View v) {
        super.initThings(v);

        presenter.getUserData();

//        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/" + DBManager.DB_NAME, null);
//
//        Cursor cursor = db.rawQuery("SELECT city_id FROM city_id WHERE city_area=?", new String[]{_cityName});
//
//        if (cursor.moveToFirst()) {
//            String _CityId = cursor.getString(cursor.getColumnIndex("city_id"));
//            KLog.d(_CityId);
//
//        } else {
//
//        }
//
//        cursor.close();


//        locationClient = new AMapLocationClient(getActivity().getApplicationContext());
//        locationClient.setLocationListener(this);

//        initLoc();


        getWeather();

    }

    private void getWeather() {
        String url = "https://api.heweather.com/x3/weather?cityid=" + cityId + "&key=df75b4ca1fae4a70b131669142d4cbee";
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray jsonArray = jsonObject.optJSONArray("HeWeather data service 3.0");

                            JSONObject jsonObject1 = jsonArray.optJSONObject(0);

                            Weather weather = new Gson().fromJson(jsonObject1.toString(), Weather.class);

                            tvAirQuality.setText("空气质量：无数据");
                            tvTemperature.setText(weather.now.tmp + "C");
                            tvPm.setText("PM：无数据");
                            tvAddress.setText(_cityName);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
    }

    @Override
    public void initListeners() {
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(getActivity(), ProfileActivity.class);
                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), imgAvatar, "imgAvatar");
                    ActivityCompat.startActivity(getActivity(), intent, optionsCompat.toBundle());
                } catch (IllegalArgumentException e) {
                    startActivity(new Intent(getActivity(), ProfileActivity.class));
                }
            }
        });

        tvFlyDream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), FlyDreamActivity.class));

//                startActivity(SignupProfileActivity.class);


            }
        });
        tvStudyOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), StudyOnlineActivity.class));
            }
        });
        tvMyCollections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MyCollectionsActivity.class));
            }
        });
        layTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), TrainActivity.class));
            }
        });
        layRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), RecordActivity.class));
            }
        });
        layIntegral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), IntegralActivity.class));
            }
        });
        layLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LiveVideoActivity.class));
            }
        });
        tvDojoRecommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), DoJoRecommendActivity.class));
            }
        });
        getLayIntegralOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(IntegralActivity.class);
            }
        });

    }

    /**
     * @return 提供LayoutId
     */
    @Override
    public int provideLayoutId() {
        return R.layout.fragment_warrior;
    }

    /**
     * 绑定Presenter
     */
    @Override
    public WarriorFragmentPresenter createPresenter() {
        return new WarriorFragmentPresenter();
    }


    @Override
    public void setUserData() {
        user = new SessionUtil(getContext()).getUser();

        tvUsername.setText(user.NicName);
        tvId.setText("ID:" + getUserId());
        tvLevel.setText("lv" + user.Rank);
        tvIntegral.setText(user.Credit + "");
        tvLevelName.setText(user.isVip() ? "会员" : "成为会员");

        layLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MemberActivity.class);
            }
        });


        Glide.with(getActivity())
                .load(C.BASE_URL + user.PhotoPath)
                .centerCrop()
                .crossFade()
                .placeholder(R.mipmap.img_avatar)
                .transform(new GlideCircleTransform(getContext()))
                .into(imgAvatar);

        connect(user.Token);


    }

    private String getUserId() {
        return StringUtil.isEmpty(user.Phone) ? "000000" + user.Id : user.Phone;
    }


    @Override
    public void isWuYou(Boolean data, int userid) {
        if (data) {
            startActivity(FriendProfileActivity.class, new Bun().putInt("id", userid).ok());
        } else {
            startActivity(StrangerProfileSettingsActivity.class, new Bun().putInt("id", userid).ok());
        }
    }

    @Override
    public boolean canBack() {
        return false;
    }


    @Override
    public void onResume() {
        super.onResume();

        setUserData();
    }

    /**
     * 建立与融云服务器的连接
     *
     * @param token
     */
    private void connect(String token) {

        /**
         * IMKit SDK调用第二步,建立与服务器的连接
         */
        if (getActivity().getApplicationInfo().packageName.equals(App.getCurProcessName(getActivity().getApplicationContext()))) {
            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
                 */
                @Override
                public void onTokenIncorrect() {

                    Log.d("LoginActivity", "--onTokenIncorrect");
                }

                /**
                 * 连接融云成功
                 *
                 * @param userid 当前 token
                 */
                @Override
                public void onSuccess(String userid) {

                    Log.d("LoginActivity", "--onSuccess" + userid);
//                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                    finish();


                    RongIM.setUserInfoProvider(WarriorFragment.this, true);//设置用户信息提供者。
                    RongIM.setGroupInfoProvider(WarriorFragment.this, true);//设置群组信息提供者。
                    RongIM.setConversationBehaviorListener(WarriorFragment.this);//设置会话界面操作的监听器。
                    RongIM.setLocationProvider(WarriorFragment.this);//设置地理位置提供者,不用位置的同学可以注掉此行代码


                }

                /**
                 * 连接融云失败
                 *
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                    Log.d("LoginActivity", "--onError" + errorCode);
                }
            });
        }
    }

    @Override
    public void onStartLocation(Context context, LocationCallback locationCallback) {


    }

    @Override
    public UserInfo getUserInfo(String s) {
//        User u = new Select().from(User.class).where("Id=" + s).executeSingle();
        User u = null;

        if (u == null) {
            presenter.getUserInfo(Integer.parseInt(s));
            return null;
        } else {

            return new UserInfo(s, u.NicName, Uri.parse(C.BASE_URL + u.PhotoPath));
        }

    }


    @Override
    public Group getGroupInfo(String s) {
//        User u = new Select().from(com.hewuzhe.model.Group.class).where("Id=" + s).executeSingle();
        User u = null;

        if (u == null) {

            presenter.getGroup(Integer.parseInt(s));
            return null;
        } else {

            return new Group(s, u.NicName, Uri.parse(C.BASE_URL + u.PhotoPath));
        }
    }

    @Override
    public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {

        presenter.isWuyou(Integer.parseInt(userInfo.getUserId()));

        return false;
    }

    @Override
    public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
        return false;
    }

    @Override
    public boolean onMessageClick(Context context, View view, Message message) {

        toast(message.getExtra());

        return false;
    }

    @Override
    public boolean onMessageLinkClick(Context context, String s) {
        return false;
    }

    @Override
    public boolean onMessageLongClick(Context context, View view, Message message) {
        return false;
    }


///*
//    public void initLoc() {
//        //声明mLocationOption对象
////        AMapLocationClientOption mLocationOption = null;
////初始化定位参数
//        mLocationOption = new AMapLocationClientOption();
////设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
//        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
////设置是否返回地址信息（默认返回地址信息）
//        mLocationOption.setNeedAddress(true);
////设置是否只定位一次,默认为false
//        mLocationOption.setOnceLocation(true);
////设置是否强制刷新WIFI，默认为强制刷新
//        mLocationOption.setWifiActiveScan(true);
////设置是否允许模拟位置,默认为false，不允许模拟位置
//        mLocationOption.setMockEnable(false);
////设置定位间隔,单位毫秒,默认为2000ms
////        mLocationOption.setInterval(2000);
////给定位客户端对象设置定位参数
//        locationClient.setLocationOption(mLocationOption);
////启动定位
//
//        locationClient.startLocation();
//
//    }
//*/


//    @Override
//    public void onLocationChanged(AMapLocation aMapLocation) {
//        if (aMapLocation != null) {
//            if (aMapLocation.getErrorCode() == 0) {
//                //定位成功回调信息，设置相关消息
//                _Lat = aMapLocation.getLatitude();//获取纬度
//                _Lng = aMapLocation.getLongitude();//获取经度
//                _cityName = aMapLocation.getCity();
//                if (!StringUtil.isEmpty(_cityName)) {
//                    _cityName = _cityName.substring(0, _cityName.length() - 1);
//                }
//
//                KLog.d(_cityName + _Lat + "---" + _Lng);
//                String cityId = "";
//
//                if (_cityName.equals("临沂")) {
//                    cityId = "CN101120901";
//                } else if (_cityName.equals("北京")) {
//                    cityId = "CN101010100";
//                } else if (_cityName.equals("上海")) {
//                    cityId = "CN101020100";
//                }
//
//                String url = "https://api.heweather.com/x3/weather?cityid=" + cityId + "&key=df75b4ca1fae4a70b131669142d4cbee";
//                OkHttpUtils
//                        .get()
//                        .url(url)
//                        .build()
//                        .execute(new StringCallback() {
//                            @Override
//                            public void onError(Request request, Exception e) {
//
//                            }
//
//                            @Override
//                            public void onResponse(String response) {
//
//                                try {
//                                    JSONObject jsonObject = new JSONObject(response);
//
//                                    JSONArray jsonArray = jsonObject.optJSONArray("HeWeather data service 3.0");
//
//                                    JSONObject jsonObject1 = jsonArray.optJSONObject(0);
//
//                                    Weather weather = new Gson().fromJson(jsonObject1.toString(), Weather.class);
//
//                                    tvAirQuality.setText("空气质量：无数据");
//                                    tvTemperature.setText(weather.now.tmp + "C");
//                                    tvPm.setText("PM：无数据");
//                                    tvAddress.setText(_cityName);
//
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//
//                            }
//                        });
//
//
//            }
//        }
//    }
}
