package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.model.SortModel;
import com.hewuzhe.ui.adapter.SortAdapter;
import com.hewuzhe.ui.base.BaseActivity2;
import com.hewuzhe.ui.widget.CharacterParser;
import com.hewuzhe.ui.widget.PinyinComparator;
import com.hewuzhe.view.LoadingView;
import com.hewuzhe.view.MyCommonTitle;
import com.hewuzhe.view.SideBar;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/4 0004.
 */
public class TongXunLuActivity extends BaseActivity2 {

    private MyCommonTitle myCommonTitle;
    private LinearLayout ll_resultdata;
    private List list_contacts;
    private SideBar sideBar;
    private LoadingView loadingView;
    private TextView dialog;
    private SortAdapter adapter;
    private Map<String, String> callRecords;
    private StringBuffer phones = new StringBuffer();
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<SortModel> SourceDateList;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tongxunlu);

        initView();
        requestData();
    }

    private void initView() {
        myCommonTitle = (MyCommonTitle) findViewById(R.id.aci_mytitle);
        myCommonTitle.setTitle("通讯录");

        ll_resultdata = (LinearLayout) findViewById(R.id.ll_resultdata);
        list_contacts = (List) findViewById(R.id.list_country);
        sideBar = (SideBar) findViewById(R.id.sidebar);
        loadingView = (LoadingView) findViewById(R.id.loading_view);
    }

    private void requestData() {

    }
}
