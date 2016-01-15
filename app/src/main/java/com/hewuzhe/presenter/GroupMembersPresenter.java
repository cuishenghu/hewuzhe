package com.hewuzhe.presenter;

import com.hewuzhe.model.Friend;
import com.hewuzhe.model.Res;
import com.hewuzhe.presenter.base.RefreshAndLoadMorePresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NetEngine;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.GroupMembersView;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by xianguangjin on 16/1/12.
 */
public class GroupMembersPresenter extends RefreshAndLoadMorePresenter<GroupMembersView> {

    /**
     * 获取数据
     *
     * @param page
     * @param count
     */
    @Override
    public void getData(final int page, final int count) {
        NetEngine.getService()
                .SelectTeamMember(view.getData(),new SessionUtil(view.getContext()).getUserId())
                .enqueue(new Callback<Res<ArrayList<Friend>>>() {
                    @Override
                    public void onResponse(Response<Res<ArrayList<Friend>>> response, Retrofit retrofit) {
                        Res<ArrayList<Friend>> res = response.body();
                        if (res != null) {
                            if (res.code == C.OK) {
                                view.bindData(res.data);
                                setDataStatus(page, count, res);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });

    }
}
