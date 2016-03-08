package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.hewuzhe.R;
import com.hewuzhe.model.Comment;
import com.hewuzhe.model.FriendCondition;
import com.hewuzhe.model.Group;
import com.hewuzhe.model.User;
import com.hewuzhe.presenter.GroupConditionPresenter;
import com.hewuzhe.presenter.adapter.ConditionPresenter;
import com.hewuzhe.ui.adapter.GroupConditionAdapter;
import com.hewuzhe.ui.base.SwipeRecycleViewActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widget.GlideCircleTransform;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.SPUtil;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.view.GroupConditionView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.xm.weidongjian.popuphelper.PopupWindowHelper;
import de.greenrobot.event.EventBus;
import io.rong.imkit.RongIM;

public class GroupConditionActivity extends SwipeRecycleViewActivity<GroupConditionPresenter, GroupConditionAdapter, FriendCondition> implements AppBarLayout.OnOffsetChangedListener, GroupConditionView {

    private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 30;
    @Bind(R.id.edt_comment)
    EditText _EdtComment;
    @Bind(R.id.btn_publish)
    Button _BtnPublish;
    @Bind(R.id.lay_comment)
    LinearLayout _LayComment;


    private int mMaxScrollSize;
    private boolean mIsAvatarShown = true;
    private TextView action_1;
    private TextView action_2;
    private User user;
    private int teamId = -1;

    private String _NickName = "";
    private int myTeamId;
    private String _PhotoPath = "";
    private SPUtil spData;
    private View header;


    private ImageView imgAvatar;
    private TextView tvName;
    private TextView tvAddress;
    private LinearLayout _LayMembers;
    private LinearLayout _LayDesc;
    private LinearLayout _LayAnounce;
    private LinearLayout _LayChat;
    private Button _BtnJoin;
    private TextView _TvTip;


    @Override
    protected int provideContentViewId() {
        return R.layout.activity_group_condition;
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {
        _LayMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(GroupMembersActivity.class, new Bun().putInt("id", getData()).ok());
            }
        });
        _LayDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(TeamIntroduceActivity.class, new Bun().putInt("id", getData()).ok());
            }
        });
        _LayAnounce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(TeamAnnounceActivity.class, new Bun().putInt("id", getData()).ok());
            }
        });
    }


    @Override
    protected String provideTitle() {
        return "圈子";
    }


    @Override
    public boolean canAction() {
        return false;
    }

    @Override
    protected void action() {
        super.action();

        View view = LayoutInflater.from(this).inflate(R.layout.popview, null);

        action_1 = (TextView) view.findViewById(R.id.tv_local_video);
        action_2 = (TextView) view.findViewById(R.id.tv_take_video);
        action_1.setText("发动态");
        action_2.setText("小视频");

        PopupWindowHelper popupWindowHelper = new PopupWindowHelper(view);
        popupWindowHelper.showAsDropDown(imgAction);

    }

    /**
     * Called when the {@link AppBarLayout}'s layout offset has been changed. This allows
     * child views to implement custom behavior based on the offset (for instance pinning a
     * view at a certain y value).
     *
     * @param appBarLayout the {@link AppBarLayout} which offset has changed
     * @param i            the vertical offset for the parent {@link AppBarLayout}, in px
     */
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {

        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();

        int percentage = (Math.abs(i)) * 100 / mMaxScrollSize;

        if (percentage >= PERCENTAGE_TO_ANIMATE_AVATAR && mIsAvatarShown) {
            mIsAvatarShown = false;
            imgAvatar.animate().scaleY(0).scaleX(0).setDuration(200).start();
            tvAddress.animate().scaleY(0).scaleX(0).setDuration(200).start();
            tvName.animate().scaleY(0).scaleX(0).setDuration(200).start();
        }

        if (percentage <= PERCENTAGE_TO_ANIMATE_AVATAR && !mIsAvatarShown) {
            mIsAvatarShown = true;

            imgAvatar.animate()
                    .scaleY(1).scaleX(1)
                    .start();
            tvAddress.animate()
                    .scaleY(1).scaleX(1)
                    .start();
            tvName.animate()
                    .scaleY(1).scaleX(1)
                    .start();
        }

    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        EventBus.getDefault().register(this);
        initHeader();
        spData = new SPUtil(getContext()).open("data");
        String friendcondition = spData.getString("groupcondition");
        if (!StringUtil.isEmpty(friendcondition)) {
            ArrayList<FriendCondition> friendConditions = new Gson().fromJson(friendcondition, new TypeToken<List<FriendCondition>>() {
            }.getType());
            bindData(friendConditions);
        }
        presenter.getBasicInfo();

    }

    private void initHeader() {
        imgAvatar = (ImageView) header.findViewById(R.id.img_avatar);
        tvName = (TextView) header.findViewById(R.id.tv_name);
        tvAddress = (TextView) header.findViewById(R.id.tv_address);
        _LayMembers = (LinearLayout) header.findViewById(R.id.lay_members);
        _LayDesc = (LinearLayout) header.findViewById(R.id.lay_desc);
        _LayAnounce = (LinearLayout) header.findViewById(R.id.lay_anounce);
        _LayChat = (LinearLayout) header.findViewById(R.id.lay_chat);
        _BtnJoin = (Button) header.findViewById(R.id.btn_join);
        _TvTip = (TextView) header.findViewById(R.id.tv_tip);


    }

    /**
     * 绑定Presenter
     */
    @Override
    public GroupConditionPresenter createPresenter() {
        return new GroupConditionPresenter();
    }

    @Override
    public void bindData(ArrayList<FriendCondition> data) {

        if (page == 1) {
            String json = new Gson().toJson(data);
            spData.putString("groupcondition", json);
        }

        bd(data);
    }


    /**
     * @return 提供Adapter
     */
    @Override
    protected GroupConditionAdapter provideAdapter() {
        header = getLayoutInflater().inflate(R.layout.header_group_condition, null);
        return new GroupConditionAdapter(getContext(), presenter, header);

    }

    /**
     * @return 提供LayoutManager
     */
    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    /**
     * @param view
     * @param pos
     * @param item
     */
    @Override
    public void onItemClick(View view, int pos, FriendCondition item) {
        startActivity(ConditionDetialTwoActivity.class, new Bun().putInt("id", item.Id).putString("_NickName", _NickName).putString("_PhotoPath", _PhotoPath).putInt("whitch", C.WHITCH_TWO).ok());
//        startActivity(ConditionDetialActivity.class, new Bun().putP("item", item).putString("_NickName", _NickName).putString("_PhotoPath", _PhotoPath).putInt("whitch", C.WHITCH_TWO).ok());
    }

    /**
     * 点赞收藏等
     *
     * @param b
     * @param flag
     * @param position
     */
    @Override
    public void collectAndOther(boolean b, int flag, int position) {
        if (flag == ConditionPresenter.PRAISE) {
            if (b) {
                toast("点赞成功");
                adapter.data.get(position).LikeNum += 1;
                adapter.data.get(position).IsLike = true;
                adapter.notifyDataSetChanged();
            } else {
                toast("取消点赞");
                adapter.data.get(position).LikeNum -= 1;
                adapter.data.get(position).IsLike = false;
                adapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 显示评论框
     *
     * @param id
     * @param position
     * @param view
     */
    @Override
    public void showCommentInput(final int id, final int position, View view) {
        _LayComment.setVisibility(View.VISIBLE);
        _EdtComment.requestFocus();
        _EdtComment.setHint("");
        showSoftInput(_EdtComment);
        _BtnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.publisComment(id, _EdtComment.getText().toString().trim(), view, position);
            }
        });
    }

    /**
     * 评论成功
     *
     * @param position
     */
    @Override
    public void commentSuccess(int position, Comment comment) {
        _EdtComment.setText("");
        user = new SessionUtil(getContext()).getUser();
        FriendCondition condition = adapter.data.get(position);
        comment.CommentedNicName = condition.NicName;
        comment.CommenterId = new SessionUtil(getContext()).getUserId();
        comment.CommentedId = condition.UserId;
        comment.NicName = user.NicName;
        condition.ComList.add(comment);
        adapter.notifyItemChanged(position);
    }

    /**
     * 回复评论
     *
     * @param id
     * @param nicName
     * @param commenterId
     * @param position
     * @param view
     */
    @Override
    public void showReplyInput(final int id, final String nicName, final int commenterId, final int position, View view) {
        _LayComment.setVisibility(View.VISIBLE);
        _EdtComment.requestFocus();
        showSoftInput(_EdtComment);
        _EdtComment.setHint("回复 " + nicName + ":");
        _BtnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.publisReply(id, nicName, commenterId, _EdtComment.getText().toString().trim(), view, position);
            }
        });
    }

    /**
     * 回复成功
     *
     * @param position
     * @param comment
     */
    @Override
    public void replySuccess(int position, Comment comment) {
        _EdtComment.setText("");
        user = new SessionUtil(getContext()).getUser();
        FriendCondition condition = adapter.data.get(position);
        comment.NicName = user.NicName;
        comment.CommenterId = new SessionUtil(getContext()).getUserId();

        condition.ComList.add(comment);
        adapter.notifyItemChanged(position);
    }

    @Override
    public void deleteConditionSuccess(int position) {

    }

    @Override
    public void onscroll(RecyclerView recyclerView, int dx, int dy) {
        super.onscroll(recyclerView, dx, dy);
        _LayComment.setVisibility(View.GONE);
        hideSoftMethod(_EdtComment);
    }

    @Override
    public Integer getData() {
        return getIntentData().getInt("teamid");
    }

    @Override
    public void setGroupData(final Group group) {
        myTeamId = new SessionUtil(getContext()).getUser().TeamId;

        if (getData() != myTeamId) {
            _BtnJoin.setVisibility(View.VISIBLE);
            _TvTip.setVisibility(View.VISIBLE);

            _BtnJoin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.joinTeam(group.Id, group.Name);
                }
            });

            _LayChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snb("您还不是该圈子的成员", _LayChat);
                }
            });

        } else {
            _LayChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RongIM.getInstance().startGroupChat(getContext(), getData() + "", _NickName);
                }
            });

            showAction(true);

        }


        presenter.getData(page, count);
        tvName.setText(group.Name);
        tvAddress.setText(group.Address);

        adapter._NickName = group.Name;
        _NickName = group.Name;
        adapter._PhotoPath = group.ImagePath;
        _PhotoPath = group.ImagePath;

        Glide.with(getContext())
                .load(C.BASE_URL + group.ImagePath)
                .centerCrop()
                .crossFade()
                .transform(new GlideCircleTransform(getContext()))
                .placeholder(R.mipmap.img_avatar)
                .into(imgAvatar);
    }

    private void showAction(boolean b) {
        if (b) {
            imgAction.setVisibility(View.VISIBLE);
            imgAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    startActivity(TeamManagerActivity.class, new Bun().putInt("id", getData()).ok());
                }
            });
        } else {
            imgAction.setVisibility(View.GONE);
        }
    }

    @Override
    public void updateItem(boolean b) {
        if (b) {
            _BtnJoin.setVisibility(View.GONE);
            _TvTip.setVisibility(View.GONE);

            _LayChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RongIM.getInstance().startGroupChat(getContext(), getData() + "", _NickName);
                }
            });

            showAction(true);
        }
    }

    public void onEvent(Integer msg) {
        if (msg == C.MSG_CLOSE_GROUP_CONDITION) {
            finishActivity();
        }
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
