package com.hewuzhe.presenter;

import com.hewuzhe.model.Friend;
import com.hewuzhe.model.Group;
import com.hewuzhe.model.Res;
import com.hewuzhe.model.WrapFriend;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widget.CharacterParser;
import com.hewuzhe.ui.widget.PinyinFriendComparator;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.FriendsView;

import java.util.ArrayList;
import java.util.Collections;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 15/12/31.
 */
public class FriendsPresenter extends BasePresenterImp<FriendsView> {
    private ArrayList<Friend> friends = new ArrayList<>();

    public void getFriends() {

        Subscription subscription = NetEngine.getService()
                .GetFriendForKeyValue(view.getData())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<WrapFriend>>>() {
                    @Override
                    public void next(Res<ArrayList<WrapFriend>> res) {
                        if (res.code == C.OK) {

                            for (WrapFriend wrapFriend : res.data) {
                                Friend friend = wrapFriend.Value;
                                friend.Id = wrapFriend.Key;
                                friends.add(friend);
                            }
                            bindData();
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

    private void bindData() {
        PinyinFriendComparator mPinyinComparator = new PinyinFriendComparator();
        CharacterParser mCharacterParser = CharacterParser.getInstance();
        for (Friend friend : friends) {
            friend.topc = mCharacterParser.getSelling(friend.NicName).substring(0, 1).toUpperCase();
            if (friend.NicName.equals("重庆")) {
                friend.topc = "C";
            }
        }

        Collections.sort(friends, mPinyinComparator);
        if (view != null) {
            view.bindData(friends);
        }
    }

    public void getGroup() {

        Subscription subscription = NetEngine.getService()
                .SelectTeam(new SessionUtil(view.getContext()).getUser().TeamId)
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
}
