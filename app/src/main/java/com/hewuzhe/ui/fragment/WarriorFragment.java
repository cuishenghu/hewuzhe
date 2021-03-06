package com.hewuzhe.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hewuzhe.R;
import com.hewuzhe.banner.CircleFlowIndicator;
import com.hewuzhe.banner.ImagePagerAdapter;
import com.hewuzhe.banner.ViewFlow;
import com.hewuzhe.model.AboutUs;
import com.hewuzhe.model.CityInfo;
import com.hewuzhe.model.Images;
import com.hewuzhe.model.User;
import com.hewuzhe.model.Weather;
import com.hewuzhe.presenter.WarriorFragmentPresenter;
import com.hewuzhe.ui.App;
import com.hewuzhe.ui.activity.BasicMapActivity;
import com.hewuzhe.ui.activity.DoJoRecommendActivity;
import com.hewuzhe.ui.activity.DojoRecommend2Activity;
import com.hewuzhe.ui.activity.FriendProfileActivity;
import com.hewuzhe.ui.activity.IntegralActivity;
import com.hewuzhe.ui.activity.LiveVideoListActivity;
import com.hewuzhe.ui.activity.LocationActivity;
import com.hewuzhe.ui.activity.MemberActivity;
import com.hewuzhe.ui.activity.MoreActivity;
import com.hewuzhe.ui.activity.MyCollectionsActivity;
import com.hewuzhe.ui.activity.MyScoreActivity;
import com.hewuzhe.ui.activity.PhotoActivity;
import com.hewuzhe.ui.activity.PrivateTrainerList2Activity;
import com.hewuzhe.ui.activity.ProfileActivity;
import com.hewuzhe.ui.activity.RecordActivity;
import com.hewuzhe.ui.activity.ScreenListActivity;
import com.hewuzhe.ui.activity.SignInActivity;
import com.hewuzhe.ui.activity.StrangerProfileSettingsActivity;
import com.hewuzhe.ui.activity.StudyOnlineActivity;
import com.hewuzhe.ui.activity.TrainActivity;
import com.hewuzhe.ui.activity.VideoMessageActivity;
import com.hewuzhe.ui.base.ToolBarFragment;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widget.GlideCircleTransform;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.SPUtil;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.view.WarriorFragmentView;
import com.pgyersdk.update.PgyUpdateManager;
import com.socks.library.KLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import io.rong.message.ImageMessage;
import io.rong.message.LocationMessage;
import io.rong.message.RichContentMessage;
import okhttp3.Request;


/**
 * A simple {@link Fragment} subclass.
 */
public class WarriorFragment extends ToolBarFragment<WarriorFragmentPresenter> implements WarriorFragmentView, RongIM.LocationProvider, RongIM.UserInfoProvider, RongIM.GroupInfoProvider, RongIM.ConversationBehaviorListener {
    protected Toolbar toolBar;
    protected ActionBar actionBar;
    protected TextView tv_action;
    private TextView tvTitle;
    @Bind(R.id.img_avatar)
    ImageView imgAvatar;
    @Bind(R.id.tv_train)
    LinearLayout tvTrain;//课程
    @Bind(R.id.tv_dojo_recommend)
    LinearLayout tvDojoRecommend;//场馆
    @Bind(R.id.tv_fly_dream)
    LinearLayout tvFlyDream;//私教
    @Bind(R.id.tv_live)
    LinearLayout tvLive;//直播
    @Bind(R.id.lay_study_online)
    LinearLayout layStudyOnline;//学习计划
    @Bind(R.id.lay_record)
    LinearLayout layRecord;//成长记录
    @Bind(R.id.lay_my_collections)
    LinearLayout layMyCollections;//精品收藏
    @Bind(R.id.lay_exchange)
    LinearLayout layExchange;//积分兑换
    @Bind(R.id.lay_integral_one)
    LinearLayout getLayIntegralOne;//积分
    @Bind(R.id.lay_level)
    LinearLayout layLevel;//会员

    @Nullable
    @Bind(R.id.lay_more)
    RelativeLayout layMore;
    @Nullable
    @Bind(R.id.tv_air_quality)
    TextView tvAirQuality;
    @Nullable
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Nullable
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
//    @Bind(R.id.img_index)
//    ImageView _ImgIndex;
//    @Bind(R.id.img_index_del)
//    ImageView _ImgIndexDel;
//    @Bind(R.id.lay_index_img)
//    FrameLayout _LayIndexImg;
    @Bind(R.id.img_weather)
    ImageView _ImgWeather;
    private View rootView;
    private User user;
    private boolean isHasSetRongIM = false;
    //    private AMapLocationClient locationClient;
    private Fragment curFragment;
    private MoreFragment moreFragment;

    private String _cityName = "临沂市";
    private String _Lat;
    private String _Lng;
    private String cityId = "CN101120901";
    private String userId;
    public static LocationCallback _LocationCallback;
    private String _Address;
    private SPUtil spUtil;
    private boolean isFirstRun = true;
    private boolean hasConnected = false;
    private ArrayList<CityInfo> cityInfos = new ArrayList<>();
    private LocationClient mLocClient;
    Timer timer = new Timer();

    public MyLocationListenner myListener = new MyLocationListenner();
    private MyLocationConfiguration.LocationMode mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
    private String city = "";

    @Bind(R.id.viewflow)
    ViewFlow mViewFlow;
    @Bind(R.id.viewflowindic)
    CircleFlowIndicator mFlowIndicator;
    @Bind(R.id.framelayout)
    FrameLayout framelayout;

    private int mCurrPos;
    private ViewFlipper notice_vf;
    ArrayList<String> imageUrlList = new ArrayList<String>();
    ArrayList<String> linkUrlArray = new ArrayList<String>();
    ArrayList<String> titleList = new ArrayList<String>();

    public WarriorFragment() {
        // Required empty public constructor
    }


    @Override
    protected String provideTitle() {
        return "学习";
    }

    /**
     * 初始化一些事情
     *
     * @param v
     */
    @Override
    protected void initThings(View v) {
        super.initThings(v);
        Glide.get(getContext()).clearMemory();
        timer.schedule(task, 10);
        //定位

        // 定位初始化
        mLocClient = new LocationClient(getActivity());
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setIsNeedAddress(true);
        mLocClient.setLocOption(option);
        mLocClient.start();


        presenter.getUserData();

        spUtil = new SPUtil(getContext())
                .open("settings");

        PgyUpdateManager.register(getActivity());
        presenter.getBannerImg();
    }
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            Glide.get(getContext()).clearDiskCache();
        }
    };
    private void getCityId() {

        String url = "https://api.heweather.com/x3/citylist?search=allchina" + "&key=df75b4ca1fae4a70b131669142d4cbee";
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
                        KLog.json(response);


                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.optJSONArray("city_info");
                            cityInfos = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<CityInfo>>() {
                            }.getType());

                            for (CityInfo cityInfo : cityInfos) {
                                if (city.equals(cityInfo.city)) {
                                    getWeather(cityInfo.id);
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });


    }

    private void getWeather(String city) {
        String url = "https://api.heweather.com/x3/weather?cityid=" + city + "&key=df75b4ca1fae4a70b131669142d4cbee";
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
                        KLog.json(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray jsonArray = jsonObject.optJSONArray("HeWeather data service 3.0");

                            JSONObject jsonObject1 = jsonArray.optJSONObject(0);

                            Weather weather = new Gson().fromJson(jsonObject1.toString(), Weather.class);

                            if (tvAirQuality != null) {
                                tvAirQuality.setText(weather.daily_forecast.get(0).cond.txt_d);

                                tvTemperature.setText(weather.now.tmp + "℃");

                                tvAddress.setText(_cityName);

                                Glide.with(getActivity())
                                        .load("http://files.heweather.com/cond_icon/" + weather.daily_forecast.get(0).cond.code_d + ".png")
                                        .centerCrop()
                                        .fitCenter()
                                        .into(_ImgWeather);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
    }

    @Override
    public void initToolBar(View rootView) {
        toolBar = (Toolbar) rootView.findViewById(R.id.toolbar);
//        tv_action = (TextView) rootView.findViewById(R.id.tv_action);
        tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
//        tv_action.setText("设置");
        tvTitle.setText("学习");
    }


    @Override
    public void initListeners() {
        layMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MoreActivity.class));
            }
        });


        /**
         * 个人资料
         */
        imgAvatar.setOnClickListener(new View.OnClickListener()

                                     {
                                         @Override
                                         public void onClick(View view) {
                                             if(new SessionUtil(getContext()).isLogin())
                                                 try {
                                                     Intent intent = new Intent(getActivity(), ProfileActivity.class);
                                                     ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), imgAvatar, "imgAvatar");
                                                     ActivityCompat.startActivity(getActivity(), intent, optionsCompat.toBundle());
                                                 } catch (IllegalArgumentException e) {
                                                     startActivity(new Intent(getActivity(), ProfileActivity.class));
                                                 }
                                             else
                                             startActivity(SignInActivity.class);

                                         }
                                     }

        );
        /**
         * 私教
         */
        tvFlyDream.setOnClickListener(new View.OnClickListener()

                                      {
                                          @Override
                                          public void onClick(View view) {
                                              if(new SessionUtil(getContext()).isLogin())
                                                  startActivity(new Intent(getActivity(), PrivateTrainerList2Activity.class));
                                              else
                                              startActivity(SignInActivity.class);


//                startActivity(SignupProfileActivity.class);


                                          }
                                      }

        );
        /**
         * 学习计划
         */
        layStudyOnline.setOnClickListener(new View.OnClickListener()

                                          {
                                              @Override
                                              public void onClick(View view) {
                                                  if(new SessionUtil(getContext()).isLogin())
                                                      startActivity(new Intent(getActivity(), TrainActivity.class));
                                                  else
                                                  startActivity(SignInActivity.class);

                                              }
                                          }

        );
        /**
         * 精品收藏
         */
        layMyCollections.setOnClickListener(new View.OnClickListener()

                                            {
                                                @Override
                                                public void onClick(View view) {
                                                    if(new SessionUtil(getContext()).isLogin())
                                                        startActivity(new Intent(getActivity(), MyCollectionsActivity.class));
                                                    else
                                                    startActivity(SignInActivity.class);
                                                }
                                            }

        );
        /**
         *课程
         */
        tvTrain.setOnClickListener(new View.OnClickListener()

                                   {
                                       @Override
                                       public void onClick(View view) {
                                           if(new SessionUtil(getContext()).isLogin())
                                               startActivity(new Intent(getActivity(), StudyOnlineActivity.class));
                                           else
                                           startActivity(SignInActivity.class);
                                       }
                                   }

        );
        /**
         * 成长记录
         */
        layRecord.setOnClickListener(new View.OnClickListener()

                                     {
                                         @Override
                                         public void onClick(View view) {
                                             if(new SessionUtil(getContext()).isLogin())
                                                 startActivity(new Intent(getActivity(), RecordActivity.class));
                                             else
                                             startActivity(SignInActivity.class);
                                         }
                                     }

        );
        /**
         * 积分兑换
         */
        layExchange.setOnClickListener(new View.OnClickListener()

                                       {
                                           @Override
                                           public void onClick(View view) {
                                               if(new SessionUtil(getContext()).isLogin())
                                                   startActivity(new Intent(getActivity(), MyScoreActivity.class));
                                               else
                                               startActivity(SignInActivity.class);
                                           }
                                       }

        );
        /**
         * 直播
         */
        tvLive.setOnClickListener(new View.OnClickListener()

                                  {
                                      @Override
                                      public void onClick(View view) {
                                          if(new SessionUtil(getContext()).isLogin())
                                              startActivity(new Intent(getActivity(), LiveVideoListActivity.class));


                                          else
                                          startActivity(SignInActivity.class);
                                      }
                                  }

        );
        /**
         * 场馆
         */
        tvDojoRecommend.setOnClickListener(new View.OnClickListener()

                                           {
                                               @Override
                                               public void onClick(View view) {
                                                   if(new SessionUtil(getContext()).isLogin())
                                                       startActivity(new Intent(getActivity(), DojoRecommend2Activity.class));
                                                   else
                                                   startActivity(SignInActivity.class);
                                               }
                                           }

        );
        /**
         * 积分
         */
        getLayIntegralOne.setOnClickListener(new View.OnClickListener()

                                             {
                                                 @Override
                                                 public void onClick(View view) {
                                                     if(new SessionUtil(getContext()).isLogin())
                                                        startActivity(IntegralActivity.class);
                                                     else
                                                         startActivity(SignInActivity.class);
                                                 }
                                             }

        );

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

        if (!hasConnected) {
            connect(user.Token);
        }

        tvUsername.setText(user.NicName);
        tvId.setText("ID:" + getUserId());
        tvLevel.setText("lv" + user.Rank);
        tvIntegral.setText(user.Credit + "");
//        tvLevelName.setText(user.isVip() ? "会员" : "成为会员");
        tvLevelName.setText("会员");

        /**
         * 会员======================暂时关闭============用时再打开========================
         */
        layLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MemberActivity.class);
            }
        });

        Glide.with(getActivity())
                .load(user.PhotoPath.contains("UpLoad/Photo/")?C.BASE_URL + user.PhotoPath:user.PhotoPath)
                .centerCrop()
                .crossFade()
                .placeholder(R.mipmap.img_avatar)
                .transform(new GlideCircleTransform(getContext()))
                .into(imgAvatar);

    }

    private String getUserId() {
        return StringUtil.isEmpty(user.Phone) ? "000000" + user.Id : getPhone(user.Phone);
    }

    private String getPhone(String phone) {
        if (StringUtil.isMobile(phone)) {
            String start = phone.substring(0, 3);
            String end = phone.substring(8, 11);
            return start + "*****" + end;
        } else if (!StringUtil.isEmpty(phone)) {
            return phone;
        } else {
            return "";
        }
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
    public void setIndexImg(ArrayList<Images> list) {
//        多张轮播图
//        banner轮播图
        for (int i = 0; i < list.size(); i++) {
            imageUrlList.add(C.BASE_URL + list.get(i).Path);
            linkUrlArray.add(list.get(i).Url);
            titleList.add("商品图片");

        }
        ViewGroup.LayoutParams para;
        para = framelayout.getLayoutParams();
        para.height = StringUtil.getScreenWidth(getActivity()) / 2;
        framelayout.setLayoutParams(para);

        initBanner(imageUrlList);

    }

    /**
     * 中间广告链接
     *
     * @param data
     */

    public void setIndexImg(final AboutUs data) {

        //多张轮播图
        //banner轮播图
//        for (int i = 0; i < product.PicList.size(); i++) {
//            imageUrlList.add(C.BASE_URL + product.PicList.get(i).Path);
//            linkUrlArray.add("");
//            titleList.add("商品图片");
//
//        }
        //单张轮播图

//        imageUrlList.add(C.BASE_URL +  data.IndexImage);
//        linkUrlArray.add(data.ImageUrl);
//        titleList.add("商品图片");

//        Glide.with(getActivity())
//                .load(C.BASE_URL + data.IndexImage)
//                .fitCenter()
//                .crossFade()
//                .into(_ImgIndex);

//        _LayIndexImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                startActivity(BasicWebActivity.class, new Bun().putString("url", data.ImageUrl).putString("title", "首页广告").ok());
//
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(data.ImageUrl));
//                startActivity(intent);
//
//            }
//        });
//        ViewGroup.LayoutParams para;
//        para = _LayIndexImg.getLayoutParams();
//        para.height = StringUtil.getScreenWidth(this.getActivity()) / 2;
//        _LayIndexImg.setLayoutParams(para);
//        _ImgIndexDel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                _LayIndexImg.setVisibility(View.GONE);
//            }
//        });
    }

    //轮播图sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss

    private void initBanner(ArrayList<String> imageUrlList) {

        mViewFlow.setAdapter(new ImagePagerAdapter(getContext(), imageUrlList, linkUrlArray, titleList).setInfiniteLoop(true));
        mViewFlow.setmSideBuffer(imageUrlList.size()); // 实际图片张数，
        // 我的ImageAdapter实际图片张数为3

        mViewFlow.setFlowIndicator(mFlowIndicator);
        mViewFlow.setTimeSpan(4500);
        mViewFlow.setSelection(imageUrlList.size() * 1000); // 设置初始位置
        mViewFlow.startAutoFlowTimer(); // 启动自动播放
    }

    //轮播图eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee


    @Override
    public boolean canBack() {
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstRun) {
            isFirstRun = false;
        } else {
            presenter.getUserData();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden) {
            presenter.getUserData();
        }

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
                    user = new SessionUtil(getContext()).getUser();
                    connect(user.Token);
                }

                /**
                 * 连接融云成功
                 *
                 * @param userid 当前 token
                 */
                @Override
                public void onSuccess(String userid) {

                    hasConnected = true;
                    Log.d("LoginActivity", "--onSuccess" + userid);
//                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                    finish();


                    RongIM.setUserInfoProvider(WarriorFragment.this, true);//设置用户信息提供者。
                    RongIM.setGroupInfoProvider(WarriorFragment.this, true);//设置群组信息提供者。
                    RongIM.setConversationBehaviorListener(WarriorFragment.this);//设置会话界面操作的监听器。
                    RongIM.setLocationProvider(WarriorFragment.this);//设置地理位置提供者,不用位置的同学可以注掉此行代码

                    RongIM.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
                        @Override
                        public boolean onReceived(Message message, int i) {
                            return !spUtil.getBoolean("msg");
//                            return false;
                        }
                    });
                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    user = new SessionUtil(getContext()).getUser();
                    connect(user.Token);
                    Log.d("LoginActivity", "--onError" + errorCode);
                }
            });
        }
    }

    @Override
    public void onStartLocation(Context context, LocationCallback locationCallback) {
        this._LocationCallback = locationCallback;
        Intent intent = new Intent(getContext(), LocationActivity.class);
        intent.putExtra("data", new Bun().putString("title", "选择位置").ok());
        getActivity().startActivity(intent);
    }

    @Override
    public UserInfo getUserInfo(String s) {
//      User u = new Select().from(User.class).where("Id=" + s).executeSingle();
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
//      User u = new Select().from(com.hewuzhe.model.Group.class).where("Id=" + s).executeSingle();
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

        int id = Integer.parseInt(userInfo.getUserId());
        if (new SessionUtil(getContext()).getUserId() == id) {
            startActivity(ProfileActivity.class);

        } else {
            presenter.isWuyou(id);
        }

        return false;
    }


    @Override
    public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
        return false;
    }

    /**
     * @param context
     * @param view
     * @param message
     * @return
     */
    @Override
    public boolean onMessageClick(Context context, View view, Message message) {

        /**
         * demo 代码  开发者需替换成自己的代码。
         */
        if (message.getContent() instanceof LocationMessage) {
            Intent intent = new Intent(context, BasicMapActivity.class);
            intent.putExtra("data", new Bun().putString("lat", ((LocationMessage) message.getContent()).getLat() + "").putString("lng", ((LocationMessage) message.getContent()).getLng() + "").putString("address", ((LocationMessage) message.getContent()).getPoi()).putString("name", "").putString("title", "位置信息").ok());
            context.startActivity(intent);
        } else if (message.getContent() instanceof RichContentMessage) {
            RichContentMessage mRichContentMessage = (RichContentMessage) message.getContent();
            Log.d("Begavior", "extra:" + mRichContentMessage.getExtra());

        } else if (message.getContent() instanceof ImageMessage) {
            if (((ImageMessage) message.getContent()).getExtra() != null) {
                String path = ((ImageMessage) message.getContent()).getExtra().toString().substring(0, 7);
                String video = ((ImageMessage) message.getContent()).getExtra().toString().substring(7);
                if (path.equals("123456;")) {
                    context.startActivity(new Intent(context, VideoMessageActivity.class).putExtra("videoPath", video));
                }
            } else {
                ImageMessage imageMessage = (ImageMessage) message.getContent();
                Intent intent = new Intent(context, PhotoActivity.class);

                intent.putExtra("photo", imageMessage.getLocalUri() == null ? imageMessage.getRemoteUri() : imageMessage.getLocalUri());
                if (imageMessage.getThumUri() != null)
                    intent.putExtra("thumbnail", imageMessage.getThumUri());

                context.startActivity(intent);
            }
        }

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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null) {
                return;
            }
            _cityName = location.getCity() + "";

            city = _cityName.substring(0, _cityName.length() - 1);

            KLog.d(city);

            getCityId();

        }


        public void onReceivePoi(BDLocation poiLocation) {


        }
    }


}
