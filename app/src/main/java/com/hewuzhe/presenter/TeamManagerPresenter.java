package com.hewuzhe.presenter;

import com.hewuzhe.model.Res;
import com.hewuzhe.model.User;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.TeamManagerView;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 16/1/15.
 */
public class TeamManagerPresenter extends BasePresenterImp<TeamManagerView> {


    public void quitGroup(int id) {

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

                            view.updateItem(false);
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
