package com.hewuzhe.presenter;

import android.view.View;

import com.google.gson.Gson;
import com.hewuzhe.model.Charge;
import com.hewuzhe.model.Product;
import com.hewuzhe.model.Res;
import com.hewuzhe.model.Result;
import com.hewuzhe.model.ShopCar;
import com.hewuzhe.model.Tel;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.ProductInfoView;

import java.util.ArrayList;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zycom on 2016/1/23.
 */
public class ProductInfoPresenter extends BasePresenterImp<ProductInfoView> {

    public void getData() {
        Product product = view.getData();
        Subscription subscription = NetEngine.getService()
                .SelectProductNew(product.Id,new SessionUtil(view.getContext()).getUserId(), 5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<Product>>() {

                    @Override
                    public void next(Res<Product> res) {
                        view.bindData(res.data);
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

    public void setFavoState(boolean isFavo) {
        if(isFavo){
            Product product = view.getData();
            Subscription subscription = NetEngine.getService()
                    .CancleFavoriteProduct(new SessionUtil(view.getContext()).getUserId(), product.Id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SB<Res>() {

                        @Override
                        public void next(Res res) {

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
        }else{
            Product product = view.getData();
            Subscription subscription = NetEngine.getService()
                    .FavoriteProduct(new SessionUtil(view.getContext()).getUserId(), product.Id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SB<Res>() {

                        @Override
                        public void next(Res res) {

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


    public void addInsertBasket(int productId,int num,int priceId,double price,final View v){

        Subscription subscription = NetEngine.getService()
                .InsertBasket(productId, num, priceId, new SessionUtil(view.getContext()).getUserId(),price )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res>() {

                    @Override
                    public void next(Res res) {
                        if (res.code == C.OK) {
                            view.snb("加入购物车成功", v);
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

    public void buyNow(int productId,int num,int priceId,double price,int deliveryId,String description){
        Subscription subscription = NetEngine.getService()
                .BuyNow(productId, num, priceId, new SessionUtil(view.getContext()).getUserId(), price, deliveryId, description)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res>() {

                    @Override
                    public void next(Res res) {
                        if (res.code == C.OK) {
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

    public void getCount(){
        int i = new SessionUtil(view.getContext()).getUserId();
        Subscription subscription = NetEngine.getService()
                .SelectBasketProductNew(0, 1000, new SessionUtil(view.getContext()).getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<ShopCar>>>() {

                    @Override
                    public void next(Res<ArrayList<ShopCar>> res) {
//                        String s = res.toString();
                        if (res.code == C.OK) {
                            view.showCount(res.data.size());
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

    public void SelectProductPhone() {
        Subscription subscription = NetEngine.getService()
                .SelectProductPhone()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<Tel>>>() {
                    @Override
                    public void next(Res<ArrayList<Tel>> res) {
                        if (res.code == C.OK) {
                            view.bindTel(res.data);
                        }
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

}
