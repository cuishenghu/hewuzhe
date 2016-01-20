package com.hewuzhe.presenter;

import com.hewuzhe.model.Address;
import com.hewuzhe.model.Dojo;
import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.RefreshAndLoadMorePresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.view.DojoRecommendView;

import java.util.ArrayList;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 15/12/25.
 */
public class DojoRecommendPresenter extends RefreshAndLoadMorePresenter<DojoRecommendView> {

    /**
     * @param cityName
     * @param lat
     * @param lng
     * @param page
     * @param count
     * @desc 获取数据
     */
    public void getData(String cityName, String lat, String lng, final int page, final int count) {

        Subscription subscription = NetEngine.getService()
                .SelectWuGuanPageByCityName((page - 1) * count, count, cityName, lat, lng)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<Dojo>>>() {
                    @Override
                    public void next(Res<ArrayList<Dojo>> res) {
                        if (res.code == C.OK) {
                            view.bindData(res.data);
                            setDataStatus(page, count, res);
                            view.setNodata(res.recordcount);
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

    public void getData(final int page, final int count) {
        int cityId = view.getData();
        Subscription subscription = NetEngine.getService()
                .SelectWuGuanPageByCityId((page - 1) * count, count, cityId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<Dojo>>>() {
                    @Override
                    public void next(Res<ArrayList<Dojo>> res) {
                        if (res.code == C.OK) {
                            view.bindData(res.data);
                            setDataStatus(page, count, res);
                            view.setNodata(res.recordcount);

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

    public void getCitys() {
        Subscription subscription = NetEngine.getService()
                .GetCity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<Address>>>() {
                    @Override
                    public void next(Res<ArrayList<Address>> res) {

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
