package com.hewuzhe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.hewuzhe.R;
import com.hewuzhe.model.Friend;
import com.hewuzhe.ui.adapter.RecommendFriendAdapter;
import com.hewuzhe.ui.base.BaseActivity2;
import com.hewuzhe.view.MyCommonTitle;


public class AddWarriorsActivity extends BaseActivity2 {
    private MyCommonTitle myCommonTitle;
    private RecyclerView mRecyclerView;
    private LinearLayout lay_tongxunlu;
    private LinearLayout lay_select_condition;
    private LinearLayout lay_select_near;
    private EditText _EdtSearchContent;
    private Friend _item;
    private boolean isFirstRun = true;
    private View header;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_warriors);

        initView();
    }

    private void initView() {
        myCommonTitle = (MyCommonTitle) findViewById(R.id.aci_mytitle);
        myCommonTitle.setTitle("添加武友");
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //2代表The number of columns in the grid 列数
        final GridLayoutManager manager = new GridLayoutManager(AddWarriorsActivity.this, 2);
        mRecyclerView.setLayoutManager(manager);

        View topView = LayoutInflater.from(AddWarriorsActivity.this).inflate(R.layout.header_add_warriors, null);


        lay_tongxunlu = (LinearLayout) topView.findViewById(R.id.lay_tongxunlu);
        lay_select_condition = (LinearLayout) topView.findViewById(R.id.ll_select_by_condition);
        lay_select_near = (LinearLayout) topView.findViewById(R.id.lay_select_near_person);
        _EdtSearchContent = (EditText) topView.findViewById(R.id.edt_search_content);
        final RecommendFriendAdapter recommendFriendAdapter= new RecommendFriendAdapter(topView, 10);
        mRecyclerView.setAdapter(recommendFriendAdapter);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return recommendFriendAdapter.isHeader(position) ? manager.getSpanCount() : 1;

            }
        });

        setListener(lay_tongxunlu,lay_select_condition,lay_select_near);

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.lay_tongxunlu://通讯录
                startActivity(new Intent(AddWarriorsActivity.this,TongXunLuActivity.class));
                break;
            case R.id.ll_select_by_condition://按条件查找陌生人
                startActivity(new Intent(AddWarriorsActivity.this,MakeWarriorsActivity.class));
                break;
            case R.id.lay_select_near_person://查看附近人
                startActivity(new Intent(AddWarriorsActivity.this,MakeWarriorsActivity.class));
                break;
        }
    }
}

