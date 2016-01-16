package com.hewuzhe.presenter;

import android.view.View;

import com.hewuzhe.MegaVideo;
import com.hewuzhe.model.Res;
import com.hewuzhe.model.UploadImage;
import com.hewuzhe.model.User;
import com.hewuzhe.model.Video;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.activity.MegaGameActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.Encoder;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.view.MegaGameApplyView;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 16/1/15.
 */
public class MegaGameApplyPresenter extends BasePresenterImp<MegaGameApplyView> {

    private boolean isVideoChanged;
    private boolean isImageChanged;

    public void publish(final View v, boolean isJoin, boolean isImageChanged, boolean isVideoChanged) {
        this.isImageChanged = isImageChanged;
        this.isVideoChanged = isVideoChanged;
        final MegaVideo megaVideo = view.getData();

        if (StringUtil.isEmpty(megaVideo.MatchDescription)) {
            view.snb("请填写视频介绍", v);
            return;
        }

        if (StringUtil.isEmpty(megaVideo.Title)) {
            view.snb("请填写视频标题", v);
            return;
        }


        if (StringUtil.isEmpty(megaVideo.MatchImage)) {
            view.snb("请选择主图", v);
            return;
        }


        if (StringUtil.isEmpty(megaVideo.MatchVideo)) {
            view.snb("请选择视频", v);
            return;
        }

        if (!isJoin) {
            uploadVideo(v, megaVideo);
        } else {
            if (!isVideoChanged && !isImageChanged) {
                publishContent(megaVideo, v);
            } else if (isVideoChanged && isImageChanged) {
                uploadVideo(v, megaVideo);
            } else if (isVideoChanged && !isImageChanged) {
                uploadVideo(v, megaVideo);
            } else if (!isVideoChanged && isImageChanged) {
                uploadImg(megaVideo, v);
            }
        }

    }

    private void uploadVideo(final View v, final MegaVideo megaVideo) {
        Subscription subscription = NetEngine.getService()
                .UpLoadVideo(Encoder.getFileName(megaVideo.MatchVideo), Encoder.encodeBase64File(megaVideo.MatchVideo))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (!view.isShowingDialog()) {
                            view.showDialog();
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<Video>>() {
                    @Override
                    public void next(Res<Video> res) {
                        if (res.code == C.OK) {
                            megaVideo.VideoDuration = res.data.VideoDuration;
                            megaVideo.MatchVideo = res.data.VideoName;

                            if (isVideoChanged && !isImageChanged) {
                                publishContent(megaVideo, v);
                            } else {
                                uploadImg(megaVideo, v);
                            }

                        } else {
                            view.dismissDialog();
                            view.snb("上传视频失败", v);
                        }
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.dismissDialog();

                        view.snb("上传视频失败", v);
                    }
                });

        addSubscription(subscription);
    }

    private void uploadImg(final MegaVideo megaVideo, final View v) {

        Subscription subscription = NetEngine.getService()
                .UpLoadImage(Encoder.getFileName(megaVideo.MatchImage), Encoder.getEnocodeStr(megaVideo.MatchImage))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<UploadImage>>() {
                    @Override
                    public void next(Res<UploadImage> res) {
                        if (res.code == C.OK) {
                            megaVideo.MatchImage = res.data.ImagePath;
                            publishContent(megaVideo, v);
                        } else {
                            view.dismissDialog();
                            view.snb("上传图片失败", v);
                        }

                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.dismissDialog();
                        view.snb("上传图片失败", v);

                    }
                });

        addSubscription(subscription);
    }

    private void publishContent(MegaVideo megaVideo, final View v) {
        Subscription subscription = NetEngine.getService()
                .JoinMatch(new SessionUtil(view.getContext()).getUserId(), megaVideo.Id, megaVideo.MatchVideo, megaVideo.VideoDuration, megaVideo.MatchDescription, megaVideo.MatchImage, megaVideo.Title)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res>() {
                    @Override
                    public void next(Res res) {
                        if (res.code == C.OK) {
                            view.publishSuccess();
                            view.snb("发布成功", v);

                        } else {
                            view.snb("发布失败", v);
                        }

                    }

                    @Override
                    public void onCompleted() {
                        view.dismissDialog();

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.dismissDialog();
                        view.snb("发布失败", v);
                    }
                });
        addSubscription(subscription);
    }


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

    public void CancleJoinMatch(final MegaVideo megaVideo, final View v) {
        Subscription subscription = NetEngine.getService()
                .CancleJoinMatch(new SessionUtil(view.getContext()).getUserId(), megaVideo.Id)
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
                            publishContent(megaVideo, v);
                        } else {
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
