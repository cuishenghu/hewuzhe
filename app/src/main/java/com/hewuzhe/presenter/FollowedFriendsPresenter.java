package com.hewuzhe.presenter;

import com.hewuzhe.model.Friend;
import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.RefreshAndLoadMorePresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.FollowedFriendsView;

import java.util.ArrayList;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 16/1/15.
 */
public class FollowedFriendsPresenter extends RefreshAndLoadMorePresenter<FollowedFriendsView> {
    /**
     * 获取数据
     *
     * @param page
     * @param count
     */
    @Override
    public void getData(final int page, final int count) {

        Subscription subscription = NetEngine.getService()
                .SelectFriended(view.getData())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<Friend>>>() {
                    @Override
                    public void next(Res<ArrayList<Friend>> res) {
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

    /**
     * 关注武友
     *
     * @param id
     */
    public void follow(int id, final int pos) {
        Subscription sub = NetEngine.getService()
                .SaveFriend(new SessionUtil(view.getContext()).getUser().Id, id)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        view.showDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res>() {
                    @Override
                    public void next(Res res) {
                        if (res.code == C.OK) {
                            view.updatePosItem(pos, true);
                        } else {


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
        addSubscription(sub);


    }

    /**
     * 取消关注武友
     *
     * @param id
     */
    public void unFollow(int id, final int pos) {
        Subscription sub = NetEngine.getService()
                .DeleteFriend(new SessionUtil(view.getContext()).getUser().Id, id)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        view.showDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res>() {
                    @Override
                    public void next(Res res) {
                        if (res.code == C.OK) {
                            view.updatePosItem(pos, false);
                        } else {


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
        addSubscription(sub);


    }


    public void isWuyou(int id) {
        Subscription subscription = NetEngine.getService()
                .IsWuyou(new SessionUtil(view.getContext()).getUserId(), id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<Boolean>>() {
                    @Override
                    public void next(Res<Boolean> res) {
                        if (res.code == C.OK) {
                            view.isWuYou(res.data);
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
