package com.hewuzhe.presenter;

import com.hewuzhe.model.Res;
import com.hewuzhe.model.Video;
import com.hewuzhe.presenter.base.RefreshAndLoadMorePresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.view.Videos2View;

import java.util.ArrayList;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
        Subscription subscription = NetEngine.getService()
                .SelectVideoByCategory((page - 1) * count, count, catId, "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<Video>>>() {
                    @Override
                    public void next(Res<ArrayList<Video>> res) {
                        if (res.code == C.OK) {
                            view.bindData(res.data);
                        } else {


                        }
                    }

                    @Override
                    public void onCompleted() {
                        view.refresh(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.refresh(false);
                    }
                });

        addSubscription(subscription);

    }
}
