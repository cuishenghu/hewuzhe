package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hewuzhe.R;
import com.hewuzhe.model.Pic;
import com.hewuzhe.model.User;
import com.hewuzhe.presenter.FriendProfilePresenter;
import com.hewuzhe.ui.base.ToolBarActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widget.GlideCircleTransform;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.utils.TimeUtil;
import com.hewuzhe.view.FriendProfileView;

import java.util.ArrayList;

import butterknife.Bind;
import de.greenrobot.event.EventBus;
import io.rong.imkit.RongIM;

public class FriendProfileActivity extends ToolBarActivity<FriendProfilePresenter> implements FriendProfileView {


    @Bind(R.id.img)
    ImageView _Img;
    @Bind(R.id.tv_line)
    TextView _TvLine;
    @Bind(R.id.tv_username)
    TextView _TvUsername;
    @Bind(R.id.tv_info)
    TextView _TvInfo;
    @Bind(R.id.tv_weight)
    TextView _TvWeight;
    @Bind(R.id.tv_height)
    TextView _TvHeight;
    @Bind(R.id.tv_time)
    TextView _TvTime;
    @Bind(R.id.tv_desc)
    TextView _TvDesc;
    @Bind(R.id.btn)
    Button _Btn;
    @Bind(R.id.img_avatar)
    ImageView _ImgAvatar;
    @Bind(R.id.lay_friends_record)
    LinearLayout lay_friends_record;//他的记录
    @Bind(R.id.lay_friends_condition)
    LinearLayout _LayFriendsCondition;//他的动态
    @Bind(R.id.lay_friends_team)
    LinearLayout _LayFriendsTeam;//他的战队
    private TextView _TvItemOne;
    private TextView _TvItemTwo;
    private TextView _TvItemThree;

    private int id;
    /**
     * 是否是好友
     */
    private boolean isWy;
    private User _Friend;
    private boolean isFirstRun = true;

    /**
     * @return 提供标题
     */
    @Override
    protected String provideTitle() {
        return "好友资料";
    }

    /**
     * @return 提供LayoutId
     */
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_friend_profile;
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        id = getIntentData().getInt("id");
        EventBus.getDefault().register(this);
        imgAction.setImageResource(R.mipmap.icon_more);
        presenter.getUserData();

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
    public FriendProfilePresenter createPresenter() {
        return new FriendProfilePresenter();
    }

    @Override
    public Integer getData() {
        return id;
    }

    @Override
    public void setData(final User friend) {
        _Friend = friend;
        _TvUsername.setText(friend.NicName + "");
        _TvDesc.setText(friend.Description);
        _TvHeight.setText(friend.Height + "cm");
        _TvWeight.setText(friend.Weight + "kg");
        _TvTime.setText(friend.Experience + "年");

        _TvInfo.setText(StringUtil.getGender(friend.Sexuality) + " " + TimeUtil.timeHaved(friend.Birthday) + "岁 " + "   " + friend.HomeAreaprovinceName + " " + friend.HomeAreaCityName + " " + friend.HomeAreaCountyName);

        /**
         * 他的记录
         */
        lay_friends_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(RecordActivity.class, new Bun().putInt("id", friend.Id).ok());
            }
        });

        /**
         * 他的动态
         */
        _LayFriendsCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MyConditionActivity.class, new Bun().putInt("id", friend.Id).putString("title", "个人动态").ok());
            }
        });

        /**
         * 他的战队
         */
        if (friend.TeamId == 0) {
            _TvLine.setVisibility(View.GONE);
            _LayFriendsTeam.setVisibility(View.GONE);
        } else {
            _LayFriendsTeam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(GroupConditionActivity.class, new Bun().putInt("teamid", friend.TeamId).ok());
                }
            });
        }
        Glide.with(getContext())
                .load(C.BASE_URL + friend.PhotoPath)
                .centerCrop()
                .crossFade()
                .transform(new GlideCircleTransform(getContext()))
                .into(_ImgAvatar);


        _ImgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pic pic = new Pic();
                pic.PictureUrl = friend.PhotoPath;
                ArrayList<Pic> pics = new ArrayList();
                pics.add(pic);
                startActivity(PicsActivity.class, new Bun().putString("pics", new Gson().toJson(pics)).putBoolean("isHidePage", true).ok());
            }
        });


        _Btn.setText("发送消息");
        _Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RongIM.getInstance().startPrivateChat(getContext(), _Friend.Id + "", _Friend.NicName);
            }
        });

    }

    @Override
    public boolean canAction() {
        return true;
    }

    @Override
    protected void action() {
        super.action();

        startActivity(FriendProfileSettingsActivity.class, new Bun().putString("title", "聊天设置").putInt("id", _Friend.Id).ok());
    }

    @Override
    public void followSuccess() {
        _Btn.setText("已关注");
    }

    public void onEvent(Integer msg) {
        if (msg == C.MSG_CLOSE_FRIEND_PROFILE) {
            finishActivity();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isFirstRun) {
            presenter.getUserData();
        } else {
            isFirstRun = false;
        }
    }
}
