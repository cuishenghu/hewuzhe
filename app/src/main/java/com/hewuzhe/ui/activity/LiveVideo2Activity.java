package com.hewuzhe.ui.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.model.LiveVideo;
import com.hewuzhe.presenter.LiveVideoPresenter;
import com.hewuzhe.ui.adapter.LiveVideo2Adapter;
import com.hewuzhe.ui.base.RecycleViewActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widget.VideoControllerView;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.view.LiveVideoView;

import java.util.ArrayList;

import butterknife.Bind;
import io.vov.vitamio.widget.VideoView;

public class LiveVideo2Activity extends RecycleViewActivity<LiveVideoPresenter, LiveVideo2Adapter, LiveVideo> implements LiveVideoView {

    private static final String TAG = "video";
    private static int HEITH_VIDEO = 200;
    @Bind(R.id.vv1)
    VideoControllerView mVDVideoView;

    TextView _TvTimeStart;
    TextView _TvApplyEnd;
    TextView _TvRequire;
    TextView _TvName;


    private boolean isFulllScreen = false;
    private WindowManager windowManager;
    private int mLayout;
    private View headerView;

    LiveVideo liveVideo;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_live_2_video;
    }

    /**
     * @param savedInstanceState 缓存数据
     *                           <p/>
     */
    @Override
    protected void initThings(Bundle savedInstanceState) {
        if(!new SessionUtil(getContext()).isLogin()) {

            startActivity(SignInActivity.class);
            finish();
        }
        io.vov.vitamio.LibsChecker.checkVitamioLibs(this);
        super.initThings(savedInstanceState);
        // 手动这是播放窗口父类，横屏的时候，会用这个做为容器使用，如果不设置，那么默认直接跳转到DecorView
        initHeader();

        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        presenter.SelectVideoLive(getIntentData().getInt("Id"));
    }

    @Override
    public LiveVideoPresenter createPresenter() {
        return new LiveVideoPresenter();
    }

    @Override
    protected LiveVideo2Adapter provideAdapter() {
        headerView = getLayoutInflater().inflate(R.layout.header_live2_video, null);
        return new LiveVideo2Adapter(getContext(), presenter, headerView);
    }

    private void initHeader() {
        _TvTimeStart  = (TextView)headerView.findViewById(R.id.tv_time_start);
        _TvApplyEnd = (TextView)headerView.findViewById(R.id.tv_apply_end);
        _TvRequire = (TextView)headerView.findViewById(R.id.tv_require);
        _TvName = (TextView)headerView.findViewById(R.id.tv_name);
    }



    /**
     * @return 提供LayoutManager
     */
    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {

        mVDVideoView.setOnFullScreenBtnClickListener(new VideoControllerView.OnFullScreenBtnClick() {
            @Override
            public void onClick(View v) {
                int mCurrentOrientation = LiveVideo2Activity.this.getResources().getConfiguration().orientation;
                WindowManager.LayoutParams attrs = getWindow().getAttributes();
                if (mCurrentOrientation == Configuration.ORIENTATION_PORTRAIT) {
                    LiveVideo2Activity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
                    getWindow().setAttributes(attrs);
                    //设置全屏
                    recyclerView.setVisibility(View.GONE);
                    toolBar.setVisibility(View.GONE);
//                    v.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

                    mVDVideoView.btnFullScreen.setImageResource(R.mipmap.icon_origin_screen);

                }
                if (mCurrentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    LiveVideo2Activity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    getWindow().setAttributes(attrs);
                    //取消全屏设置
                    toolBar.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    hideOrShowToolbar();
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

                    mVDVideoView.btnFullScreen.setImageResource(R.mipmap.icon_full_screen);
                }
            }
        });




    }




    @Override
    protected String provideTitle() {
        return "";
    }

    @Override
    public void setData(final LiveVideo liveVideo) {
        this.liveVideo = liveVideo;

        _TvName.setText(liveVideo.Title);
        _TvTimeStart.setText(liveVideo.TimeStart);
        _TvApplyEnd.setText(liveVideo.TimeEnd);
        _TvRequire.setText(liveVideo.Content);
//        ViewGroup.LayoutParams params = mVDVideoView.getLayoutParams();
//        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
//
////        if (video.UserId == 0) {
//        HEITH_VIDEO = 200;
////        } else {
////            HEITH_VIDEO = 320;
////        }
//        params.height = StringUtil.dip2px(getContext(), HEITH_VIDEO);
//
//        mVDVideoView.setLayoutParams(params);
        _TvRequire.setMovementMethod(ScrollingMovementMethod.getInstance());
        startPlay();

    }

    private void startPlay() {
        mVDVideoView.setonPreparedListener(new VideoControllerView.OnPreparedListener() {
            @Override
            public void onPrepared() {
                mVDVideoView.getVideoView().setVideoLayout(VideoView.VIDEO_LAYOUT_FIT_PARENT, 0);
            }
        });
        mVDVideoView.setVideoPath(C.BASE_URL + liveVideo.LiveBack);
        mVDVideoView.start();

    }



    @Override
    protected void onDestroy() {
        mVDVideoView.release();
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mLayout = VideoView.VIDEO_LAYOUT_STRETCH;//全屏
            ViewGroup.LayoutParams params = mVDVideoView.getLayoutParams();
            params.height = windowManager.getDefaultDisplay().getHeight();
            params.width = windowManager.getDefaultDisplay().getWidth();
            mVDVideoView.setLayoutParams(params);

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mLayout = VideoView.VIDEO_LAYOUT_FIT_PARENT;//原始尺寸
            ViewGroup.LayoutParams params = mVDVideoView.getLayoutParams();
            params.height = StringUtil.dip2px(getContext(), HEITH_VIDEO);
            params.width = windowManager.getDefaultDisplay().getWidth();
            mVDVideoView.setLayoutParams(params);
        }
        if (mVDVideoView.getVideoView() != null)
            mVDVideoView.getVideoView().setVideoLayout(mLayout, 0);
    }


    @Override
    public void onBackPressed() {
        if (isFulllScreen) {
        } else {
            super.onBackPressed();
        }

    }


    @Override
    public void bindData(ArrayList<LiveVideo> data) {

    }

    @Override
    public void onItemClick(View view, int pos, LiveVideo item) {

    }

    @Override
    public void refresh(boolean refreshing) {

    }
}
