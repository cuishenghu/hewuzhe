package com.hewuzhe.presenter;

import android.view.View;

import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.ProfileSettingsView;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 16/1/11.
 */
public class ProfileSettingsPresenter extends BasePresenterImp<ProfileSettingsView> {


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
                            view.followSuccess(false);
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


    /**
     * 关注武友
     */
    public void unFollow(final View v) {

        Subscription subscription = NetEngine.getService()
                .DeleteFriend(new SessionUtil(view.getContext()).getUser().Id, view.getData())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res>() {
                    @Override
                    public void next(Res res) {
                        if (res.code == C.OK) {
                            view.followSuccess(false);
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
