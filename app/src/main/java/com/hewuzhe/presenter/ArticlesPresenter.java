package com.hewuzhe.presenter;

import com.hewuzhe.model.Article;
import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.RefreshAndLoadMorePresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.ArticlesView;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 15/12/31.
 */
public class ArticlesPresenter extends RefreshAndLoadMorePresenter<ArticlesView> {

    public void getData(final int page, final int count) {
        int cateId = view.getData();
//        Subscription subscription = NetEngine.getService()
//                .GetDongtaiPageByLianmeng((page - 1) * count, count, cateId)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SB<Res<ArrayList<Article>>>() {
//                    @Override
//                    public void next(Res<ArrayList<Article>> res) {
//                        if (res.code == C.OK) {
//                            view.bindData(res.data);
//                            setDataStatus(page, count, res);
//                        }
//
//
//                    }
//
//                    @Override
//                    public void onCompleted() {
//
//                        view.refresh(false);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        view.refresh(false);
//
//                    }
//                });
//        addSubscription(subscription);

        NetEngine.getService()
                .GetDongtaiPageByLianmeng((page - 1) * count, count, cateId, new SessionUtil(view.getContext()).getUserId())
                .enqueue(new Callback<Res<ArrayList<Article>>>() {
                    @Override
                    public void onResponse(Response<Res<ArrayList<Article>>> response, Retrofit retrofit) {
                        Res<ArrayList<Article>> res = response.body();
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

    public void getArticlesTwo(int cateId, final int page, final int count) {
        Subscription subscription = NetEngine.getService()
                .GetHezuoListByPage((page - 1) * count, count, cateId, new SessionUtil(view.getContext()).getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<Article>>>() {
                    @Override
                    public void next(Res<ArrayList<Article>> res) {
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
