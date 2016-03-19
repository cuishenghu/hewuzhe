package com.hewuzhe.presenter;

import com.hewuzhe.model.Res;
import com.hewuzhe.model.Video;
import com.hewuzhe.presenter.base.RefreshAndLoadMorePresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.OneFragmentView;

import java.util.ArrayList;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 15/12/25.
 */
public class OneFragmentPresenter extends RefreshAndLoadMorePresenter<OneFragmentView> {

    public void getData(final int page, final int count) {
        String path = view.getData();
        /**
         * 如果path的值是SelectGuanzhuVideoList获取关注的视频
         */
        if (path.equals("SelectGuanzhuVideoList")) {
            SelectGuanzhuVideoList(path, page, count);
        } else {//否则是获取最新和热门的视频的列表
            Subscription subscription = NetEngine.getService()
                    .getVideos(path, (page - 1) * count, count, "")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SB<Res<ArrayList<Video>>>() {
                        @Override
                        public void next(Res<ArrayList<Video>> res) {
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
                            view.refresh(false);

                        }
                    });

            addSubscription(subscription);
        }
    }

    public void SelectGuanzhuVideoList(String path, final int page, final int count) {
        Subscription subscription = NetEngine.getService()
                .SelectGuanzhuVideoList(path, (page - 1) * count, count, new SessionUtil(view.getContext()).getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<Video>>>() {
                    @Override
                    public void next(Res<ArrayList<Video>> res) {
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
                        view.refresh(false);

                    }
                });

        addSubscription(subscription);
    }
}
