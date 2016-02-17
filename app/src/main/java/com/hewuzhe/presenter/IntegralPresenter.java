package com.hewuzhe.presenter;

import com.hewuzhe.model.IntegralRecord;
import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.RefreshAndLoadMorePresenter;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.IntegralView;

import java.util.ArrayList;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 15/12/25.
 */
public class IntegralPresenter extends RefreshAndLoadMorePresenter<IntegralView> {

    public void getData(final int page, final int count) {


        Subscription subscription = NetEngine.getService()
                .GetCreditRecord((page - 1) * count, count, new SessionUtil(view.getContext()).getUser().Id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<IntegralRecord>>>() {
                    @Override
                    public void next(Res<ArrayList<IntegralRecord>> res) {
                        view.bindData(res.data);
                        setDataStatus(page, count, res);
                    }

                    @Override
                    public void onCompleted() {
//                        view.refresh(false);
                    }

                    @Override
                    public void onError(Throwable e) {
//                        view.refresh(false);
                    }
                });

        addSubscription(subscription);

    }
}
