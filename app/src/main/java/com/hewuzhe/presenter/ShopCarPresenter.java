package com.hewuzhe.presenter;

import com.hewuzhe.model.Res;
import com.hewuzhe.model.Result;
import com.hewuzhe.model.ShopCar;
import com.hewuzhe.presenter.base.RefreshAndLoadMorePresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.ShopCarView;

import java.util.ArrayList;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zycom on 2016/2/2.
 */
public class ShopCarPresenter extends RefreshAndLoadMorePresenter<ShopCarView> {
    @Override
    public void getData(final int page,final int count) {
        ShopCar shopCar = view.getData();
        int i = new SessionUtil(view.getContext()).getUserId();
        Subscription subscription = NetEngine.getService()
                .SelectBasketProduct((page - 1) * count, 1000, new SessionUtil(view.getContext()).getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<ShopCar>>>() {

                    @Override
                    public void next(Res<ArrayList<ShopCar>> res) {
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

    public void deleteShopCar(String idList){
        Subscription subscription = NetEngine.getService()
                .DeleteBasketProduct(idList, new SessionUtil(view.getContext()).getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res>() {


                    @Override
                    public void next(Res res) {
                        getData( 1, 1000);
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

    public void updateProductNum(int id,int num){
        Subscription subscription = NetEngine.getService()
                .ChangeBasketProductNum(id, num, new SessionUtil(view.getContext()).getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res>() {


                    @Override
                    public void next(Res res) {
                        getData( 1, 1000);
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

    public void GetPostage(String idList){

        Subscription subscription = NetEngine.getService()
                .GetPostage(new SessionUtil(view.getContext()).getUserId(), idList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<Result>>() {


                    @Override
                    public void next(Res<Result> res) {
                        Result result = res.data;
                        view.GetPostageState(result.result);
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
