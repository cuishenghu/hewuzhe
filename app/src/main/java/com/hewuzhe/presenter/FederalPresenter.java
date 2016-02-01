package com.hewuzhe.presenter;

import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.FederalView;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 16/1/26.
 */
public class FederalPresenter extends BasePresenterImp<FederalView> {

    public void getNoReads() {


    }


    public void GetNoReadCommentNumByUserId() {

        Subscription subscription = NetEngine.getService()
                .GetNoReadCommentNumByUserId(new SessionUtil(view.getContext()).getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res>() {
                    @Override
                    public void next(Res res) {
                        if (res.code == C.OK) {
                            view.updateFriendNoReadNum(res.count);
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


    public void SelectNoReadMatch() {
        Subscription subscription = NetEngine.getService()
                .SelectNoReadMatch(new SessionUtil(view.getContext()).getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res>() {
                    @Override
                    public void next(Res res) {
                        if (res.code == C.OK) {
                            view.updateMegaGameNoReadNum(res.count);
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

}
