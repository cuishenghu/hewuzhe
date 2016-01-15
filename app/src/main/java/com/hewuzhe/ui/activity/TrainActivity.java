package com.hewuzhe.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.adapter.MyViewPagerAdapter;
import com.hewuzhe.ui.base.TabToolBarActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.fragment.PlanFragment;
import com.hewuzhe.ui.inter.OnReceiveListener;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.NU;

import cn.xm.weidongjian.popuphelper.PopupWindowHelper;

public class TrainActivity extends TabToolBarActivity {


    private TextView action_1;
    private TextView action_2;
    private ViewPager mViewPager;
    private MyViewPagerAdapter viewPagerAdapter;
    private PopupWindowHelper popupWindowHelper = null;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_train;
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
        return "训练计划";
    }


    @Override
    public boolean canAction() {
        return true;
    }

    @Override
    protected void action() {
        super.action();

        if (popupWindowHelper == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.popview, null);
            popupWindowHelper = new PopupWindowHelper(view);
            action_1 = (TextView) view.findViewById(R.id.tv_local_video);
            action_2 = (TextView) view.findViewById(R.id.tv_take_video);
            action_1.setText("发布");
            action_2.setText("编辑");

            action_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(PublishPlanActivity.class);
                    popupWindowHelper.dismiss();
                }
            });

            action_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!NU.isNull(mViewPager)) {
                        OnReceiveListener<Integer, Boolean> onReceiveListener = (OnReceiveListener) viewPagerAdapter.getItem(mViewPager.getCurrentItem());
                        if (onReceiveListener.getMsg()) {
                            action_2.setText("编辑");
                            /**
                             * 执行删除操作
                             * */
                            onReceiveListener.onReceive(C.MSG_ONE);
                            popupWindowHelper.dismiss();
                        } else {
                            action_2.setText("删除");
                            onReceiveListener.onReceive(C.MSG_DEFAUT);
                        }
                    }

                }
            });
        }

        popupWindowHelper.showAsDropDown(imgAction);
    }

    /**
     * 初始化Tabs
     */
    @Override
    public void initTabs() {

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(PlanFragment.newInstance(new Bun().putInt("id", 51).ok()), "周计划");
        viewPagerAdapter.addFragment(PlanFragment.newInstance(new Bun().putInt("id", 50).ok()), "月计划");
        viewPagerAdapter.addFragment(PlanFragment.newInstance(new Bun().putInt("id", 49).ok()), "季计划");
        viewPagerAdapter.addFragment(PlanFragment.newInstance(new Bun().putInt("id", 48).ok()), "年计划");//添加Fragment
        mViewPager.setAdapter(viewPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);

        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.addTab(mTabLayout.newTab());//给TabLayout添加Tab
        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.setupWithViewPager(mViewPager);//给TabLayout设置关联ViewPager，如果设置了ViewPager，那么ViewPagerAdapter中的getPageTitle()方法返回的就是Tab上的标题

    }
}
