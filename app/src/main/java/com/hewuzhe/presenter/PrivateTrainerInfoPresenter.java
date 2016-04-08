package com.hewuzhe.presenter;

import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.activity.PrivateTrainerInfoActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zycom on 2016/4/6.
 */
public class PrivateTrainerInfoPresenter extends BasePresenterImp {
    private PrivateTrainerInfoActivity privateTrainerInfoActivity;
    public PrivateTrainerInfoPresenter(PrivateTrainerInfoActivity privateTrainerInfoActivity){
        this.privateTrainerInfoActivity = privateTrainerInfoActivity;
    }
    public PrivateTrainerInfoPresenter(){

    }
    public void isWuyou(int friend) {
        Subscription subscription = NetEngine.getService()
                .IsWuyou(new SessionUtil(privateTrainerInfoActivity).getUserId(), friend)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<Boolean>>() {
                    @Override
                    public void next(Res<Boolean> res) {
                        if (res.code == C.OK) {
                            privateTrainerInfoActivity.getIsWuYou(res.data);
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
