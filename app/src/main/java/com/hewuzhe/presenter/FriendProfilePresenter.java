package com.hewuzhe.presenter;

import android.view.View;

import com.hewuzhe.model.Res;
import com.hewuzhe.model.User;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.FriendProfileView;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 16/1/6.
 */
public class FriendProfilePresenter extends BasePresenterImp<FriendProfileView> {


    public void getUserData() {
        Subscription subscription = NetEngine.getService()
                .SelectMyFriend(view.getData(), new SessionUtil(view.getContext()).getUserId())
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
                            view.setData(res.data);
                        }
                    }

                    @Override
                    public void onCompleted() {

                        view.dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.dismissDialog();

                    }
                });
        addSubscription(subscription);


    }


    public void isWuyou() {
        Subscription subscription = NetEngine.getService()
                .IsWuyou(new SessionUtil(view.getContext()).getUserId(), view.getData())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res>() {
                    @Override
                    public void next(Res res) {
                        if (res.code == C.OK) {

                        }
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

        addSubscription(subscription);

    }

    /**
     * 关注武友
     */
    public void follow(final View v) {

        Subscription subscription = NetEngine.getService()
                .SaveFriend(new SessionUtil(view.getContext()).getUser().Id, view.getData())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res>() {
                    @Override
                    public void next(Res res) {
                        if (res.code == C.OK) {
                            view.snb("已关注", v);

                            view.followSuccess();


                        } else {


                        }

                    }

                    @Override
                    public void onCompleted() {
                        view.dismissDialog();

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.dismissDialog();

                    }
                });
        addSubscription(subscription);
    }


}
