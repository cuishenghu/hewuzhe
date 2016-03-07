package com.hewuzhe.presenter;

import android.app.Activity;
import android.view.View;

import com.hewuzhe.model.Res;
import com.hewuzhe.model.Video;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.rongc.provider.VideoInputProvider;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.Encoder;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.view.UploadVideoView;
import com.hewuzhe.view.base.BaseView;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by zycom on 2016/3/5.
 */
public class UploadVideoPresenter extends BasePresenterImp<UploadVideoView> {

    public void UpLoadVideo(String path) {
        String fileName = path.substring(path.lastIndexOf("/") + 1, path.length());
        try {
            Subscription subscription = NetEngine.getService()
                    .UpLoadVideo(fileName, Encoder.encodeBase64File(path))
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SB<Res<Video>>() {
                        @Override
                        public void next(Res<Video> res) {
                            if (res.code == C.OK) {
                                view.sendMessage(res.data.VideoName);
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

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
