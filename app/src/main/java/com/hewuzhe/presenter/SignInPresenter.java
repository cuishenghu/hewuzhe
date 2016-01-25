package com.hewuzhe.presenter;

import android.content.Intent;
import android.view.View;

import com.hewuzhe.model.Res;
import com.hewuzhe.model.UP;
import com.hewuzhe.model.User;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.activity.MainActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.view.SignInView;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 15/12/14.
 */
public class SignInPresenter extends BasePresenterImp<SignInView> {

    private User user;

    public void signin(final View v) {
        user = view.getData();
        if (user != null) {
            if (StringUtil.isEmpty(user.UserName)) {
                view.snb("用户名不能为空", v);
                return;
            }

            if (StringUtil.isEmpty(user.PassWord)) {
                view.snb("密码不能为空", v);
                return;
            }

            Subscription subscription = NetEngine.getService()
                    .Login(user.UserName, user.PassWord)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            view.showDialog();
                        }
                    })
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Res<User>>() {
                        @Override
                        public void onCompleted() {
                            view.dismissDialog();
                        }

                        @Override
                        public void onError(Throwable e) {
                            view.dismissDialog();
                            view.snb("登录失败", v);
                        }

                        @Override
                        public void onNext(Res<User> userRes) {
                            if (userRes.code == C.OK) {
                                /**保存数据**/
                                SessionUtil sessionUtil = new SessionUtil(view.getContext());
                                UP up = new UP(user.UserName, user.PassWord);
                                sessionUtil.putUP(up);

                                user = userRes.data;
                                sessionUtil.putUser(user);

                                view.getContext().startActivity(new Intent(view.getContext(), MainActivity.class));
                            } else {
                                view.snb("登录失败", v);
                            }

                        }
                    });
            addSubscription(subscription);
        } else {
            view.snb("登录失败__", v);
            view.dismissDialog();
        }

    }


    public void otherSigin(String nickName, String openid, final View v) {
        Subscription subscription = NetEngine.getService()
                .LoginByOther(openid, nickName)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        view.showDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<User>>() {
                    @Override
                    public void next(Res<User> res) {
                        if (res.code == C.OK) {
                            /**保存数据**/
                            user = res.data;
                            new SessionUtil(view.getContext()).putUser(user);

                            view.getContext().startActivity(new Intent(view.getContext(), MainActivity.class));
                        } else {
                            view.snb("登录失败", v);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        view.dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.snb("登录失败__", v);
                        view.dismissDialog();
                    }
                });
        addSubscription(subscription);
    }
}
