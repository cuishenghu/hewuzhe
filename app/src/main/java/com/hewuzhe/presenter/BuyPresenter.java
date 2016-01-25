package com.hewuzhe.presenter;

import com.google.gson.Gson;
import com.hewuzhe.model.Charge;
import com.hewuzhe.model.GetChargeRequest;
import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.BuyView;
import com.socks.library.KLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Request;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 15/12/30.
 */
public class BuyPresenter extends BasePresenterImp<BuyView> {

    public void getCharge() {

        GetChargeRequest request = view.getData();

        Subscription subscription = NetEngine.getService()
                .GetCharge(request.userId, request.channel, request.amount, request.description, 0)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        view.showDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Charge>() {
                    @Override
                    public void onCompleted() {
                        view.dismissDialog();

                    }

                    @Override
                    public void onError(Throwable e) {

                        view.dismissDialog();
                    }

                    @Override
                    public void onNext(Charge s) {
//                        String charge = "{\n" +
//                                "\t\"id\": \"ch_GKizzTPKmv9Ky1irT8SCy9KK\",\n" +
//                                "\t\"object\": \"charge\",\n" +
//                                "\t\"created\": 1453689718,\n" +
//                                "\t\"livemode\": true,\n" +
//                                "\t\"paid\": false,\n" +
//                                "\t\"refunded\": false,\n" +
//                                "\t\"app\": \"app_qfD04CjHGu54aXXv\",\n" +
//                                "\t\"channel\": \"wx\",\n" +
//                                "\t\"order_no\": \"2016012500000011\",\n" +
//                                "\t\"client_ip\": \"127.0.0.1\",\n" +
//                                "\t\"amount\": 120000,\n" +
//                                "\t\"amount_settle\": 120000,\n" +
//                                "\t\"currency\": \"cny\",\n" +
//                                "\t\"subject\": \"46\",\n" +
//                                "\t\"body\": \"1\",\n" +
//                                "\t\"extra\": {},\n" +
//                                "\t\"time_paid\": null,\n" +
//                                "\t\"time_expire\": 1453696918,\n" +
//                                "\t\"time_settle\": null,\n" +
//                                "\t\"transaction_no\": null,\n" +
//                                "\t\"refunds\": {\n" +
//                                "\t\t\"object\": \"list\",\n" +
//                                "\t\t\"url\": \"/v1/charges/ch_GKizzTPKmv9Ky1irT8SCy9KK/refunds\",\n" +
//                                "\t\t\"has_more\": false,\n" +
//                                "\t\t\"data\": []\n" +
//                                "\t},\n" +
//                                "\t\"amount_refunded\": 0,\n" +
//                                "\t\"failure_code\": null,\n" +
//                                "\t\"failure_msg\": null,\n" +
//                                "\t\"metadata\": {},\n" +
//                                "\t\"credential\": {\n" +
//                                "\t\t\"object\": \"credential\",\n" +
//                                "\t\t\"wx\": {\n" +
//                                "\t\t\t\"appId\": \"wxb1fb6e1f1f47c07f\",\n" +
//                                "\t\t\t\"partnerId\": \"1299611201\",\n" +
//                                "\t\t\t\"prepayId\": \"wx201601251041596f27ae6bf30881432530\",\n" +
//                                "\t\t\t\"nonceStr\": \"1a6e8c6e49b9d817337e7e1d3dd21d62\",\n" +
//                                "\t\t\t\"timeStamp\": 1453689719,\n" +
//                                "\t\t\t\"packageValue\": \"Sign=WXPay\",\n" +
//                                "\t\t\t\"sign\": \"AF6F5855F8D06F3EA31CA55BBEF7EEF0\"\n" +
//                                "\t\t}\n" +
//                                "\t},\n" +
//                                "\t\"description\": null\n" +
//                                "}";

//                        view.toPay(charge);

                        view.toPay(new Gson().toJson(s));
                    }
                });
        addSubscription(subscription);

    }

    public void getCharges() {
        GetChargeRequest request = view.getData();

        OkHttpUtils
                .post()
                .addParams("userid", request.userId + "")
                .addParams("channel", request.channel)
                .addParams("amount", request.amount + "")
                .addParams("description", request.description)
                .addParams("flg", "0")
                .url(C.BASE_URL + "LoginAndRegister.asmx/GetCharge")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        KLog.d(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response) {

                        view.toPay(response);
                        KLog.json(response);
                    }
                });


    }

    public void OpenHuiyuan(int month) {

        Subscription subscription = NetEngine.getService()
                .OpenHuiyuan(new SessionUtil(view.getContext()).getUserId(), month)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Res res) {
                        if (res.code == C.OK) {
                            view.toast("开通成功");
                        }
                    }
                });

        addSubscription(subscription);
    }
}
