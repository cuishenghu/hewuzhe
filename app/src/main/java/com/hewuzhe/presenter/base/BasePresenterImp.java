package com.hewuzhe.presenter.base;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by xianguangjin on 15/12/14.
 */
public abstract class BasePresenterImp<V> implements BasePresenter<V> {


    protected V view;
    protected CompositeSubscription mCompositeSubscription;

    @Override
    public void attachView(V view) {
        this.view = view;



    }

    @Override
    public void detachView() {
        this.view = null;
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }

    }


    public CompositeSubscription getCompositeSubscription() {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        return this.mCompositeSubscription;
    }

    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        this.mCompositeSubscription.add(s);
    }



}
