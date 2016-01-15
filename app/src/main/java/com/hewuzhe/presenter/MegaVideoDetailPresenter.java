package com.hewuzhe.presenter;

import android.view.View;

import com.hewuzhe.MegaVideo;
import com.hewuzhe.model.Group;
import com.hewuzhe.model.MegaComment;
import com.hewuzhe.model.Res;
import com.hewuzhe.model.User;
import com.hewuzhe.presenter.adapter.CommentPresenter;
import com.hewuzhe.ui.activity.MegaGameActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.view.MegaVideoDetailView;

import java.util.ArrayList;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 15/12/23.
 */
public class MegaVideoDetailPresenter extends CommentPresenter<MegaVideoDetailView> {

    public void getVideoDetail(int id, int userid, int teamId) {
        String path = MegaGameActivity.PAGE == 0 ? "SelectMatchMemberDetail" : "SelectMatchTeamDetail";
        User user = new SessionUtil(view.getContext()).getUser();
        Subscription subscription = NetEngine.getService()
                .SelectMatchDetail(path, userid, user.Id, id, teamId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        view.showDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<MegaVideo>>() {
                    @Override
                    public void next(Res<MegaVideo> res) {
                        if (res.code == C.OK) {
                            view.setData(res.data);
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


    public void getData(final int page, final int count) {
        int id = view.getData();

        Subscription subscription = NetEngine.getService()
                .GetMatchComment((page - 1) * count, count, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<MegaComment>>>() {
                    @Override
                    public void next(Res<ArrayList<MegaComment>> res) {
                        if (res.code == C.OK) {
                            view.bindData(res.data);
                            view.setDataCount(res.recordcount);
                            setDataStatus(page, count, res);
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


    public void collectAndFavorateCance(int id, final int flag, final View v) {

        view.showDialog();
        NetEngine.getService()
                .MessageRepeatAndFavoriteCancel(id, new SessionUtil(view.getContext()).getUser().Id, flag)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res>() {
                    @Override
                    public void next(Res res) {
                        if (res.code == C.OK) {
                            view.collectAndOther(false, flag, 1);
                        } else {
                            view.snb("操作失败", v);
                        }

                    }

                    @Override
                    public void onCompleted() {
                        view.dismissDialog();

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.dismissDialog();
                        view.snb("操作失败", v);

                    }
                });

    }


    public void collectAndOther(int id, final int flag, final View v, final int position, String content) {

        Subscription subscription = NetEngine.getService()
                .SelectCommentByMessageId(id, new SessionUtil(view.getContext()).getUser().Id, flag, content)
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
                            view.collectAndOther(true, flag, position);
                        } else {
                            view.snb("操作失败", v);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        view.dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.dismissDialog();
                        view.snb("操作失败", v);
                    }
                });

        addSubscription(subscription);


    }

    public void publisComment(int id, String content, final View v) {
        if (StringUtil.isEmpty(content)) {
            view.snb("内容不能为空", v);
            return;
        }

        Subscription subscription = NetEngine.getService()
                .CommentMatch(new SessionUtil(view.getContext()).getUser().Id, id, content)
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
                            view.snb("评论成功", v);
                            view.commentSuccess();
                        } else {
                            view.snb("评论失败", v);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        view.dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.dismissDialog();
                        view.snb("评论失败", v);
                    }
                });

        addSubscription(subscription);


    }

    public void vote(int id, int userid) {
        Subscription subscription = NetEngine.getService()
                .VotePerso(userid, id, new SessionUtil(view.getContext()).getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res>() {
                    @Override
                    public void next(Res res) {
                        if (res.code == C.OK) {
                            view.voteSuccess();
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


    public void getUserData(int userid) {
        Subscription subscription = NetEngine.getService()
                .UpdateUser(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<User>>() {
                    @Override
                    public void next(Res<User> res) {
                        if (res.code == C.OK) {
                            view.setUserData(res.data);
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

    public void getBasicInfo(int teamid) {
        Subscription subscription = NetEngine.getService()
                .SelectTeam(teamid)
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
