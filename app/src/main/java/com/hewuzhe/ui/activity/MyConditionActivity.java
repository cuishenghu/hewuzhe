package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hewuzhe.R;
import com.hewuzhe.model.Comment;
import com.hewuzhe.model.FriendCondition;
import com.hewuzhe.model.Res;
import com.hewuzhe.model.User;
import com.hewuzhe.presenter.MyConditionPresenter;
import com.hewuzhe.presenter.adapter.ConditionPresenter;
import com.hewuzhe.ui.adapter.FriendConditionAdapter;
import com.hewuzhe.ui.base.SwipeRecycleViewActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.FriendsConditionView;

import java.util.ArrayList;

import butterknife.Bind;

public class MyConditionActivity extends SwipeRecycleViewActivity<MyConditionPresenter, FriendConditionAdapter, FriendCondition> implements FriendsConditionView {


    @Bind(R.id.edt_comment)
    EditText _EdtComment;
    @Bind(R.id.btn_publish)
    Button _BtnPublish;
    @Bind(R.id.lay_comment)
    LinearLayout _LayComment;
    private User user;
    private View header;
    private ImageView _ImgBg;
    private TextView _TvUsername;
    private ImageView _ImgAvatar;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_my_condition;
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {

    }

    /**
     * 绑定Presenter
     */
    @Override
    public MyConditionPresenter createPresenter() {
        return new MyConditionPresenter();
    }


    @Override
    protected String provideTitle() {
        return getIntentData().getString("title");
    }


    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        initHeader();
        presenter.getUserInfo();
        presenter.getData(page, count);

        if (getData() == new SessionUtil(getContext()).getUserId()) {
            tvAction.setVisibility(View.VISIBLE);
            tvAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(CondtionCommetnsActivity.class);
                }
            });
        } else {
            tvAction.setVisibility(View.GONE);

        }

    }


    @Override
    public void bindData(ArrayList<FriendCondition> data) {
        bd(data);
    }


    private void initHeader() {
        _ImgBg = (ImageView) header.findViewById(R.id.img_bg);
        _TvUsername = (TextView) header.findViewById(R.id.tv_username);
        _ImgAvatar = (ImageView) header.findViewById(R.id.img_avatar);

    }


    /**
     * @return 提供Adapter
     */
    @Override
    protected FriendConditionAdapter provideAdapter() {
        header = getLayoutInflater().inflate(R.layout.header_friend_condtions, null);
        return new FriendConditionAdapter(getContext(), presenter, header);
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

        startActivity(ConditionDetialTwoActivity.class, new Bun().putInt("id", item.Id).ok());

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
                adapter.data.get(position).LikeNum += 1;
                adapter.data.get(position).IsLike = true;
                adapter.notifyItemChanged(position);
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
        comment.CommentedId = condition.UserId;
        comment.CommenterId = new SessionUtil(getContext()).getUserId();

        comment.NicName = user.NicName;
        condition.ComList.add(comment);
//        adapter.notifyItemChanged(position);
        adapter.notifyDataSetChanged();

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
//        adapter.notifyItemChanged(position);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void deleteConditionSuccess(int position) {
        adapter.data.remove(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onscroll(RecyclerView recyclerView, int dx, int dy) {
        super.onscroll(recyclerView, dx, dy);
        _LayComment.setVisibility(View.GONE);
        hideSoftMethod(_EdtComment);
    }

    @Override
    public Integer getData() {
        return getIntentData().getInt("id");
    }

    @Override
    public void setUserData(User data) {

        if (data != null) {
            Glide.with(getContext())
                    .load(C.BASE_URL + data.PhotoPath)
                    .centerCrop()
                    .crossFade()
                    .into(_ImgAvatar);
            _TvUsername.setText(data.NicName);
        }
    }


    @Override
    public void setDataStatus(int page, int count, Res<ArrayList<FriendCondition>> res) {
        if (page * count >= res.recordcount) {
            //没有更多了
            adapter.noMore("还没留下任何动态");
        } else {
            //还有更多
            adapter.hasMore();
        }
    }

    @Override
    public void updateFriendNoReadNum(int count, String data) {

    }
}
