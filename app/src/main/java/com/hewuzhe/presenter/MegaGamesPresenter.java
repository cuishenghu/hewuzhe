package com.hewuzhe.presenter;

import com.hewuzhe.model.MatchCategory;
import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.activity.MegaGameActivity;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.base.BaseView;

import java.util.ArrayList;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 16/1/31.
 */
public class MegaGamesPresenter extends BasePresenterImp<BaseView> {

    MegaGameActivity megaGameActivity;

    public MegaGamesPresenter(MegaGameActivity megaGameActivity){
        this.megaGameActivity = megaGameActivity;
    }

    public void DeleteNoReadMatch() {
        Subscription subscription = NetEngine.getService()
                .DeleteNoReadMatch(new SessionUtil(view.getContext()).getUserId(), MegaGameActivity.PAGE == 0 ? 1 : 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res>() {
                    @Override
                    public void next(Res res) {

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

    public void SelectMatchCategory() {
        Subscription subscription = NetEngine.getService()
                .SelectMatchCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<MatchCategory>>>() {
                    @Override
                    public void next(Res<ArrayList<MatchCategory>> res) {
                        megaGameActivity.getData(res.data);


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
