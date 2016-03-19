package com.hewuzhe.presenter;

import com.hewuzhe.model.Res;
import com.hewuzhe.model.StudyOnlineCatItem;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.view.StudyOnlineFragView;

import java.util.ArrayList;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 16/1/16.
 */
public class StudyOnlineFragPresenter extends BasePresenterImp<StudyOnlineFragView> {
    public void getCates(int catId) {

        Subscription subscription = NetEngine.getService()
                .GetSmallCateForOnlineStudy(catId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<StudyOnlineCatItem>>>() {
                    @Override
                    public void next(Res<ArrayList<StudyOnlineCatItem>> res) {
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

    public void GetChannel() {

        Subscription subscription = NetEngine.getService()
                .GetChannel()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<StudyOnlineCatItem>>>() {
                    @Override
                    public void next(Res<ArrayList<StudyOnlineCatItem>> res) {
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

    public void SelectCategory() {

        Subscription subscription = NetEngine.getService()
                .SelectCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<StudyOnlineCatItem>>>() {
                    @Override
                    public void next(Res<ArrayList<StudyOnlineCatItem>> res) {
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
