package com.hewuzhe.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.hewuzhe.R;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.base.ToolBarActivity;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.view.base.BaseView;

import butterknife.Bind;

public class CateSelectActivity extends ToolBarActivity<BasePresenterImp<BaseView>> {


    @Bind(R.id.lay_view_group)
    LinearLayout layViewGroup;
    @Bind(R.id.lay_video)
    FrameLayout layVideo;
    @Bind(R.id.lay_encouragement)
    FrameLayout layEncouragement;
    @Bind(R.id.lay_performance)
    FrameLayout layPerformance;
    @Bind(R.id.lay_theory)
    FrameLayout layTheory;
    @Bind(R.id.lay_megagame)
    FrameLayout layMegagame;
    @Bind(R.id.lay_manage)
    FrameLayout layManage;
    @Bind(R.id.lay_tec)
    FrameLayout layTec;
    @Bind(R.id.lay_teach)
    FrameLayout layTeach;
    @Bind(R.id.lay_master)
    FrameLayout layMaster;

    /**
     * @return 提供LayoutId
     */
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_cate_select;
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {
        layVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_OK, new Intent().putExtra("data", new Bun().putInt("id", 63).putString("title", "电影").ok()));
                finishActivity();
            }
        });

        layEncouragement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_OK, new Intent().putExtra("data", new Bun().putInt("id", 64).putString("title", "励志").ok()));
                finishActivity();
            }
        });

        layManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_OK, new Intent().putExtra("data", new Bun().putInt("id", 1).putString("title", "管理").ok()));
                finishActivity();


            }
        });
        layMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_OK, new Intent().putExtra("data", new Bun().putInt("id", 1).putString("title", "大师").ok()));
                finishActivity();


            }
        });
        layMegagame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_OK, new Intent().putExtra("data", new Bun().putInt("id", 67).putString("title", "赛事").ok()));
                finishActivity();

            }
        });
        layPerformance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_OK, new Intent().putExtra("data", new Bun().putInt("id", 1).putString("title", "表演").ok()));
                finishActivity();


            }
        });
        layTeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_OK, new Intent().putExtra("data", new Bun().putInt("id", 1).putString("title", "教学").ok()));
                finishActivity();

            }
        });
        layTec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_OK, new Intent().putExtra("data", new Bun().putInt("id", 69).putString("title", "技术").ok()));
                finishActivity();


            }
        });

        layTheory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_OK, new Intent().putExtra("data", new Bun().putInt("id", 66).putString("title", "理念").ok()));
                finishActivity();
            }
        });
    }

    /**
     * 绑定Presenter
     */
    @Override
    public BasePresenterImp<BaseView> createPresenter() {
        return null;
    }


    /**
     * @return 提供标题
     */
    @Override
    protected String provideTitle() {
        return "选择分类";
    }
}
