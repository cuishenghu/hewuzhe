package com.hewuzhe.presenter;

import com.hewuzhe.model.Dojo;
import com.hewuzhe.model.PrivateTrainerList;
import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.RefreshAndLoadMorePresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.SearchDojoView;
import com.hewuzhe.view.SearchPtlView;

import java.util.ArrayList;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ninos on 2016/5/3.
 */
public class SearchDojiPresenter extends RefreshAndLoadMorePresenter<SearchDojoView> {
    @Override
    public void getData(final int page, final int count) {
        String []cityName = view.getStringData();
        Subscription subscription = NetEngine.getService()
                .SelectWuGuanPageBySearchNew(new SessionUtil(view.getContext()).getUser().Id, cityName[0], cityName[1], (page - 1) * count, count, cityName[5], cityName[3],cityName[4],cityName[6],cityName[7])
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<Dojo>>>() {
                    @Override
                    public void next(Res<ArrayList<Dojo>> res) {
                        if (res.code == C.OK) {
                            view.bindData(res.data);
                            setDataStatus(page, count, res);
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
