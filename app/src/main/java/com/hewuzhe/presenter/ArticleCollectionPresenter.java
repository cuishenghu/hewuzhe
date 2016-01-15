package com.hewuzhe.presenter;

import com.hewuzhe.model.ArticleCollection;
import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.RefreshAndLoadMorePresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.view.ArticleCollectionsView;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by xianguangjin on 15/12/25.
 */
public class ArticleCollectionPresenter extends RefreshAndLoadMorePresenter<ArticleCollectionsView> {

    public void getData(final int page, final int count) {
        int id = view.getData();

//        Subscription subscription = NetEngine.getService()
//                .SelectFavoriteByUserIdArticle((page - 1) * count, count, false, id)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SB<Res<ArrayList<Article>>>() {
//                    @Override
//                    public void next(Res<ArrayList<Article>> res) {
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
                .SelectFavoriteByUserIdArticle((page - 1) * count, count, false, id)
                .enqueue(new Callback<Res<ArrayList<ArticleCollection>>>() {
                    @Override
                    public void onResponse(Response<Res<ArrayList<ArticleCollection>>> response, Retrofit retrofit) {
                        Res<ArrayList<ArticleCollection>> res = response.body();
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

}
