package com.hewuzhe.presenter;

import android.view.View;

import com.hewuzhe.model.Res;
import com.hewuzhe.model.UploadImage;
import com.hewuzhe.model.Video;
import com.hewuzhe.model.common.DataModel;
import com.hewuzhe.model.common.PickImg;
import com.hewuzhe.presenter.base.ListPresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.Alist;
import com.hewuzhe.utils.Encoder;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.view.PublishConditionView;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 16/1/2.
 */
public class PublishConditionPresenter extends ListPresenter<PublishConditionView> {

    /**
     * 需要上传的图片数
     */
    private int neeCount = 0;
    private DataModel<PickImg, Object> data;
    private View v;
    private int picCount = 0;
    private String pathList = "";
    private String videopath = "";

    /**
     * 显示选择dialog
     *
     * @param v
     */
    public void showPickDialog(View v) {
        view.showPIckDialog();
    }

    @Override
    public void getData(int page, int count) {
        view.bindData(new Alist().add(new PickImg()).ok());
    }

    /**
     * 发布动态
     */

    public void publish(View v) {

        this.v = v;
        data = view.getData();

        if (StringUtil.isEmpty(data.content)) {
            view.snb("内容不能为空", v);
            return;
        }

        //上传图片
        if (data.list.size() > 0) {
            uploadImg(data.list);

        } else {
            //发布
            publishTwo("");
        }

    }

    private void uploadImg(ArrayList<PickImg> list) {
        neeCount = list.size();
        for (PickImg pickImg : list) {
            Subscription subscription = NetEngine.getService()
                    .UpLoadImage(Encoder.getFileName(pickImg.filePath), Encoder.encodeBase64File(pickImg.filePath))
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            view.showDialog();
                        }
                    })
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SB<Res<UploadImage>>() {
                        @Override
                        public void next(Res<UploadImage> res) {
                            if (res.code == C.OK) {
                                picCount++;
                                pathList += res.data.ImagePath + "&";
                                if (picCount >= neeCount) {
                                    publishTwo(pathList);
                                }

                            } else {
                                neeCount--;
                            }

                        }

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            neeCount--;
                        }
                    });
            addSubscription(subscription);

        }
    }

    private void publishTwo(String pathList) {
        if (data == null) {
            data = view.getData();
        }
        if (StringUtil.isEmpty(data.content)) {
            view.snb("内容不能为空", v);
            return;
        }

        String videoImg = "";
        int videoDuration = 0;
        if (!StringUtil.isEmpty(pathList)) {
            pathList = pathList.substring(0, pathList.length() - 1);
        }

//        Subscription subscription = NetEngine.getService()
//                .SaveDongtai(pathList, new SessionUtil(view.getContext()).getUserId(), data.content, videoImg, videopath, videoDuration)
//                .subscribeOn(Schedulers.io())
//                .doOnSubscribe(() -> {
//                    if (!view.isShowingDialog()) {
//                        view.showDialog();
//                    }
//                })
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SB<Res>() {
//                    @Override
//                    public void next(Res res) {
//                        if (res.code == C.OK) {
//                            view.snb("发布成功", v);
//                            view.finishActivity();
//                        }
//
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                        view.dismissDialog();
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        view.dismissDialog();
//                    }
//                });
//        addSubscription(subscription);


        if (!view.isShowingDialog()) {
            view.showDialog();
        }
        NetEngine.getService()
                .SaveDongtai(pathList, new SessionUtil(view.getContext()).getUserId(), data.content, videoImg, videopath, videoDuration)
                .enqueue(new Callback<Res>() {
                    @Override
                    public void onResponse(Response<Res> response, Retrofit retrofit) {
                        Res res = response.body();
                        if (res.code == C.OK) {
                            view.snb("发布成功", v);
                            view.finishActivity();
                        }
                        view.dismissDialog();


                    }

                    @Override
                    public void onFailure(Throwable t) {
                        view.dismissDialog();

                    }
                });
    }

    public void UpLoadConditionVideo(View v, String path) {
        this.v = v;

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
                                videopath = res.data.VideoName;
                                publishTwo("");
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
