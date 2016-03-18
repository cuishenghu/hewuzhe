package com.hewuzhe.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import android.widget.AdapterView.OnItemClickListener;

import com.alibaba.fastjson.JSONObject;
import com.hewuzhe.R;
import com.hewuzhe.model.PrivateTrainer;
import com.hewuzhe.model.TrainerFans;
import com.hewuzhe.model.TrainerGuanZhu;
import com.hewuzhe.model.TrainerLesson;
import com.hewuzhe.model.TrainerVideo;
import com.hewuzhe.ui.adapter.TrainerSignAdapter;
import com.hewuzhe.ui.adapter.TrainerVideoAdapter;
import com.hewuzhe.ui.adapter.TrainerVideoAndFocusAndFansAdapter;
import com.hewuzhe.ui.base.BaseActivity2;
import com.hewuzhe.ui.http.AbstractHttpHandler;
import com.hewuzhe.ui.http.EntityHandler;
import com.hewuzhe.ui.http.HttpErrorHandler;
import com.hewuzhe.ui.http.HttpUtils;
import com.hewuzhe.ui.http.UrlContants;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.view.CircleImageView;
import com.hewuzhe.view.MyCommonTitle;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/11 0011.
 */
public class PrivateTrainerInfoActivity extends BaseActivity2 implements OnItemClickListener {
    private int nowPage = 1;
    private static int PERPAGE = 10;
    private MyCommonTitle myCommonTitle;
    private CircleImageView img_avatar;
    private String phoneNumber;
    private boolean isGuanZhu;
    private RequestParams params;
    private TextView tv_user_name, tv_profession, tv_address, tv_focus, tv_contact;
    private TextView tv_video, tv_sign, tv_focused, tv_fans;
    private GridView mListView;
    private List<TrainerVideo> trainerVideos = new ArrayList<TrainerVideo>();
    private List<TrainerLesson> trainerLessons = new ArrayList<TrainerLesson>();
    private ArrayList<PrivateTrainer> privateTrainers = new ArrayList<PrivateTrainer>();
    private TrainerVideoAdapter trainerVideoAdapter;
    private TrainerSignAdapter trainerSignAdapter;
    private TrainerVideoAndFocusAndFansAdapter trainerVideoAndFocusAndFansAdapter;
    private Handler mHandler;
    private int mType = 1;
    private int IsGuanzhu;
    private int Id;
    private View viewItem;
    private String[] names = {"张三", "李四", "王五", "张三", "李四", "王五", "张三", "李四", "王五"};
    private int[] avatars = {R.mipmap.img_avatar, R.mipmap.img_avatar, R.mipmap.img_avatar, R.mipmap.img_avatar, R.mipmap.img_avatar, R.mipmap.img_avatar, R.mipmap.img_avatar, R.mipmap.img_avatar, R.mipmap.img_avatar};
    private List<Map<String, String>> list = new ArrayList<Map<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Id = getIntent().getBundleExtra("data").getInt("Id");
        setContentView(R.layout.activity_private_trainer);

        initView();
        getTrainerInfoById();
//        requestData();
        if (mType == 1) {
            mListView.setNumColumns(2);
            initData(mType);
            getVideoByTeancherId();
//            setListViewHeightBasedOnChildren(mListView, 2,this);
        }
    }

    private void initView() {
        myCommonTitle = (MyCommonTitle) findViewById(R.id.aci_mytitle);
        myCommonTitle.setTitle("私教详情");

        img_avatar = (CircleImageView) findViewById(R.id.img_avatar);//头像
        tv_user_name = (TextView) findViewById(R.id.tv_nick_name);//姓名
        tv_profession = (TextView) findViewById(R.id.tv_profession);//擅长
        tv_address = (TextView) findViewById(R.id.tv_address);//地址
        tv_focus = (TextView) findViewById(R.id.tv_focus);//关注
        tv_contact = (TextView) findViewById(R.id.tv_contact);//联系
        tv_video = (TextView) findViewById(R.id.tv_video);//视频
        tv_sign = (TextView) findViewById(R.id.tv_sign);//相册
        tv_focused = (TextView) findViewById(R.id.tv_focused);//关注
        tv_fans = (TextView) findViewById(R.id.tv_fans);//粉丝

        mListView = (GridView) findViewById(R.id.list_content);

//        mListView.setDividerHeight(0);
        mListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
//        mListView.setPullRefreshEnable(true);
//        mListView.setPullLoadEnable(true);
//        mListView.setXListViewListener(this);
        mListView.setOnItemClickListener(this);

        setListener(tv_focus, tv_contact, tv_video, tv_sign, tv_focused, tv_fans);

        mHandler = new Handler();
    }

    private void initData(int mType) {
        if (mType == 1) {
            trainerVideoAdapter = new TrainerVideoAdapter(PrivateTrainerInfoActivity.this, trainerVideos, mType);
            mListView.setAdapter(trainerVideoAdapter);
        } else if (mType == 2) {
            trainerSignAdapter = new TrainerSignAdapter(PrivateTrainerInfoActivity.this, trainerLessons, mType);
            mListView.setAdapter(trainerSignAdapter);
        } else {
            trainerVideoAndFocusAndFansAdapter = new TrainerVideoAndFocusAndFansAdapter(PrivateTrainerInfoActivity.this, privateTrainers, mType);
            mListView.setAdapter(trainerVideoAndFocusAndFansAdapter);
        }
    }

    /**
     * 根据教练的ID查询教练信息
     */
    public void getTrainerInfoById() {
        params = new RequestParams();
        params.put("userid", new SessionUtil(PrivateTrainerInfoActivity.this).getUser().Id);
        params.put("teacherid", Id);
        HttpUtils.getTrainerById(new HttpErrorHandler() {
            @Override
            public void onRecevieSuccess(JSONObject json) {
                JSONObject jsonObject = json.getJSONObject(UrlContants.jsonData);
                ImageLoader.getInstance().displayImage(StringUtil.toString(
                        UrlContants.IMAGE_URL + jsonObject.getString("PhotoPath"), "http://"), img_avatar);
                tv_user_name.setText(jsonObject.getString("NicName"));
                tv_profession.setText(jsonObject.getString("Speciality"));
                tv_address.setText(jsonObject.getString("HomeAddress"));
                phoneNumber = jsonObject.getString("Phone");
                IsGuanzhu = jsonObject.getIntValue("IsGuanZhu");
                    if (IsGuanzhu==1) {
                        tv_focus.setText("已关注");
                    }else {
                        tv_focus.setText("关注Ta");
                    }
               
            }
        }, params);
    }

    /**
     * 点击不同的空间颜色变化,并获取相应的数据
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.img_avatar:
                startActivity(new Intent(PrivateTrainerInfoActivity.this, ProfileActivity.class).putExtra("data", new Bun().putInt("Id", Id).ok()));
                break;
            case R.id.tv_contact://联系他,拨打电话
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber)));
            case R.id.tv_video:
                mType = 1;
                tv_video.setTextColor(getResources().getColor(R.color.colorYellow));
                tv_sign.setTextColor(Color.WHITE);
                tv_focused.setTextColor(Color.WHITE);
                tv_fans.setTextColor(Color.WHITE);
                mListView.setNumColumns(2);
                initData(mType);
                trainerVideos.clear();
                requestData(mType);
//                setListViewHeightBasedOnChildren(mListView, 2,this);
                break;
            case R.id.tv_sign:
                mType = 2;
                tv_video.setTextColor(Color.WHITE);
                tv_sign.setTextColor(getResources().getColor(R.color.colorYellow));
                tv_focused.setTextColor(Color.WHITE);
                tv_fans.setTextColor(Color.WHITE);
                mListView.setNumColumns(1);
                initData(mType);
                trainerLessons.clear();
                requestData(mType);
//                setListViewHeightBasedOnChildren(mListView, 1,this);
//                trainerSignAdapter=new TrainerSignAdapter()
                break;
            case R.id.tv_focused:
                mType = 3;
                tv_video.setTextColor(Color.WHITE);
                tv_sign.setTextColor(Color.WHITE);
                tv_focused.setTextColor(getResources().getColor(R.color.colorYellow));
                tv_fans.setTextColor(Color.WHITE);
                mListView.setNumColumns(3);
                initData(mType);
                privateTrainers.clear();
                requestData(mType);
//                setListViewHeightBasedOnChildren(mListView, 3,this);
                break;
            case R.id.tv_fans:
                mType = 4;
                tv_video.setTextColor(Color.WHITE);
                tv_sign.setTextColor(Color.WHITE);
                tv_focused.setTextColor(Color.WHITE);
                tv_fans.setTextColor(getResources().getColor(R.color.colorYellow));
                mListView.setNumColumns(3);
                initData(mType);
                privateTrainers.clear();
                requestData(mType);
//                setListViewHeightBasedOnChildren(mListView, 3,this);
                break;
        }
    }

    /**
     * @param mType
     */
    private void requestData(int mType) {
        if (mType == 1) {
            getVideoByTeancherId();
        } else if (mType == 2) {
            getLessonByTeancherId();
        } else if (mType == 3) {
//            setListViewHeightBasedOnChildren(mListView,3,this);
            getGuanZhuByTeancherId();
        } else if (mType == 4) {
//            setListViewHeightBasedOnChildren(mListView,3,this);
            getFenSiByTeancherId();
        }
    }

    /**
     * 根据教练的ID查询教练的视频列表
     */
    public void getVideoByTeancherId() {
        params = new RequestParams();
        params.put("startRowIndex", 0);
        params.put("maximumRows", 10);
        params.put("search", "");
        params.put("userid", new SessionUtil(PrivateTrainerInfoActivity.this).getUser().Id);
        params.put("teacherid", Id);//======待修改=======待修改========待修改======待修改========待修改======待修改=========待修改===
//        string userid 用户ID  string teacherid 教练ID   string startRowIndex 开始行索引     string maximumRows 每页最大条数
        HttpUtils.getVideoByTeacherId(res_getVideoByTeacherId, params);
    }

    AbstractHttpHandler res_getVideoByTeacherId = new EntityHandler<TrainerVideo>(TrainerVideo.class) {

        @Override
        public void onReadSuccess(List<TrainerVideo> list) {
            if (nowPage == 0) {
                trainerVideos.clear();
            }
            trainerVideos.addAll(list);
            trainerVideoAdapter.notifyDataSetChanged();
        }
    };

    /**
     * 根据教练的ID查询教练的课程列表
     */
    public void getLessonByTeancherId() {
        params = new RequestParams();
        params.put("startRowIndex", 0);
        params.put("maximumRows", 10);
        params.put("search", "");
        params.put("userid", new SessionUtil(PrivateTrainerInfoActivity.this).getUser().Id);
        params.put("teacherid", Id);//======待修改=======待修改========待修改======待修改========待修改======待修改=========待修改===
        HttpUtils.getLessonByTeacherId(res_getLessonByTeacherId, params);
    }

    AbstractHttpHandler res_getLessonByTeacherId = new EntityHandler<TrainerLesson>(TrainerLesson.class) {

        @Override
        public void onReadSuccess(List<TrainerLesson> list) {
            if (nowPage == 0) {
                trainerLessons.clear();
            }
            trainerLessons.addAll(list);
            trainerSignAdapter.notifyDataSetChanged();
        }
    };

    /**
     * 根据教练的ID查询教练的关注列表
     */
    public void getGuanZhuByTeancherId() {
        params = new RequestParams();
        params.put("startRowIndex", 0);
        params.put("maximumRows", 10);
        params.put("search", "");
        params.put("userid", new SessionUtil(PrivateTrainerInfoActivity.this).getUser().Id);
        params.put("teacherid", Id);//======待修改=======待修改========待修改======待修改========待修改
        HttpUtils.getGuanZhuByTeacherId(res_getGuanZhuByTeacherId, params);
    }

    AbstractHttpHandler res_getGuanZhuByTeacherId = new EntityHandler<PrivateTrainer>(PrivateTrainer.class) {

        @Override
        public void onReadSuccess(List<PrivateTrainer> list) {
            if (nowPage == 0) {
                privateTrainers.clear();
            }
            privateTrainers.addAll(list);
            trainerVideoAndFocusAndFansAdapter.notifyDataSetChanged();
        }
    };

    /**
     * 根据教练的ID查询教练的粉丝列表
     */
    public void getFenSiByTeancherId() {
        params = new RequestParams();
        params.put("startRowIndex", 0);
        params.put("maximumRows", 10);
        params.put("search", "");
        params.put("userid", new SessionUtil(PrivateTrainerInfoActivity.this).getUser().Id);
        params.put("teacherid", Id);//======待修改=======待修改========待修改======待修改========待修改
        HttpUtils.getFenSiByTeacherId(res_getFenSiByTeacherId, params);
    }

    AbstractHttpHandler res_getFenSiByTeacherId = new EntityHandler<PrivateTrainer>(PrivateTrainer.class) {

        @Override
        public void onReadSuccess(List<PrivateTrainer> list) {
            if (nowPage == 0) {
                privateTrainers.clear();
            }
            privateTrainers.addAll(list);
            trainerVideoAndFocusAndFansAdapter.notifyDataSetChanged();
        }
    };

    /**
     * 点击进入详情
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mType == 1) {//视频详情
            startActivity(new Intent(PrivateTrainerInfoActivity.this, VideoDetail2Activity.class).
                    putExtra("data", new Bun().putInt("Id", trainerVideos.get(position).getId()).ok()));
        } else if (mType == 2) {//报名课程详情

        } else if (mType == 3) {//关注的私教详情
//            startActivity(new Intent(PrivateTrainerInfoActivity.this, PrivateTrainerInfoActivity.class).
//                    putExtra("data", new Bun().putInt("Id", privateTrainers.get(position).getId()).ok()));
        } else if (mType == 4) {//粉丝的详情
//            startActivity(new Intent(PrivateTrainerInfoActivity.this, PrivateTrainerInfoActivity.class).
//                    putExtra("data", new Bun().putInt("Id", privateTrainers.get(position).getId()).ok()));
        }
    }


    /**
     * @param listView
     * @param col      显示的列数 如果是固定列数,此参数不需添加
     * @param activity 当前activity,用来获取适配器屏幕尺寸
     */
    public static void setListViewHeightBasedOnChildren(GridView listView, int col, Activity activity) {
        // 获取listview的adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // 固定列宽，有多少列
//        int col = 4;// listView.getNumColumns();
        int totalHeight = 0;//总高度
        WindowManager manager = activity.getWindowManager();//获取当前适配器
        Display display = manager.getDefaultDisplay();
        //屏幕高度
        int screenHeight = display.getHeight();
        //屏幕宽度
        int screenWidth = display.getWidth();
        // i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
        // listAdapter.getCount()小于等于8时计算两次高度相加
        for (int i = 0; i < listAdapter.getCount(); i += col) {
            // 获取listview的每一个item
            View listItem = listAdapter.getView(i, null, listView);
            int w = screenWidth / col;
            listItem.measure(screenWidth / col, 0);//两个参数,前面是item的宽度
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
        }

        // 获取listview的布局参数
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // 设置高度
        params.height = totalHeight;
        // 设置margin
        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        // 设置参数
        listView.setLayoutParams(params);
    }

//    /**
//     * 请求数据
//     */
//    private void requestData() {
//        for (int i = 0; i < names.length; i++) {
//            Map<String, String> map = new HashMap<String, String>();
//            map.put("name", names[i]);
//            map.put("avatar", String.valueOf(avatars[i]));
//            list.add(map);
//        }
//        SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, R.layout.item_trainer_focus_fans,
//                new String[]{"name", "avatar"}, new int[]{R.id.tv_nick_name, R.id.img_avatar});
//        mListView.setGravity(Gravity.CENTER);
//        mListView.setAdapter(simpleAdapter);
//    }

//    @Override
//    public void onRefresh() {
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                nowPage = 1;
//                requestData();
//                onLoad();
//            }
//        }, 1000);
//    }
//
//    @Override
//    public void onLoadMore() {
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                nowPage += 1;
//                requestData();
//                onLoad();
//            }
//        }, 1000);
//    }
//
//    public void onLoad() {
//        mListView.stopRefresh();
//        mListView.stopLoadMore();
//        mListView.setRefreshTime("刚刚");
//
//    }

}
