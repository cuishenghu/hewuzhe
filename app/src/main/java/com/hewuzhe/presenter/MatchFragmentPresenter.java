package com.hewuzhe.presenter;

import com.hewuzhe.model.New;
import com.hewuzhe.model.Res;
import com.hewuzhe.model.SaiShiPic;
import com.hewuzhe.presenter.base.RefreshAndLoadMorePresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.MatchFragmentView;

import java.util.ArrayList;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 15/12/25.
 */
public class MatchFragmentPresenter extends RefreshAndLoadMorePresenter<MatchFragmentView> {

    @Override
    public void getData(final int page, final int count) {
        Subscription subscription = NetEngine.getService()
                .SelectRecommendZiXun((page - 1) * count, count, new SessionUtil(view.getContext()).getUserId(), "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<New>>>() {
                    @Override
                    public void next(Res<ArrayList<New>> res) {
                        if (res.code == C.OK) {
                            view.bindData(res.data);
                            setDataStatus(page, count, res);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        view.refresh(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.dismissDialog();
                    }
                });
        addSubscription(subscription);
    }

    public void SelectZixunPicList() {
        Subscription subscription = NetEngine.getService()
                .SelectZixunPicList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<New>>>() {
                    @Override
                    public void next(Res<ArrayList<New>> res) {
                        if (res.code == C.OK) {
                            view.getZixunPicList(res.data);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        view.refresh(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.dismissDialog();
                    }
                });
        addSubscription(subscription);
    }
}
