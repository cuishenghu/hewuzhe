package com.hewuzhe.presenter;

import com.hewuzhe.model.Res;
import com.hewuzhe.model.Video;
import com.hewuzhe.presenter.base.RefreshAndLoadMorePresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.view.OneFragmentView;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by xianguangjin on 15/12/25.
 */
public class OneFragmentPresenter extends RefreshAndLoadMorePresenter<OneFragmentView> {

    public void getData(final int page, final int count) {
        String path = view.getData();
//        Subscription subscription = NetEngine.getService()
//                .getVideos(path, (page - 1) * count, count)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SB<Res<ArrayList<Video>>>() {
//                    @Override
//                    public void next(Res<ArrayList<Video>> res) {
//                        if (res.code == C.OK) {
//                            view.bindData(res.data);
//                            setDataStatus(page, count, res);
//                        }
//                    }
//
//                    @Override
//                    public void onCompleted() {
//
//                        view.refresh(false);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        view.refresh(false);
//
//                    }
//                });

//        addSubscription(subscription);

        NetEngine.getService()
                .getVideos(path, (page - 1) * count, count)
                .enqueue(new Callback<Res<ArrayList<Video>>>() {
                    @Override
                    public void onResponse(Response<Res<ArrayList<Video>>> response, Retrofit retrofit) {
                        Res<ArrayList<Video>> res = response.body();
                        if (res.code == C.OK) {
                            view.bindData(res.data);
                            setDataStatus(page, count, res);
                        }
                        view.refresh(false);

                    }

                    @Override
                    public void onFailure(Throwable t) {
                        view.refresh(false);

                    }
                });
    }

}
