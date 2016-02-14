package com.hewuzhe.utils;

import rx.Subscriber;

/**
 * Subscriber的封装类，打印一些信息等。。
 * Created by xianguangjin on 15/12/15.
 */
public abstract class SB<T> extends Subscriber<T> {

    @Override
    public void onNext(T t) {
//        KLog.json(new Gson().toJson(t));
        next(t);
    }

    public abstract void next(T res);


}

