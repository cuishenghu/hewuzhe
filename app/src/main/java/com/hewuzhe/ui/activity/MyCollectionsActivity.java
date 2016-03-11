package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.hewuzhe.R;
import com.hewuzhe.model.Product;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.adapter.MyViewPagerAdapter;
import com.hewuzhe.ui.base.TabToolBarActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.fragment.ArticleCollectionsFragment;
import com.hewuzhe.ui.fragment.ProductCollectionsFragment;
import com.hewuzhe.ui.fragment.VideoCollectionsFragment;
import com.hewuzhe.ui.inter.OnReceiveListener;
import com.hewuzhe.utils.NU;

public class MyCollectionsActivity extends TabToolBarActivity {


    private ViewPager mViewPager;
    private MyViewPagerAdapter viewPagerAdapter;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_my_collections;
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
        tvAction.setText("删除");
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
        return "精品收藏";
    }

    @Override
    public boolean canAction() {
        return true;
    }


    @Override
    protected void action() {
        super.action();

        if (!NU.isNull(mViewPager)) {
            OnReceiveListener<Integer, Boolean> onReceiveListener = (OnReceiveListener) viewPagerAdapter.getItem(mViewPager.getCurrentItem());
            if (onReceiveListener.getMsg()) {
                tvAction.setText("删除");
                /**
                 * 执行删除操作
                 * */
                onReceiveListener.onReceive(C.MSG_ONE);
            } else {
                tvAction.setText("确定");
                onReceiveListener.onReceive(C.MSG_DEFAUT);
            }
        }
    }

    /**
     * 初始化Tabs
     */
    @Override

    public void initTabs() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(VideoCollectionsFragment.newInstance(), "视频");//添加Fragment
        viewPagerAdapter.addFragment(ProductCollectionsFragment.newInstance(), "商品");//添加Fragment
        viewPagerAdapter.addFragment(ArticleCollectionsFragment.newInstance(), "文章");
        mViewPager.setAdapter(viewPagerAdapter);

        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.addTab(mTabLayout.newTab());//给TabLayout添加Tab
        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.setupWithViewPager(mViewPager);//给TabLayout设置关联ViewPager，如果设置了ViewPager，那么ViewPagerAdapter中的getPageTitle()方法返回的就是Tab上的标题

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                if (!NU.isNull(mViewPager)) {
                    OnReceiveListener<Integer, Boolean> onReceiveListener = (OnReceiveListener) viewPagerAdapter.getItem(position);

                    if (onReceiveListener.getMsg()) {
                        tvAction.setText("确定");
                    } else {
                        tvAction.setText("删除");
                    }

                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
