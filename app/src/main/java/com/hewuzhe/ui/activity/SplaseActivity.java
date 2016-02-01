package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.hewuzhe.R;
import com.hewuzhe.presenter.SplashPresenter;
import com.hewuzhe.ui.base.BaseActivity;
import com.hewuzhe.view.SplashView;

import butterknife.Bind;

/**
 * Created by xianguangjin on 15/12/15.
 */
public class SplaseActivity extends BaseActivity<SplashPresenter> implements SplashView {

    @Bind(R.id.img_splash)
    ImageView imgSplash;

    @Override
    protected int provideContentViewId() {
        return R.layout.splash_activity;
    }

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
        setTheme(R.style.AppTheme);
        startAnim();
    }

    /**
     * 绑定Presenter
     */
    @Override
    public SplashPresenter createPresenter() {
        return new SplashPresenter();
    }

    @Override
    public void startAnim() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                presenter.navigate();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        imgSplash.startAnimation(animation);

    }

    @Override
    public void startActivity(Class clazz) {
        super.startActivity(clazz);
        finish();
    }
}
