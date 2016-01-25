package com.hewuzhe.presenter;

import com.hewuzhe.model.Record;
import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.RefreshAndLoadMorePresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SB;
import com.hewuzhe.view.RecordView;

import java.util.ArrayList;
import java.util.LinkedList;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by xianguangjin on 15/12/25.
 */
public class RecordPresenter extends RefreshAndLoadMorePresenter<RecordView> {

    /**
     * 需要删除的数量
     */
    private int needCount = 0;
    /**
     * 已经删除的数量
     */
    private int nowCount;

    public void getData(final int page, final int count) {
        Subscription subscription = NetEngine.getService()
                .GetUpRecord(page, count, view.getData())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SB<Res<ArrayList<Record>>>() {
                    @Override
                    public void onCompleted() {
                        view.refresh(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.refresh(false);
                    }

                    @Override
                    public void next(Res<ArrayList<Record>> res) {
                        if (res.code == C.OK) {
                            view.bindData(res.data);
                            setDataStatus(page, count, res);
                        }
                    }
                });

        addSubscription(subscription);

    }

    public void deleteViedeo(LinkedList<Record> records) {

        needCount = records.size();
        nowCount = 0;

        for (Record record : records) {

            view.removeItem(record);

            Subscription subscription = NetEngine.getService()
                    .DeleteVideo(record.Id)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            {
                                if (!view.isShowingDialog()) {
                                    view.showDialog();
                                }
                            }
                        }
                    })
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SB<Res>() {
                        @Override
                        public void next(Res res) {
                            if (res.code == C.OK) {
                                nowCount++;
                                if (nowCount >= needCount) {
                                    view.deleteFinished();
                                    view.dismissDialog();
                                }

                            } else {
                                needCount--;
                            }

                        }

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            needCount--;

                        }
                    });
            addSubscription(subscription);


            if (!view.isShowingDialog()) {
                view.showDialog();
            }


        }
    }
}
