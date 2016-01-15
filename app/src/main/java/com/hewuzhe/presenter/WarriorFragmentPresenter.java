package com.hewuzhe.presenter;

import com.hewuzhe.model.Res;
import com.hewuzhe.model.User;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.WarriorFragmentView;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 15/12/30.
 */
public class WarriorFragmentPresenter extends BasePresenterImp<WarriorFragmentView> {

    public void getUserData() {
        User user = new SessionUtil(view.getContext()).getUser();
        if (user != null) {
            Subscription subscription = NetEngine.getService()
                    .UpdateUser(user.Id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SB<Res<User>>() {
                        @Override
                        public void next(Res<User> res) {
                            if (res.code == C.OK) {
                                new SessionUtil(view.getContext()).putUser(res.data);
                                view.setUserData();
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

}
