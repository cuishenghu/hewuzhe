package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hewuzhe.R;
import com.hewuzhe.model.Friend;
import com.hewuzhe.model.Friends;
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
import com.socks.library.KLog;

import org.litepal.crud.DataSupport;

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
    @Bind(R.id.edt_search_content)
    EditText _EdtSearchContent;
    @Bind(R.id.tv_group)
    TextView _TvGroup;
    private RecyclerIndexFriendsAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<Friend> _Friends = new ArrayList<>();
    private ArrayList<Friend> _NewFriends = new ArrayList<>();
    private boolean isFirstRun = true;

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
        _RecyclerView.setAdapter(mAdapter);


        ArrayList<Friends> friends = (ArrayList<Friends>) DataSupport.findAll(Friends.class);

        if (friends != null && friends.size() > 0) {
            _RecyclerView.setAdapter(mAdapter);
            ArrayList<Friend> friends3 = new ArrayList<>();
            for (Friends friends1 : friends) {
                Friend friend = new Friend();
                friend.Id = friends1.friendid;
                friend.topc = friends1.topc;
                friend.IsFriend = friends1.isfriend;
                friend.IsShield = friends1.isshield;
                friend.JoinTime = friends1.jointime;
                friend.NicName = friends1.nicname;
                friend.Phone = friends1.phone;
                friend.PhotoPath = friends1.photopath;
                friend.MemnerId = friends1.memnerid;
                friend.UserId = friends1.userid;
                friend.RemarkName = friends1.remarkname;
                friend.TeamId = friends1.teamid;
                friend.Rank = friends1.rank;
                friends3.add(friend);
            }

            mAdapter.setDatas(friends3);

            presenter.getFriends();

        } else {
            showDialog();
            presenter.getFriends();
        }

        if (new SessionUtil(getContext()).getUser().TeamId == 0) {
            _LayGroup.setVisibility(View.GONE);
            _TvGroup.setVisibility(View.GONE);

        } else {
            _LayGroup.setVisibility(View.VISIBLE);
            _TvGroup.setVisibility(View.VISIBLE);

            presenter.getGroup();
        }
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

        _EdtSearchContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (_EdtSearchContent.getText().toString().trim().length() == 0) {
                    if (_Friends.size() > 0) {
                        mAdapter.setDatas(_Friends);
                        _RecyclerView.setAdapter(mAdapter);
                    }

                }


            }
        });

    }

    private void search(String keyWord) {
        hideSoftMethod(_EdtSearchContent);

        for (Friend friend : _Friends) {

            if (friend.NicName.contains(keyWord)) {
                _NewFriends.add(friend);
            }
        }

        _RecyclerView.setAdapter(mAdapter);
        mAdapter.setDatas(_NewFriends);
        if (_NewFriends.size() > 0) {
            _TvRecyclerindexviewTopc.setText(mAdapter.getItem(0).topc);
        }
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
        startActivity(FriendProfileActivity.class, new Bun().putInt("id", friend.Id).ok());

    }

    @Override
    public boolean canAction() {
        return false;
    }

    @Override
    protected void action() {
        super.action();
        startActivity(MakeWarriorsActivity.class);

    }

    @Override
    public void bindData(ArrayList<Friend> friends) {
        _Friends = friends;
        if (_Friends.size() > 0) {
            DataSupport.deleteAll(Friends.class);

            for (Friend friend : friends) {
                Friends friends1 = new Friends();
                friends1.friendid = friend.Id;
                friends1.topc = friend.topc;
                friends1.isfriend = friend.IsFriend;
                friends1.isshield = friend.IsShield;
                friends1.jointime = friend.JoinTime;
                friends1.nicname = friend.NicName;
                friends1.phone = friend.Phone;
                friends1.photopath = friend.PhotoPath;
                friends1.memnerid = friend.MemnerId;
                friends1.userid = friend.UserId;
                friends1.remarkname = friend.RemarkName;
                friends1.teamid = friend.TeamId;
                friends1.rank = friend.Rank;
                boolean isSuccess = friends1.save();

                KLog.d("isSuccess:" + isSuccess);

            }
        }
        _RecyclerView.setAdapter(mAdapter);
//        mAdapter.getDatas().clear();
//        mAdapter.getDatas().addAll(_Friends);
//        mAdapter.notifyDataSetChanged();
        mAdapter.setDatas(friends);
//      _TvRecyclerindexviewTopc.setText(mAdapter.getItem(0).topc);
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

    @Override
    protected void onResume() {
        super.onResume();
        if (!isFirstRun) {
//            presenter.getFriends();
        } else {
            isFirstRun = false;
        }
    }
}
