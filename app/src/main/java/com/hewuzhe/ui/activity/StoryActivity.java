package com.hewuzhe.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.hewuzhe.R;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.adapter.MyViewPagerAdapter;
import com.hewuzhe.ui.base.TabToolBarActivity;
import com.hewuzhe.ui.fragment.FederalConditionFragment;
import com.hewuzhe.utils.Bun;

public class StoryActivity extends TabToolBarActivity {


    @Override
    protected int provideContentViewId() {
        return R.layout.activity_story;
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
        return "江湖故事";
    }

    /**
     * 初始化Tabs
     */
    @Override
    public void initTabs() {

        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewpager);
        MyViewPagerAdapter viewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(FederalConditionFragment.newInstance(new Bun().putInt("catid", 72).ok()), "公益组织");//添加Fragment
        viewPagerAdapter.addFragment(FederalConditionFragment.newInstance(new Bun().putInt("catid", 73).ok()), "慈善活动");
        viewPagerAdapter.addFragment(FederalConditionFragment.newInstance(new Bun().putInt("catid", 74).ok()), "交流培训");
        viewPagerAdapter.addFragment(FederalConditionFragment.newInstance(new Bun().putInt("catid", 75).ok()), "全国巡演");
        mViewPager.setAdapter(viewPagerAdapter);

        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.addTab(mTabLayout.newTab().setText("公益组织"));//给TabLayout添加Tab
        mTabLayout.addTab(mTabLayout.newTab().setText("慈善活动"));
        mTabLayout.addTab(mTabLayout.newTab().setText("交流培训"));
        mTabLayout.addTab(mTabLayout.newTab().setText("全国巡演"));
        mTabLayout.setupWithViewPager(mViewPager);//给TabLayout设置关联ViewPager，如果设置了ViewPager，那么ViewPagerAdapter中的getPageTitle()方法返回的就是Tab上的标题

    }




}
