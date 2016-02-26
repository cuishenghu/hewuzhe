package com.hewuzhe.presenter;

import com.hewuzhe.model.Friend;
import com.hewuzhe.model.Group;
import com.hewuzhe.model.Res;
import com.hewuzhe.model.WrapFriend;
import com.hewuzhe.presenter.base.RefreshAndLoadMorePresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widget.CharacterParser;
import com.hewuzhe.ui.widget.PinyinFriendComparator;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.view.FriendsView;

import java.util.ArrayList;
import java.util.Collections;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 15/12/31.
 */
public class FriendsPresenter extends RefreshAndLoadMorePresenter<FriendsView> {
    private ArrayList<Friend> friends = new ArrayList<>();

    public void getFriends() {
        friends.clear();
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
                                if (!StringUtil.isEmpty(friend.RemarkName)) {
                                    friends.add(friend);
                                }
                            }
                            bindData();
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

    private void bindData() {
        PinyinFriendComparator mPinyinComparator = new PinyinFriendComparator();
        CharacterParser mCharacterParser = CharacterParser.getInstance();

        for (Friend friend : friends) {
            if (StringUtil.isEmpty(friend.RemarkName)) {
                friend.topc = "A";
            } else {
                friend.topc = mCharacterParser.getSelling(friend.RemarkName).substring(0, 1).toUpperCase();
                if (friend.RemarkName.equals("重庆")) {
                    friend.topc = "C";
                }
            }
        }

        Collections.sort(friends, mPinyinComparator);
        view.bindData(friends);
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

    /**
     * 获取数据
     *
     * @param page
     * @param count
     */
    @Override
    public void getData(int page, int count) {

    }
}
