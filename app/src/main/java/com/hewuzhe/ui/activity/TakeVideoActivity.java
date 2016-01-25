package com.hewuzhe.ui.activity;

import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.hewuzhe.R;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.base.ToolBarActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widget.MovieRecorderView;
import com.hewuzhe.utils.Bun;

import butterknife.Bind;

public class TakeVideoActivity extends ToolBarActivity {


    @Bind(R.id.movieRecorderView)
    MovieRecorderView movieRecorderView;
    @Bind(R.id.shoot_button)
    Button shootButton;
    @Bind(R.id.btn_save)
    Button btnSave;
    @Bind(R.id.btn_record_again)
    Button btnRecordAgain;
    @Bind(R.id.lay_btns)
    LinearLayout layBtns;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            stopActivity();
        }
    };
    private boolean isFinish = true;


    @Override
    protected int provideContentViewId() {
        return R.layout.activity_take_video;

    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {

        shootButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    movieRecorderView.record(new MovieRecorderView.OnRecordFinishListener() {
                        @Override
                        public void onRecordFinish() {
                            handler.sendEmptyMessage(1);
                        }
                    });
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (movieRecorderView.getTimeCount() > 1)
                        handler.sendEmptyMessage(1);
                    else {
                        // if (mRecorderView.getVecordFile() != null)
                        //	mRecorderView.getVecordFile().delete();
                        movieRecorderView.stop();
                    }
                }
                return true;
            }
        });

        movieRecorderView.setStopListener(new MovieRecorderView.StopListener() {
            @Override
            public void onStop() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        shootButton.setVisibility(View.GONE);
                        layBtns.setVisibility(View.VISIBLE);
                    }
                });
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shootButton.setVisibility(View.VISIBLE);
                layBtns.setVisibility(View.GONE);
                if (getIntentData().getInt(C.WHITCH, C.WHITCH_DEFAUT) == C.WHITCH_DEFAUT) {//发表视频
                    startActivity(PublishVideoActivity.class, new Bun().putString("file_name", movieRecorderView.getmVecordFile().getPath()).putInt("uploadType", C.UPLOAD_TYPE_RECORD).ok());
                } else if (getIntentData().getInt(C.WHITCH, C.WHITCH_DEFAUT) == C.WHITCH_ONE) {//发表动态
                    startActivity(PublishConditionVideoActivity.class, new Bun().putString("file_name", movieRecorderView.getmVecordFile().getPath()).putInt("uploadType", C.UPLOAD_TYPE_RECORD).ok());
                    finishActivity();
                }
            }
        });

        btnRecordAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shootButton.setVisibility(View.VISIBLE);
                layBtns.setVisibility(View.GONE);
            }
        });

    }


    /**
     * 绑定Presenter
     */
    @Override
    public BasePresenterImp createPresenter() {
        return null;
    }


    @Override
    protected String provideTitle() {
        return "拍摄视频";
    }

    private void stopActivity() {
        if (isFinish) {
            movieRecorderView.stop();
            // startActivity(this, mRecorderView.getVecordFile().toString());
        }
    }

    // TODO:你
    @Override
    protected void onDestroy() {
        movieRecorderView.stop();
        super.onDestroy();
    }

}
