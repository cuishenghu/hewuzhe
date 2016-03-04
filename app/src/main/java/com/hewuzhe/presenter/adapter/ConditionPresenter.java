package com.hewuzhe.presenter.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;

import com.hewuzhe.model.Comment;
import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.RefreshAndLoadMorePresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.view.adapter.ConditionView;

import materialdialogs.DialogAction;
import materialdialogs.MaterialDialog;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 16/1/1.
 */
public abstract class ConditionPresenter<V extends ConditionView> extends RefreshAndLoadMorePresenter<V> {

    public static final int PRAISE = 2;
    public static final int COLLECT = 1;
    public static final int TRANSPORT = 0;

    /**
     * 点赞
     */
    public void collectAndOther(int id, final int flag, final View v, final int position) {
//        Subscription subscription = NetEngine.getService()
//                .MessageRepeatAndFavorite(id, new SessionUtil(view.getContext()).getUser().Id, flag)
//                .subscribeOn(Schedulers.io())
//                .doOnSubscribe(() -> view.showDialog())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SB<Res>() {
//                    @Override
//                    public void next(Res res) {
//                        if (res.code == C.OK) {
//                            view.collectAndOther(true, flag, position);
//                        } else {
//                            view.snb("操作失败", v);
//                        }
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
//                        view.snb("操作失败", v);
//                    }
//                });

//        addSubscription(subscription);

        view.showDialog();
        NetEngine.getService()
                .MessageRepeatAndFavorite(id, new SessionUtil(view.getContext()).getUser().Id, flag)
                .enqueue(new Callback<Res>() {
                    @Override
                    public void onResponse(Response<Res> response, Retrofit retrofit) {
                        Res res = response.body();
                        if (res.code == C.OK) {
                            view.collectAndOther(true, flag, position);
                        } else {
                            view.snb("操作失败", v);
                        }
                        view.dismissDialog();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        view.dismissDialog();
                        view.snb("操作失败", v);
                    }
                });
    }


    public void collectAndOtherCanl(int id, final int flag, final View v, final int position) {
        view.showDialog();
        NetEngine.getService()
                .MessageRepeatAndFavoriteCancel(id, new SessionUtil(view.getContext()).getUser().Id, flag)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res>() {
                    @Override
                    public void next(Res res) {
                        if (res.code == C.OK) {
                            view.collectAndOther(false, flag, position);
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
                        view.snb("操作失败", v);
                        view.dismissDialog();

                    }
                });
    }


    /**
     * 评论
     */
    public void publisComment(int id, final String content, final View v, final int position) {
        if (StringUtil.isEmpty(content)) {
            view.snb("评论内容不能为空", v);
            return;
        }

        Subscription subscription = NetEngine.getService()
                .MessageCommentDT(id, new SessionUtil(view.getContext()).getUser().Id, content)
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
                            Comment comment = new Comment();
                            comment.Content = content;
                            comment.Id = res.insertid;
                            view.commentSuccess(position, comment);
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


    /**
     * 显示评论框
     *
     * @param id
     * @param position
     * @param v
     */
    public void showCommentInput(int id, int position, View v) {
        view.showCommentInput(id, position, v);

    }

    public void showReplyInput(int id, String nicName, int commenterId, int position, View v) {
        view.showReplyInput(id, nicName, commenterId, position, v);

    }

    /**
     * 发表回复
     *
     * @param id
     * @param nicName
     * @param content
     * @param v
     * @param position
     */
    public void publisReply(int id, final String nicName, final int commenterId, final String content, final View v, final int position) {

        if (StringUtil.isEmpty(content)) {
            view.snb("回复内容不能为空", v);
            return;
        }

//        Subscription subscription = NetEngine.getService()
//                .CommentComment(id, new SessionUtil(view.getContext()).getUser().Id, content)
//                .subscribeOn(Schedulers.io())
//                .doOnSubscribe(() -> view.showDialog())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SB<Res>() {
//                    @Override
//                    public void next(Res res) {
//                        if (res.code == C.OK) {
//                            view.snb("回复成功", v);
//                            Comment comment = new Comment();
//                            comment.Content = content;
//                            comment.CommentedNicName = nicName;
//                            comment.Id = res.insertid;
//                            view.replySuccess(position, comment);
//                        } else {
//                            view.snb("回复失败", v);
//                        }
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
//                        view.snb("回复失败", v);
//                    }
//                });
//        addSubscription(subscription);


        view.showDialog();
        NetEngine.getService()
                .CommentComment(id, new SessionUtil(view.getContext()).getUser().Id, content)
                .enqueue(new Callback<Res>() {
                    @Override
                    public void onResponse(Response<Res> response, Retrofit retrofit) {
                        Res res = response.body();

                        if (res.code == C.OK) {
                            view.snb("回复成功", v);
                            Comment comment = new Comment();
                            comment.Content = content;
                            comment.CommentedNicName = nicName;
                            comment.ParentId = commenterId;
                            comment.Id = res.insertid;
                            view.replySuccess(position, comment);
                        } else {
                            view.snb("回复失败", v);
                        }
                        view.dismissDialog();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        view.dismissDialog();
                        view.snb("回复失败", v);
                    }
                });
    }

    public void deleteComment(final int id, final View v) {

        view.showDefautInfoDialog("温馨提示", "确定要删除评论吗？", new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                Subscription subscription = NetEngine.getService()
                        .DeleteComment(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SB<Res>() {
                            @Override
                            public void next(Res res) {
                                if (res.code == C.OK) {
                                    ((LinearLayout) v.getParent()).removeView(v);
                                } else {

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
        });

    }

    public void deleteCondition(final int id, final int position) {

        view.showDefautInfoDialog("温馨提示", "确认删除动态？", new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                Subscription subscription = NetEngine.getService()
                        .DeletePlan(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SB<Res>() {
                            @Override
                            public void next(Res res) {
                                if (res.code == C.OK) {
                                    view.deleteConditionSuccess(position);
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
        });

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
