package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.hewuzhe.R;
import com.hewuzhe.model.Cate;
import com.hewuzhe.presenter.FederalConditionPresenter;
import com.hewuzhe.ui.adapter.MyViewPagerAdapter;
import com.hewuzhe.ui.base.ToolBarActivity;
import com.hewuzhe.ui.fragment.FederalConditionFragment;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.view.FederalConditionView;

import java.util.ArrayList;

public class CoorperationActivity extends ToolBarActivity<FederalConditionPresenter> implements FederalConditionView {


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


    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);

        presenter.getCates("GetCateForHezuo");

    }


    /**
     * 绑定Presenter
     */
    @Override
    public FederalConditionPresenter createPresenter() {
        return new FederalConditionPresenter();
    }

    @Override
    protected String provideTitle() {
        return "招聘合作";
    }


    @Override
    public void bindData(ArrayList<Cate> data) {
        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewpager);
        MyViewPagerAdapter viewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabLayout);

        for (Cate cate : data) {
            viewPagerAdapter.addFragment(FederalConditionFragment.newInstance(new Bun().putInt("catid", cate.Id).ok()), cate.Name);//添加Fragment
            mTabLayout.addTab(mTabLayout.newTab());//给TabLayout添加Tab
        }


        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(viewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);//给TabLayout设置关联ViewPager，如果设置了ViewPager，那么ViewPagerAdapter中的getPageTitle()方法返回的就是Tab上的标题
    }




}
