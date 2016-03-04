package com.hewuzhe.presenter;

import android.widget.Toast;

import com.hewuzhe.model.ChangePwd;
import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.view.ChangePwdView;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 16/2/16.
 */
public class ChangePwdPresenter extends BasePresenterImp<ChangePwdView> {


    public void changePwd() {
        ChangePwd changePwd = view.getData();

        if (StringUtil.isEmpty(changePwd.oldPwd)) {
            Toast.makeText(view.getContext(), "请正确填写原密码", Toast.LENGTH_SHORT).show();
            return;
        }

        if (StringUtil.isEmpty(changePwd.newPwd)) {
            Toast.makeText(view.getContext(), "请正确填写新密码", Toast.LENGTH_SHORT).show();
            return;
        }

        if (StringUtil.isEmpty(changePwd.newPwdAgain)) {
            Toast.makeText(view.getContext(), "请正确填写新密码", Toast.LENGTH_SHORT).show();
            return;
        }


        if (!changePwd.newPwdAgain.equals(changePwd.newPwd)) {
            Toast.makeText(view.getContext(), "两次密码输入不一致", Toast.LENGTH_SHORT).show();
            return;
        }


        Subscription subscription = NetEngine.getService()
                .ChangePassWord(new SessionUtil(view.getContext()).getUserId(), changePwd.oldPwd, changePwd.newPwdAgain)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res>() {
                    @Override
                    public void next(Res res) {
                        if (res.code == C.OK) {

//                            SessionUtil sessionUtil = new SessionUtil(view.getContext());
//                            UP up = new UP(phoneNum, pwdModel.pwd);
//                            sessionUtil.putUP(up);
//
                            Toast.makeText(view.getContext(), "修改成功", Toast.LENGTH_SHORT).show();
                            view.finishActivity();
                        } else {
                            Toast.makeText(view.getContext(), "修改失败", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(view.getContext(), "修改失败", Toast.LENGTH_SHORT).show();

                    }
                });

        addSubscription(subscription);
    }
}
