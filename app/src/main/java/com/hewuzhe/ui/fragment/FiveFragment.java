package com.hewuzhe.ui.fragment;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.hewuzhe.R;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.activity.VideosActivity;
import com.hewuzhe.ui.base.BaseFragment;
import com.hewuzhe.utils.Bun;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class FiveFragment extends BaseFragment {


    private static FiveFragment instance = null;
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

    private Intent intent;


    public static FiveFragment newInstance() {
        if (instance == null) {
            instance = new FiveFragment();
        }
        return instance;
    }

    public FiveFragment() {
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {

        layVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(VideosActivity.class, new Bun().putInt("id", 63).putString("title", "电影").ok());
            }
        });

        layEncouragement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(VideosActivity.class, new Bun().putInt("id", 64).putString("title", "励志").ok());
            }
        });

        layManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(VideosActivity.class, new Bun().putInt("id", 1).putString("title", "管理").ok());

            }
        });
        layMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(VideosActivity.class, new Bun().putInt("id", 1).putString("title", "大师").ok());

            }
        });
        layMegagame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(VideosActivity.class, new Bun().putInt("id", 67).putString("title", "赛事").ok());

            }
        });
        layPerformance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(VideosActivity.class, new Bun().putInt("id", 1).putString("title", "表演").ok());

            }
        });
        layTeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(VideosActivity.class, new Bun().putInt("id", 1).putString("title", "教学").ok());

            }
        });
        layTec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(VideosActivity.class, new Bun().putInt("id", 69).putString("title", "技术").ok());

            }
        });

        layTheory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(VideosActivity.class, new Bun().putInt("id", 66).putString("title", "理念").ok());
            }
        });

    }

    /**
     * 初始化一些事情
     *
     * @param v
     */
    @Override
    protected void initThings(View v) {

    }

    /**
     * @return 提供LayoutId
     */
    @Override
    public int provideLayoutId() {
        return R.layout.fragment_five;
    }

    /**
     * 绑定Presenter
     */
    @Override
    public BasePresenterImp createPresenter() {
        return null;
    }


}
