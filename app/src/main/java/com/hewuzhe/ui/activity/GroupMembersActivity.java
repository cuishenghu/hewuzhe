package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.model.Friend;
import com.hewuzhe.presenter.GroupMembersPresenter;
import com.hewuzhe.ui.adapter.AddressAdapter;
import com.hewuzhe.ui.adapter.GroupMembersAdapter;
import com.hewuzhe.ui.base.RecycleViewActivity;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.view.GroupMembersView;

import java.util.ArrayList;

import butterknife.Bind;

public class GroupMembersActivity extends RecycleViewActivity<GroupMembersPresenter, GroupMembersAdapter, Friend> implements GroupMembersView {

    @Bind(R.id.edt_search_content)
    EditText _EdtSearchContent;

    private int provinceId;
    private int cityId;
    private int countyId;
    private int age = 0;
    private int gender = 0;
    private AddressAdapter cityDialogAdapter;
    private AddressAdapter disctrictAdapter;
    private int mAreaId = 0;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_group_members;
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        presenter.getData(page, count);
    }

    /**
     * @return 提供Adapter
     */
    @Override
    protected GroupMembersAdapter provideAdapter() {
        return new GroupMembersAdapter(getContext());
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {

        _EdtSearchContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    showDialog();
                    presenter.getData(page, count);
                }
                return false;
            }
        });

    }

    /**
     * 绑定Presenter
     */
    @Override
    public GroupMembersPresenter createPresenter() {
        return new GroupMembersPresenter();
    }


    @Override
    protected String provideTitle() {
        return "结交武者";
    }


    /**
     * @return 提供LayoutManager
     */
    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    public void bindData(ArrayList<Friend> data) {
        bd(data);
    }

    /**
     * @param view
     * @param pos
     * @param item
     */
    @Override
    public void onItemClick(View view, int pos, Friend item) {
        startActivity(FriendProfileActivity.class, new Bun().putInt("id", item.Id).ok());
    }

    @Override
    public Integer getData() {
        return getIntentData().getInt("id");
    }
}
