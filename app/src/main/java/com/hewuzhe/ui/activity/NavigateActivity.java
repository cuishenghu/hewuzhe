package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.hewuzhe.R;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.adapter.MyViewPagerAdapter;
import com.hewuzhe.ui.base.BaseActivity;
import com.hewuzhe.ui.fragment.NavigateOneFragment;
import com.hewuzhe.ui.fragment.NavigateThreeFragment;
import com.hewuzhe.ui.fragment.NavigateTwoFragment;
import com.hewuzhe.utils.SPUtil;
import com.hewuzhe.utils.SessionUtil;

import butterknife.Bind;

public class NavigateActivity extends BaseActivity implements View.OnTouchListener, GestureDetector.OnGestureListener {

    @Bind(R.id.viewpager)
    ViewPager viewpager;
    private GestureDetector gestureDetector;

    /**
     * @return 提供LayoutId
     */
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_navigate;
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {


    }

    /**
     * @param savedInstanceState 缓存数据
     *                           <p/>
     *                           初始化一些事情
     */
    @Override
    protected void initThings(Bundle savedInstanceState) {

        SPUtil spUtil = new SPUtil(getContext())
                .open("settings");
        spUtil.putBoolean("msg", true);
        spUtil.putBoolean("sound", true);
        spUtil.putBoolean("vibrate", true);

        MyViewPagerAdapter viewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(NavigateOneFragment.newInstance(), "");
        viewPagerAdapter.addFragment(NavigateTwoFragment.newInstance(), "");
        viewPagerAdapter.addFragment(NavigateThreeFragment.newInstance(), "");
        viewpager.setAdapter(viewPagerAdapter);

        viewpager.setOnTouchListener(this);
        gestureDetector = new GestureDetector(this);

    }

    /**
     * 绑定Presenter
     */
    @Override
    public BasePresenterImp createPresenter() {
        return null;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }


    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float v, float v1) {
        if (e1.getX() - e2.getX() > 120) {
            if (viewpager.getCurrentItem() == 2) {

                /**
                 * 更新isNotFirst*/

                new SPUtil(getContext()).open("user").putBoolean("isNotFirst", true);

                /**
                 * 判断进入登录还是首页*/
//                if (new SessionUtil(getContext()).isLogin()) {
                    startActivity(MainActivity.class);
//                } else {
//                    startActivity(SignInActivity.class);
//                }

            }
        }

        return false;
    }


    @Override
    public void startActivity(Class clazz) {
        super.startActivity(clazz);
        finish();
    }
}
