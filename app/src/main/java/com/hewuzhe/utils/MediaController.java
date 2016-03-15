package com.hewuzhe.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.hewuzhe.R;
import com.easefun.polyvsdk.ijk.IjkBaseMediaController;
import com.easefun.polyvsdk.ijk.IjkVideoView;

public class MediaController extends IjkBaseMediaController {
	private static final String TAG = "MediaController";
	private MediaPlayerControl mPlayer;
	private Context mContext;
	private PopupWindow mWindow;
	private int mAnimStyle;
	private View mAnchor;
	private View mRoot;
	private IjkVideoView ijkVideoView;

	private boolean mShowing;
	private static final int sDefaultTimeout = 3000;
	private static final int FADE_OUT = 1;
	private boolean mFromXml = false;
	private ImageButton mPauseButton = null;
	private OnBoardChangeListener onBoardChangeListener = null;
	private ImageButton btn_boardChange = null;
	private ImageButton btn_soundSwitch = null;
	private RelativeLayout bot = null;

	public MediaController(Context context, AttributeSet attrs) {
		super(context, attrs);
		mRoot = this;
		mFromXml = true;
		initController(context);
	}

	/**
	 * 当你不需要实现上一集下一集按钮时，设置isUsePreNext 为false，需要时设为true
	 * 并实现setPreNextListener()方法
	 * 
	 * @param context
	 * @param isUsePreNext
	 * @param
	 */
	public MediaController(Context context, boolean isUsePreNext) {
		super(context);
		if (!mFromXml && initController(context))
			initFloatingWindow();
	}

	private boolean initController(Context context) {
		mContext = context;
		return true;
	}

	@Override
	public void onFinishInflate() {
		if (mRoot != null)
			initControllerView(mRoot);
	}

	private void initFloatingWindow() {
		mWindow = new PopupWindow(mContext);
		mWindow.setFocusable(false);
		mWindow.setTouchable(true);
		mWindow.setBackgroundDrawable(null);
		mWindow.setOutsideTouchable(true);
		mAnimStyle = android.R.style.Animation;
	}

	/**
	 * Set the view that acts as the anchor for the control view. This can for
	 * example be a VideoView, or your Activity's main view.
	 * 
	 * @param view
	 *            The view to which to anchor the controller when it is visible.
	 */
	@Override
	public void setAnchorView(View view) {
		mAnchor = view;
		if (!mFromXml) {
			removeAllViews();
			mRoot = makeControllerView();
			mWindow.setContentView(mRoot);
			mWindow.setWidth(mAnchor.getWidth());
			mWindow.setHeight(mAnchor.getHeight());
		}
		initControllerView(mRoot);
	}

	@Override
	protected View makeControllerView() {

		mRoot = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
				.inflate(R.layout.ijkmedia_controller, this);
		return mRoot;
	}

	@Override
	protected void initControllerView(View v) {
		bot = (RelativeLayout) v.findViewById(R.id.bot);
		
		mPauseButton = (ImageButton) v.findViewById(R.id.mediacontroller_play_pause);
		mPauseButton.requestFocus();
		mPauseButton.setOnClickListener(mPauseListener);
		
		btn_boardChange = (ImageButton) v.findViewById(R.id.landscape);
		btn_boardChange.setOnClickListener(mBoardListener);
		
		btn_soundSwitch = (ImageButton) v.findViewById(R.id.soundSwitch);
		btn_soundSwitch.setTag("0");
		btn_soundSwitch.setOnClickListener(mSoundListener);
		if (ijkVideoView.isOpenSound()) {
			btn_soundSwitch.setImageResource(R.drawable.pl_video_player_speaker);
		} else {
			btn_soundSwitch.setImageResource(R.drawable.pl_video_player_mute);
		}
	}

	public void setOnBoardChangeListener(OnBoardChangeListener l) {
		onBoardChangeListener = l;
	}

	public interface OnBoardChangeListener {
		public void onLandscape();
		public void onPortrait();
	}

	private boolean isScreenPortrait() {
		return mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
	}
	
	private OnClickListener mSoundListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (ijkVideoView.isOpenSound()) {
				ijkVideoView.closeSound();
				btn_soundSwitch.setImageResource(R.drawable.pl_video_player_mute);
			} else {
				ijkVideoView.openSound();
				btn_soundSwitch.setImageResource(R.drawable.pl_video_player_speaker);
			}
		}
	};
	private OnClickListener mBoardListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (isScreenPortrait()) {
				btn_boardChange.setImageResource(R.drawable.pl_video_player_shrinkscreen);
				if (onBoardChangeListener != null)
					onBoardChangeListener.onPortrait();
			} else {
				btn_boardChange.setImageResource(R.drawable.pl_video_player_fullscreen);
				if (onBoardChangeListener != null)
					onBoardChangeListener.onLandscape();
			}
		}
	};

	@Override
	public void setMediaPlayer(MediaPlayerControl player) {
		mPlayer = player;
	}

	@Override
	public void show() {
		show(sDefaultTimeout);
	}

	private void disableUnsupportedButtons() {
		try {
			if (mPauseButton != null && !mPlayer.canPause())
				mPauseButton.setEnabled(false);
		} catch (IncompatibleClassChangeError ex) {

		}
	}

	/**
	 * Change the animation style resource for this controller. If the
	 * controller is showing, calling this method will take effect only the next
	 * time the controller is shown.
	 * 
	 * @param animationStyle
	 *            animation style to use when the controller appears and
	 *            disappears. Set to -1 for the default animation, 0 for no
	 *            animation, or a resource identifier for an explicit animation.
	 */
	public void setAnimationStyle(int animationStyle) {
		mAnimStyle = animationStyle;
	}

	/**
	 * Show the controller on screen. It will go away automatically after
	 * 'timeout' milliseconds of inactivity.
	 * 
	 * @param timeout
	 *            The timeout in milliseconds. Use 0 to show the controller
	 *            until hide() is called.
	 */
	@Override
	public void show(int timeout) {
		if (!mShowing && mAnchor != null && mAnchor.getWindowToken() != null) {
			if (mPauseButton != null)
				mPauseButton.requestFocus();
			disableUnsupportedButtons();

			if (mFromXml) {
				setVisibility(View.VISIBLE);
			} else {
				int[] location = new int[2];

				mAnchor.getLocationInWindow(location);
				Rect anchorRect = new Rect(location[0], location[1], location[0] + mAnchor.getWidth(),
						location[1] + mAnchor.getHeight());
				mWindow.setWidth(mAnchor.getWidth());
				mWindow.setHeight(mAnchor.getHeight());
				mWindow.setAnimationStyle(mAnimStyle);
				mWindow.showAtLocation(mAnchor, Gravity.NO_GRAVITY, 0, anchorRect.top);
			}
			mShowing = true;
			if (mShownListener != null)
				mShownListener.onShown();
		}

		if (timeout != 0) {
			mHandler.removeMessages(FADE_OUT);
			mHandler.sendMessageDelayed(mHandler.obtainMessage(FADE_OUT), timeout);
		}
	}

	public boolean isShowing() {
		return mShowing;
	}

	public void hide() {
		if (mAnchor == null)
			return;
		if (mShowing) {
			try {
				if (mFromXml)
					setVisibility(View.GONE);
				else
					mWindow.dismiss();
			} catch (IllegalArgumentException ex) {
				Log.d(TAG, "MediaController already removed");
			}
			mShowing = false;
			if (mHiddenListener != null)
				mHiddenListener.onHidden();
		}
	}

	public interface OnShownListener {
		public void onShown();
	}

	private OnShownListener mShownListener;

	public void setOnShownListener(OnShownListener l) {
		mShownListener = l;
	}

	public interface OnHiddenListener {
		public void onHidden();
	}

	private OnHiddenListener mHiddenListener;

	public void setOnHiddenListener(OnHiddenListener l) {
		mHiddenListener = l;
	}

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case FADE_OUT:
					hide();
					break;
			}
		}
	};

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
			//判断如果是点击控件bot 的范围就不执行操作
			//因为如果bot范围也隐藏控件，就会出现难点击功能按钮的情况
			int eventX = (int) (event.getX() * 100);
			int eventY = (int) (event.getY() * 100);
			int botStartX = (int) (bot.getX() * 100);
			int botStartY = (int) (bot.getY() * 100);
			int botEndX = botStartX + bot.getWidth() * 100;
			int botEndY = botStartY + bot.getHeight() * 100;
			if (eventX >= botStartX && eventX <= botEndX && eventY >= botStartY && eventY < botEndY) {
				return false;
			}
			
			hide();
		}
		
		return false;
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		int keyCode = event.getKeyCode();
		if (event.getRepeatCount() == 0 && (keyCode == KeyEvent.KEYCODE_HEADSETHOOK
				|| keyCode == KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE || keyCode == KeyEvent.KEYCODE_SPACE)) {
			doPauseResume();
			show(sDefaultTimeout);
			if (mPauseButton != null)
				mPauseButton.requestFocus();
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_MEDIA_STOP) {
			if (mPlayer.isPlaying()) {
				mPlayer.pause();
			}
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isShowing()) {
				hide();
				return true;
			}
			
			if (isScreenPortrait() == false) {
				btn_boardChange.setImageResource(R.drawable.pl_video_player_fullscreen);
				if (onBoardChangeListener != null)
					onBoardChangeListener.onLandscape();
				return true;
			}
			
			return false;
		} else if (keyCode == KeyEvent.KEYCODE_MENU) {
			show(sDefaultTimeout);
		} else {
			show(sDefaultTimeout);
		}
		
		return super.dispatchKeyEvent(event);
	}

	private OnClickListener mPauseListener = new OnClickListener() {
		public void onClick(View v) {
			show(sDefaultTimeout);
			doPauseResume();
		}
	};

	private void doPauseResume() {
		if (mPlayer.isPlaying()) {
			mPlayer.pause();
			mPauseButton.setImageResource(R.drawable.pl_video_player_play);
		} else {
			mPlayer.start();
			mPauseButton.setImageResource(R.drawable.pl_video_player_pause);
		}
	}

	@Override
	public void setEnabled(boolean enabled) {
		if (mPauseButton != null)
			mPauseButton.setEnabled(enabled);
		disableUnsupportedButtons();
		super.setEnabled(enabled);
	}
	
	/**
	 * 设置IjkVideoView 对象，如果没有设置则在码率按钮切换码率的操作中会报错
	 * @param ijkVideoView
	 */
	public void setIjkVideoView(IjkVideoView ijkVideoView) {
		this.ijkVideoView = ijkVideoView;
	}
	
	@Override
	public void setViewBitRate(String vid, int bitRate) {
		
	}
}
