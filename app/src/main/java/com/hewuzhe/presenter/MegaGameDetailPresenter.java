package com.hewuzhe.presenter;

import android.view.View;

import com.hewuzhe.model.MegaGame;
import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.MegaGameDetailView;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 15/12/31.
 */
public class MegaGameDetailPresenter extends BasePresenterImp<MegaGameDetailView> {

    public void getDetail(int id) {
        Subscription subscription = NetEngine.getService()
                .SelectMatchDetailWithIsJoin(new SessionUtil(view.getContext()).getUserId(), id)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        view.showDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<MegaGame>>() {
                    @Override
                    public void next(Res<MegaGame> res) {
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

    public void CancleJoinMatch(int id, final View v) {
        Subscription subscription = NetEngine.getService()
                .CancleJoinMatch(new SessionUtil(view.getContext()).getUserId(), id)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        view.showDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res>() {
                    @Override
                    public void next(Res res) {
                        if (res.code == C.OK) {
                            view.refresh();
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
