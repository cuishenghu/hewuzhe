package com.hewuzhe.presenter;

import com.hewuzhe.model.ProductComment;
import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.RefreshAndLoadMorePresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.view.ProductCommentView;

import java.util.ArrayList;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zycom on 2016/2/20.
 */
public class ProductCommentPresenter extends RefreshAndLoadMorePresenter<ProductCommentView> {

    public void getData(final int productId,final int page,final int count) {

        Subscription subscription = NetEngine.getService()
                .SelectCommentByProductId(productId,(page - 1) * count, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<ProductComment>>>() {

                    @Override
                    public void next(Res<ArrayList<ProductComment>> res) {
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

    }
}
