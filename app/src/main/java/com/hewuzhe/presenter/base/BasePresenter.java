package com.hewuzhe.presenter.base;

/**
 * Created by xianguangjin on 15/12/14.
 */
public interface BasePresenter<V> {

    void attachView(V view);

    void detachView();

}
