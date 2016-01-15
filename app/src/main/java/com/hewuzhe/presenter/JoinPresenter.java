package com.hewuzhe.presenter;

import com.hewuzhe.model.Group;
import com.hewuzhe.model.Res;
import com.hewuzhe.model.User;
import com.hewuzhe.presenter.common.AreaPresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.JoinGroupView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 15/12/31.
 */
public class JoinPresenter extends AreaPresenter<JoinGroupView> {

    public void getData(final int page, final int count) {

        Group group = view.getData();
        Subscription subscription = NetEngine.getService()
                .SelectTeamPage((page - 1) * count, count, group.citycode, group.name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<Group>>>() {
                    @Override
                    public void next(Res<ArrayList<Group>> res) {
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

    public void joinGroup(final int id, String name, final int pos) {

        User user = new SessionUtil(view.getContext()).getUser();
        if (user.TeamId != 0) {
            view.toast("请先退出当前战队");
            return;
        }

        Subscription subscription = NetEngine.getService()
                .JoinTeam(user.Id, id, name)
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
                            User user = new SessionUtil(view.getContext()).getUser();
                            user.TeamId = id;
                            new SessionUtil(view.getContext()).putUser(user);

                            HashSet<String> sets = new HashSet<>();
                            sets.add("TeamId_" + user.TeamId);
                            JPushInterface.setTags(view.getContext(), sets, new TagAliasCallback() {
                                @Override
                                public void gotResult(int i, String s, Set<String> set) {

                                }
                            });

                            view.updateItem(true, pos);
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

    public void quitGroup(int id, final int position) {

        Subscription subscription = NetEngine.getService()
                .QuitGroup(new SessionUtil(view.getContext()).getUser().Id, id)
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

                            User user = new SessionUtil(view.getContext()).getUser();
                            user.TeamId = 0;
                            new SessionUtil(view.getContext()).putUser(user);


                            HashSet<String> sets = new HashSet<>();
                            sets.add("TeamId_" + "0");
                            JPushInterface.setTags(view.getContext(), sets, new TagAliasCallback() {
                                @Override
                                public void gotResult(int i, String s, Set<String> set) {

                                }
                            });

                            view.updateItem(false, position);
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
}
