package com.hewuzhe.presenter;

import com.hewuzhe.model.Address;
import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widget.CharacterParser;
import com.hewuzhe.ui.widget.PinyinComparator;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.view.CitySelectView;

import java.util.ArrayList;
import java.util.Collections;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 15/12/30.
 */
public class CitySelectPresenter extends BasePresenterImp<CitySelectView> {

    public void getCitys() {
        Subscription subscription = NetEngine.getService()
                .GetCity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<Address>>>() {
                    @Override
                    public void next(Res<ArrayList<Address>> res) {
                        if (res.code == C.OK) {

                            PinyinComparator mPinyinComparator = new PinyinComparator();
                            CharacterParser mCharacterParser = CharacterParser.getInstance();
                            for (Address address : res.data) {
                                address.topc = mCharacterParser.getSelling(address.Name).substring(0, 1).toUpperCase();
                                if (address.Name.equals("重庆")) {
                                    address.topc = "C";
                                }
                            }
                            Collections.sort(res.data, mPinyinComparator);

                            view.bindData(res.data);

                        }

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


}
