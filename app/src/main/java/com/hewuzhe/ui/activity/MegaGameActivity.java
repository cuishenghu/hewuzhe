package com.hewuzhe.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.hewuzhe.R;
import com.hewuzhe.model.MatchCategory;
import com.hewuzhe.presenter.MegaGamesPresenter;
import com.hewuzhe.ui.adapter.MyViewPagerAdapter;
import com.hewuzhe.ui.base.TabToolBarActivity;
import com.hewuzhe.ui.fragment.MegaGameFragment;
import com.hewuzhe.utils.Bun;

import java.util.ArrayList;

public class MegaGameActivity extends TabToolBarActivity<MegaGamesPresenter> {
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
    public MegaGamesPresenter createPresenter() {
        return new MegaGamesPresenter(this);
    }


    @Override
    protected String provideTitle() {
        return "赛事";
    }


    public void getData(ArrayList<MatchCategory> data){
        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewpager);
        MyViewPagerAdapter viewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(MegaGameFragment.newInstance(new Bun().putString("path",data.get(0).Id+"").ok()), data.get(0).Name);//添加Fragment
        viewPagerAdapter.addFragment(MegaGameFragment.newInstance(new Bun().putString("path", data.get(1).Id+"").ok()),data.get(1).Name);
        viewPagerAdapter.addFragment(MegaGameFragment.newInstance(new Bun().putString("path", data.get(2).Id+"").ok()),data.get(2).Name);
        viewPagerAdapter.addFragment(MegaGameFragment.newInstance(new Bun().putString("path", data.get(3).Id+"").ok()),data.get(3).Name);
        mViewPager.setAdapter(viewPagerAdapter);

        mViewPager.setOffscreenPageLimit(5);

        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.addTab(mTabLayout.newTab().setText(data.get(0).Name));//给TabLayout添加Tab
        mTabLayout.addTab(mTabLayout.newTab().setText(data.get(1).Name));//给TabLayout添加Tab
        mTabLayout.addTab(mTabLayout.newTab().setText(data.get(2).Name));//给TabLayout添加Tab
        mTabLayout.addTab(mTabLayout.newTab().setText(data.get(3).Name));//给TabLayout添加Tab
        mTabLayout.setupWithViewPager(mViewPager);//给TabLayout设置关联ViewPager，如果设置了ViewPager，那么ViewPagerAdapter中的getPageTitle()方法返回的就是Tab上的标题

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                PAGE = position;
                presenter.DeleteNoReadMatch();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    /**
     * 初始化Tabs
     */
    @Override
    public void initTabs() {
        presenter.SelectMatchCategory();

    }
}
