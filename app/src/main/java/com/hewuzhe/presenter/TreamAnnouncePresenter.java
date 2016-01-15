package com.hewuzhe.presenter;

import com.hewuzhe.model.Res;
import com.hewuzhe.model.TeamAnnounce;
import com.hewuzhe.presenter.base.RefreshAndLoadMorePresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.view.TreamAnnounceView;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by xianguangjin on 16/1/12.
 */
public class TreamAnnouncePresenter extends RefreshAndLoadMorePresenter<TreamAnnounceView> {
    /**
     * 获取数据
     *
     * @param page
     * @param count
     */
    @Override
    public void getData(final int page, final int count) {

        NetEngine.getService()
                .SelectTeamAnnouncement((page - 1) * count, count, view.getData())
                .enqueue(new Callback<Res<ArrayList<TeamAnnounce>>>() {
                    @Override
                    public void onResponse(Response<Res<ArrayList<TeamAnnounce>>> response, Retrofit retrofit) {
                        Res<ArrayList<TeamAnnounce>> res = response.body();
                        if (res.code == C.OK) {
                            view.bindData(res.data);
                            setDataStatus(page, count, res);
                        }
                        view.refresh(false);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        view.refresh(false);

                    }
                });
    }
}
