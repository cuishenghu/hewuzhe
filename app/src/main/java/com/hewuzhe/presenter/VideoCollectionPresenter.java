package com.hewuzhe.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hewuzhe.model.Res;
import com.hewuzhe.model.Video;
import com.hewuzhe.presenter.base.RefreshAndLoadMorePresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.VideoCollectionsView;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 15/12/25.
 */
public class VideoCollectionPresenter extends RefreshAndLoadMorePresenter<VideoCollectionsView> {

    private int _NeedCount = 0;
    private int _HasCount = 0;

    public void getData(final int page, final int count) {
        int id = view.getData();
//        Subscription subscription = NetEngine.getService()
//                .SelectFavoriteByUserId((page - 1) * count, count, true, id)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SB<Res<ArrayList<Video>>>() {
//                    @Override
//                    public void next(Res<ArrayList<Video>> res) {
//                        if (res.code == C.OK) {
//                            view.bindData(res.data);
//                            setDataStatus(page, count, res);
//                        } else {
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
                .SelectFavoriteByUserId((page - 1) * count, count, true, id)
                .enqueue(new Callback<Res<ArrayList<Video>>>() {
                    @Override
                    public void onResponse(Response<Res<ArrayList<Video>>> response, Retrofit retrofit) {
                        Res<ArrayList<Video>> res = response.body();
                        if (res.code == C.OK) {
                            view.bindData(res.data);
                            setDataStatus(page, count, res);
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

    public void deletePlan(int MessageId, int size, RecyclerView v) {
        this._NeedCount = size;
        this._HasCount = 0;
        collectAndFavorateCance(MessageId, 1, v);
    }


    public void collectAndFavorateCance(int id, final int flag, final View v) {
        view.showDialog();
        NetEngine.getService()
                .MessageRepeatAndFavoriteCancel(id, new SessionUtil(view.getContext()).getUser().Id, flag)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res>() {
                    @Override
                    public void next(Res res) {
                        if (res.code == C.OK) {
                            _HasCount++;
                            if (_HasCount >= _NeedCount) {
                                view.collectAndOther();
                            }

                        } else {
                            view.snb("操作失败", v);
                            _NeedCount--;
                        }
                    }

                    @Override
                    public void onCompleted() {
                        view.dismissDialog();


                    }

                    @Override
                    public void onError(Throwable e) {
                        _NeedCount--;
                        view.dismissDialog();
                        view.snb("操作失败", v);


                    }
                });
    }


}
