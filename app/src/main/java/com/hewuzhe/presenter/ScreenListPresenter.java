package com.hewuzhe.presenter;

import com.hewuzhe.model.Res;
import com.hewuzhe.model.ScreenList;
import com.hewuzhe.model.Video;
import com.hewuzhe.presenter.base.RefreshAndLoadMoreForListPresenter;
import com.hewuzhe.presenter.base.RefreshAndLoadMorePresenter;
import com.hewuzhe.ui.activity.GDMapLocationActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.view.ScreenListView;

import java.util.ArrayList;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zycom on 2016/3/23.
 */
public class ScreenListPresenter extends RefreshAndLoadMorePresenter<ScreenListView> {

    GDMapLocationActivity gdmla;

    public ScreenListPresenter(){}
    public ScreenListPresenter(GDMapLocationActivity gdMapLocationActivity){gdmla = gdMapLocationActivity;}

    public void SelectTeacherCateList(double lat,double lng,int m){
        Subscription subscription = NetEngine.getService()
                .SelectTeacherCateList(lat, lng, m)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<ScreenList>>>() {
                    @Override
                    public void next(Res<ArrayList<ScreenList>> res) {
                        if (res.code == C.OK) {
                            view.bindData(res.data);
                            view.noMore();

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

    @Override
    public void getData(int page, int count) {

    }

    public void GetProvinceByCity(String city){
        Subscription subscription = NetEngine.getService()
                .GetProvinceByCity(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res>() {
                    @Override
                    public void next(Res res) {
                        if (res.code == C.OK) {
                            String city = res.data.toString();
                            gdmla.bindCity(city);
                            view.noMore();

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
