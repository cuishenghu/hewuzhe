package com.hewuzhe.presenter;

import com.hewuzhe.model.FriendCondition;
import com.hewuzhe.model.Group;
import com.hewuzhe.model.Res;
import com.hewuzhe.model.User;
import com.hewuzhe.presenter.adapter.ConditionPresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.GroupConditionView;

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
public class GroupConditionPresenter extends ConditionPresenter<GroupConditionView> {

    public void getData(final int page, final int count) {

        if (view.getData() == new SessionUtil(view.getContext()).getUser().TeamId) {
            //获取自己的战队
            Subscription subscription = NetEngine.getService()
                    .GetTeamDongtaiPageByUserId((page - 1) * count, count, new SessionUtil(view.getContext()).getUserId())
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
        } else {
            Subscription subscription = NetEngine.getService()
                    .GetTeamDongtaiPageByTeamId((page - 1) * count, count, view.getData())
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

    }


    public void getBasicInfo() {
        Subscription subscription = NetEngine.getService()
                .SelectTeam(view.getData())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<Group>>() {
                    @Override
                    public void next(Res<Group> res) {
                        if (res.code == C.OK) {
                            view.setGroupData(res.data);
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

    public void joinTeam(final int teamId, String name) {

        User user = new SessionUtil(view.getContext()).getUser();
        if (user.TeamId != 0) {
            view.toast("请先退出当前兴趣圈");
            return;
        }

        Subscription subscription = NetEngine.getService()
                .JoinTeam(user.Id, teamId, name)
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
                            user.TeamId = teamId;
                            new SessionUtil(view.getContext()).putUser(user);

                            HashSet<String> sets = new HashSet<>();
                            sets.add("TeamId_" + user.TeamId);
                            JPushInterface.setTags(view.getContext(), sets, new TagAliasCallback() {
                                @Override
                                public void gotResult(int i, String s, Set<String> set) {

                                }
                            });

                            view.updateItem(true);
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
