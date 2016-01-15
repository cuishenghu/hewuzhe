package com.hewuzhe.presenter;

import com.hewuzhe.model.Plan;
import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.RefreshAndLoadMorePresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.PlanView;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by xianguangjin on 16/1/4.
 */
public class PlanPresenter extends RefreshAndLoadMorePresenter<PlanView> {

    private int nowCount = 0;
    private int _needCount = 0;

    /**
     * 获取数据
     *
     * @param page
     * @param count
     */
    @Override
    public void getData(final int page, final int count) {
        Integer id = view.getData();
//        Subscription subscription = NetEngine.getService()
//                .GetPlanByCate(new SessionUtil(view.getContext()).getUserId(), id, (page - 1) * count, count)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SB<Res<ArrayList<Plan>>>() {
//                    @Override
//                    public void next(Res<ArrayList<Plan>> res) {
//                        if (res.code == C.OK) {
//                            view.bindData(res.data);
//                            setDataStatus(page, count, res);
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
//        addSubscription(subscription);

        NetEngine.getService()
                .GetPlanByCate(new SessionUtil(view.getContext()).getUserId(), id, (page - 1) * count, count)
                .enqueue(new Callback<Res<ArrayList<Plan>>>() {
                    @Override
                    public void onResponse(Response<Res<ArrayList<Plan>>> response, Retrofit retrofit) {
                        Res<ArrayList<Plan>> res = response.body();
                        if (res.code == C.OK) {
                            view.bindData(res.data);
                            setDataStatus(page, count, res);
                        }
                        view.refresh(false);

                    }

                    @Override
                    public void onFailure(Throwable t) {
                        view.refresh(false);

                    }
                });
    }

    public void deletePlan(int id, int needCount) {
        this._needCount = needCount;
        nowCount = 0;
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
//                        if (res.code == C.OK) {
//                            nowCount++;
//                            if (nowCount >= _needCount) {
//                                view.dismissDialog();
//                                view.onReceive(C.MSG_TWO);
//                            }
//                        } else {
//                            _needCount--;
//                        }
//                    }
//
//                    @Override
//                    public void onCompleted() {
//
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        _needCount--;
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
                        if (res.code == C.OK) {
                            nowCount++;
                            if (nowCount >= _needCount) {
                                view.dismissDialog();
                                view.onReceive(C.MSG_TWO);
                            }
                        } else {
                            _needCount--;
                        }

                    }

                    @Override
                    public void onFailure(Throwable t) {
                        _needCount--;

                    }
                });
    }
}
