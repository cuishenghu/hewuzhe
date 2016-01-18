package com.hewuzhe.presenter;

import com.hewuzhe.model.Cate;
import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.view.FederalConditionView;

import java.util.ArrayList;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 16/1/18.
 */
public class FederalConditionPresenter extends BasePresenterImp<FederalConditionView> {


    public void getCates(String path) {

        Subscription subscription = NetEngine.getService()
                .GetLianmengCate(path)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<Cate>>>() {
                    @Override
                    public void next(Res<ArrayList<Cate>> res) {
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
