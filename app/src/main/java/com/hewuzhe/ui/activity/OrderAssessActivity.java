package com.hewuzhe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hewuzhe.R;
import com.hewuzhe.model.Assess;
import com.hewuzhe.model.OrderContent;
import com.hewuzhe.model.OrderGroup;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Administrator on 2016/1/25 0025.
 */
public class OrderAssessActivity extends BaseActivity2 {
    private MyCommonTitle myCommonTitle;
    private EditText ed_content;
    ImageView img_chaping, img_zhongping, img_haoping;
    private OrderNumber order;
    private String comlist;
    private TextView tv_submit;
    private ListView mListView;
    private OrderAssessAdapter orderAssessAdapter;
    private Assess assess;
    boolean ping1 = false, ping2 = false, ping3 = false;
    private List<Map<String, String>> lists = new ArrayList<Map<String, String>>();
    private List<OrderContent> list;
    private String billId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        order = (OrderNumber) getIntent().getSerializableExtra("order");
        list = (ArrayList<OrderContent>) getIntent().getSerializableExtra("list");
        billId = (String) getIntent().getSerializableExtra("billId");
        setContentView(R.layout.activity_order_assess);

        initView();
//        requestData();
    }

    /**
     * 初始化
     */
    public void initView() {
        myCommonTitle = (MyCommonTitle) findViewById(R.id.aci_mytitle);
        myCommonTitle.setTitle("评价订单");
//        myCommonTitle.setListener(null, this, null);
//        myCommonTitle.setTextEdit("确定");

        mListView = (ListView) findViewById(R.id.list_order);
        tv_submit = (TextView) findViewById(R.id.tv_submit_btn);//提交

        orderAssessAdapter = new OrderAssessAdapter(OrderAssessActivity.this, list);
        mListView.setAdapter(orderAssessAdapter);
        /**
         * ListView自适应高度
         */
        StringUtil.listAutoHeight(mListView);
        setListener(tv_submit);
    }

    /**
     * 请求数据
     */
//    public void requestData() {
//        for (int i = 0; i < mListView.getChildCount(); i++) {
//            Map<String, String> map = new HashMap<String, String>();
//            LinearLayout layout = (LinearLayout) mListView.getChildAt(i);
//            EditText et = (EditText) layout.findViewById(R.id.ed_content);
//            img_chaping = (ImageView) layout.findViewById(R.id.img_chaping);
//            img_zhongping = (ImageView) layout.findViewById(R.id.img_zhongping);
//            img_haoping = (ImageView) layout.findViewById(R.id.img_haoping);
//            String content = et.getText().toString();
//            String userId = new SessionUtil(this).getUserId() + "";
//            String productId = order.getProList().get(i).getId();
//            String productPriceId = order.getProList().get(i).getProductPriceId();
//            ping1= img_chaping.isSelected();
//            ping2= img_zhongping.isSelected();
//            String type = ping1 ? "差评" : (ping2 ? "中评" : "好评");
//            map.put("userId", userId);
//            map.put("productId", productId);
//            map.put("type", type);
//            map.put("productPriceId", productPriceId);
//            map.put("content", content);
//            lists.add(map);
//
//            Gson gson = new Gson();
//            comlist = gson.toJson(lists).toString();
//        }
//    }
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_submit_btn://提交评论
                for (int i = 0; i < list.size(); i++) {
                    Map<String, String> map = new HashMap<String, String>();
                    LinearLayout layout = (LinearLayout) mListView.getChildAt(i);
                    EditText et = (EditText) layout.findViewById(R.id.ed_content);
                    img_chaping = (ImageView) layout.findViewById(R.id.img_chaping);
                    img_zhongping = (ImageView) layout.findViewById(R.id.img_zhongping);
                    img_haoping = (ImageView) layout.findViewById(R.id.img_haoping);
                    String content = et.getText().toString();
                    String userId = new SessionUtil(OrderAssessActivity.this).getUserId() + "";
                    String productId = list.get(i).getProductId();
                    String productPriceId = list.get(i).getProductPriceId();
                    ping1 = img_chaping.isSelected();
                    ping2 = img_zhongping.isSelected();
                    String type = ping1 ? "0" : (ping2 ? "1" : "2");
                    map.put("userId", userId);
                    map.put("productId", productId);
                    map.put("typeId", type);
                    map.put("productPriceId", productPriceId);
                    map.put("content", content);
                    lists.add(map);

                    Gson gson = new Gson();
                    comlist = gson.toJson(lists).toString();
                }
                RequestParams params = new RequestParams();
                params.put("comlist", comlist);
                params.put("billid", billId);
                HttpUtils.submitCommentOrder(new HttpErrorHandler() {
                    @Override
                    public void onRecevieSuccess(com.alibaba.fastjson.JSONObject json) {
                        Tools.toast(OrderAssessActivity.this, "提交评论成功");
                        startActivity(new Intent(OrderAssessActivity.this, OrderCenterActivity2.class).putExtra("mType", 4));
                        OrderCenterActivity2.OrderCenterActivity2.finish();
                        finish();
                    }
                }, params);
                break;
        }
    }
}
