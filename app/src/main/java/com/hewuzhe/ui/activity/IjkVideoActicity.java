package com.hewuzhe.ui.activity;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hewuzhe.R;
import com.easefun.polyvsdk.ijk.IjkVideoView;
import com.easefun.polyvsdk.ijk.OnPreparedListener;
import com.easefun.polyvsdk.ijk.IjkVideoView.ErrorReason;
import com.hewuzhe.utils.MediaController;

public class IjkVideoActicity extends Activity {
	private static final String TAG = "IjkVideoActicity";
	IjkVideoView videoview = null;
	MediaController mediaController = null;
	ProgressBar progressBar = null;
	float ratio;
	int w, h, adjusted_h;
	RelativeLayout rl;
	private boolean isLandscape = false;
	private int stopPosition = 0;
	private AppBarLayout top_toolbar;
	private LinearLayout video_content;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_small2);
		
		Bundle e = getIntent().getBundleExtra("data");
		String uid = e.getString("uid");
		String vid = e.getString("vid");
		findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		((TextView)findViewById(R.id.tv_title)).setText("正在直播");
		findViewById(R.id.tv_action).setVisibility(View.GONE);

		top_toolbar = (AppBarLayout)findViewById(R.id.app_bar_layout);
		video_content = (LinearLayout)findViewById(R.id.video_content);

		((TextView)findViewById(R.id.tv_name)).setText(e.getString("title"));
		((TextView)findViewById(R.id.tv_time_start)).setText(e.getString("start"));
		((TextView)findViewById(R.id.tv_apply_end)).setText(e.getString("end"));
		((TextView)findViewById(R.id.tv_require)).setText(e.getString("content"));

		
		Point point = new Point();
		WindowManager wm = this.getWindowManager();
		wm.getDefaultDisplay().getSize(point);
		w = point.x;
		h = point.y;
		// 小窗口的比例
		ratio = (float) 4 / 3;
		adjusted_h = (int) Math.ceil((float) w / ratio);
		rl = (RelativeLayout) findViewById(R.id.rl);
		rl.setLayoutParams(new RelativeLayout.LayoutParams(w, adjusted_h));
		videoview = (IjkVideoView) findViewById(R.id.videoview);
		progressBar = (ProgressBar) findViewById(R.id.loadingprogress);
		progressBar.setVisibility(View.GONE);
		videoview.setMediaBufferingIndicator(progressBar); // 在缓冲时出现的loading
		videoview.setVideoLayout(IjkVideoView.VIDEO_LAYOUT_SCALE);

		mediaController = new MediaController(this, false);//
		mediaController.setIjkVideoView(videoview);
		mediaController.setAnchorView(videoview);
		videoview.setMediaController(mediaController);
		videoview.setOnPreparedListener(new OnPreparedListener() {
			@Override
			public void onPrepared(IMediaPlayer mp) {
				videoview.setVideoLayout(IjkVideoView.VIDEO_LAYOUT_SCALE);
				if (stopPosition > 0) {
					Log.d(TAG, "seek to stopPosition:" + stopPosition);
					videoview.seekTo(stopPosition);
				}
			}
		});

		videoview.setOnVideoStatusListener(new IjkVideoView.OnVideoStatusListener() {

			@Override
			public void onStatus(int status) {

			}
		});
		
		videoview.setOnVideoPlayErrorLisener(new IjkVideoView.OnVideoPlayErrorLisener() {
			
			@Override
			public boolean onVideoPlayError(ErrorReason errorReason) {
				//直播只需要处理这三个事件类型
				switch (errorReason.getType()) {
					case RUNTIME_EXCEPTION:
						break;
					case NETWORK_DENIED:
						break;
					case M3U8_URL_EMPTY:
						break;
				}
				
				return false;
			}
		});
		
		videoview.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
			
			@Override
			public boolean onError(IMediaPlayer arg0, int arg1, int arg2) {
				return false;
			}
		});
		
		videoview.setLivePlay(uid, vid);
		
		// 设置切屏事件
		mediaController.setOnBoardChangeListener(new MediaController.OnBoardChangeListener() {

			@Override
			public void onPortrait() {
				changeToLandscape();
			}

			@Override
			public void onLandscape() {
				changeToPortrait();
			}
		});
	}

	// 切换到横屏
	public void changeToLandscape() {
		WindowManager.LayoutParams attrs = getWindow().getAttributes();
		attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
		getWindow().setAttributes(attrs);

		RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(h, w);
		rl.setLayoutParams(p);
		stopPosition = videoview.getCurrentPosition();
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		isLandscape = !isLandscape;
		top_toolbar.setVisibility(View.GONE);
		video_content.setVisibility(View.GONE);
	}

	// 切换到竖屏
	public void changeToPortrait() {
		WindowManager.LayoutParams attrs = getWindow().getAttributes();
		attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setAttributes(attrs);

		RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(w, adjusted_h);
		rl.setLayoutParams(p);
		stopPosition = videoview.getCurrentPosition();
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		isLandscape = !isLandscape;
		top_toolbar.setVisibility(View.VISIBLE);
		video_content.setVisibility(View.VISIBLE);
	}


	// 配置文件设置congfigchange 切屏调用一次该方法，hide()之后再次show才会出现在正确位置
	@Override
	public void onConfigurationChanged(Configuration arg0) {
		super.onConfigurationChanged(arg0);
		videoview.setVideoLayout(IjkVideoView.VIDEO_LAYOUT_SCALE);
		mediaController.hide();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean value = mediaController.dispatchKeyEvent(event);
		if (value) return true;
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if (videoview.getMediaPlayer() != null)
			videoview.getMediaPlayer().release();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
}
