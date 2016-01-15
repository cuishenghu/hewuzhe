package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.model.PwdModel;
import com.hewuzhe.presenter.SignUpPresenter;
import com.hewuzhe.ui.base.ToolBarActivity;
import com.hewuzhe.view.SignUpView;

import butterknife.Bind;

public class SignUpActivity extends ToolBarActivity<SignUpPresenter> implements SignUpView {

    @Bind(R.id.edt_phone_num)
    EditText edtPhoneNum;
    @Bind(R.id.edt_code)
    EditText edtCode;
    @Bind(R.id.btn_get_code)
    TextView btnGetCode;
    @Bind(R.id.edt_pwd)
    EditText edtPwd;
    @Bind(R.id.edt_pwd_again)
    EditText edtPwdAgain;
    @Bind(R.id.btn_ensure)
    Button btnEnsure;
    private Handler mHandler = new Handler();
    private CountDownTimer countDownTimer;

    /**
     * @param savedInstanceState 缓存数据
     *                           <p/>
     *                           初始化一些事情
     */
    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        presenter.initSmsSdk();
    }

    /**
     * 绑定Presenter
     */
    @Override
    public SignUpPresenter createPresenter() {
        return new SignUpPresenter();
    }

    @Override
    public void initListeners() {
        btnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.getCode(view);
            }
        });
        btnEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.Register(view);
            }
        });
    }


    @Override
    protected int provideContentViewId() {
        return R.layout.activity_sign_up;
    }


    @Override
    protected String provideTitle() {
        return "注册";
    }

    @Override
    public PwdModel getPwd() {
        return new PwdModel(edtPwd.getText().toString().trim(), edtPwdAgain.getText().toString().trim());
    }

    @Override
    public String getCode() {
        return edtCode.getText().toString().trim();
    }

    @Override
    public String getPhoneNum() {
        return edtPhoneNum.getText().toString().trim();
    }


    @Override
    public void updateStatus() {
        //更改发送按钮的状态
        btnGetCode.setEnabled(false);
        btnGetCode.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_black));
        btnGetCode.setTextColor(getResources().getColor(R.color.white));
        if (countDownTimer == null) {
            countDownTimer = new CountDownTimer(60 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    btnGetCode.setText(millisUntilFinished / 1000 + "秒");
                }

                @Override
                public void onFinish() {
                    btnGetCode.setText("获取验证码");
                    btnGetCode.setEnabled(true);
                    btnGetCode.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_black));
                    btnGetCode.setTextColor(getResources().getColor(R.color.white));
                }
            };
        }

        countDownTimer.start();
    }

    @Override
    public Handler getHandler() {
        return mHandler;
    }

    @Override
    protected void onDestroy() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        super.onDestroy();
    }
}
