package com.hewuzhe.presenter;

import android.view.View;

import com.hewuzhe.model.PwdModel;
import com.hewuzhe.model.Res;
import com.hewuzhe.model.User;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.activity.SignupProfileActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.view.SignUpView;
import com.socks.library.KLog;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 15/12/17.
 */
public class SignUpPresenter extends BasePresenterImp<SignUpView> {


    public EventHandler eh;
    private String phoneNum;
    private String code;
    private View v;
    private PwdModel pwdModel;

    public void getCode(View v) {
        this.v = v;
        phoneNum = view.getPhoneNum();
        if (!StringUtil.isMobile(phoneNum)) {
            view.snb("请填写正确的手机号", v);
            return;
        }
        view.showDialog();
        SMSSDK.getVerificationCode(C.DEFAULT_COUNTRY_ID, phoneNum);
    }


    public void Register(final View v) {
        this.v = v;
        code = view.getCode();
        phoneNum = view.getPhoneNum();

        if (StringUtil.isEmpty(code)) {
            view.snb("请填写验证码", v);
            return;
        }

        if (!StringUtil.isMobile(phoneNum)) {
            view.snb("请填写正确的手机号", v);
            return;
        }

        pwdModel = view.getPwd();
        if (StringUtil.isEmpty(pwdModel.pwd)) {
            view.snb("密码不能为空", v);
            return;
        }

        if (StringUtil.isEmpty(pwdModel.pwdAgain)) {
            view.snb("确认密码不能为空", v);
            return;
        }

        if (pwdModel.pwdAgain.length() < 6) {
            view.snb("密码不能小于6位数", v);
            return;
        }

        if (pwdModel.pwd.length() < 6) {
            view.snb("密码不能小于6位数", v);
            return;
        }

        if (!StringUtil.equal(pwdModel.pwdAgain, pwdModel.pwd)) {
            view.snb("两次密码输入不一致", v);
            return;
        }

        view.showDialog();
        SMSSDK.submitVerificationCode(C.DEFAULT_COUNTRY_ID, phoneNum, code);

//        Subscription subscription = NetEngine.getService()
//                .RegisterAndLogin(phoneNum, pwdModel.pwd)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SB<Res>() {
//                    @Override
//                    public void next(Res res) {
//                        if (res.code == C.OK) {
//                            view.snb("注册成功", v);
//
//                            signin(phoneNum, pwdModel.pwd, v);
//                        } else if (res.code == 1) {
//                            view.snb("此号码已经注册", v);
//                        } else {
//                            view.snb("注册失败", v);
//                        }
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                        view.dismissDialog();
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        view.dismissDialog();
//                        view.snb("注册失败", v);
//                    }
//                });


    }


    /**
     * 初始化mob短信
     */
    public void initSmsSdk() {
        /**初始化短信sdk**/
        SMSSDK.initSDK(view.getContext(), C.MOB_KEY, C.MOB_SECRET);
        eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                KLog.d(event + "--" + result + "--" + data);
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        /**
                         * 验证成功
                         * */
                        view.getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                /**注册**/
                                Subscription subscription = NetEngine.getService()
                                        .RegisterAndLogin(phoneNum, pwdModel.pwd)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new SB<Res>() {
                                            @Override
                                            public void next(Res res) {
                                                if (res.code == C.OK) {
                                                    view.snb("注册成功", v);

                                                    signin(phoneNum, pwdModel.pwd, v);
                                                } else if (res.code == 1) {
                                                    view.snb("此号码已经注册", v);
                                                } else {
                                                    view.snb("注册失败", v);
                                                }
                                            }

                                            @Override
                                            public void onCompleted() {
                                                view.dismissDialog();

                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                view.dismissDialog();
                                                view.snb("注册失败", v);
                                            }
                                        });


                                addSubscription(subscription);


                            }
                        });

                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        /**
                         * 获取验证码成功
                         * */
                        view.getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                view.dismissDialog();
                                view.updateStatus();
                            }
                        });

                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表

                    }
                } else {
                    view.dismissDialog();
                    view.snb("操作失败", v);
                    ((Throwable) data).printStackTrace();
                }
            }
        };

        SMSSDK.registerEventHandler(eh); //注册短信回调
    }

    public void unRegistSMS() {
        SMSSDK.unregisterEventHandler(eh);
    }

    @Override
    public void detachView() {
        super.detachView();
        unRegistSMS();
    }

    public void signin(String phoneNum, String pwd, final View v) {
        if (StringUtil.isEmpty(phoneNum)) {
            view.snb("用户名不能为空", v);
            return;
        }

        if (StringUtil.isEmpty(pwd)) {
            view.snb("密码不能为空", v);
            return;
        }

        Subscription subscription = NetEngine.getService()
                .Login(phoneNum, pwd)
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
                            User user = userRes.data;
                            new SessionUtil(view.getContext()).putUser(user);
                            view.startActivity(SignupProfileActivity.class);
                        } else {
                            view.snb("登录失败", v);
                        }

                    }
                });

        addSubscription(subscription);

    }

    public void changePwd(View v) {


    }
}