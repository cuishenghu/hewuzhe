package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hewuzhe.R;
import com.hewuzhe.model.Pic;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.adapter.MyViewPagerAdapter;
import com.hewuzhe.ui.base.ToolBarActivity;
import com.hewuzhe.ui.fragment.PicsFragment;
import com.hewuzhe.utils.Bun;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class PicsActivity extends ToolBarActivity {


    private int pos;
    private ArrayList<Pic> pics;
    private String picsStr;

    public static boolean isLocal = false;


    @Bind(R.id.viewpager)
    ViewPager _ViewPage;
    private int picsCount;
    private boolean isHidePage = false;

    /**
     * @return 提供标题
     */
    @Override
    protected String provideTitle() {
        return "图片";
    }

    /**
     * @return 提供LayoutId
     */
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_pics;
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        pos = getIntentData().getInt("pos");
        picsStr = getIntentData().getString("pics");
        isHidePage = getIntentData().getBoolean("isHidePage");

        tvAction.setVisibility(isHidePage ? View.GONE : View.VISIBLE);

        pics = new Gson().fromJson(picsStr, new TypeToken<List<Pic>>() {
        }.getType());

        picsCount = pics.size();

        tvAction.setText("1/" + picsCount);

        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager());

        for (Pic pic : pics) {
            adapter.addFragment(PicsFragment.newInstance(new Bun().putString("picUrl", pic.PictureUrl).putString("imagePath", pic.ImagePath).ok()));
        }
        _ViewPage.setAdapter(adapter);

        _ViewPage.setCurrentItem(pos);

        toolBar.setBackgroundResource(R.color.transparent);

        _ViewPage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                position++;
                tvAction.setText(position + "/" + picsCount);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {

    }


    /**
     * 绑定Presenter
     */
    @Override
    public BasePresenterImp createPresenter() {
        return null;
    }
}
