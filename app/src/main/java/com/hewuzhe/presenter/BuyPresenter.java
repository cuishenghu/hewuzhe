package com.hewuzhe.presenter;

import com.google.gson.Gson;
import com.hewuzhe.model.Charge;
import com.hewuzhe.model.GetChargeRequest;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.view.BuyView;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 15/12/30.
 */
public class BuyPresenter extends BasePresenterImp<BuyView> {

    public void getCharge() {

        GetChargeRequest request = view.getData();

        Subscription subscription = NetEngine.getService()
                .GetCharge(request.userId, request.channel, request.amount, request.description, 0)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        view.showDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Charge>() {
                    @Override
                    public void onCompleted() {
                        view.dismissDialog();

                    }

                    @Override
                    public void onError(Throwable e) {

                        view.dismissDialog();
                    }

                    @Override
                    public void onNext(Charge s) {

                        view.toPay(new Gson().toJson(s));
                    }
                });
        addSubscription(subscription);

    }

}
