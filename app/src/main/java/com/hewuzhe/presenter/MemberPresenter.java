package com.hewuzhe.presenter;

import com.hewuzhe.model.OverTime;
import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.MemberView;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 16/1/17.
 */
public class MemberPresenter extends BasePresenterImp<MemberView> {

    public void GetPayOverTime() {
        Subscription subscription = NetEngine.getService()
                .GetPayOverTime(new SessionUtil(view.getContext()).getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<OverTime>>() {
                    @Override
                    public void next(Res<OverTime> res) {
                        if (res.code == C.OK) {
                            view.setData(res.data.ServerTime);
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
}
