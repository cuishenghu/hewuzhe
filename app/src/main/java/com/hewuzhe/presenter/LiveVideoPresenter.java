package com.hewuzhe.presenter;

import com.hewuzhe.model.LiveVideo;
import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.presenter.base.RefreshAndLoadMorePresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.LiveVideoView;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 16/1/26.
 */
public class LiveVideoPresenter extends RefreshAndLoadMorePresenter<LiveVideoView> {


    public void SelectVideoLive(int id) {
        Subscription subscription = NetEngine.getService()
                .SelectVideoLive(new SessionUtil(view.getContext()).getUserId(), id)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        view.showDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<LiveVideo>>() {
                    @Override
                    public void next(Res<LiveVideo> res) {
                        if (res.code == C.OK) {
                            view.setData(res.data);
                        }
                    }

                    @Override
                    public void onCompleted() {

                        view.dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.dismissDialog();

                    }
                });

        addSubscription(subscription);

    }

    public void getData(final int page, final int count) {
        NetEngine.getService()
            .SelectVideoLiveList((page - 1) * count, count, new SessionUtil(view.getContext()).getUserId())
            .enqueue(new Callback<Res<ArrayList<LiveVideo>>>() {
                @Override
                public void onResponse(Response<Res<ArrayList<LiveVideo>>> response, Retrofit retrofit) {
                    Res<ArrayList<LiveVideo>> res = response.body();
                    if (res.code == C.OK) {
                        view.bindData(res.data);
                        setDataStatus(page, count, res);
                        view.refresh(false);
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    view.refresh(false);
                }
            });

    }

}
