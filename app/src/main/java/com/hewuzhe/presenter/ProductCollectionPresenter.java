package com.hewuzhe.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hewuzhe.model.Product;
import com.hewuzhe.model.ProductCollection;
import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.RefreshAndLoadMorePresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.ProductCollectionView;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zycom on 2016/2/20.
 */
public class ProductCollectionPresenter extends RefreshAndLoadMorePresenter<ProductCollectionView> {

    private int _NeedCount = 0;
    private int _HasCount = 0;

    public void getData(final int page,final int count) {

        Subscription subscription = NetEngine.getService()
                .SelectFavoriteByUserIdAndSearch((page-1)*count, count ,new SessionUtil(view.getContext()).getUserId(),"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<ProductCollection>>>() {

                    @Override
                    public void next(Res<ArrayList<ProductCollection>> res) {
//                        String s = res.toString();
                        if (res.code == C.OK) {
                            view.bindData(res.data);
                            setDataStatus(page, count, res);
                        }
                        view.refresh(false);
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.refresh(false);

                    }
                });
        addSubscription(subscription);
    }


    public void deleteCollection(int Id, final View v) {
        view.showDialog();
        NetEngine.getService()
                .CancleFavoriteProduct(new SessionUtil(view.getContext()).getUserId(), Id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res>() {
                    @Override
                    public void next(Res res) {
                        if (res.code == C.OK) {
                            _HasCount++;
                            if (_HasCount >= _NeedCount) {
                                view.collectAndOther();
                            }

                        } else {
                            view.snb("操作失败", v);
                            _NeedCount--;
                        }
                    }

                    @Override
                    public void onCompleted() {
                        view.dismissDialog();


                    }

                    @Override
                    public void onError(Throwable e) {
                        _NeedCount--;
                        view.dismissDialog();
                        view.snb("操作失败", v);


                    }
                });
    }

}
