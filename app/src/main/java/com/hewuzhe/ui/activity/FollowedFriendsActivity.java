package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hewuzhe.R;
import com.hewuzhe.model.Friend;
import com.hewuzhe.presenter.FollowedFriendsPresenter;
import com.hewuzhe.ui.adapter.FollowedFriendAdapter;
import com.hewuzhe.ui.base.RecycleViewActivity;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.FollowedFriendsView;

import java.util.ArrayList;

public class FollowedFriendsActivity extends RecycleViewActivity<FollowedFriendsPresenter, FollowedFriendAdapter, Friend> implements FollowedFriendsView {

    /**
     * @return 提供LayoutId
     */
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_followed_friends;
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {

    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        presenter.getData(page, count);

    }

    /**
     * 绑定Presenter
     */
    @Override
    public FollowedFriendsPresenter createPresenter() {
        return new FollowedFriendsPresenter();
    }

    @Override
    public Integer getData() {
        return new SessionUtil(getContext()).getUserId();
    }

    @Override
    public void bindData(ArrayList<Friend> data) {
        bd(data);

    }

    /**
     * @return 提供Adapter
     */
    @Override
    protected FollowedFriendAdapter provideAdapter() {
        return new FollowedFriendAdapter(getContext(), presenter);
    }

    /**
     * @return 提供LayoutManager
     */
    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    /**
     * @return 提供标题
     */
    @Override
    protected String provideTitle() {
        return "新关注好友";
    }

    /**
     * @param view
     * @param pos
     * @param item
     */
    @Override
    public void onItemClick(View view, int pos, Friend item) {
        startActivity(FriendProfileActivity.class, new Bun().putInt("id", item.UserId).ok());

    }

    @Override
    public void updatePosItem(int pos, boolean b) {
        adapter.data.get(pos).IsFriend = b;
        adapter.notifyItemChanged(pos);
    }
}
