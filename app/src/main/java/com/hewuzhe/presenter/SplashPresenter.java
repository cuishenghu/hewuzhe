package com.hewuzhe.presenter;

import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.activity.MainActivity;
import com.hewuzhe.ui.activity.NavigateActivity;
import com.hewuzhe.ui.activity.SignInActivity;
import com.hewuzhe.utils.SPUtil;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.SplashView;

/**
 * Created by xianguangjin on 15/12/15.
 */
public class SplashPresenter extends BasePresenterImp<SplashView> {


    public void navigate() {
        /**
         * 是否进入引导图*/
        if (!new SPUtil(view.getContext()).open("user").getBoolean("isNotFirst")) {
            view.startActivity(NavigateActivity.class);
        } else {
            /**
             * 判断进入登录还是首页*/
            if (new SessionUtil(view.getContext()).isLogin()) {
                view.startActivity(MainActivity.class);
            } else {
                view.startActivity(SignInActivity.class);
            }
        }
    }
}
