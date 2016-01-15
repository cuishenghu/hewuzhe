package com.hewuzhe.presenter;

import android.view.View;

import com.hewuzhe.model.Plan;
import com.hewuzhe.model.Res;
import com.hewuzhe.model.UploadImage;
import com.hewuzhe.model.common.DataModel;
import com.hewuzhe.model.common.PickImg;
import com.hewuzhe.presenter.base.ListPresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.Encoder;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.view.PublishPlanView;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by xianguangjin on 16/1/2.
 */
public class PublishPlanPresenter extends ListPresenter<PublishPlanView> {


    /**
     * 需要上传的图片数
     */
    private int neeCount = 0;
    private View v;
    private int picCount = 0;
    private String pathList = "";
    private DataModel<PickImg, Plan> data;


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

        if (data.m.Id != -1) {
            deletePlan(data.m.Id);

        } else {
            beforePublish();
        }

    }


    public void deletePlan(int id) {
//        Subscription subscription = NetEngine.getService()
//                .DeletePlan(id)
//                .subscribeOn(Schedulers.io())
//                .doOnSubscribe(() -> {
//                    if (!view.isShowingDialog()) {
//                        view.showDialog();
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SB<Res>() {
//                    @Override
//                    public void next(Res res) {
//
//
//                    }
//
//                    @Override
//                    public void onCompleted() {
//
//                        beforePublish();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        beforePublish();
//                    }
//                });

//        addSubscription(subscription);

        if (!view.isShowingDialog()) {
            view.showDialog();
        }

        NetEngine.getService()
                .DeletePlan(id)
                .enqueue(new Callback<Res>() {
                    @Override
                    public void onResponse(Response<Res> response, Retrofit retrofit) {
                        Res res = response.body();
                        beforePublish();

                    }

                    @Override
                    public void onFailure(Throwable t) {
                        beforePublish();

                    }
                });
    }


    private void beforePublish() {
        if (StringUtil.isEmpty(data.m.Content)) {
            view.snb("内容不能为空", v);
            return;
        }

        if (StringUtil.isEmpty(data.m.Title)) {
            view.snb("标题不能为空", v);
            return;
        }

        if (data.m.catId == -1) {
            view.snb("周期不能为空", v);
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
            if (!StringUtil.isEmpty(pickImg.picUrl)) {
                pathList += pickImg.picUrl + "&";
                neeCount--;
                if (picCount >= neeCount) {
                    publishTwo(pathList);
                }

            } else {
//                Subscription subscription = NetEngine.getService()
//                        .UpLoadImage(Encoder.getFileName(pickImg.filePath), Encoder.encodeBase64File(pickImg.filePath))
//                        .subscribeOn(Schedulers.io())
//                        .doOnSubscribe(() -> view.showDialog())
//                        .subscribeOn(AndroidSchedulers.mainThread())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new SB<Res<UploadImage>>() {
//                            @Override
//                            public void next(Res<UploadImage> res) {
//                                if (res.code == C.OK) {
//                                    picCount++;
//                                    pathList += res.data.ImagePath + "&";
//                                    if (picCount >= neeCount) {
//                                        publishTwo(pathList);
//                                    }
//                                } else {
//                                    neeCount--;
//                                }
//
//                            }
//
//                            @Override
//                            public void onCompleted() {
//
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                neeCount--;
//                            }
//                        });
//                addSubscription(subscription);


                view.showDialog();

                NetEngine.getService()
                        .UpLoadImage(Encoder.getFileName(pickImg.filePath), Encoder.encodeBase64File(pickImg.filePath))
                        .enqueue(new Callback<Res<UploadImage>>() {
                            @Override
                            public void onResponse(Response<Res<UploadImage>> response, Retrofit retrofit) {
                                Res<UploadImage> res = response.body();

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
                            public void onFailure(Throwable t) {
                                neeCount--;

                            }
                        });
            }
        }
    }

    private void publishTwo(String pathList) {
        if (!StringUtil.isEmpty(pathList)) {
            pathList = pathList.substring(0, pathList.length() - 1);
        }

//        Subscription subscription = NetEngine.getService()
//                .SavePlan(pathList, new SessionUtil(view.getContext()).getUserId(), data.m.Content, data.m.Title, data.m.catId, data.m.StartTime, data.m.EndTime)
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

        NetEngine.getService()
                .SavePlan(pathList, new SessionUtil(view.getContext()).getUserId(), data.m.Content, data.m.Title, data.m.catId, data.m.StartTime, data.m.EndTime)
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
}
