package com.hewuzhe.presenter;

import com.hewuzhe.model.Res;
import com.hewuzhe.model.TeamIntroduce;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.view.TeamIntroduceView;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by xianguangjin on 16/1/12.
 */
public class TeamIntroducePresenter extends BasePresenterImp<TeamIntroduceView> {


    public void getData() {
        NetEngine.getService()
                .SelectTeamIntroduce(view.getData())
                .enqueue(new Callback<Res<TeamIntroduce>>() {
                    @Override
                    public void onResponse(Response<Res<TeamIntroduce>> response, Retrofit retrofit) {
                        Res<TeamIntroduce> res = response.body();
                        if (res.code == C.OK) {
                            view.setData(res.data);

                        }
                        view.dismissDialog();

                    }

                    @Override
                    public void onFailure(Throwable t) {
                        view.dismissDialog();
                    }
                });

    }
}
