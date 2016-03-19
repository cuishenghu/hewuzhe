package com.hewuzhe.presenter;

import android.view.View;

import com.hewuzhe.model.Address;
import com.hewuzhe.model.Res;
import com.hewuzhe.model.Result;
import com.hewuzhe.model.TrainerLessonTwo;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.TrainerLessonView;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zycom on 2016/3/18.
 */
public class TrainerLessonPresenter extends BasePresenterImp<TrainerLessonView> {
    public void SelectLessonById(int idList){

        Subscription subscription = NetEngine.getService()
                .SelectLessonById(new SessionUtil(view.getContext()).getUserId(), idList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<TrainerLessonTwo>>() {


                    @Override
                    public void next(Res<TrainerLessonTwo> res) {
                        view.bindData(res.data);
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

    public void JoinLessonByLessonId(final View viewshow,int Lessonid,String realname,String phone,int age,int sex,String areaid){

        Subscription subscription = NetEngine.getService()
                .JoinLessonByLessonId(new SessionUtil(view.getContext()).getUserId(), Lessonid, realname, phone, age, sex, areaid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res>() {


                    @Override
                    public void next(Res res) {
                        view.snb("报名成功！",viewshow);
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

    /**
     * 获取所有的省
     */
    public void getProvince(){
        view.showDialog();
        NetEngine.getService()
                .getProvince()
                .enqueue(new Callback<Res<ArrayList<Address>>>() {
                             @Override
                             public void onResponse(Response<Res<ArrayList<Address>>> response, Retrofit retrofit) {
                                 Res<ArrayList<Address>> res = response.body();
                                 if (res.code == C.OK) {
                                     view.setProvinces(res.data);
                                 }
                             }

                             @Override
                             public void onFailure(Throwable throwable) {

                             }
                         }
                );
    }

    /**
     * 获取所有的市
     */
    public void getCity(){
        NetEngine.getService()
                .GetCity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<Address>>>() {
                    @Override
                    public void next(Res<ArrayList<Address>> res) {
                        if (res.code == C.OK) {
                            view.setCitys(res.data);
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

    /**
     * 获取所有的区(县)
     */
    public void getCounty(){
        NetEngine.getService()
                .GetCounty()
                .enqueue(new Callback<Res<ArrayList<Address>>>() {
                             @Override
                             public void onResponse(Response<Res<ArrayList<Address>>> response, Retrofit retrofit) {
                                 Res<ArrayList<Address>> res = response.body();
                                 if (res.code == C.OK) {
                                     view.setDistricts(res.data);
                                 }
                                 view.dismissDialog();
                             }

                             @Override
                             public void onFailure(Throwable throwable) {

                             }
                         }
                );
    }
}
