package com.hewuzhe.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.hewuzhe.R;
import com.hewuzhe.model.TrainerLesson;
import com.hewuzhe.model.TrainerLessonSigner;
import com.hewuzhe.ui.adapter.TrainerSignListAdapter;
import com.hewuzhe.ui.base.BaseActivity2;
import com.hewuzhe.ui.http.EntityHandler;
import com.hewuzhe.ui.http.HttpUtils;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.MyCommonTitle;
import com.hewuzhe.view.XListView;
import com.hewuzhe.view.XListView.IXListViewListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/26 0026.
 */
public class MySignLessonListActivity extends BaseActivity2 implements IXListViewListener, OnItemClickListener {

    private MyCommonTitle myCommonTitle;
    private XListView mListView;
    private TrainerSignListAdapter trainerSignListAdapter;
    private List<TrainerLessonSigner> trainerLessonSigners = new ArrayList<TrainerLessonSigner>();
    private int pageNo = 0;//页码
    private int pageSum = 10;//每页条数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sign_lesson_list);

        initView();
        requestData();
        initData();
    }

    /**
     * 初始化
     */
    public void initView() {
        myCommonTitle = (MyCommonTitle) findViewById(R.id.aci_mytitle);
        myCommonTitle.setTitle("报名记录");

        mListView = (XListView) findViewById(R.id.list_sign_lesson);//listView
        mListView.setDividerHeight(3);
        mListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
        mListView.setXListViewListener(this);
        mListView.setOnItemClickListener(this);
    }

    /**
     * 请求数据
     */
    private void requestData() {
        RequestParams params = new RequestParams();
        params.put("startRowIndex", pageNo * pageSum);// 开始行索引
        params.put("maximumRows", pageSum);//每页条数
        params.put("userid", new SessionUtil(MySignLessonListActivity.this).getUserId());//兑换状态
        HttpUtils.selectJoinRecord(res_selectJoinRecord, params);
    }

    AsyncHttpResponseHandler res_selectJoinRecord = new EntityHandler<TrainerLessonSigner>(TrainerLessonSigner.class) {
        @Override
        public void onReadSuccess(List<TrainerLessonSigner> list) {
            if (pageNo == 0) {
                trainerLessonSigners.clear();
            }
            trainerLessonSigners.addAll(list);
            trainerSignListAdapter.notifyDataSetChanged();
        }
    };

    public void initData() {
        trainerSignListAdapter = new TrainerSignListAdapter(MySignLessonListActivity.this, trainerLessonSigners);
        mListView.setAdapter(trainerSignListAdapter);

    }

    /**
     * 上拉刷新
     */
    @Override
    public void onRefresh() {
        pageNo = 0;
        requestData();
        onLoad();
    }

    /**
     * 下拉加载
     */
    @Override
    public void onLoadMore() {
        pageNo += 1;
        requestData();
        onLoad();

    }

    /**
     * 停止刷新/加载
     */
    public void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime("刚刚");
    }

    /**
     * 点击进入详情
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(0, trainerLessonSigners.get(position).getLesson().getId() + "");
        arrayList.add(1, trainerLessonSigners.get(position).getTeacher().getNicName() + "");
        arrayList.add(2, trainerLessonSigners.get(position).getTeacher().getPhone() + "");
        arrayList.add(3, trainerLessonSigners.get(position).getTeacher().getPhotoPath() + "");
        startActivity(new Intent(MySignLessonListActivity.this, TrainerLessonActivity.class).putStringArrayListExtra("data", arrayList));
    }
}
