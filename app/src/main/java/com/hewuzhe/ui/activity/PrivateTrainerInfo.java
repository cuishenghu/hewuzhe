package com.hewuzhe.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import android.widget.AdapterView.OnItemClickListener;

import com.hewuzhe.R;
import com.hewuzhe.model.PrivateTrainer;
import com.hewuzhe.ui.adapter.FocusAndFansAdapter;
import com.hewuzhe.ui.base.BaseActivity2;
import com.hewuzhe.view.MyCommonTitle;
import com.hewuzhe.view.XListView;
import com.hewuzhe.view.XListView.IXListViewListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/11 0011.
 */
public class PrivateTrainerInfo extends BaseActivity2 implements OnItemClickListener {
    private int nowPage = 1;
    private static int PERPAGE = 10;
    private MyCommonTitle myCommonTitle;
    private ImageView img_avatar;
    private TextView tv_user_name, tv_profession, tv_address, tv_focus, tv_contact;
    private TextView tv_video, tv_photo, tv_focused, tv_fans;
    private GridView mListView;
    private Handler mHandler;
    private int mType = 1;
    private String[] names = {"张三", "李四", "王五", "张三", "李四", "王五", "张三", "李四", "王五"};
    private int[] avatars = {R.mipmap.img_avatar, R.mipmap.img_avatar, R.mipmap.img_avatar, R.mipmap.img_avatar, R.mipmap.img_avatar, R.mipmap.img_avatar, R.mipmap.img_avatar, R.mipmap.img_avatar, R.mipmap.img_avatar};
    private List<Map<String, String>> list = new ArrayList<Map<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_trainer);

        initView();
        requestData();
    }

    private void initView() {
        myCommonTitle = (MyCommonTitle) findViewById(R.id.aci_mytitle);
        myCommonTitle.setTitle("私教详情");

        img_avatar = (ImageView) findViewById(R.id.img_avatar);//头像
        tv_user_name = (TextView) findViewById(R.id.tv_nick_name);//姓名
        tv_profession = (TextView) findViewById(R.id.tv_profession);//擅长
        tv_address = (TextView) findViewById(R.id.tv_address);//地址
        tv_focus = (TextView) findViewById(R.id.tv_focus);//关注
        tv_contact = (TextView) findViewById(R.id.tv_contact);//联系

        tv_video = (TextView) findViewById(R.id.tv_video);//视频
        tv_photo = (TextView) findViewById(R.id.tv_photo);//相册
        tv_focused = (TextView) findViewById(R.id.tv_focused);//关注
        tv_fans = (TextView) findViewById(R.id.tv_fans);//粉丝

        mListView = (GridView) findViewById(R.id.list_content);

//        mListView.setDividerHeight(0);
        mListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
//        mListView.setPullRefreshEnable(true);
//        mListView.setPullLoadEnable(true);
//        mListView.setXListViewListener(this);
//        mListView.setOnItemClickListener(this);


        setListener(tv_focus, tv_contact, tv_video, tv_photo, tv_focused, tv_fans);

        mHandler = new Handler();
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
            case R.id.tv_video:
                mType = 1;
                tv_video.setTextColor(getResources().getColor(R.color.colorYellow));
                tv_photo.setTextColor(Color.WHITE);
                tv_focused.setTextColor(Color.WHITE);
                tv_fans.setTextColor(Color.WHITE);
                break;
            case R.id.tv_photo:
                mType = 2;
                tv_video.setTextColor(Color.WHITE);
                tv_photo.setTextColor(getResources().getColor(R.color.colorYellow));
                tv_focused.setTextColor(Color.WHITE);
                tv_fans.setTextColor(Color.WHITE);
                break;
            case R.id.tv_focused:
                mType = 3;
                tv_video.setTextColor(Color.WHITE);
                tv_photo.setTextColor(Color.WHITE);
                tv_focused.setTextColor(getResources().getColor(R.color.colorYellow));
                tv_fans.setTextColor(Color.WHITE);
                break;
            case R.id.tv_fans:
                mType = 4;
                tv_video.setTextColor(Color.WHITE);
                tv_photo.setTextColor(Color.WHITE);
                tv_focused.setTextColor(Color.WHITE);
                tv_fans.setTextColor(getResources().getColor(R.color.colorYellow));
                break;
        }
    }

    /**
     * 请求数据
     */
    private void requestData() {
        for (int i = 0; i < names.length; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("name", names[i]);
            map.put("avatar", String.valueOf(avatars[i]));
            list.add(map);
        }
        View view = LayoutInflater.from(this).inflate(R.layout.item_fans, null);
        view.setLayoutParams(new AbsListView.LayoutParams((int) (mListView.getWidth() / 3) - 1, (int) (mListView.getHeight() / 2)));
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, R.layout.item_fans,
                new String[]{"name", "avatar"}, new int[]{R.id.tv_nick_name, R.id.img_avatar});
        mListView.setGravity(Gravity.CENTER);
        mListView.setAdapter(simpleAdapter);
    }

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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
