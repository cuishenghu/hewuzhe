package com.hewuzhe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hewuzhe.R;
import com.hewuzhe.model.User;
import com.hewuzhe.presenter.SignInPresenter;
import com.hewuzhe.ui.base.BaseActivity;
import com.hewuzhe.ui.widget.LoadingDialog;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.view.SignInView;

import java.util.HashMap;

import butterknife.Bind;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;


public class SignInActivity extends BaseActivity<SignInPresenter> implements SignInView, PlatformActionListener, Handler.Callback {

    private static final int MSG_USERID_FOUND = 100;

    private static final int MSG_SMSSDK_CALLBACK = 1;
    private static final int MSG_AUTH_CANCEL = 2;
    private static final int MSG_AUTH_ERROR = 3;
    private static final int MSG_AUTH_COMPLETE = 4;
    @Bind(R.id.img_title)
    ImageView imgTitle;
    @Bind(R.id.tv_forget_pwd)
    TextView tvForgetPwd;
    @Bind(R.id.tv_sign_up)
    TextView tvSignUp;
    @Bind(R.id.tv_sign_in)
    TextView tvSignIn;
    @Bind(R.id.lay_qq)
    LinearLayout layQq;
    @Bind(R.id.lay_wx)
    LinearLayout layWx;
    @Bind(R.id.edt_username)
    EditText edtUsername;
    @Bind(R.id.edt_pwd)
    EditText edtPwd;
    private LoadingDialog loadingDialog;

    private Handler handler;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_sign_in;
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });
        tvForgetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ForgetPwdActivity.class, new Bun().putString("title", "忘记密码").ok());
            }
        });
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.signin(view);
            }
        });


        layQq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Platform wechat = ShareSDK.getPlatform(getContext(), QZone.NAME);
                authorize(wechat);

            }
        });

        layWx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Platform wechat = ShareSDK.getPlatform(getContext(), Wechat.NAME);
                authorize(wechat);
            }
        });

    }


    /**
     * @param savedInstanceState 缓存数据
     *                           <p/>
     *                           初始化一些事情
     */
    @Override
    protected void initThings(Bundle savedInstanceState) {
//       showAnim();

        ShareSDK.initSDK(this);

        handler = new Handler(this);
    }

    /**
     * 绑定Presenter
     */
    @Override
    public SignInPresenter createPresenter() {
        return new SignInPresenter();
    }


//    private void showAnim() {
//        new AnimatorManager.Builder(imgTitle)
//                .putEffect(ViewType.ScaleInLeft.getAnimator())
//                .build()
//                .animate();
//    }

    @Override
    public void showDialog() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(SignInActivity.this, "正在登录...");
        }
        loadingDialog.show();
    }

    @Override
    public void dismissDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

    @Override
    public void startActivity(Class clazz) {

    }

    @Override
    public User getData() {
        User user = new User();
        user.UserName = edtUsername.getText().toString().trim();
        user.PassWord = edtPwd.getText().toString().trim();
        return user;
    }


    private void authorize(Platform plat) {
        if (plat == null) {
//          popupOthers();
            return;
        }
//判断指定平台是否已经完成授权
//        if (plat.isAuthValid()) {
//        String userId = plat.getDb().getUserId();
//            if (userId != null) {
//                handler.sendEmptyMessage(MSG_USERID_FOUND);
////                login(plat.getName(), userId, null);
//                return;
//            }
//        }
        plat.setPlatformActionListener(this);
        // true不使用SSO授权，false使用SSO授权
        plat.SSOSetting(false);
        //获取用户资料
        plat.showUser(null);
    }


    public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
        if (action == Platform.ACTION_USER_INFOR) {
            Message msg = new Message();
            msg.what = MSG_AUTH_COMPLETE;
            msg.obj = new Object[]{platform.getName(), res};
            handler.sendMessage(msg);
        }
    }

    public void onError(Platform platform, int action, Throwable t) {
        if (action == Platform.ACTION_USER_INFOR) {
            handler.sendEmptyMessage(MSG_AUTH_ERROR);
        }
        t.printStackTrace();
    }

    public void onCancel(Platform platform, int action) {
        if (action == Platform.ACTION_USER_INFOR) {
            handler.sendEmptyMessage(MSG_AUTH_CANCEL);
        }
    }

    @SuppressWarnings("unchecked")
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_AUTH_CANCEL: {
                //取消授权
                Toast.makeText(this, R.string.auth_cancel, Toast.LENGTH_SHORT).show();
            }
            break;
            case MSG_AUTH_ERROR: {
                //授权失败
                Toast.makeText(this, R.string.auth_error, Toast.LENGTH_SHORT).show();
            }
            break;
            case MSG_AUTH_COMPLETE: {
                //授权成功
                Toast.makeText(this, R.string.auth_complete, Toast.LENGTH_SHORT).show();
                Object[] objs = (Object[]) msg.obj;
                String platform = (String) objs[0];
                HashMap<String, Object> res = (HashMap<String, Object>) objs[1];

                String nickName = (String) res.get("nickname");
                String openid = (String) res.get("openid");

                presenter.otherSigin(nickName, openid, layWx);

            }
            break;
        }
        return false;
    }
}