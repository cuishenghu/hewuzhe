package com.hewuzhe.presenter;

import com.hewuzhe.model.Address;
import com.hewuzhe.model.Classification;
import com.hewuzhe.model.PrivateTrainerList;
import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.RefreshAndLoadMorePresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.PrivateTrainerListView;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zycom on 2016/3/16.
 */
public class PrivateTrainerListPresenter extends RefreshAndLoadMorePresenter<PrivateTrainerListView> {

    public int currentList;

    /**
     * @param lat
     * @param lng
     * @param page
     * @param count
     * @desc 获取数据
     */
    public void getData(String areaname, String lat, String lng, String cateid,int length,final int page, final int count) {
        int id = new SessionUtil(view.getContext()).getUser().Id;
        Subscription subscription = NetEngine.getService()
                .SelectTeacherListNew(new SessionUtil(view.getContext()).getUser().Id, (page - 1) * count, count, areaname, lat, lng, "",cateid,length)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<PrivateTrainerList>>>() {
                    @Override
                    public void next(Res<ArrayList<PrivateTrainerList>> res) {
                        if (res.code == C.OK) {
                            view.bindData(res.data);
                            setDataStatus(page, count, res);
                            view.setNodata(res.recordcount);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        view.refresh(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.refresh(false);
                    }
                });

        addSubscription(subscription);


    }

    @Override
    public void getData(final int page, final int count) {
        String []cityName = view.getStringData();
        Subscription subscription = NetEngine.getService()
                .SelectTeacherListNewNew(new SessionUtil(view.getContext()).getUser().Id, cityName[0], cityName[1], (page - 1) * count, count, cityName[5], cityName[3],cityName[4],cityName[6],cityName[7])
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<PrivateTrainerList>>>() {
                    @Override
                    public void next(Res<ArrayList<PrivateTrainerList>> res) {
                        if (res.code == C.OK) {
                            view.bindData(res.data);
                            setDataStatus(page, count, res);
                            view.setNodata(res.recordcount);

                        }
                    }

                    @Override
                    public void onCompleted() {
                        view.refresh(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.refresh(false);
                    }
                });

        addSubscription(subscription);

    }
//@Override
//    public void getData(final int page, final int count) {
//        String []cityName = view.getStringData();
//        Subscription subscription = NetEngine.getService()
//                .SelectTeacherListNew(new SessionUtil(view.getContext()).getUser().Id, (page - 1) * count, count, cityName[0], cityName[1], cityName[2], "",cityName[3],Integer.parseInt(cityName[4]))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SB<Res<ArrayList<PrivateTrainerList>>>() {
//                    @Override
//                    public void next(Res<ArrayList<PrivateTrainerList>> res) {
//                        if (res.code == C.OK) {
//                            view.bindData(res.data);
//                            setDataStatus(page, count, res);
//                            view.setNodata(res.recordcount);
//
//                        }
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                        view.refresh(false);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        view.refresh(false);
//                    }
//                });
//
//        addSubscription(subscription);
//
//    }

    /**
     * 获取一级分类
     */
    public void getBigCategory() {
        NetEngine.getService()
                .SelectBigCategory()
                .enqueue(new Callback<Res<ArrayList<Classification>>>() {
                             @Override
                             public void onResponse(Response<Res<ArrayList<Classification>>> response, Retrofit retrofit) {
                                 Res<ArrayList<Classification>> res = response.body();
                                 if (res != null && res.code == C.OK) {
                                     currentList = 0;
//                                     view.bindList(res.data);
                                 }
                             }

                             @Override
                             public void onFailure(Throwable t) {

                             }
                         }
                );
    }

    /**
     * 获取小分类
     */
    public void getSmallCategory(int parentId, final int type) {
        NetEngine.getService()
                .SelectSmallCategory(parentId)
                .enqueue(new Callback<Res<ArrayList<Classification>>>() {
                             @Override
                             public void onResponse(Response<Res<ArrayList<Classification>>> response, Retrofit retrofit) {
                                 Res<ArrayList<Classification>> res = response.body();
                                 if (res != null && res.code == C.OK) {
                                     currentList = type;
//                                     view.bindList(res.data);
                                 }
                             }

                             @Override
                             public void onFailure(Throwable t) {

                             }
                         }
                );
    }

    public void getProvinces() {
        NetEngine.getService()
                .getProvince()
                .enqueue(new Callback<Res<ArrayList<Address>>>() {
                    @Override
                    public void onResponse(Response<Res<ArrayList<Address>>> response, Retrofit retrofit) {
                        Res<ArrayList<Address>> res = response.body();
                        if (res.code == C.OK) {
                            view.bindList(res.data);
                        }

                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });

    }

    public void getCitys(int provinceId, final int type) {

        NetEngine.getService()
                .GetCityByProvince(provinceId)
                .enqueue(new Callback<Res<ArrayList<Address>>>() {
                    @Override
                    public void onResponse(Response<Res<ArrayList<Address>>> response, Retrofit retrofit) {
                        Res<ArrayList<Address>> res = response.body();
                        if (res.code == C.OK) {
                            currentList = type;
                            view.bindList(res.data);
                        }

                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
    }

    public void getDistricts(int cityId) {
        NetEngine.getService()
                .GetCountyByCity(cityId)
                .enqueue(new Callback<Res<ArrayList<Address>>>() {
                    @Override
                    public void onResponse(Response<Res<ArrayList<Address>>> response, Retrofit retrofit) {
                        Res<ArrayList<Address>> res = response.body();
                        if (res.code == C.OK) {
                            view.bindList(res.data);
                        }

                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
    }

    public int getCurrentList() {
        return currentList;
    }
}
