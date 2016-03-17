package com.hewuzhe.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.hewuzhe.R;
import com.hewuzhe.model.PrivateTrainer;
import com.hewuzhe.model.TrainerFans;
import com.hewuzhe.model.TrainerGuanZhu;
import com.hewuzhe.model.TrainerLesson;
import com.hewuzhe.model.TrainerVideo;
import com.hewuzhe.ui.adapter.TrainerSignAdapter;
import com.hewuzhe.ui.adapter.TrainerVideoAndFocusAndFansAdapter;
import com.hewuzhe.ui.base.BaseActivity2;
import com.hewuzhe.ui.http.AbstractHttpHandler;
import com.hewuzhe.ui.http.EntityHandler;
import com.hewuzhe.ui.http.HttpErrorHandler;
import com.hewuzhe.ui.http.HttpUtils;
import com.hewuzhe.ui.http.UrlContants;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.view.MyCommonTitle;
import com.hewuzhe.view.XListView;
import com.hewuzhe.view.XListView.IXListViewListener;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/11 0011.
 */
public class PrivateTrainerInfoActivity2 extends BaseActivity2 implements IXListViewListener, OnItemClickListener {
    private int nowPage = 1;
    private static int PERPAGE = 10;
    private MyCommonTitle myCommonTitle;
    private XListView mListView;
    private ImageView img_avatar;
    private String phoneNumber;
    private RequestParams params;
    private TextView tv_user_name, tv_profession, tv_address, tv_focus, tv_contact;
    private int flag;//标记是否已经关注
    private TextView tv_video, tv_sign, tv_focused, tv_fans;
    private GridView mGridView;
    private List<TrainerVideo> trainerVideos = new ArrayList<TrainerVideo>();
    private List<TrainerLesson> trainerLessons = new ArrayList<TrainerLesson>();
    private List<TrainerGuanZhu> trainerGuanZhus = new ArrayList<TrainerGuanZhu>();
    private List<TrainerFans> trainerFanses = new ArrayList<TrainerFans>();
    private ArrayList<PrivateTrainer> privateTrainers = new ArrayList<PrivateTrainer>();

    private TrainerSignAdapter trainerSignAdapter;
    private TrainerVideoAndFocusAndFansAdapter trainerVideoAndFocusAndFansAdapter;
    private Handler mHandler;
    private int mType = 1;
    private View viewItem;
    private String[] names = {"张三", "李四", "王五", "张三", "李四", "王五", "张三", "李四", "王五"};
    private int[] avatars = {R.mipmap.img_avatar, R.mipmap.img_avatar, R.mipmap.img_avatar, R.mipmap.img_avatar, R.mipmap.img_avatar, R.mipmap.img_avatar, R.mipmap.img_avatar, R.mipmap.img_avatar, R.mipmap.img_avatar};
    private List<Map<String, String>> list = new ArrayList<Map<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        String id=getIntent().getBundleExtra("data").getString("");
        setContentView(R.layout.activity_private_trainer_info);

        initView();
        getTrainerInfoById();
//        requestData();
        if (mType == 1) {
            mGridView.setNumColumns(2);
            initData(mType);
            getVideoByTeancherId();
//            setListViewHeightBasedOnChildren(mListView, 2,this);
        }
    }

    private void initView() {
        myCommonTitle = (MyCommonTitle) findViewById(R.id.aci_mytitle);
        myCommonTitle.setTitle("私教详情");

        mListView = (XListView) findViewById(R.id.list_trainer_content);
        mListView.setDividerHeight(0);
        mListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
        mListView.setXListViewListener(this);

        View topView = LayoutInflater.from(PrivateTrainerInfoActivity2.this).inflate(R.layout.activity_private_trainer, null);
        img_avatar = (ImageView) topView.findViewById(R.id.img_avatar);//头像
        tv_user_name = (TextView) topView.findViewById(R.id.tv_nick_name);//姓名
        tv_profession = (TextView) topView.findViewById(R.id.tv_profession);//擅长
        tv_address = (TextView) topView.findViewById(R.id.tv_address);//地址
        tv_focus = (TextView) topView.findViewById(R.id.tv_focus);//关注
        tv_contact = (TextView) topView.findViewById(R.id.tv_contact);//联系
        tv_video = (TextView) topView.findViewById(R.id.tv_video);//视频
        tv_sign = (TextView) topView.findViewById(R.id.tv_sign);//相册
        tv_focused = (TextView) topView.findViewById(R.id.tv_focused);//关注
        tv_fans = (TextView) topView.findViewById(R.id.tv_fans);//粉丝
        mGridView = (GridView) topView.findViewById(R.id.list_content);
        mListView.addHeaderView(topView,null,true);
//        mListView.addView(topView);
        mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));

        setListener(tv_focus, tv_contact, tv_video, tv_sign, tv_focused, tv_fans);

        mHandler = new Handler();
    }

    private void initData(int mType) {
        trainerVideoAndFocusAndFansAdapter = new TrainerVideoAndFocusAndFansAdapter(PrivateTrainerInfoActivity2.this, privateTrainers, mType);
        mGridView.setAdapter(trainerVideoAndFocusAndFansAdapter);
    }

    /**
     * 根据教练的ID查询教练信息
     */
    public void getTrainerInfoById() {
        params = new RequestParams();
        params.put("userid", new SessionUtil(PrivateTrainerInfoActivity2.this).getUser().Id);
        params.put("teacherid", 2);
        HttpUtils.getTrainerById(new HttpErrorHandler() {
            @Override
            public void onRecevieSuccess(JSONObject json) {
                JSONObject jsonObject = json.getJSONObject(UrlContants.jsonData);
                ImageLoader.getInstance().displayImage(StringUtil.toString(
                        UrlContants.IMAGE_URL + jsonObject.getString("PhotoPath"), "http://"), img_avatar);
                tv_user_name.setText(jsonObject.getString("NicName"));
//                tv_profession  //擅长
//                        tv_address   //地址
                phoneNumber = jsonObject.getString("Phone");
//                flag=
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
            case R.id.tv_focus:
                break;
            case R.id.tv_contact://联系他,拨打电话
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber)));
                break;
            case R.id.tv_video:
                mType = 1;
                tv_video.setTextColor(getResources().getColor(R.color.colorYellow));
                tv_sign.setTextColor(Color.WHITE);
                tv_focused.setTextColor(Color.WHITE);
                tv_fans.setTextColor(Color.WHITE);
                mGridView.setNumColumns(2);
                requestData(mType);
//                setListViewHeightBasedOnChildren(mListView, 2,this);
                break;
            case R.id.tv_sign:
                mType = 2;
                tv_video.setTextColor(Color.WHITE);
                tv_sign.setTextColor(getResources().getColor(R.color.colorYellow));
                tv_focused.setTextColor(Color.WHITE);
                tv_fans.setTextColor(Color.WHITE);
                mGridView.setNumColumns(1);
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
                mGridView.setNumColumns(3);
                trainerVideoAndFocusAndFansAdapter = new TrainerVideoAndFocusAndFansAdapter(PrivateTrainerInfoActivity2.this, privateTrainers, 3);
                mGridView.setAdapter(trainerVideoAndFocusAndFansAdapter);
                requestData(mType);
//                setListViewHeightBasedOnChildren(mListView, 3,this);
                break;
            case R.id.tv_fans:
                mType = 4;
                tv_video.setTextColor(Color.WHITE);
                tv_sign.setTextColor(Color.WHITE);
                tv_focused.setTextColor(Color.WHITE);
                tv_fans.setTextColor(getResources().getColor(R.color.colorYellow));
                mGridView.setNumColumns(3);
                trainerVideoAndFocusAndFansAdapter = new TrainerVideoAndFocusAndFansAdapter(PrivateTrainerInfoActivity2.this, privateTrainers, 4);
                mGridView.setAdapter(trainerVideoAndFocusAndFansAdapter);
                requestData(mType);
//                setListViewHeightBasedOnChildren(mListView, 3,this);
                break;
        }
    }

    private void requestData(int mType) {
        if (mType == 1) {
            getVideoByTeancherId();
        } else if (mType == 2) {
            getLessonByTeancherId();
        } else if (mType == 3) {
            getGuanZhuByTeancherId();
        } else if (mType == 4) {
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
        params.put("userid", new SessionUtil(PrivateTrainerInfoActivity2.this).getUser().Id);
        params.put("teacherid", 2);//======待修改=======待修改========待修改======待修改========待修改======待修改=========待修改===
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
            trainerVideoAndFocusAndFansAdapter.notifyDataSetChanged();
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
        params.put("userid", new SessionUtil(PrivateTrainerInfoActivity2.this).getUser().Id);
        params.put("teacherid", 2);//======待修改=======待修改========待修改======待修改========待修改======待修改=========待修改===


    }

    /**
     * 根据教练的ID查询教练的关注列表
     */
    public void getGuanZhuByTeancherId() {
        params = new RequestParams();
        params.put("startRowIndex", 0);
        params.put("maximumRows", 10);
        params.put("search", "");
        params.put("userid", new SessionUtil(PrivateTrainerInfoActivity2.this).getUser().Id);
        params.put("teacherid", 3);//======待修改=======待修改========待修改======待修改========待修改
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
        params.put("userid", new SessionUtil(PrivateTrainerInfoActivity2.this).getUser().Id);
        params.put("teacherid", 2);//======待修改=======待修改========待修改======待修改========待修改
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

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nowPage = 1;
                requestData(mType);
                onLoad();
            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nowPage += 1;
                requestData(mType);
                onLoad();
            }
        }, 1000);
    }

    public void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime("刚刚");

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
