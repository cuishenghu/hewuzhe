package com.hewuzhe.presenter.common;

import com.hewuzhe.model.Address;
import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.RefreshAndLoadMorePresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.view.common.AreaView;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by xianguangjin on 15/12/31.
 */
public abstract class AreaPresenter<V extends AreaView> extends RefreshAndLoadMorePresenter<V> {

    public void getProvinces() {
//        Subscription subscription = NetEngine.getService()
//                .getProvince()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SB<Res<ArrayList<Address>>>() {
//                    @Override
//                    public void next(Res<ArrayList<Address>> res) {
//                        if (res.code == C.OK) {
//                            view.setProvinces(res.data);
//                        }
//                    }
//
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//                });
//        addSubscription(subscription);


        NetEngine.getService()
                .getProvince()
                .enqueue(new Callback<Res<ArrayList<Address>>>() {
                    @Override
                    public void onResponse(Response<Res<ArrayList<Address>>> response, Retrofit retrofit) {
                        Res<ArrayList<Address>> res = response.body();
                        if (res.code == C.OK) {
                            view.setProvinces(res.data);
                        }

                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });

    }

    public void getCitys(int provinceId) {
//        Subscription subscription = NetEngine.getService()
//                .GetCityByProvince(provinceId)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SB<Res<ArrayList<Address>>>() {
//                    @Override
//                    public void next(Res<ArrayList<Address>> res) {
//                        if (res.code == C.OK) {
//                            view.setCitys(res.data);
//                        }
//                    }
//
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//                });
//        addSubscription(subscription);

        NetEngine.getService()
                .GetCityByProvince(provinceId)
                .enqueue(new Callback<Res<ArrayList<Address>>>() {
                    @Override
                    public void onResponse(Response<Res<ArrayList<Address>>> response, Retrofit retrofit) {
                        Res<ArrayList<Address>> res = response.body();
                        if (res.code == C.OK) {
                            view.setCitys(res.data);
                        }

                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
    }

    public void getDistricts(int cityId) {
//        Subscription subscription = NetEngine.getService()
//                .GetCountyByCity(cityids)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SB<Res<ArrayList<Address>>>() {
//                    @Override
//                    public void next(Res<ArrayList<Address>> res) {
//                        if (res.code == C.OK) {
//                            view.setDistricts(res.data);
//                        }
//                    }
//
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//                });
//        addSubscription(subscription);

        NetEngine.getService()
                .GetCountyByCity(cityId)
                .enqueue(new Callback<Res<ArrayList<Address>>>() {
                    @Override
                    public void onResponse(Response<Res<ArrayList<Address>>> response, Retrofit retrofit) {
                        Res<ArrayList<Address>> res = response.body();
                        if (res.code == C.OK) {
                            view.setDistricts(res.data);
                        }

                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
    }
}
