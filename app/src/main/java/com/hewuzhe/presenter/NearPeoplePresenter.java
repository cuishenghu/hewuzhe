package com.hewuzhe.presenter;

import com.hewuzhe.model.NearPeople;
import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.RefreshAndLoadMoreForListPresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.NearPeopleView;

import java.util.ArrayList;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zycom on 2016/3/31.
 */
public class NearPeoplePresenter extends RefreshAndLoadMoreForListPresenter<NearPeopleView> {
    @Override
    public void getData(int page, int count, String search, int categoryId, int isPriceAsc, int isSalesAsc, int CommentAsc, int isNewAsc, int isCredit, int isRecommend) {

    }

    @Override
    public void getData(final int page, final int count, int userid, double lat, double lng, int length, int age, int sexuality) {
        Subscription subscription = NetEngine.getService()
                .SelectNearbyUser((page - 1) * count, count, new SessionUtil(view.getContext()).getUserId(), lat+"", lng+"", length, age, sexuality)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<NearPeople>>>() {

                    @Override
                    public void next(Res<ArrayList<NearPeople>> res) {
                        if (res.code == C.OK) {
                            view.bindNearPeople(res.data);
                            setDataStatus(page, count, res);
                        }
                    }

                    @Override
                    public void onCompleted() {

                        view.dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.dismissDialog();

                    }
                });
        addSubscription(subscription);
    }
}
