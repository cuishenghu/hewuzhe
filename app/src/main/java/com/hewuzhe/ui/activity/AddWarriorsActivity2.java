package com.hewuzhe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hewuzhe.R;
import com.hewuzhe.model.Friend;
import com.hewuzhe.presenter.AddWarriorsPresenter;
import com.hewuzhe.ui.adapter.AddWarriorsAdapter;
import com.hewuzhe.ui.base.RecycleViewActivity;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.AddWarriorsView;

import java.util.ArrayList;

public class AddWarriorsActivity2 extends RecycleViewActivity<AddWarriorsPresenter, AddWarriorsAdapter, Friend> implements AddWarriorsView {
    private LinearLayout lay_tongxunlu;
    private LinearLayout lay_select_condition;
    private LinearLayout lay_select_near;
    private EditText _EdtSearchContent;
    private ImageView imgSearch;
    private Friend _item;
    private View headView;
    private boolean isFirstRun = true;

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);

        initHeader();

        presenter.getData(page, count);
    }

    private void initHeader() {
        _EdtSearchContent = (EditText) headView.findViewById(R.id.edt_search_content);
//        imgSearch = (ImageView) headView.findViewById(R.id.img_search);
        lay_tongxunlu = (LinearLayout) headView.findViewById(R.id.lay_tongxunlu);
        lay_select_condition = (LinearLayout) headView.findViewById(R.id.ll_select_by_condition);
        lay_select_near = (LinearLayout) headView.findViewById(R.id.lay_select_near_person);
    }

    @Override
    public void initListeners() {
        /**
         * 搜索
         */
        headView.findViewById(R.id.img_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
                hideSoftMethod(_EdtSearchContent);
                page = 1;
                presenter.getData(page, count);
            }
        });
        /**
         * 添加手机通讯录
         */
        lay_tongxunlu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddWarriorsActivity2.this, TongXunLuActivity.class));
            }
        });
        /**
         * 按条件查询陌生人
         */
        lay_select_condition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddWarriorsActivity2.this, MakeWarriorsActivity.class));
            }
        });
        /**
         * 查看附近人
         */
        lay_select_near.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddWarriorsActivity2.this, NearPeopleActivity.class));
            }
        });
    }

    @Override
    public Friend getData() {
        Friend friend = new Friend();
        friend.nicName = _EdtSearchContent.getText().toString().trim();
        return friend;
    }

    @Override
    public void bindData(ArrayList<Friend> data) {
        bd(data);
    }

    @Override
    protected AddWarriorsAdapter provideAdapter() {
        headView = getLayoutInflater().inflate(R.layout.header_add_warriors, null);
        return new AddWarriorsAdapter(getContext(), presenter, headView);
    }

    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    protected CharSequence provideTitle() {
        return "添加武友";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_add_warriors;
    }


    @Override
    public AddWarriorsPresenter createPresenter() {
        return new AddWarriorsPresenter();
    }

    @Override
    public void onItemClick(View view, int pos, Friend item) {
        _item = item;
        presenter.isWuyou(item.Id);
    }

    @Override
    public void updatePosItem(int pos, boolean IsFriend) {
        adapter.data.get(pos).IsFriend = IsFriend;
        adapter.notifyItemChanged(pos);
    }

    @Override
    public void isWuYou(Boolean data, int userid) {
        if (data) {
            startActivity(FriendProfileActivity.class, new Bun().putInt("id", _item.Id).ok());
        } else {
            startActivity(StrangerProfileSettingsActivity.class, new Bun().putInt("id", _item.Id).ok());
        }
    }
}

