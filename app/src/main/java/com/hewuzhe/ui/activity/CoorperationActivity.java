package com.hewuzhe.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.hewuzhe.R;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.adapter.MyViewPagerAdapter;
import com.hewuzhe.ui.base.TabToolBarActivity;
import com.hewuzhe.ui.fragment.FederalConditionFragment;
import com.hewuzhe.utils.Bun;

public class CoorperationActivity extends TabToolBarActivity {


    @Override
    protected int provideContentViewId() {
        return R.layout.activity_coorperation;
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
        return "招聘合作";
    }


    /**
     * 初始化Tabs
     */
    @Override
    public void initTabs() {
        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewpager);
        MyViewPagerAdapter viewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(FederalConditionFragment.newInstance(new Bun().putInt("catid", 76).ok()), "招聘");//添加Fragment
        viewPagerAdapter.addFragment(FederalConditionFragment.newInstance(new Bun().putInt("catid", 77).ok()), "合作");
        mViewPager.setAdapter(viewPagerAdapter);

        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.addTab(mTabLayout.newTab().setText("招聘"));//给TabLayout添加Tab
        mTabLayout.addTab(mTabLayout.newTab().setText("合作"));
        mTabLayout.setupWithViewPager(mViewPager);//给TabLayout设置关联ViewPager，如果设置了ViewPager，那么ViewPagerAdapter中的getPageTitle()方法返回的就是Tab上的标题

    }




}
