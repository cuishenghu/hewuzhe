package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.ui.base.BaseActivity2;
import com.hewuzhe.view.MyCommonTitle;
import com.loopj.android.http.RequestParams;

/**
 * Created by Administrator on 2016/1/26 0026.
 */
public class MyScoretDuiHuanCheckLogticsActivity extends BaseActivity2 {

    private MyCommonTitle myCommonTitle;
    private ListView list_logtics_info;
    private TextView tv_logticd_name, tv_logtics_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_logtics);

        initView();
//        requestData();
//        initData();
    }

    /**
     * 初始化
     */
    public void initView() {
        myCommonTitle = (MyCommonTitle) findViewById(R.id.aci_mytitle);
        myCommonTitle.setTitle("物流详情");
        tv_logticd_name = (TextView) findViewById(R.id.tv_logtics_name);//物流名称
        tv_logtics_no = (TextView) findViewById(R.id.tv_logtics_no);//快递单号
        list_logtics_info = (ListView) findViewById(R.id.list_logtics);//listView

    }

    /**
     * 请求数据
     */
    private void requestData() {
        RequestParams params = new RequestParams();
        params.put("search", "");//搜索内容
        params.put("categoryId", "0");//类别ID 不按照类别搜索传0
        params.put("isPriceAsc", "0");//是否价格升序 0：默认 1：升序 2：降序
        params.put("isSalesAsc", "0");// 是否销量升序 0：默认 1：升序 2：降序
        params.put("isCommentAsc", "0");//是否好评升序 0：默认 1：升序 2：降序
        params.put("isCredit", "1");//是否可以兑换 0：购买 1：兑换
        params.put("isNewAsc", "0");//是否最新升序 0：默认 1：升序 2：降序
        params.put("isRecommend", "0");//是否推荐 0：默认 1：推荐商品 2：不推荐
//        HttpUtils.getProductForDuihuanList(res_getProductForDuihuanList, params);
    }


    public void initData() {

//        productAdapter = new DuiHuanProductAdapter(MyScoretDuiHuanCheckLogticsActivity.this, products);
//        mListView.setAdapter(productAdapter);

    }

}
