package com.hewuzhe.presenter;

import android.view.View;

import com.hewuzhe.model.FlyDreamHeader;
import com.hewuzhe.model.MyDream;
import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.view.FlyDreamView;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by xianguangjin on 15/12/15.
 */
public class FlyDreamPresenter extends BasePresenterImp<FlyDreamView> {


    public void setData() {
//        Subscription subscription = NetEngine.getService().SelectMyDream(new SessionUtil(view.getContext()).getUser().Id)
//                .subscribeOn(Schedulers.io())
//                .doOnSubscribe(() -> view.showDialog())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SB<Res<MyDream>>() {
//                    @Override
//                    public void next(Res<MyDream> res) {
//                        if (res.code == C.OK) {
//                            view.setData(res.data);
//                        }
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                        view.dismissDialog();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                        view.dismissDialog();
//                    }
//
//
//                });

//        addSubscription(subscription);

        view.showDialog();
        NetEngine.getService().SelectMyDream(new SessionUtil(view.getContext()).getUser().Id)
                .enqueue(new Callback<Res<MyDream>>() {
                    @Override
                    public void onResponse(Response<Res<MyDream>> response, Retrofit retrofit) {
                        Res<MyDream> res = response.body();
                        if (res.code == C.OK) {
                            view.setData(res.data);
                        }
                        view.dismissDialog();

                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });

    }


    public void UpdateMyDream(final View v) {
        MyDream dream = view.getDate();
        if (StringUtil.isEmpty(dream.mydream)) {

            view.snb("我的梦想不能为空", v);
            return;
        }

        if (StringUtil.isEmpty(dream.realizedream)) {

            view.snb("如何实现梦想不能为空", v);
            return;
        }


//        NetEngine.getService()
//                .SavetMyDream(new SessionUtil(view.getContext()).getUser().Id, dream.mydream, dream.realizedream)
//                .subscribeOn(Schedulers.io())
//                .doOnSubscribe(() -> view.showDialog())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<Res>() {
//                    @Override
//                    public void onCompleted() {
//
//                        view.dismissDialog();
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        view.dismissDialog();
//                        view.snb("保存失败", v);
//
//                    }
//
//                    @Override
//                    public void onNext(Res resCode) {
//
//                        if (resCode.code == C.OK) {
//                            view.snb("保存成功", v);
//                        }
//
//                    }
//                });

        view.showDialog();

        NetEngine.getService()
                .SavetMyDream(new SessionUtil(view.getContext()).getUser().Id, dream.mydream, dream.realizedream)
                .enqueue(new Callback<Res>() {
                    @Override
                    public void onResponse(Response<Res> response, Retrofit retrofit) {
                        Res res = response.body();
                        if (res.code == C.OK) {
                            view.snb("保存成功", v);
                        }
                        view.dismissDialog();

                    }

                    @Override
                    public void onFailure(Throwable t) {
                        view.dismissDialog();
                        view.snb("保存失败", v);
                    }
                });

    }


    public void getHeader() {

//        Subscription subscription = NetEngine.getService()
//                .GetDreamHeader()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SB<Res<FlyDreamHeader>>() {
//                    @Override
//                    public void next(Res<FlyDreamHeader> res) {
//
//                        if (res.code == C.OK) {
//
//                            view.setHeader(res.data);
//                        }
//                    }
//
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//                });
//        addSubscription(subscription);

        NetEngine.getService()
                .GetDreamHeader().enqueue(new Callback<Res<FlyDreamHeader>>() {
            @Override
            public void onResponse(Response<Res<FlyDreamHeader>> response, Retrofit retrofit) {
                Res<FlyDreamHeader> res = response.body();
                if (res.code == C.OK) {

                    view.setHeader(res.data);
                }

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}
