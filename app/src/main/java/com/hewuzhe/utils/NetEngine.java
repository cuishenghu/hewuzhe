package com.hewuzhe.utils;

import com.hewuzhe.ui.cons.ApiService;
import com.hewuzhe.ui.cons.C;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;


/**
 * Created by xianguangjin on 15/12/14.
 */
public class NetEngine {

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(C.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();

    public static ApiService getService() {
        return retrofit.create(ApiService.class);
    }


}
