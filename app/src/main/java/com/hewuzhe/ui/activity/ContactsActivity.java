package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hewuzhe.R;
import com.hewuzhe.model.Friend;
import com.hewuzhe.model.Group;
import com.hewuzhe.presenter.FriendsPresenter;
import com.hewuzhe.ui.adapter.RecyclerIndexFriendsAdapter;
import com.hewuzhe.ui.base.ToolBarActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widget.GlideCircleTransform;
import com.hewuzhe.ui.widget.IndexView;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.FriendsView;

import java.util.ArrayList;

import butterknife.Bind;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import io.rong.imkit.RongIM;


public class ContactsActivity extends ToolBarActivity<FriendsPresenter> implements BGAOnItemChildClickListener, FriendsView {

    @Bind(R.id.tv_recyclerindexview_topc)
    TextView _TvRecyclerindexviewTopc;
    @Bind(R.id.tv_recyclerindexview_tip)
    TextView _TvRecyclerindexviewTip;
    @Bind(R.id.indexview)
    IndexView _Indexview;
    @Bind(R.id.recycler_view)
    RecyclerView _RecyclerView;
    @Bind(R.id.img_group_avatar)
    ImageView _ImgGroupAvatar;
    @Bind(R.id.tv_group_name)
    TextView _TvGroupName;
    @Bind(R.id.lay_group)
    LinearLayout _LayGroup;
    @Bind(R.id.lay_followed)
    LinearLayout _LayFollowed;
    private RecyclerIndexFriendsAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    /**
     * @return 提供标题
     */
    @Override
    protected String provideTitle() {
        return "我的武友";
    }

    /**
     * @return 提供LayoutId
     */
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_contacts;
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);

        tvTitle.requestFocus();

        imgAction.setImageResource(R.mipmap.icon_add);

        _Indexview.setTipTv(_TvRecyclerindexviewTip);
        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        _RecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerIndexFriendsAdapter(_RecyclerView);
        presenter.getFriends();

        presenter.getGroup();

    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {
        mAdapter.setOnItemChildClickListener(this);
        _Indexview.setOnChangedListener(new IndexView.OnChangedListener() {
            @Override
            public void onChanged(String text) {
                int position = mAdapter.getPositionForSection(text.charAt(0));
                if (position != -1) {
                    // position的item滑动到RecyclerView的可见区域，如果已经可见则不会滑动
                    mLayoutManager.scrollToPosition(position);
                }
            }
        });

        _RecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (mAdapter.getItemCount() > 0) {
                    _TvRecyclerindexviewTopc.setText(mAdapter.getItem(mLayoutManager.findFirstVisibleItemPosition()).topc);
                }
            }
        });

        _LayFollowed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(FollowedFriendsActivity.class);
            }
        });
    }

    /**
     * 绑定Presenter
     */
    @Override
    public FriendsPresenter createPresenter() {
        return new FriendsPresenter();
    }


    @Override
    public void onItemChildClick(ViewGroup viewGroup, View view, int i) {
        Friend friend = mAdapter.getItem(i);
//        RongIM.getInstance().startPrivateChat(getContext(), friend.Id + "", friend.NicName);

        startActivity(FriendProfileActivity.class, new Bun().putInt("id", friend.Id).ok());
    }

    @Override
    public boolean canAction() {
        return true;
    }

    @Override
    protected void action() {
        super.action();
        startActivity(MakeWarriorsActivity.class);


    }

    @Override
    public void bindData(ArrayList<Friend> friends) {
        _RecyclerView.setAdapter(mAdapter);
        mAdapter.setDatas(friends);
        _TvRecyclerindexviewTopc.setText(mAdapter.getItem(0).topc);
    }

    @Override
    public void setGroupData(final Group data) {
        _TvGroupName.setText(data.Name);
        Glide.with(getContext())
                .load(C.BASE_URL + data.ImagePath)
                .crossFade()
                .centerCrop()
                .transform(new GlideCircleTransform(getContext()))
                .into(_ImgGroupAvatar);

        _LayGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RongIM.getInstance().startGroupChat(getContext(), data.Id + "", data.Name);

            }
        });


    }


    @Override
    public Integer getData() {
        return new SessionUtil(getContext()).getUserId();
    }
}
