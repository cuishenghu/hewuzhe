package com.hewuzhe.presenter;

import android.view.View;
import com.hewuzhe.model.Address;
import com.hewuzhe.model.Res;
import com.hewuzhe.model.Site;
import com.hewuzhe.presenter.base.RefreshAndLoadMorePresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.view.SiteView;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.Retrofit;
import retrofit.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by csh on 2016/02/01.
 */
public class SitePresenter extends RefreshAndLoadMorePresenter<SiteView> {

    /**
     * 获取收货地址列表
     */
    @Override
    public void getData(int page, int count) {
        int userId = view.getData();
        NetEngine.getService()
                .SelectAllDeliveryAddress(userId)
                .enqueue(new Callback<Res<ArrayList<Site>>>() {
                 @Override
                 public void onResponse(Response<Res<ArrayList<Site>>> response, Retrofit retrofit) {
                     Res<ArrayList<Site>> res = response.body();
                     if (res != null && res.code == C.OK) {
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
     * 更改默认收货地址
     * @param id 地址Id
     */
    public void setDefault(int id){
        NetEngine.getService()
                .SetDefaultDeliveryAddress(view.getData(), id)
                .enqueue(new Callback<Res>() {
                @Override
                public void onResponse(Response<Res> response, Retrofit retrofit) {
                    Res res = response.body();
                    if (res.code == C.OK) {
                        getData(1, 10);
                    }
                }

                @Override
                public void onFailure(Throwable t) {

                }
            }
        );
    }

    /**
     * 删除收货地址
     * @param id 地址Id
     */
    public void delete(int id, final View v){
        NetEngine.getService()
                .DeleteDeliveryAddress(view.getData(), id+"")
                .enqueue(new Callback<Res>() {
                @Override
                public void onResponse(Response<Res> response, Retrofit retrofit) {
                    Res res = response.body();
                    if (res.code == C.OK) {
                        view.noMore();
                    } else {
                        view.snb(StringUtil.toString(res.data), v);
                    }
                }

                @Override
                public void onFailure(Throwable t) {

                }
            }
        );
    }

    /**
     * 获取地址对象
     * @param site 地址对象
     */
    public void setSiteData(Site site){
        view.setData(site);
    }

    /**
     * 添加新地址
     * @param site 地址对象
     * @param v 录入信息验证
     */
    public void saveSite(Site site, final View v){
        if (StringUtil.isEmpty(site.ReceiverName)) {
            view.snb("收货人不能为空", v);
            return;
        }
        if (!StringUtil.isMobile(site.Phone)) {
            view.snb("联系电话格式不对", v);
            return;
        }
        if (StringUtil.isEmpty(site.AreaId)) {
            view.snb("省市区不能为空", v);
            return;
        }
        if (StringUtil.isEmpty(site.Address)) {
            view.snb("详细地址不能为空", v);
            return;
        }
        if (StringUtil.isEmpty(site.Code)) {
            view.snb("邮编不能为空", v);
            return;
        }
        NetEngine.getService()
                .EditDeliveryAddress(view.getData(), site.Id, site.AreaId, site.Address, "true".equals(site.IsDefault)?1:0, site.ReceiverName, site.Phone, site.Code)
                .enqueue(new Callback<Res>() {
                @Override
                public void onResponse(Response<Res> response, Retrofit retrofit) {
                    Res res = response.body();
                    if (res.code == C.OK) {
                        view.noMore();
                    } else {
                        view.snb(StringUtil.toString(res.data), v);
                    }
                }

                @Override
                public void onFailure(Throwable t) {

                }
            }
        );
    }

    /**
     * 获取所有的省
     */
    public void getProvince(){
        view.showDialog();
        NetEngine.getService()
                .getProvince()
                .enqueue(new Callback<Res<ArrayList<Address>>>() {
                @Override
                public void onResponse(Response<Res<ArrayList<Address>>> response, Retrofit retrofit) {
                    Res<ArrayList<Address>> res = response.body();
                    if (res.code == C.OK) {
                        view.setProvinces(res.data);
                    }
                }

                @Override
                public void onFailure(Throwable throwable) {

                }
            }
        );
    }

    /**
     * 获取所有的市
     */
    public void getCity(){
        NetEngine.getService()
            .GetCity()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new SB<Res<ArrayList<Address>>>() {
                @Override
                public void next(Res<ArrayList<Address>> res) {
                    if (res.code == C.OK) {
                        view.setCitys(res.data);
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

    /**
     * 获取所有的区(县)
     */
    public void getCounty(){
        NetEngine.getService()
                .GetCounty()
                .enqueue(new Callback<Res<ArrayList<Address>>>() {
                 @Override
                 public void onResponse(Response<Res<ArrayList<Address>>> response, Retrofit retrofit) {
                     Res<ArrayList<Address>> res = response.body();
                     if (res.code == C.OK) {
                         view.setDistricts(res.data);
                     }
                     view.dismissDialog();
                 }

                 @Override
                 public void onFailure(Throwable throwable) {

                 }
             }
        );
    }
}