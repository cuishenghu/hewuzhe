package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
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
    @Bind(R.id.img_search)
    ImageView _ImgSearch;

    private int provinceId;
    private int cityId;
    private int countyId;
    private int age = 0;
    private int gender = 0;
    private AddressAdapter cityDialogAdapter;
    private AddressAdapter disctrictAdapter;
    private int mAreaId = 0;
    private ArrayList<Friend> data = new ArrayList<>();
    private ArrayList<Friend> _NewFriends = new ArrayList<>();

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
                    String keyWord = _EdtSearchContent.getText().toString().trim();
                    search(keyWord);
                }
                return false;
            }
        });

        _ImgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyWord = _EdtSearchContent.getText().toString().trim();
                search(keyWord);
            }
        });


    }


    private void search(String keyWord) {

        hideSoftMethod(_EdtSearchContent);

        for (Friend friend : data) {
            if (friend.NicName.contains(keyWord)) {
                _NewFriends.add(friend);
            }

            String mememberId = friend.Phone + "";

            if (mememberId.contains(keyWord)) {
                _NewFriends.add(friend);
            }

        }

        adapter.addDatas(_NewFriends);

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
        return "战队成员";
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
        this.data = data;
        bd(data);
    }

    /**
     * @param view
     * @param pos
     * @param item
     */
    @Override
    public void onItemClick(View view, int pos, Friend item) {
        presenter.isWuyou(item.MemnerId);
    }

    @Override
    public Integer getData() {
        return getIntentData().getInt("id");
    }

    @Override
    public void isWuYou(Boolean data, int userid) {
        if (data) {
            startActivity(FriendProfileActivity.class, new Bun().putInt("id", userid).ok());
        } else {
            startActivity(StrangerProfileSettingsActivity.class, new Bun().putInt("id", userid).ok());
        }
    }
}
