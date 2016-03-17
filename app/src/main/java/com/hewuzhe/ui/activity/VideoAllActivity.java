package com.hewuzhe.ui.activity;

import android.content.Context;
import android.content.Intent;
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
import com.hewuzhe.utils.Bun;
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
public class VideoAllActivity extends BaseActivity{//extends BaseActionBarActivity {
    private WindowManager windowManager;
    private int mLayout;
    @Bind(R.id.vvv)
    VideoControllerView mVDVideoView;
    public int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.de_ac_video);
        io.vov.vitamio.LibsChecker.checkVitamioLibs(this);
        ButterKnife.bind(this);
        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        initView();
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        VideoAllActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        mVDVideoView.btnFullScreen.setImageResource(R.mipmap.icon_origin_screen);
        id = getIntent().getBundleExtra("data").getInt("id");
        String video = getIntent().getStringExtra("videoPath");
        initData(video);
    }


    protected void initView() {
        mVDVideoView.setOnFullScreenBtnClickListener(new VideoControllerView.OnFullScreenBtnClick() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(VideoAllActivity.this,DojoDetailActivity.class));
//                finish();
            }
        });

    }

    protected void initData(String v) {
        mLayout = VideoView.VIDEO_LAYOUT_STRETCH;//全屏
        ViewGroup.LayoutParams params = mVDVideoView.getLayoutParams();
        params.height = windowManager.getDefaultDisplay().getHeight();
        params.width = windowManager.getDefaultDisplay().getWidth();
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

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(VideoAllActivity.this, DojoDetailActivity.class).putExtra("data",new Bun().putInt("id", id).ok()));
        finish();
    }
}

