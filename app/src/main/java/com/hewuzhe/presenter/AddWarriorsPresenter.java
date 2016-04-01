package com.hewuzhe.presenter;

import com.hewuzhe.model.Friend;
import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.RefreshAndLoadMorePresenter;
import com.hewuzhe.presenter.common.AreaPresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.AddWarriorsView;
import com.hewuzhe.view.MakeWarriorsView;

import java.util.ArrayList;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 15/12/31.
 */
public class AddWarriorsPresenter extends RefreshAndLoadMorePresenter<AddWarriorsView> {

    public void getData(final int page, final int count) {
        /**
         * 查询推荐人 search搜索时值为friend.nicName,可为空
         */
        Friend friend = view.getData();
        Subscription subscription = NetEngine.getService()
                .GetRecommendUser((page - 1) * count, count, new SessionUtil(view.getContext()).getUserId(), 1, friend.nicName)
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
                        view.dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.dismissDialog();
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


    public void isWuyou(final int userid) {
        Subscription subscription = NetEngine.getService()
                .IsWuyou(new SessionUtil(view.getContext()).getUserId(), userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<Boolean>>() {
                    @Override
                    public void next(Res<Boolean> res) {
                        if (res.code == C.OK) {
                            view.isWuYou(res.data, userid);

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
