package com.hewuzhe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hewuzhe.R;
import com.hewuzhe.model.Assess;
import com.hewuzhe.model.OrderContent;
import com.hewuzhe.model.OrderNumber;
import com.hewuzhe.ui.adapter.OrderAssessAdapter;
import com.hewuzhe.ui.base.BaseActivity2;
import com.hewuzhe.ui.http.HttpErrorHandler;
import com.hewuzhe.ui.http.HttpUtils;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.utils.Tools;
import com.hewuzhe.view.MyCommonTitle;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/25 0025.
 */
public class FeedBackActivity extends BaseActivity2 {
    private MyCommonTitle myCommonTitle;
    private EditText ed_title, ed_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        initView();
//        requestData();
    }

    /**
     * 初始化
     */
    public void initView() {
        myCommonTitle = (MyCommonTitle) findViewById(R.id.aci_mytitle);
        myCommonTitle.setTitle("留言反馈");
        myCommonTitle.setListener(null, this, null);
        myCommonTitle.setTextEdit("保存");

        ed_title = (EditText) findViewById(R.id.edt_title);//标题
        ed_content = (EditText) findViewById(R.id.edt_content);//内容
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.aci_edit_textview://提交
                if (StringUtil.isEmpty(ed_title.getText().toString().trim())) {
                    Tools.toast(this, "标题不能为空");
                    return;
                }
                if (StringUtil.isEmpty(ed_content.getText().toString().trim())) {
                    Tools.toast(this, "内容不能为空");
                    return;
                }
                RequestParams params=new RequestParams();
                params.put("",ed_title.getText().toString().trim());
                params.put("",ed_content.getText().toString().trim());

                break;
        }
    }
}
