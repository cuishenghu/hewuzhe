package com.hewuzhe.ui.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hewuzhe.R;
import com.hewuzhe.model.SortModel;
import com.hewuzhe.ui.adapter.SortAdapter;
import com.hewuzhe.ui.base.BaseActivity2;
import com.hewuzhe.ui.http.HttpErrorHandler;
import com.hewuzhe.ui.http.HttpUtils;
import com.hewuzhe.ui.http.UrlContants;
import com.hewuzhe.ui.widget.CharacterParser;
import com.hewuzhe.view.PinyinComparator;
import com.hewuzhe.utils.ConstactUtil;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.Tools;
import com.hewuzhe.view.LoadingView;
import com.hewuzhe.view.MyCommonTitle;
import com.hewuzhe.view.SideBar;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/4 0004.
 */
public class TongXunLuActivity extends BaseActivity2 {

    private MyCommonTitle myCommonTitle;
    private LinearLayout ll_resultdata;
    private ListView list_contacts;
    private SideBar sideBar;
    private LoadingView loadingView;
    private TextView tv_dialog;
    private SortAdapter sortAdapter;
    private Map<String, String> callRecords;
    private StringBuffer phones = new StringBuffer();
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<SortModel> SourceDataList;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tongxunlu);

        initView();
        initData();
        requestData();
    }

    private void initView() {
        myCommonTitle = (MyCommonTitle) findViewById(R.id.aci_mytitle);
        myCommonTitle.setTitle("通讯录");

        ll_resultdata = (LinearLayout) findViewById(R.id.ll_resultdata);
        list_contacts = (ListView) findViewById(R.id.list_contacts);
        tv_dialog = (TextView) findViewById(R.id.tv_dialog);
        sideBar = (SideBar) findViewById(R.id.sidebar);
        loadingView = (LoadingView) findViewById(R.id.loading_view);

        list_contacts.setDivider(new ColorDrawable(0xffeeeeee));
        list_contacts.setDividerHeight(1);
    }

    private void initData() {
        // 实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();

        sideBar.setTextView(tv_dialog);
        /**
         * 设置右侧触摸监听事件
         */
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = sortAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    list_contacts.setSelection(position);
                }
            }
        });

        list_contacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 这里要利用adapter.getItem(position)来获取当前position所对应的对象
                String name = callRecords.get(((SortModel) sortAdapter.getItem(position)).getName());
                Tools.toast(TongXunLuActivity.this, name);
            }
        });
        new AsyncTaskContast().execute();
    }

    private class AsyncTaskContast extends AsyncTask<Integer, Integer, Integer> {
        @Override
        protected Integer doInBackground(Integer... params) {
            int result = -1;
            callRecords = ConstactUtil.getAllCallRecords(TongXunLuActivity.this);
            result = 1;
            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (result == 1) {
                ll_resultdata.setVisibility(View.VISIBLE);
                loadingView.setVisibility(View.GONE);
                final List<String> contact = new ArrayList<String>();
                for (Iterator<String> keys = callRecords.keySet().iterator(); keys.hasNext(); ) {
                    String key = keys.next();
                    phones.append(callRecords.get(key) + ",");
                }
                if (phones.length() > 0) {
                    /**
                     * 调匹配手机通讯录接口
                     */
                    RequestParams params = new RequestParams();
                    params.put("", new SessionUtil(TongXunLuActivity.this).getUser().Id);
                    params.put("", phones.substring(0, phones.length() - 1));
                    HttpUtils.getAddressByAreaId(new HttpErrorHandler() {//===修改===修改=修改==修改==修改==修改==修改===修改==修改===修改
                        @Override
                        public void onRecevieSuccess(JSONObject json) {
                            JSONArray jsonArray = json.getJSONObject(UrlContants.jsonData).getJSONArray("list");
                            String[] names = new String[]{};
                            names = contact.toArray(names);
                            SourceDataList = filledData(names, jsonArray);
                            // 根据a-z进行排序源数据
                            Collections.sort(SourceDataList, pinyinComparator);

                            sortAdapter = new SortAdapter(TongXunLuActivity.this, SourceDataList);
                            list_contacts.setAdapter(sortAdapter);
                        }
                    }, params);
                }
            } else {
                loadingView.setText("网络异常");
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ll_resultdata.setVisibility(View.GONE);
            loadingView.setVisibility(View.VISIBLE);
        }
    }

    private List<SortModel> filledData(String[] data, JSONArray jsonArray) {
        List<SortModel> mSorList = new ArrayList<SortModel>();
        for (int i = 0; i < data.length; i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(data[i]);
            sortModel.setId(jsonArray.getJSONObject(i).getString("id"));
            sortModel.setPhone(jsonArray.getJSONObject(i).getString("mob"));
            sortModel.setState(jsonArray.getJSONObject(i).getString("state"));
            //汉子转拼音
            String pinyin = characterParser.getSelling(data[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase(Locale.getDefault());

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]]")) {
                sortModel.setSortLetters(sortString.toUpperCase(Locale.getDefault()));
            } else {
                sortModel.setSortLetters("#");
            }
            mSorList.add(sortModel);
        }
        return mSorList;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.img_user_avatar:

                break;
//            case R.id.tv_add_friend:
//
//                break;
        }
    }

    private void requestData() {

    }
}
