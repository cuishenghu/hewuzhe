package com.hewuzhe.presenter;

import com.hewuzhe.model.Plan;
import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.RefreshAndLoadMorePresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.PlanView;

import java.util.ArrayList;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

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
        Subscription subscription = NetEngine.getService()
                .GetPlanByCate(new SessionUtil(view.getContext()).getUserId(), id, (page - 1) * count, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<Plan>>>() {
                    @Override
                    public void next(Res<ArrayList<Plan>> res) {
                        if (res.code == C.OK) {
                            view.bindData(res.data);
                            setDataStatus(page, count, res);
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

    public void deletePlan(int id, int needCount) {
        this._needCount = needCount;
        nowCount = 0;
        Subscription subscription = NetEngine.getService()
                .DeletePlan(id)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (!view.isShowingDialog()) {
                            view.showDialog();
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res>() {
                    @Override
                    public void next(Res res) {
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
                    public void onCompleted() {


                    }

                    @Override
                    public void onError(Throwable e) {
                        _needCount--;
                    }
                });

        addSubscription(subscription);


        if (!view.isShowingDialog()) {
            view.showDialog();
        }

    }
}
