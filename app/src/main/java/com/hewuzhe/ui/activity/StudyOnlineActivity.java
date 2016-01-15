package com.hewuzhe.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.hewuzhe.R;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.adapter.MyViewPagerAdapter;
import com.hewuzhe.ui.base.TabToolBarActivity;
import com.hewuzhe.ui.fragment.StudyOnlineFourFragment;
import com.hewuzhe.ui.fragment.StudyOnlineOneFragment;
import com.hewuzhe.ui.fragment.StudyOnlineThreeFragment;
import com.hewuzhe.ui.fragment.StudyOnlineTwoFragment;

public class StudyOnlineActivity extends TabToolBarActivity {


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
    public BasePresenterImp createPresenter() {
        return null;
    }

    @Override
    protected String provideTitle() {
        return "在线学习";
    }


    /**
     * 初始化Tabs
     */
    @Override
    public void initTabs() {
        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewpager);
        MyViewPagerAdapter viewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(StudyOnlineOneFragment.newInstance(), "正向品格");//添加Fragment
        viewPagerAdapter.addFragment(StudyOnlineTwoFragment.newInstance(), "极限武术");
        viewPagerAdapter.addFragment(StudyOnlineThreeFragment.newInstance(), "教练核能");
        viewPagerAdapter.addFragment(StudyOnlineFourFragment.newInstance(), "运营管理");
        mViewPager.setAdapter(viewPagerAdapter);

        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.addTab(mTabLayout.newTab().setText("正向品格"));//给TabLayout添加Tab
        mTabLayout.addTab(mTabLayout.newTab().setText("极限武术"));
        mTabLayout.addTab(mTabLayout.newTab().setText("教练核能"));
        mTabLayout.addTab(mTabLayout.newTab().setText("运营管理"));
        mTabLayout.setupWithViewPager(mViewPager);//给TabLayout设置关联ViewPager，如果设置了ViewPager，那么ViewPagerAdapter中的getPageTitle()方法返回的就是Tab上的标题
    }
}
