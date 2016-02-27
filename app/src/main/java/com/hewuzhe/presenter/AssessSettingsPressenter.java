package com.hewuzhe.presenter;

import android.content.Context;

import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.AssessSettingsView;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/2/27 0027.
 */
public class AssessSettingsPressenter extends BasePresenterImp<AssessSettingsView> {

    public void AssessState(boolean isTuiSong, Context context) {
//       int id= new SessionUtil(context).getUser().Id;
        Subscription subscription = NetEngine.getService()
                .ChangeTuiSong(new SessionUtil(context).getUser().Id, isTuiSong)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res>() {
                    @Override
                    public void next(Res res) {
                        if (res.code == C.OK) {

                        } else {

                        }

                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
        addSubscription(subscription);

    }
}
