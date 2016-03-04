package com.hewuzhe.presenter;

import android.view.View;

import com.hewuzhe.model.Res;
import com.hewuzhe.model.UploadImage;
import com.hewuzhe.model.Video;
import com.hewuzhe.model.common.DataModel;
import com.hewuzhe.model.common.PickImg;
import com.hewuzhe.presenter.base.ListPresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.Encoder;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.utils.TimeUtil;
import com.hewuzhe.view.PublishConditionView;

import java.util.ArrayList;

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
    private int _UploadType = C.UPLOAD_TYPE_LOCAL;
    private String videoImg = "";
    private ArrayList<PickImg> list = new ArrayList<>();
    private String videoDuration = "0";

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
//        view.bindData(new Alist().add(new PickImg()).ok());
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
        this.list = list;
        neeCount = list.size();
        uploadImage();

    }

    private void uploadImage() {
        PickImg pickImg = list.get(picCount);

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
                            } else {
                                uploadImage();
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

    private void publishTwo(String pathList) {
        if (data == null) {
            data = view.getData();
        }
        if (StringUtil.isEmpty(data.content)) {
            view.snb("内容不能为空", v);
            return;
        }

        if (!StringUtil.isEmpty(pathList)) {
            pathList = pathList.substring(0, pathList.length() - 1);
        }

        videoDuration = TimeUtil.timeFormat(Long.parseLong(videoDuration));
        Subscription subscription = NetEngine.getService()
                .SaveDongtai(pathList, new SessionUtil(view.getContext()).getUserId(), data.content, videoImg, videopath, videoDuration)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (!view.isShowingDialog()) {
                            view.showDialog();
                        }
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res>() {
                    @Override
                    public void next(Res res) {
                        if (res.code == C.OK) {
                            view.publishSuccess();
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

    public void UpLoadConditionVideo(View v, String path, final String thumnail, String duration) {
        this.v = v;
        this.videoDuration = duration;
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
                                uploadVideoImg(thumnail);
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


    private void uploadVideoImg(String path) {
        Subscription subscription = NetEngine.getService()
                .UpLoadImage(Encoder.getFileName(path), Encoder.encodeBase64File(path))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (!view.isShowingDialog()) {
                            view.showDialog();
                        }
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<UploadImage>>() {
                    @Override
                    public void next(Res<UploadImage> res) {
                        if (res.code == C.OK) {
                            videoImg = res.data.ImagePath;
                            publishTwo("");
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
