package com.hewuzhe.presenter;

import android.net.Uri;

import com.hewuzhe.model.AboutUs;
import com.hewuzhe.model.Group;
import com.hewuzhe.model.Images;
import com.hewuzhe.model.Res;
import com.hewuzhe.model.User;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.WarriorFragmentView;

import java.util.ArrayList;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 15/12/30.
 */
public class WarriorFragmentPresenter extends BasePresenterImp<WarriorFragmentView> {

    public void getUserData() {
        User user = new SessionUtil(view.getContext()).getUser();
        if (user != null) {
            Subscription subscription = NetEngine.getService()
                    .UpdateUser(user.Id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SB<Res<User>>() {
                        @Override
                        public void next(Res<User> res) {
                            if (res.code == C.OK) {
                                new SessionUtil(view.getContext()).putUser(res.data);
                                view.setUserData();
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


    public void getUserInfo(int userid) {
        Subscription subscription = NetEngine.getService()
                .SelectMyFriend(userid, new SessionUtil(view.getContext()).getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<User>>() {
                    @Override
                    public void next(Res<User> res) {
                        if (res.code == C.OK) {
                            RongIM.getInstance().refreshUserInfoCache(new UserInfo(res.data.Id + "", res.data.RemarkName, Uri.parse(C.BASE_URL + res.data.PhotoPath)));
//                            res.data.save();
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


    public void getGroup(int groupid) {

        Subscription subscription = NetEngine.getService()
                .SelectTeam(groupid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<Group>>() {
                    @Override
                    public void next(Res<Group> res) {
                        if (res.code == C.OK) {

                            RongIM.getInstance().refreshGroupInfoCache(new io.rong.imlib.model.Group(res.data.Id + "", res.data.Name, Uri.parse(C.BASE_URL + res.data.ImagePath)));
//                            res.data.save();

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


    public void getIndexImg() {
        Subscription subscription = NetEngine.getService()
                .GetAuoutUs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<AboutUs>>() {
                    @Override
                    public void next(Res<AboutUs> res) {
                        if (res.code == C.OK) {
                            view.setIndexImg(res.data);
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

    public void getBannerImg() {
        Subscription subscription = NetEngine.getService()
                .GetBannerUs(new SessionUtil(view.getContext()).getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<Images>>>() {
                    @Override
                    public void next(Res<ArrayList<Images>> res) {
                        if (res.code == C.OK) {
                            view.setIndexImg(res.data);
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
