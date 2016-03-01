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
import com.hewuzhe.ui.adapter.FriendsAdapter;
import com.hewuzhe.ui.base.RecycleViewActivity;
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


public class ContactsActivity extends RecycleViewActivity<FriendsPresenter, FriendsAdapter, Friend> implements BGAOnItemChildClickListener, FriendsView {

    @Bind(R.id.indexview)
    IndexView _Indexview;
    private LinearLayout _LayFollowed;
    private TextView _TvGroup;
    private LinearLayout _LayGroup;
    private ImageView _ImgGroupAvatar;
    private TextView _TvGroupName;
    private TextView _TvRecyclerindexviewTopc;
    private TextView _TvRecyclerindexviewTip;

    private ArrayList<Friend> _Friends = new ArrayList<>();
    private ArrayList<Friend> _NewFriends = new ArrayList<>();
    private boolean isFirstRun = true;
    private View header;
    private EditText _EdtSearchContent;

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

        initHeader();

        imgAction.setImageResource(R.mipmap.icon_add);

        _Indexview.setTipTv(_TvRecyclerindexviewTip);


        ArrayList<Friends> friends = (ArrayList<Friends>) DataSupport.findAll(Friends.class);

        if (friends != null && friends.size() > 0) {
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
            _Friends = friends3;
            adapter.addDatas(friends3);
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

    private void initHeader() {

        _LayFollowed = (LinearLayout) header.findViewById(R.id.lay_followed);
        _TvGroup = (TextView) header.findViewById(R.id.tv_group);
        _LayGroup = (LinearLayout) header.findViewById(R.id.lay_group);
        _ImgGroupAvatar = (ImageView) header.findViewById(R.id.img_group_avatar);
        _TvGroupName = (TextView) header.findViewById(R.id.tv_group_name);
        _TvRecyclerindexviewTopc = (TextView) findViewById(R.id.tv_recyclerindexview_topc);
        _TvRecyclerindexviewTip = (TextView) findViewById(R.id.tv_recyclerindexview_tip);
        _EdtSearchContent = (EditText) header.findViewById(R.id.edt_search_content);

    }

    /**
     * @return 提供Adapter
     */
    @Override
    protected FriendsAdapter provideAdapter() {
        header = getLayoutInflater().inflate(R.layout.header_contacts, null);
        return new FriendsAdapter(getContext(), presenter, header);
    }

    /**
     * @return 提供LayoutManager
     */
    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {
        _Indexview.setOnChangedListener(new IndexView.OnChangedListener() {
            @Override
            public void onChanged(String text) {
                int position = adapter.getPositionForSection(text.charAt(0));
                position++;
                if (position != -1) {
                    // position的item滑动到RecyclerView的可见区域，如果已经可见则不会滑动
                    layoutManager.scrollToPosition(position);
                }
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (adapter.getItemCount() > 1) {
                    int firstPos = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                    if (firstPos == 0) {
                        _TvRecyclerindexviewTopc.setVisibility(View.GONE);
                    } else {
                        _TvRecyclerindexviewTopc.setVisibility(View.VISIBLE);
                        _TvRecyclerindexviewTopc.setText(adapter.data.get(firstPos - 1).topc);
                    }
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


        header.findViewById(R.id.img_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyWord = _EdtSearchContent.getText().toString().trim();
                search(keyWord);
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
                        adapter.addDatas(_Friends);
                    }

                }


            }
        });

    }

    private void search(String keyWord) {
        hideSoftMethod(_EdtSearchContent);
        _NewFriends.clear();
        for (Friend friend : _Friends) {
            if (friend.NicName.contains(keyWord)) {
                _NewFriends.add(friend);
            }
        }
        recyclerView.setAdapter(adapter);
        adapter.addDatas(_NewFriends);
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
        Friend friend = adapter.data.get(i);
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
        adapter.addDatas(friends);
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
            presenter.getFriends();
        } else {
            isFirstRun = false;
        }
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
    protected void onPause() {
        super.onPause();
        presenter.stopTask();
    }
}
