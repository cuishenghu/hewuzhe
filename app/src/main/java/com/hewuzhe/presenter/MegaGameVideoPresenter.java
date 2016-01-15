package com.hewuzhe.presenter;

import com.hewuzhe.model.MegaGameVideo;
import com.hewuzhe.model.MegaGameVideosRequest;
import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.RefreshAndLoadMorePresenter;
import com.hewuzhe.ui.activity.MegaGameActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.view.MegaGameVideosView;

import java.util.ArrayList;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 16/1/14.
 */
public class MegaGameVideoPresenter extends RefreshAndLoadMorePresenter<MegaGameVideosView> {
    /**
     * 获取数据
     *
     * @param page
     * @param count
     */
    @Override
    public void getData(final int page, final int count) {
        MegaGameVideosRequest data = view.getData();

        Subscription subscription = NetEngine.getService()
                .SelectAllMatchMemberBySearch(MegaGameActivity.PAGE == 0 ? "SelectAllMatchMemberBySearch" : "SelectAllMatchTeamBySearch", (page - 1) * count, count, data.matchId, data.membercode, data.nicname, data.flg)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<MegaGameVideo>>>() {
                    @Override
                    public void next(Res<ArrayList<MegaGameVideo>> res) {
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
