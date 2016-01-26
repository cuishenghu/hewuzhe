package com.hewuzhe.ui.activity;

import android.os.Bundle;
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
import com.hewuzhe.R;
import com.hewuzhe.model.Comment;
import com.hewuzhe.model.FriendCondition;
import com.hewuzhe.model.Res;
import com.hewuzhe.model.User;
import com.hewuzhe.presenter.FriendConditionPresenter;
import com.hewuzhe.presenter.adapter.ConditionPresenter;
import com.hewuzhe.ui.adapter.FriendConditionAdapter;
import com.hewuzhe.ui.base.SwipeRecycleViewActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.FriendsConditionView;
import com.qd.recorder.FFmpegRecorderActivity;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import cn.xm.weidongjian.popuphelper.PopupWindowHelper;

public class FriendsConditionActivity extends SwipeRecycleViewActivity<FriendConditionPresenter, FriendConditionAdapter, FriendCondition> implements FriendsConditionView {


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
    @Bind(R.id.img_msg_avatar)
    ImageView _ImgMsgAvatar;
    @Bind(R.id.tv_msg_count)
    TextView _TvMsgCount;
    @Bind(R.id.lay_msg)
    LinearLayout _LayMsg;
    private TextView action_1;
    private TextView action_2;
    private User user;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_friends_condition;
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
    public FriendConditionPresenter createPresenter() {
        return new FriendConditionPresenter();
    }


    @Override
    protected String provideTitle() {
        return "武者动态";
    }


    @Override
    public boolean canAction() {
        return true;
    }

    @Override
    protected void action() {
        super.action();

        View view = LayoutInflater.from(this).inflate(R.layout.popview, null);
        final PopupWindowHelper popupWindowHelper = new PopupWindowHelper(view);

        action_1 = (TextView) view.findViewById(R.id.tv_local_video);
        action_2 = (TextView) view.findViewById(R.id.tv_take_video);
        action_1.setText("发动态");
        action_2.setText("小视频");
        action_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(PublishConditionActivity.class);
                popupWindowHelper.dismiss();
            }
        });

        action_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(FFmpegRecorderActivity.class, new Bun().putInt(C.WHITCH, C.WHITCH_ONE).ok());
                popupWindowHelper.dismiss();
            }
        });

        popupWindowHelper.showAsDropDown(imgAction);
    }


    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        initBasicInfo();
        presenter.getData(page, count);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                presenter.GetNoReadCommentNumByUserId();
                KLog.d("更新未读条数");
            }
        }, 1000, 1000);

    }

    private void initBasicInfo() {
        user = new SessionUtil(getContext()).getUser();
        if (user != null) {
            Glide.with(getContext())
                    .load(C.BASE_URL + user.PhotoPath)
                    .centerCrop()
                    .crossFade()
                    .into(_ImgAvatar);
            _TvUsername.setText(user.NicName);

            _ImgAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(MyConditionActivity.class, new Bun().putString("title", "我的动态").putInt("id", new SessionUtil(getContext()).getUserId()).ok());
                }
            });
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
        startActivity(ConditionDetialActivity.class, new Bun().putInt("id", item.Id).putInt("whitch", C.WHITCH_ONE).ok());
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
        return null;
    }

    @Override
    public void setUserData(User data) {

    }

    @Override
    public void setDataStatus(int page, int count, Res<ArrayList<FriendCondition>> res) {

    }

    @Override
    public void updateFriendNoReadNum(final int count) {

        if (count > 0) {
            _LayMsg.setVisibility(View.VISIBLE);
            _TvMsgCount.setText(count + "条新消息");

            _LayMsg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    page = 1;
                    refresh(true);
                    presenter.getData(page, count);
                }
            });

        } else {
            _LayMsg.setVisibility(View.GONE);

        }
    }
}
