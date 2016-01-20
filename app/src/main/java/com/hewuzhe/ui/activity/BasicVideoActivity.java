package com.hewuzhe.ui.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.hewuzhe.R;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.base.ToolBarActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widget.VideoControllerView;
import com.hewuzhe.utils.StringUtil;

import butterknife.Bind;
import io.vov.vitamio.widget.VideoView;

public class BasicVideoActivity extends ToolBarActivity {


    @Bind(R.id.video_controller)
    VideoControllerView videoController;
    private int mLayout;
    private WindowManager windowManager;


    /**
     * @return 提供标题
     */
    @Override
    protected String provideTitle() {
        return "视频";
    }

    /**
     * @return 提供LayoutId
     */
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_basic_video;
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {
        videoController.setOnFullScreenBtnClickListener(new VideoControllerView.OnFullScreenBtnClick() {
            @Override
            public void onClick(View v) {
                int mCurrentOrientation = BasicVideoActivity.this.getResources().getConfiguration().orientation;
                WindowManager.LayoutParams attrs = getWindow().getAttributes();
                if (mCurrentOrientation == Configuration.ORIENTATION_PORTRAIT) {
                    BasicVideoActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
                    getWindow().setAttributes(attrs);
                    //设置全屏
//                    recyclerView.setVisibility(View.GONE);
                    toolBar.setVisibility(View.GONE);
//                    v.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

                    videoController.btnFullScreen.setImageResource(R.mipmap.icon_origin_screen);

                }
                if (mCurrentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    BasicVideoActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    getWindow().setAttributes(attrs);
                    //取消全屏设置
                    toolBar.setVisibility(View.VISIBLE);
//                    recyclerView.setVisibility(View.VISIBLE);
                    hideOrShowToolbar();
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

                    videoController.btnFullScreen.setImageResource(R.mipmap.icon_full_screen);
                }
            }
        });

    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);

        String videoPath = getIntentData().getString("videoPath");
        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        videoController.setVideoPath(C.BASE_URL + videoPath);
        videoController.start();
    }

    /**
     * 绑定Presenter
     */
    @Override
    public BasePresenterImp createPresenter() {
        return null;
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mLayout = VideoView.VIDEO_LAYOUT_STRETCH;//全屏
            ViewGroup.LayoutParams params = videoController.getLayoutParams();
            params.height = windowManager.getDefaultDisplay().getHeight();
            params.width = windowManager.getDefaultDisplay().getWidth();
            videoController.setLayoutParams(params);

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mLayout = VideoView.VIDEO_LAYOUT_FIT_PARENT;//原始尺寸

            ViewGroup.LayoutParams params = videoController.getLayoutParams();
            params.height = StringUtil.dip2px(getContext(), 200);
            params.width = windowManager.getDefaultDisplay().getWidth();
            videoController.setLayoutParams(params);
        }
        if (videoController.getVideoView() != null)
            videoController.getVideoView().setVideoLayout(mLayout, 0);
    }
}
