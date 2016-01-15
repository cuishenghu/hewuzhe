package com.hewuzhe.presenter;

import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.view.CatView;

/**
 * Created by xianguangjin on 15/12/25.
 */
public class CatPresenter extends BasePresenterImp<CatView> {


//    public void getData(int page, int count) {
//
//        Subscription subscription = NetEngine.getService()
//                .GetChannel((page - 1) * count, count)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SB<Res<ArrayList<Cate>>>() {
//                    @Override
//                    public void next(Res<ArrayList<Cate>> res) {
//                        if (res.code == C.OK) {
//                            view.bindData(res.data);
//
//                        }
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                        view.ref(false);
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        view.setRefresh(false);
//
//                    }
//                });
//
//        addSubscription(subscription);
//
//
//    }
}
