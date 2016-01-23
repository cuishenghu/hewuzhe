package com.hewuzhe.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.ui.activity.MemberActivity;
import com.hewuzhe.utils.DataTypeUtils;
import com.hewuzhe.utils.TimeUtil;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.VideoView;
import materialdesign.views.ProgressWheel;

/**
 * Created by sunger on 15/11/7.
 */
public class VideoControllerView extends FrameLayout implements View.OnTouchListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, SeekBar.OnSeekBarChangeListener {
    private static final long delayMillis = 2000;
    private static final int MSG_PROGRESS_CHANGE = 2;
    private VideoView mVideoView;
    private ProgressWheel mProgressWheel;
    private ImageButton viewPlay;
    public SeekBar slider;
    private Context context;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            layMenus.setVisibility(GONE);
        }
    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_PROGRESS_CHANGE:
                    setProgress();
                    sendEmptyMessageDelayed(MSG_PROGRESS_CHANGE, 500);
                    break;
            }
        }
    };
    private TextView tvCurDuration;
    private TextView tvDuration;
    public ImageButton btnFullScreen;
    private OnFullScreenBtnClick clickListener;
    private ImageButton viewPlayCenter;
    public LinearLayout _LayNoVip;
    private Button _BtnToMember;
    private OnPreparedListener _OnPreparedListener;

    private void setProgress() {
        slider.setProgress(DataTypeUtils.toInt(mVideoView.getCurrentPosition()));
        tvCurDuration.setText(TimeUtil.timeFormat(mVideoView.getCurrentPosition()));
    }

    private ViewGroup layMenus;

    public VideoControllerView(Context context) {
        super(context);
        initView(context);
    }

    public VideoControllerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public VideoControllerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(final Context context) {
        this.context = context;
        View v = LayoutInflater.from(context).inflate(R.layout.view_video_controller, null);
        addView(v);
        mVideoView = findView(R.id.videoView);
        mVideoView.setOnTouchListener(this);
        mVideoView.setOnPreparedListener(this);
        mVideoView.setBufferSize(1024);
        mVideoView.setOnBufferingUpdateListener(this);

        mVideoView.setOnCompletionListener(this);
        tvCurDuration = findView(R.id.tv_cur_duration);
        tvDuration = findView(R.id.tv_ducration);
        mProgressWheel = findView(R.id.progressWheel);
        mProgressWheel.startSpinning();
        viewPlay = findView(R.id.button_play);
        viewPlayCenter = findView(R.id.button_play_center);
        btnFullScreen = findView(R.id.btn_full_screen);

        _BtnToMember = findView(R.id.btn_to_member);
        _LayNoVip = findView(R.id.lay_no_vip);

        slider = findView(R.id.slider);
        layMenus = findView(R.id.lay_menus);
        slider.setOnSeekBarChangeListener(this);
//        slider.setOnNumberIndicatorConvert(new Slider.OnNumberIndicatorConvert() {
//            @Override
//            public String covert(long val) {
//                return MediaPlayerUtils.getVideoDisplayTime(val);
//            }
//        });
        setVideoPlayButton();

        btnFullScreen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    clickListener.onClick(view);
                }
            }
        });

        _BtnToMember.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, MemberActivity.class));
            }
        });
    }

    private void setVideoPlayButton() {
        viewPlay.setImageResource(R.mipmap.icon_play);
        viewPlay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                playStateChange();
            }
        });
        viewPlayCenter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPlayCenter.setVisibility(GONE);
                mVideoView.seekTo(0);
                start();
            }
        });
    }

    private boolean isVideoPause() {
        return !mVideoView.isPlaying();
    }

    private void updateTimeTask() {
        slider.setProgress(DataTypeUtils.toInt(mVideoView.getCurrentPosition()));
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, delayMillis);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        updateTimeTask();
        if (layMenus.getVisibility() == View.VISIBLE) {
            layMenus.setVisibility(GONE);
        } else {
            layMenus.setVisibility(VISIBLE);
        }
        return false;
    }

    private void playStateChange() {
        updateTimeTask();
        if (!isVideoPause()) {
            pause();
        } else {
            start();
        }
    }

    private <T extends View> T findView(int id) {
        return (T) super.findViewById(id);
    }

    public void setVideoPath(String videoUrl) {
        mVideoView.setVideoPath(videoUrl);
    }

    /**
     * 开始播放
     */
    public void start() {
        if (viewPlayCenter.getVisibility() == VISIBLE) {
            viewPlayCenter.setVisibility(GONE);
        }
        slider.setProgress(DataTypeUtils.toInt(mVideoView.getCurrentPosition()));
        mVideoView.start();
        viewPlay.setImageResource(R.mipmap.icon_play);
    }

    /**
     * 暂停播放
     */
    public void pause() {
        slider.setProgress(DataTypeUtils.toInt(mVideoView.getCurrentPosition()));
        mVideoView.pause();
        viewPlay.setImageResource(R.mipmap.icon_pause);
    }

    /**
     * 释放资源
     */
    public void release() {
        mVideoView.stopPlayback();
    }

    public VideoView getVideoView() {
        return mVideoView;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.d("VideoControllerView", "mediaPlayer.getDuration():" + mediaPlayer.getDuration());
        slider.setMax(DataTypeUtils.toInt(mediaPlayer.getDuration()));
        tvDuration.setText("/" + TimeUtil.timeFormat(mediaPlayer.getDuration()));
        mediaPlayer.setPlaybackSpeed(1.0f);
        updateTimeTask();
        handler.sendEmptyMessage(MSG_PROGRESS_CHANGE);

        if (this._OnPreparedListener != null) {
            this._OnPreparedListener.onPrepared();
        }
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        mProgressWheel.setText(percent + "%");
        if (percent >= 30 || percent <= 0) {
            mProgressWheel.setVisibility(View.INVISIBLE);
        } else {
            mProgressWheel.setVisibility(View.VISIBLE);
        }
    }


    @TargetApi(21)
    @Override
    public void onCompletion(MediaPlayer mp) {
        viewPlayCenter.setVisibility(VISIBLE);
        pause();
//      mVideoView.seekTo(0);
        setVideoPlayButton();
    }

    public void setOnFullScreenBtnClickListener(OnFullScreenBtnClick clickListener) {
        this.clickListener = clickListener;
    }

    public void setonPreparedListener(OnPreparedListener onPreparedListener) {
        this._OnPreparedListener = onPreparedListener;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        Log.d("VideoControllerView", "i:" + i);

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {


    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mVideoView.seekTo(seekBar.getProgress());
        updateTimeTask();
        Log.d("VideoControllerView", "seekBar.getProgress():" + seekBar.getProgress());
    }

    public interface OnFullScreenBtnClick {
        public void onClick(View v);
    }

    public interface OnPreparedListener {
        public void onPrepared();
    }


}
