package com.hewuzhe.presenter;

import android.content.Context;

import com.hewuzhe.model.ChatList;
import com.hewuzhe.model.Friend;
import com.hewuzhe.model.RecommendUser;
import com.hewuzhe.model.Res;
import com.hewuzhe.model.WrapFriend;
import com.hewuzhe.presenter.base.RefreshAndLoadMorePresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.view.ChatView;

import java.util.ArrayList;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zycom on 2016/3/29.
 */
public class ChatPresenter extends RefreshAndLoadMorePresenter<ChatView> {
    @Override
    public void getData(final int page, final int count) {
        Subscription subscription = NetEngine.getService()
                .SelectRecommendDongTai((page - 1) * count, count, new SessionUtil(view.getContext()).getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<ChatList>>>() {
                    @Override
                    public void next(Res<ArrayList<ChatList>> res) {
                        if (res.code == C.OK) {
                            view.bindTuijian(res.data);
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

    public void SelectFriends(Context context){
        Subscription subscription = NetEngine.getService()
                .GetFriendForKeyValue(new SessionUtil(context).getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<WrapFriend>>>() {
                    @Override
                    public void next(Res<ArrayList<WrapFriend>> res) {
                        if (res.code == C.OK) {
                            view.bindCount(res.data.size());
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

    public void SelectRecommendUser(){
        Subscription subscription = NetEngine.getService()
                .SelectRecommendUser(new SessionUtil(view.getContext()).getUserId(),2,0,10,"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<RecommendUser>>>() {
                    @Override
                    public void next(Res<ArrayList<RecommendUser>> res) {
                        if (res.code == C.OK) {
                            view.bindUsers(res.data);
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
