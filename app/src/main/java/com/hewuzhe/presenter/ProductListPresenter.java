package com.hewuzhe.presenter;

import com.hewuzhe.model.Product;
import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.presenter.base.RefreshAndLoadMorePresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.view.ProductListView;

import java.util.ArrayList;

import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zycom on 2016/1/23.
 */
public class ProductListPresenter extends RefreshAndLoadMorePresenter<ProductListView> {

    public void getData(final int page,final int count,final String search,final int categoryId,final int isPriceAsc,final int isSalesAsc,final int CommentAsc,final int isNewAsc,final int isCredit,final int isRecommend) {
        Product product = view.getData();
        Subscription subscription = NetEngine.getService()
                .SelectProductBySearch((page-1)*count, count ,search,categoryId,isPriceAsc,isSalesAsc,CommentAsc,isNewAsc,isCredit,isRecommend)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<Product>>>() {

                    @Override
                    public void next(Res<ArrayList<Product>> res) {
//                        String s = res.toString();
                        if (res.code == C.OK) {
                            view.bindData(res.data);
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

    @Override
    public void getData(int page, int count) {
        getData(page,count,"",0,0,0,0,0,0,0);
    }
}
