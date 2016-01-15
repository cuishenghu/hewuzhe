package com.hewuzhe.presenter;

import com.hewuzhe.model.Plan;
import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.view.PlanDetialView;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by xianguangjin on 16/1/5.
 */
public class PlanDetailPresenter extends BasePresenterImp<PlanDetialView> {

    private Res<Plan> res;

    public void getPlanDetail() {
        Integer id = view.getData();

//        Subscription subscription = NetEngine.getService()
//                .getPlanDetail(id)
//                .subscribeOn(Schedulers.io())
//                .doOnSubscribe(() -> view.showDialog())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SB<Res<Plan>>() {
//                    @Override
//                    public void next(Res<Plan> res) {
//                        if (res.code == C.OK) {
//                            view.setData(res.data);
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


        view.showDialog();
        NetEngine.getService()
                .getPlanDetail(id)
                .enqueue(new Callback<Res<Plan>>() {
                    @Override
                    public void onResponse(Response<Res<Plan>> response, Retrofit retrofit) {
                        res = response.body();
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
