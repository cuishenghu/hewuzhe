package com.hewuzhe.presenter;

import com.hewuzhe.model.Classification;
import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.view.base.ListView;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by csh on 2016/01/29.
 * 商品分类Presenter
 */
public class ClassificationPresenter extends BasePresenterImp<ListView> {

    private int currentList;

    /**
     * 获取一级分类
     */
    public void getBigCategory() {
        NetEngine.getService()
                .SelectBigCategory()
                .enqueue(new Callback<Res<ArrayList<Classification>>>() {
                @Override
                public void onResponse(Response<Res<ArrayList<Classification>>> response, Retrofit retrofit) {
                    Res<ArrayList<Classification>> res = response.body();
                    if (res != null && res.code == C.OK) {
                        currentList = 0;
                        view.bindData(res.data);
                    }
                }

                @Override
                public void onFailure(Throwable t) {

                }
            }
        );
    }

    /**
     * 获取小分类
     */
    public void getSmallCategory(int parentId, final int type) {
        NetEngine.getService()
                .SelectSmallCategory(parentId)
                .enqueue(new Callback<Res<ArrayList<Classification>>>() {
                @Override
                public void onResponse(Response<Res<ArrayList<Classification>>> response, Retrofit retrofit) {
                    Res<ArrayList<Classification>> res = response.body();
                    if (res != null && res.code == C.OK) {
                        currentList = type;
                        view.bindData(res.data);
                    }
                }

                @Override
                public void onFailure(Throwable t) {

                }
            }
        );
    }

    public int getCurrentList() {
        return currentList;
    }
}
