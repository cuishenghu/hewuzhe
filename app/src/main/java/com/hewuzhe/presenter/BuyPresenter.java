package com.hewuzhe.presenter;

import com.hewuzhe.model.GetChargeRequest;
import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.view.BuyView;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 15/12/30.
 */
public class BuyPresenter extends BasePresenterImp<BuyView> {

    public void getCharge() {

        GetChargeRequest request = view.getData();

//        Subscription subscription = NetEngine.getService()
//                .GetCharge(request.userId, request.channel, request.amount, request.description)
//                .subscribeOn(Schedulers.io())
//                .doOnSubscribe(() -> view.showDialog())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SB<Res<String>>() {
//                    @Override
//                    public void next(Res<String> res) {
//                        if (res.code == C.OK) {
//                            view.toPay(res.data);
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
//
//                    }
//                });
//        addSubscription(subscription);

        NetEngine.getService()
                .GetCharge(request.userId, request.channel, request.amount, request.description, 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<String>>() {
                    @Override
                    public void next(Res<String> res) {
                        if (res.code == C.OK) {
                            view.toPay(res.data);
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
    }

}
