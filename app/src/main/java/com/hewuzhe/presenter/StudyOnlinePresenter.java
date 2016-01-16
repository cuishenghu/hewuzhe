package com.hewuzhe.presenter;

import com.hewuzhe.model.Res;
import com.hewuzhe.model.StudyOnlineCate;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.view.StudyOnlineView;

import java.util.ArrayList;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 16/1/16.
 */
public class StudyOnlinePresenter extends BasePresenterImp<StudyOnlineView> {
    public void getCates() {

        Subscription subscription = NetEngine.getService()
                .GetBigCateForOnlineStudy()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<StudyOnlineCate>>>() {
                    @Override
                    public void next(Res<ArrayList<StudyOnlineCate>> res) {
                        if (res.code == C.OK) {
                            view.bindData(res.data);
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
