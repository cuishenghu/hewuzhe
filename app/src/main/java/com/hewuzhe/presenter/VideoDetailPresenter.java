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
import com.hewuzhe.view.VideoDetailView;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 15/12/23.
 */
public class VideoDetailPresenter extends CommentPresenter<VideoDetailView> {

    public void getVideoDetail(int id) {

//        Subscription subscription = NetEngine.getService()
//                .GetOnlineStudy(id, new SessionUtil(view.getContext()).getUserId())
//                .subscribeOn(Schedulers.io())
//                .doOnSubscribe(() -> view.showDialog())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SB<Res<Video>>() {
//                    @Override
//                    public void next(Res<Video> res) {
//                        if (res.code == C.OK) {
//                            view.setData(res.data);
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
//                    }
//                });
//        addSubscription(subscription);


        view.showDialog();
        NetEngine.getService()
                .GetOnlineStudy(id, new SessionUtil(view.getContext()).getUserId())
                .enqueue(new Callback<Res<Video>>() {
                    @Override
                    public void onResponse(Response<Res<Video>> response, Retrofit retrofit) {
                        Res<Video> res = response.body();
                        if (res.code == C.OK) {
                            view.setData(res.data);
                        }
                        view.dismissDialog();

                    }

                    @Override
                    public void onFailure(Throwable t) {
                        view.dismissDialog();

                    }
                });
    }


    /**
     * 获取其他视频
     */
    public void getOtherVideos(int id) {

//        Subscription subscription = NetEngine.getService()
//                .GetOtherVideo(new SessionUtil(view.getContext()).getUser().Id, 3, id)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SB<Res<ArrayList<Video>>>() {
//                    @Override
//                    public void next(Res<ArrayList<Video>> res) {
//                        view.setOtherVideos(res.data);
//                    }
//
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//                });

//        addSubscription(subscription);

        NetEngine.getService()
                .GetOtherVideo(new SessionUtil(view.getContext()).getUser().Id, 3, id)
                .enqueue(new Callback<Res<ArrayList<Video>>>() {
                    @Override
                    public void onResponse(Response<Res<ArrayList<Video>>> response, Retrofit retrofit) {
                        Res<ArrayList<Video>> res = response.body();
                        view.setOtherVideos(res.data);

                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
    }

    public void getData(final int page, final int count) {
        int id = view.getData();
//        Subscription subscription = NetEngine.getService()
//                .SelectCommentByMessageId(id)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SB<Res<ArrayList<Comment>>>() {
//                    @Override
//                    public void next(Res<ArrayList<Comment>> res) {
//                        view.setCommentsData(res.data);
//                        setDataStatus(page, count, res);
//                    }
//
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//                });
//        addSubscription(subscription);

        NetEngine.getService()
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


    public void collectAndOther(int id, final int flag, final View v, final int position,String content) {

        Subscription subscription = NetEngine.getService()
                .SelectCommentByMessageId(id, new SessionUtil(view.getContext()).getUser().Id, flag,content)
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
//        Subscription subscription = NetEngine.getService()
//                .MessageComment(id, new SessionUtil(view.getContext()).getUser().Id, content)
//                .subscribeOn(Schedulers.io())
//                .doOnSubscribe(() -> view.showDialog())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SB<Res>() {
//                    @Override
//                    public void next(Res res) {
//                        if (res.code == C.OK) {
//                            view.snb("评论成功", v);
//                            view.commentSuccess();
//                        } else {
//                            view.snb("评论失败", v);
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
//                        view.snb("评论失败", v);
//                    }
//                });

//        addSubscription(subscription);


        view.showDialog();
        NetEngine.getService()
                .MessageComment(id, new SessionUtil(view.getContext()).getUser().Id, content)
                .enqueue(new Callback<Res>() {
                    @Override
                    public void onResponse(Response<Res> response, Retrofit retrofit) {
                        Res res = response.body();
                        if (res.code == C.OK) {
                            view.snb("评论成功", v);
                            view.commentSuccess();
                        } else {
                            view.snb("评论失败", v);
                        }
                        view.dismissDialog();

                    }

                    @Override
                    public void onFailure(Throwable t) {
                        view.dismissDialog();
                        view.snb("评论失败", v);
                    }
                });
    }

}
