package com.hewuzhe.ui.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.hewuzhe.R;
import com.hewuzhe.model.Video;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widget.VideoControllerView;
import com.hewuzhe.utils.StringUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.rong.imkit.tools.PhotoFragment;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by zycom on 2016/3/6.
 */
public class VideoMessageActivity extends BaseActivity{//extends BaseActionBarActivity {
    private WindowManager windowManager;
    private int mLayout;
    @Bind(R.id.vvv)
    VideoControllerView mVDVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.de_ac_video);
        io.vov.vitamio.LibsChecker.checkVitamioLibs(this);
        ButterKnife.bind(this);
        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        initView();
        String video = getIntent().getStringExtra("videoPath");
        initData(video);
    }


    protected void initView() {
        mVDVideoView.setOnFullScreenBtnClickListener(new VideoControllerView.OnFullScreenBtnClick() {
            @Override
            public void onClick(View v) {
                int mCurrentOrientation = VideoMessageActivity.this.getResources().getConfiguration().orientation;
                WindowManager.LayoutParams attrs = getWindow().getAttributes();
                if (mCurrentOrientation == Configuration.ORIENTATION_PORTRAIT) {
                    VideoMessageActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
                    getWindow().setAttributes(attrs);
                    //设置全屏
//                    v.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

                    mVDVideoView.btnFullScreen.setImageResource(R.mipmap.icon_origin_screen);

                }
                if (mCurrentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    VideoMessageActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    getWindow().setAttributes(attrs);
                    //取消全屏设置
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

                    mVDVideoView.btnFullScreen.setImageResource(R.mipmap.icon_full_screen);
                }
            }
        });

    }

    protected void initData(String v) {
        ViewGroup.LayoutParams params = mVDVideoView.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;

        params.height = StringUtil.dip2px(this, 300);

        mVDVideoView.setLayoutParams(params);
        startPlay(v);

    }

    @Override
    protected void onDestroy() {
        mVDVideoView.release();
        super.onDestroy();
    }

    private void startPlay(String video) {
        mVDVideoView.setonPreparedListener(new VideoControllerView.OnPreparedListener() {
            @Override
            public void onPrepared() {
                mVDVideoView.getVideoView().setVideoLayout(VideoView.VIDEO_LAYOUT_FIT_PARENT, 0);
            }
        });
//       mVDVideoView.btnFullScreen.setVisibility(View.GONE);
        mVDVideoView.setVideoPath(video);
        mVDVideoView.start();

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
            params.height = StringUtil.dip2px(this, 300);
            params.width = windowManager.getDefaultDisplay().getWidth();
            mVDVideoView.setLayoutParams(params);
        }
        if (mVDVideoView.getVideoView() != null)
            mVDVideoView.getVideoView().setVideoLayout(mLayout, 0);
    }
}

