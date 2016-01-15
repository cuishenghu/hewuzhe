package com.hewuzhe.presenter;

import com.hewuzhe.model.Res;
import com.hewuzhe.model.Video;
import com.hewuzhe.presenter.base.RefreshAndLoadMorePresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.view.Videos2View;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by xianguangjin on 15/12/22.
 */
public class VideosPresenter extends RefreshAndLoadMorePresenter<Videos2View> {


    /**
     * @param page
     * @param count 视频列表
     */
    public void getData(int page, int count) {
        int catId = view.getData();
//        Subscription subscription = NetEngine.getService()
//                .SelectVideoByCategory((page - 1) * count, count, catId)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SB<Res<ArrayList<Video>>>() {
//                    @Override
//                    public void next(Res<ArrayList<Video>> res) {
//                        if (res.code == C.OK) {
//                            view.bindData(res.data);
//                        } else {
//
//
//                        }
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                        view.refresh(false);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        view.refresh(false);
//                    }
//                });

//        addSubscription(subscription);

        NetEngine.getService()
                .SelectVideoByCategory((page - 1) * count, count, catId)
                .enqueue(new Callback<Res<ArrayList<Video>>>() {
                    @Override
                    public void onResponse(Response<Res<ArrayList<Video>>> response, Retrofit retrofit) {
                        Res<ArrayList<Video>> res = response.body();
                        if (res.code == C.OK) {
                            view.bindData(res.data);
                        } else {


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
