package com.hewuzhe.presenter;

import com.hewuzhe.model.Dojo;
import com.hewuzhe.model.OtherImage;
import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.view.DojoDetailView;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by xianguangjin on 15/12/25.
 */
public class DojoDetailPresenter extends BasePresenterImp<DojoDetailView> {

    public void getDetail() {
//        Subscription subscription = NetEngine.getService()
//                .SelectWuGuan(view.getData())
//                .subscribeOn(Schedulers.io())
//                .doOnSubscribe(() -> view.showDialog())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SB<Res<Dojo>>() {
//                    @Override
//                    public void next(Res<Dojo> res) {
//                        if (res.code == C.OK) {
//                            view.setData(res.data);
//                        }
//
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                        view.dismissDialog();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        view.dismissDialog();
//                    }
//                });
//        addSubscription(subscription);


        NetEngine.getService()
                .SelectWuGuan(view.getData())
                .enqueue(new Callback<Res<Dojo>>() {
                    @Override
                    public void onResponse(Response<Res<Dojo>> response, Retrofit retrofit) {
                        Res<Dojo> res = response.body();
                        if (res.code == C.OK) {
                            view.setData(res.data);
                        }
                        view.dismissDialog();

                    }

                    @Override
                    public void onFailure(Throwable t) {
                        view.dismissDialog();

                    }
                });
    }


    public void getOthers() {
//        Subscription subscription = NetEngine.getService()
//                .SelectImageByMessageId(view.getData())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SB<Res<ArrayList<OtherImage>>>() {
//                    @Override
//                    public void next(Res<ArrayList<OtherImage>> res) {
//                        if (res.code == C.OK) {
//                            view.setOthers(res.data);
//                        }
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                        view.dismissDialog();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        view.dismissDialog();
//                    }
//                });

//        addSubscription(subscription);

        NetEngine.getService()
                .SelectImageByMessageId(view.getData())
                .enqueue(new Callback<Res<ArrayList<OtherImage>>>() {
                    @Override
                    public void onResponse(Response<Res<ArrayList<OtherImage>>> response, Retrofit retrofit) {
                        Res<ArrayList<OtherImage>> res = response.body();
                        if (res.code == C.OK) {
                            view.setOthers(res.data);
                        }
                        view.dismissDialog();

                    }

                    @Override
                    public void onFailure(Throwable t) {
                        view.dismissDialog();

                    }
                });
    }
}
