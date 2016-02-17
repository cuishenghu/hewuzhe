package com.hewuzhe.presenter;

import android.view.View;

import com.hewuzhe.model.Comment;
import com.hewuzhe.model.Res;
import com.hewuzhe.model.Video;
import com.hewuzhe.presenter.adapter.CommentPresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.view.VideoDetailView;

import java.util.ArrayList;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 15/12/23.
 */
public class VideoDetailPresenter extends CommentPresenter<VideoDetailView> {

    public void getVideoDetail(int id) {

        Subscription subscription = NetEngine.getService()
                .GetOnlineStudy(id, new SessionUtil(view.getContext()).getUserId())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        view.showDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<Video>>() {
                    @Override
                    public void next(Res<Video> res) {
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


    /**
     * 获取其他视频
     */
    public void getOtherVideos(int userId, int id) {

        Subscription subscription = NetEngine.getService()
                .GetOtherVideo(userId, 4, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<Video>>>() {
                    @Override
                    public void next(Res<ArrayList<Video>> res) {
                        view.setOtherVideos(res.data);
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

    public void getData(final int page, final int count) {
        int id = view.getData();

        Subscription subscription = NetEngine.getService()
                .SelectCommentByMessageId(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<Comment>>>() {
                    @Override
                    public void next(Res<ArrayList<Comment>> res) {
                        if (res.code == C.OK) {
                            view.setCommentsData(res.data);
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
        Subscription subscription = NetEngine.getService()
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

        addSubscription(subscription);
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
                .MessageComment(id, new SessionUtil(view.getContext()).getUser().Id, content)
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
