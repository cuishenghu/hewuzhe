package com.hewuzhe.presenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;

import com.hewuzhe.model.Res;
import com.hewuzhe.model.UploadImage;
import com.hewuzhe.model.User;
import com.hewuzhe.presenter.common.AreaPresenter;
import com.hewuzhe.ui.activity.MainActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.Encoder;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.ProfileView;
import com.socks.library.KLog;

import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 15/12/15.
 */
public class ProfilePresenter extends AreaPresenter<ProfileView> {


    public void updateUserInfo() {


    }

    public void saveImgAvatar(final View v, Bitmap b, String name) {

        try {
            Bitmap bb = Encoder.compressImage(b);
            FileOutputStream out = new FileOutputStream(name);
            bb.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

            String fileStream = Encoder.getEnocodeStr(name);

            Subscription subscription = NetEngine.getService()
                    .UpLoadPhoto(name, fileStream, new SessionUtil(view.getContext()).getUser().Id)
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
                                view.snb("上传成功", v);
                                User user = new SessionUtil(view.getContext()).getUser();
                                user.PhotoPath = res.date.ImageName;

                                new SessionUtil(view.getContext())
                                        .putUser(user);
                                view.setData();
                            } else {
                                view.snb("上传失败", v);
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void saveData(final View v) {
        User user = view.getData();

        Subscription subscription = NetEngine.getService()
                .ChangeInfor(user.Id, user.NicName, user.Sexuality, user.Height, user.Weight, user.HomeAreaId, user.Experience, user.Description, user.Birthday)
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
                            view.snb("修改成功", v);
                            getUserData();
                        } else {
                            view.snb("修改失败", v);
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


    public void saveDataSignup(final View v) {
        User user = view.getData();

        Subscription subscription = NetEngine.getService()
                .ChangeInfor(user.Id, user.NicName, user.Sexuality, user.Height, user.Weight, user.HomeAreaId, user.Experience, user.Description, user.Birthday)
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
                            view.snb("修改成功", v);
                            /**
                             * 进入主页*/
                            Intent intent = new Intent(view.getContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            view.getContext().startActivity(intent);

                        } else {
                            view.snb("修改失败", v);
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


    public void getUserData() {
        User user = new SessionUtil(view.getContext()).getUser();
        if (user != null) {
            Subscription subscription = NetEngine.getService()
                    .UpdateUser(user.Id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SB<Res<User>>() {
                        @Override
                        public void next(Res<User> res) {
                            if (res.code == C.OK) {

                                HashSet<String> sets = new HashSet<String>();
                                sets.add("AreaId_" + res.data.HomeAreaId);
                                JPushInterface.setTags(view.getContext(), sets, new TagAliasCallback() {
                                    @Override
                                    public void gotResult(int i, String s, Set<String> set) {
                                        KLog.e(i + s);
                                    }
                                });
                                new SessionUtil(view.getContext()).putUser(res.data);
                                view.setData();
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

    /**
     * 获取数据
     *
     * @param page
     * @param count
     */
    @Override
    public void getData(int page, int count) {

    }
}
