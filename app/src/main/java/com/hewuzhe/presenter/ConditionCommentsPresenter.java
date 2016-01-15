package com.hewuzhe.presenter;

import com.hewuzhe.model.ConditionComment;
import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.RefreshAndLoadMorePresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.ConditionCommentsView;

import java.util.ArrayList;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 16/1/15.
 */
public class ConditionCommentsPresenter extends RefreshAndLoadMorePresenter<ConditionCommentsView> {
    private int userId;

    /**
     * 获取数据
     *
     * @param page
     * @param count
     */
    @Override
    public void getData(final int page, final int count) {
        userId = new SessionUtil(view.getContext()).getUserId();

        Subscription subscription = NetEngine.getService()
                .GetMyDongtaiPage(userId, (page - 1) * count, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<ConditionComment>>>() {
                    @Override
                    public void next(Res<ArrayList<ConditionComment>> res) {
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
}
