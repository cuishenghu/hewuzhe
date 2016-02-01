package com.hewuzhe.presenter;

import com.hewuzhe.model.Res;
import com.hewuzhe.model.Video;
import com.hewuzhe.presenter.base.RefreshAndLoadMorePresenter;
import com.hewuzhe.ui.activity.SearchCatVideosActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.view.SearchVideosView;

import java.util.ArrayList;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 16/1/31.
 */
public class SearchCatVideosPresenter extends RefreshAndLoadMorePresenter<SearchVideosView> {

    /**
     * 获取数据
     *
     * @param page
     * @param count
     */
    @Override
    public void getData(final int page, final int count) {
        String keyword = view.getData();
        Subscription subscription = NetEngine.getService()
                .SelectVideoByCategory((page - 1) * count, count, SearchCatVideosActivity.CAT_ID, keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<Video>>>() {
                    @Override
                    public void next(Res<ArrayList<Video>> res) {
                        if (res.code == C.OK) {
                            view.bindData(res.data);
                            setDataStatus(page, count, res);
                            if (res.recordcount > 0) {
                                view.showNoData(false, "什么都没有找到");
                            }

                        } else {

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
    public void setDataStatus(int page, int count, Res res) {
        super.setDataStatus(page, count, res);
        if (res.recordcount <= 0) {
            view.showNoData(true, "什么都没有找到");
        }

    }


}
