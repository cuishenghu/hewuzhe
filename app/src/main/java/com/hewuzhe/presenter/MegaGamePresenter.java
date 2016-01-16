package com.hewuzhe.presenter;

import com.hewuzhe.model.MegaGame;
import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.RefreshAndLoadMorePresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.view.MegaGameView;

import java.util.ArrayList;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 15/12/31.
 */
public class MegaGamePresenter extends RefreshAndLoadMorePresenter<MegaGameView> {

    public void getData(final int page, final int count) {
        String path = view.getData();
        Subscription subscription = NetEngine.getService()
                .getGames(path, (page - 1) * count, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<MegaGame>>>() {
                    @Override
                    public void next(Res<ArrayList<MegaGame>> res) {
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
