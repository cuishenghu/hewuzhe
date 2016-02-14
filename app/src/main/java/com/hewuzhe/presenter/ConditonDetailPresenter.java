package com.hewuzhe.presenter;

import android.view.View;

import com.hewuzhe.model.Comment;
import com.hewuzhe.model.FriendCondition;
import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.RefreshAndLoadMorePresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.view.CondtionDetailView;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 16/1/12.
 */
public class ConditonDetailPresenter extends RefreshAndLoadMorePresenter<CondtionDetailView> {
    /**
     * 获取数据
     *
     * @param page
     * @param count
     */
    @Override
    public void getData(int page, int count) {
        Subscription subscription = NetEngine.getService()
                .GetDongtaiById(new SessionUtil(view.getContext()).getUserId(), view.getData())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<FriendCondition>>() {
                    @Override
                    public void next(Res<FriendCondition> res) {
                        if (res.code == C.OK) {
                            view.setData(res.data);
                            view.bindData(res.data.ComList);
                        }

                    }

                    @Override
                    public void onCompleted() {
                        view.dismissDialog();
                        view.refresh(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.dismissDialog();
                        view.refresh(false);
                    }
                });

        addSubscription(subscription);

    }

    public void collectAndOther(int id, final int flag, final View v) {
        view.showDialog();
        NetEngine.getService()
                .MessageRepeatAndFavorite(id, new SessionUtil(view.getContext()).getUser().Id, flag)
                .enqueue(new Callback<Res>() {
                    @Override
                    public void onResponse(Response<Res> response, Retrofit retrofit) {
                        Res res = response.body();
                        if (res.code == C.OK) {
                            view.collectAndOther(true, flag);
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


    /**
     * 评论
     */
    public void publisComment(int id, final String content, final View v) {
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
                            view.commentSuccess(comment);
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
     * @param v
     */
    public void showCommentInput(int id, View v) {
        view.showCommentInput(id, v);

    }

    public void showReplyInput(int id, String nicName, int commenterId, View v) {
        view.showReplyInput(id, nicName, commenterId, v);

    }

    /**
     * 发表回复
     *
     * @param id
     * @param nicName
     * @param commenterId
     * @param content
     * @param v
     */
    public void publisReply(int id, final String nicName, final int commenterId, final String content, final View v) {

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
                            comment.CommentedId = commenterId;
                            comment.Id = res.insertid;
                            view.replySuccess(comment);
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
}
