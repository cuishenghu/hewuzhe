package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.hewuzhe.R;
import com.hewuzhe.model.StudyOnlineCate;
import com.hewuzhe.presenter.StudyOnlinePresenter;
import com.hewuzhe.ui.adapter.MyViewPagerAdapter;
import com.hewuzhe.ui.base.ToolBarActivity;
import com.hewuzhe.ui.fragment.StudyOnlineOneFragment;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.view.StudyOnlineView;

import java.util.ArrayList;

public class StudyOnlineActivity extends ToolBarActivity<StudyOnlinePresenter> implements StudyOnlineView {


    @Override
    protected int provideContentViewId() {
        return R.layout.activity_study_online;
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
    public StudyOnlinePresenter createPresenter() {
        return new StudyOnlinePresenter();
    }

    @Override
    protected String provideTitle() {
        return "在线学习";
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        presenter.getCates();

    }

    @Override
    public void bindData(ArrayList<StudyOnlineCate> data) {
        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewpager);
        MyViewPagerAdapter viewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabLayout);

        for (StudyOnlineCate studyOnlineCate : data) {
            viewPagerAdapter.addFragment(StudyOnlineOneFragment.newInstance(new Bun().putInt("id", studyOnlineCate.Id).ok()), studyOnlineCate.Name);//添加Fragment
            mTabLayout.addTab(mTabLayout.newTab());//给TabLayout添加Tab
        }


        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(viewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);//给TabLayout设置关联ViewPager，如果设置了ViewPager，那么ViewPagerAdapter中的getPageTitle()方法返回的就是Tab上的标题
    }
}
