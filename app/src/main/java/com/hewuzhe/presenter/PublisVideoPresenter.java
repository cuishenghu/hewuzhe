package com.hewuzhe.presenter;

import android.view.View;

import com.hewuzhe.model.Res;
import com.hewuzhe.model.Video;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.Encoder;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.PublistVideoVIew;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 15/12/28.
 */
public class PublisVideoPresenter extends BasePresenterImp<PublistVideoVIew> {

    /**
     * 发布视频
     *
     * @param videoName
     * @param imageName
     * @param videoDuration
     */
    public void publistVideo(String videoName, String imageName, String videoDuration, int cateId) {
        Video video = view.getData();
        Subscription subscription = NetEngine.getService()
                .SaveOrEditVideoMessage(0, video.Title, imageName, video.Content, videoName, "TRUE", "FALSE", cateId, new SessionUtil(view.getContext()).getUser().Id, videoDuration)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res>() {
                    @Override
                    public void next(Res res) {
                        if (res.code == C.OK) {
                            view.toast("发布成功");
                            view.finishActivity();
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


    public void UpLoadVideo(View v, String path, final int cateId) {

        String fileName = path.substring(path.lastIndexOf("/") + 1, path.length());
        try {
            Subscription subscription = NetEngine.getService()
                    .UpLoadVideo(fileName, Encoder.encodeBase64File(path))
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            view.showDialog();
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SB<Res<Video>>() {
                        @Override
                        public void next(Res<Video> res) {
                            if (res.code == C.OK) {
                                publistVideo(res.data.VideoName, res.data.ImageName, res.data.VideoDuration, cateId);
                            } else {
                                view.dismissDialog();

                            }

                        }

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            view.dismissDialog();

                        }
                    });
            addSubscription(subscription);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
