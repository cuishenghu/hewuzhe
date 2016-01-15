package com.hewuzhe.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.hewuzhe.R;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.adapter.MyViewPagerAdapter;
import com.hewuzhe.ui.base.TabToolBarActivity;
import com.hewuzhe.ui.fragment.MegaGameFragment;
import com.hewuzhe.utils.Bun;

public class MegaGameActivity extends TabToolBarActivity {
    public static int PAGE = 0;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_megagame;
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
        return "武者大赛";
    }


    /**
     * 初始化Tabs
     */
    @Override
    public void initTabs() {
        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewpager);
        MyViewPagerAdapter viewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(MegaGameFragment.newInstance(new Bun().putString("path", "SelectMatchPageByPerson").ok()), "武者大赛");//添加Fragment
        viewPagerAdapter.addFragment(MegaGameFragment.newInstance(new Bun().putString("path", "SelectMatchPageByTeam").ok()), "战队赛事");
        mViewPager.setAdapter(viewPagerAdapter);

        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.addTab(mTabLayout.newTab().setText("武者大赛"));//给TabLayout添加Tab
        mTabLayout.addTab(mTabLayout.newTab().setText("战队赛事"));
        mTabLayout.setupWithViewPager(mViewPager);//给TabLayout设置关联ViewPager，如果设置了ViewPager，那么ViewPagerAdapter中的getPageTitle()方法返回的就是Tab上的标题

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                PAGE = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
