package com.hewuzhe.presenter;

import com.hewuzhe.model.MegaGame;
import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.RefreshAndLoadMorePresenter;
import com.hewuzhe.ui.activity.MegaGameActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.fragment.MegaGameFragment;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.MegaGameView;

import java.util.ArrayList;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 15/12/31.
 */
public class MegaGamePresenter extends RefreshAndLoadMorePresenter<MegaGameView> {
    MegaGameFragment megaGameFragment;
    public MegaGamePresenter(MegaGameFragment megaGameFragment){
        this.megaGameFragment = megaGameFragment;
    }

    public void getData(final int page, final int count) {
        String path = view.getData();
        Subscription subscription = NetEngine.getService()
                .getGames( (page - 1) * count, count,new SessionUtil(megaGameFragment.getContext()).getUserId()+"",path,0+"")
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
}
