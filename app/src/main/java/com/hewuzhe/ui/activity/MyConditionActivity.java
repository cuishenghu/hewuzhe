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


    @Bind(R.id.img_bg)
    ImageView _ImgBg;
    @Bind(R.id.tv_username)
    TextView _TvUsername;
    @Bind(R.id.img_avatar)
    ImageView _ImgAvatar;
    @Bind(R.id.edt_comment)
    EditText _EdtComment;
    @Bind(R.id.btn_publish)
    Button _BtnPublish;
    @Bind(R.id.lay_comment)
    LinearLayout _LayComment;
    private User user;

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


    /**
     * @return 提供Adapter
     */
    @Override
    protected FriendConditionAdapter provideAdapter() {
        return new FriendConditionAdapter(getContext(), presenter);
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

        startActivity(ConditionDetialActivity.class, new Bun().putInt("id", item.Id).ok());

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
        comment.NicName = user.NicName;
        condition.ComList.add(comment);
        adapter.notifyItemChanged(position);
    }

    /**
     * 回复评论
     *
     * @param id
     * @param nicName
     * @param position
     * @param view
     */
    @Override
    public void showReplyInput(final int id, final String nicName, final int position, View view) {
        _LayComment.setVisibility(View.VISIBLE);
        _EdtComment.requestFocus();
        showSoftInput(_EdtComment);
        _EdtComment.setHint("回复 " + nicName + ":");
        _BtnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.publisReply(id, _EdtComment.getText().toString().trim(), nicName, view, position);
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
        condition.ComList.add(comment);
        adapter.notifyItemChanged(position);
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
}
