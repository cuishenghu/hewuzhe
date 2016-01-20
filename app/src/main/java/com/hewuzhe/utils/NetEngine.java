package com.hewuzhe.utils;

import com.hewuzhe.ui.cons.ApiService;
import com.hewuzhe.ui.cons.C;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by xianguangjin on 15/12/14.
 */

public class NetEngine {

    private static OkHttpClient client = new OkHttpClient();

    public static Retrofit retrofit;

    public static ApiService getService() {
        if (retrofit == null) {
            client.setReadTimeout(20, TimeUnit.MINUTES);
            retrofit = new Retrofit.Builder()
                    .baseUrl(C.BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }

        return retrofit.create(ApiService.class);
    }

}
