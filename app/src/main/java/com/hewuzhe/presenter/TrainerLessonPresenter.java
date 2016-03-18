package com.hewuzhe.presenter;

import com.hewuzhe.model.Res;
import com.hewuzhe.model.Result;
import com.hewuzhe.model.TrainerLessonTwo;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.TrainerLessonView;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zycom on 2016/3/18.
 */
public class TrainerLessonPresenter extends BasePresenterImp<TrainerLessonView> {
    public void SelectLessonById(int idList){

        Subscription subscription = NetEngine.getService()
                .SelectLessonById(new SessionUtil(view.getContext()).getUserId(), idList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<TrainerLessonTwo>>() {


                    @Override
                    public void next(Res<TrainerLessonTwo> res) {
                        view.bindData(res.data);
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
