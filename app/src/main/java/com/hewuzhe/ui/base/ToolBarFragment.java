package com.hewuzhe.ui.base;

import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.activity.MainActivity;

/**
 * Created by xianguangjin on 15/12/8.
 */
public abstract class ToolBarFragment<P extends BasePresenterImp> extends BaseFragment<P> {

    protected Toolbar toolBar;
    private ImageView imgBack;
    private TextView tvTitle;
    private AppBarLayout appBar;
    private boolean mIsHidden = false;
    private TextView tvAction;

    abstract protected String provideTitle();

    @Override
    protected void initThings(View view) {
        initToolBar(view);
    }

    public void initToolBar(View rootView) {
        toolBar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((MainActivity) getActivity()).setSupportActionBar(toolBar);
        imgBack = (ImageView) rootView.findViewById(R.id.img_back);
        tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        tvAction = (TextView) rootView.findViewById(R.id.tv_action);

        appBar = (AppBarLayout) rootView.findViewById(R.id.app_bar_layout);

        if (canBack()) {
//            imgBack.setOnClickListener(v -> );
        } else {
            imgBack.setVisibility(View.GONE);
        }

        if (canTvAction()) {
            tvAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tvAction();
                }
            });
        } else {
            tvAction.setVisibility(View.GONE);
        }
        tvTitle.setText(provideTitle());
    }

    public boolean canTvAction() {
        return false;
    }

    protected void tvAction() {

    }

    protected void setAppBarAlpha(float alpha) {
        appBar.setAlpha(alpha);
    }


    protected void hideOrShowToolbar() {
        appBar.animate()
                .translationY(mIsHidden ? 0 : -appBar.getHeight())
                .setInterpolator(new DecelerateInterpolator(2))
                .start();

        mIsHidden = !mIsHidden;
    }


    public boolean canBack() {
        return false;
    }

}
