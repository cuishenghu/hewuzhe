package com.hewuzhe.presenter;

import com.hewuzhe.model.PrivateTrainerList;
import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.RefreshAndLoadMorePresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.PrivateTrainerListView;

import java.util.ArrayList;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zycom on 2016/3/16.
 */
public class PrivateTrainerListPresenter extends RefreshAndLoadMorePresenter<PrivateTrainerListView> {

    /**
     * @param lat
     * @param lng
     * @param page
     * @param count
     * @desc 获取数据
     */
    public void getData(String areaname, String lat, String lng, final int page, final int count) {
        int id = new SessionUtil(view.getContext()).getUser().Id;
        Subscription subscription = NetEngine.getService()
                .SelectTeacherList(new SessionUtil(view.getContext()).getUser().Id,(page - 1) * count, count, areaname, lat, lng,"")
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
                .SelectTeacherList(new SessionUtil(view.getContext()).getUser().Id, (page - 1) * count, count, cityName[0], cityName[1], cityName[2], "")
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
}
