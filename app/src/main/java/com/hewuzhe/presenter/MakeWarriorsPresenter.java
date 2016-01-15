package com.hewuzhe.presenter;

import com.hewuzhe.model.Friend;
import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.common.AreaPresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.MakeWarriorsView;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 15/12/31.
 */
public class MakeWarriorsPresenter extends AreaPresenter<MakeWarriorsView> {

    public void getData(final int page, final int count) {

        Friend friend = view.getData();
//        Subscription subscription = NetEngine.getService()
//                .GetFriendBySearch((page - 1) * count, count, friend.areaId, friend.nicName, friend.age, friend.sexuality, new SessionUtil(view.getContext()).getUser().Id)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SB<Res<ArrayList<Friend>>>() {
//                    @Override
//                    public void next(Res<ArrayList<Friend>> res) {
//                        if (res.code == C.OK) {
//                            view.bindData(res.data);
//                            setDataStatus(page, count, res);
//                        }
//
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                        view.dismissDialog();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        view.dismissDialog();
//                    }
//                });
//        addSubscription(subscription);

        NetEngine.getService()
                .GetFriendBySearch((page - 1) * count, count, friend.areaId, friend.nicName, friend.age, friend.sexuality, new SessionUtil(view.getContext()).getUser().Id)
                .enqueue(new Callback<Res<ArrayList<Friend>>>() {
                    @Override
                    public void onResponse(Response<Res<ArrayList<Friend>>> response, Retrofit retrofit) {
                        Res<ArrayList<Friend>> res = response.body();
                        if (res.code == C.OK) {
                            view.bindData(res.data);
                            setDataStatus(page, count, res);
                        }
                        view.dismissDialog();

                    }

                    @Override
                    public void onFailure(Throwable t) {
                        view.dismissDialog();

                    }
                });
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


}
