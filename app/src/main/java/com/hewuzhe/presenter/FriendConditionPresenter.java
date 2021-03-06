package com.hewuzhe.presenter;

import com.hewuzhe.model.FriendCondition;
import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.adapter.ConditionPresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.FriendsConditionView;

import java.util.ArrayList;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 15/12/31.
 */
public class FriendConditionPresenter extends ConditionPresenter<FriendsConditionView> {

    public void getData(final int page, final int count) {

        Subscription subscription = NetEngine.getService()
                .GetDongtaiPageByFriends((page - 1) * count, count, new SessionUtil(view.getContext()).getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<FriendCondition>>>() {
                    @Override
                    public void next(Res<ArrayList<FriendCondition>> res) {
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


    public void GetNoReadCommentNumByUserId() {

        Subscription subscription = NetEngine.getService()
                .GetNoReadCommentNumByUserId(new SessionUtil(view.getContext()).getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<String>>() {
                    @Override
                    public void next(Res<String> res) {
                        if (res.code == C.OK) {
                            view.updateFriendNoReadNum(res.count, res.data);
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
