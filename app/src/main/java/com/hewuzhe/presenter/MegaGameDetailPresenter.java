package com.hewuzhe.presenter;

import com.hewuzhe.model.MegaGame;
import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.view.base.SetView;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by xianguangjin on 15/12/31.
 */
public class MegaGameDetailPresenter extends BasePresenterImp<SetView<MegaGame>> {

    public void getDetail(int id) {
//        Subscription subscription = NetEngine.getService()
//                .SelectMatchDetail(id)
//                .subscribeOn(Schedulers.io())
//                .doOnSubscribe(() -> view.showDialog())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SB<Res<MegaGame>>() {
//                    @Override
//                    public void next(Res<MegaGame> res) {
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
                .SelectMatchDetail(id)
                .enqueue(new Callback<Res<MegaGame>>() {
                    @Override
                    public void onResponse(Response<Res<MegaGame>> response, Retrofit retrofit) {
                        Res<MegaGame> res = response.body();
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
}
