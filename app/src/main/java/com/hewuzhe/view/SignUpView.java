package com.hewuzhe.view;

import android.os.Handler;

import com.hewuzhe.model.PwdModel;
import com.hewuzhe.view.base.BaseView;

/**
 * Created by xianguangjin on 15/12/17.
 */
public interface SignUpView extends BaseView {

    PwdModel getPwd();

    String getCode();

    String getPhoneNum();

    void updateStatus();

    Handler getHandler();

}
