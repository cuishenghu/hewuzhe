package com.hewuzhe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    private Friend _Item;
    private boolean isFirstRun = true;
    private LinearLayout lay_search, lay_tongxunlu;
    private EditText edt_search_content;
    private ImageView img_search;

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
        /**
         * 添加武友
         */
        tvAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FollowedFriendsActivity.this, MakeWarriorsActivity.class));
            }
        });

        /**
         * 通讯录
         */
        lay_tongxunlu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FollowedFriendsActivity.this, TongXunLuActivity.class));
            }
        });

        edt_search_content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    showDialog();
                    hideSoftMethod(edt_search_content);
                    page = 1;
                    presenter.getData(page, count);
                }
                return false;
            }
        });
        /**
         * 搜索
         */
        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
                hideSoftMethod(edt_search_content);
                page = 1;
                presenter.getData(page, count);
            }
        });
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        tvAction.setText("添加武友");
        initHeader();
        presenter.getData(page, count);
        /**
         * 判断ID是当前用户还是好友的ID,当前用户编辑显示,好友隐藏
         */
        if (getData() == new SessionUtil(FollowedFriendsActivity.this).getUser().Id) {
            tvAction.setVisibility(View.VISIBLE);
        } else {
            tvAction.setVisibility(View.GONE);
        }
    }

    private void initHeader() {
        lay_search = (LinearLayout) findViewById(R.id.lay_search);
        lay_tongxunlu = (LinearLayout) findViewById(R.id.lay_tongxunlu);
        edt_search_content = (EditText) findViewById(R.id.edt_search_content);
        img_search = (ImageView) findViewById(R.id.img_search);
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
        _Item = item;

        if (item.IsFriend) {
            startActivity(FriendProfileActivity.class, new Bun().putInt("id", _Item.UserId).ok());
        } else {
            startActivity(StrangerProfileSettingsActivity.class, new Bun().putInt("id", _Item.UserId).ok());
        }

    }

    @Override
    public void updatePosItem(int pos, boolean b) {
        adapter.data.get(pos).IsFriend = b;
        adapter.notifyItemChanged(pos);
    }

    @Override
    public void isWuYou(Boolean data) {
        if (data) {
            startActivity(FriendProfileActivity.class, new Bun().putInt("id", _Item.UserId).ok());
        } else {
            startActivity(StrangerProfileSettingsActivity.class, new Bun().putInt("id", _Item.UserId).ok());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFirstRun) {
            isFirstRun = false;
        } else {
            page = 1;
            presenter.getData(page, count);
        }
    }
}
