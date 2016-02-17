package com.hewuzhe.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.presenter.base.BasePresenterImp;

/**
 * Created by xianguangjin on 15/12/8.
 */
public abstract class ToolBarActivity<P extends BasePresenterImp> extends BaseActivity<P> {

    protected Toolbar toolBar;
    protected ImageView imgBack;
    protected TextView tvTitle;
    protected
    @Nullable
    AppBarLayout appBar;
    protected boolean mIsHidden = false;
    protected
    @Nullable
    TextView tvAction;
    protected
    @Nullable
    ImageView imgAction;


    /**
     * @return 提供标题
     */
    abstract protected CharSequence provideTitle();

    /**
     * @param savedInstanceState 缓存数据
     *                           <p/>
     *                           初始化一些事情
     */
    @Override
    protected void initThings(Bundle savedInstanceState) {
        initToolBar();
    }

    private void initToolBar() {
        toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        imgBack = (ImageView) findViewById(R.id.img_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvAction = (TextView) findViewById(R.id.tv_action);
        imgAction = (ImageView) findViewById(R.id.img_action);
        appBar = (AppBarLayout) findViewById(R.id.app_bar_layout);

        if (canBack()) {
            imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        } else {
            imgBack.setVisibility(View.GONE);
        }


        if (canAction()) {
            if (tvAction != null) {
                tvAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        action();
                    }
                });
            }
            if (imgAction != null) {
                imgAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        action();
                    }
                });
            }
        } else {
            if (tvAction != null) {

                tvAction.setVisibility(View.GONE);
            }

            if (imgAction != null) {

                imgAction.setVisibility(View.GONE);
            }


        }


        tvTitle.setText(provideTitle());

    }

    /**
     * Toolbar右边按钮的点击事件
     */
    protected void action() {

    }


    /**
     * @param alpha 设置标题栏的透明度
     */
    protected void setAppBarAlpha(float alpha) {
        if (appBar != null) {
            appBar.setAlpha(alpha);
        }

    }


    /**
     * 隐藏和显示Toolbar
     */
    protected void hideOrShowToolbar() {
        if (appBar != null) {
            appBar.animate()
                    .translationY(mIsHidden ? 0 : -appBar.getHeight())
                    .setInterpolator(new DecelerateInterpolator(2))
                    .start();

            mIsHidden = !mIsHidden;
        }

    }

    /**
     * @return 返回按钮是否可以显示
     */
    public boolean canBack() {
        return true;
    }


    /**
     * @return 右边按钮是否显示
     */
    public boolean canAction() {

        return false;
    }

}
