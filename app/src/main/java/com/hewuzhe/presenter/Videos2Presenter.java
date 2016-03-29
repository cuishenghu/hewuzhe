package com.hewuzhe.presenter;

import com.hewuzhe.model.Res;
import com.hewuzhe.model.Video;
import com.hewuzhe.presenter.base.RefreshAndLoadMorePresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.view.Videos2View;

import java.util.ArrayList;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 15/12/22.
 */
public class Videos2Presenter extends RefreshAndLoadMorePresenter<Videos2View> {


    public void getData(final int page, final int count) {
        int catId = view.getData();
        Subscription subscription = NetEngine.getService()
                .GetOnlineStudyList((page - 1) * count, count, catId, "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<Video>>>() {
                    @Override
                    public void next(Res<ArrayList<Video>> res) {
                        if (res.code == C.OK) {
                            view.bindData(res.data);
                            setDataStatus(page, count, res);

                        } else {


                        }
                    }

                    @Override
                    public void onCompleted() {
                        view.refresh(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.refresh(false);
                    }
                });

        addSubscription(subscription);

    }

    public void SelectVideoByRecommendCategory(final int page, final int count,int userid) {
        int catId = view.getData();
        Subscription subscription = NetEngine.getService()
                .SelectVideoByRecommendCategory(userid,(page - 1) * count, count, catId, "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<Video>>>() {
                    @Override
                    public void next(Res<ArrayList<Video>> res) {
                        if (res.code == C.OK) {
                            view.bindData(res.data);
                            setDataStatus(page, count, res);

                        } else {


                        }
                    }

                    @Override
                    public void onCompleted() {
                        view.refresh(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.refresh(false);
                    }
                });

        addSubscription(subscription);

    }

 public void SelectVideoByCategory(final int page, final int count) {
        int catId = view.getData();
        Subscription subscription = NetEngine.getService()
                .SelectVideoByCategory((page - 1) * count, count, catId, "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<Video>>>() {
                    @Override
                    public void next(Res<ArrayList<Video>> res) {
                        if (res.code == C.OK) {
                            view.bindData(res.data);
                            setDataStatus(page, count, res);

                        } else {


                        }
                    }

                    @Override
                    public void onCompleted() {
                        view.refresh(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.refresh(false);
                    }
                });

        addSubscription(subscription);

    }
}
