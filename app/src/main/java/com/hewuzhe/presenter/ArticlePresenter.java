package com.hewuzhe.presenter;

import android.view.View;

import com.hewuzhe.model.Article;
import com.hewuzhe.model.Comment;
import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.adapter.CommentPresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.ArticleView;

import java.util.ArrayList;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 15/12/31.
 */
public class ArticlePresenter extends CommentPresenter<ArticleView> {

    /**
     * 获取文章详情
     *
     * @param id
     */
    public void getArticleDetail(int id) {
        Subscription subscription = NetEngine.getService()
                .GetLianmengDongtai(id)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        view.showDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<Article>>() {
                    @Override
                    public void next(Res<Article> res) {
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
     * 评论
     */
    public void publisComment(int id, String content, final View v) {
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
                            view.commentSuccess(0, null);
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


    @Override
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
                            view.bindData(res.data);
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

    public void collectAndFavorate(int id, final int flag, final View v) {

        String content = "";
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
                            view.collectAndOther(true, flag);
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
                            view.collectAndOther(false, flag);
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
