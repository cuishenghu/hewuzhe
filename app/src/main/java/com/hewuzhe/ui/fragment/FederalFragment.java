package com.hewuzhe.ui.fragment;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Zxing.CaptureActivity;
import com.hewuzhe.R;
import com.hewuzhe.presenter.FederalPresenter;
import com.hewuzhe.ui.activity.ContactsActivity;
import com.hewuzhe.ui.activity.ConversationListActivity;
import com.hewuzhe.ui.activity.CoorperationActivity;
import com.hewuzhe.ui.activity.FederalConditionActivity;
import com.hewuzhe.ui.activity.FriendsConditionActivity;
import com.hewuzhe.ui.activity.GroupConditionActivity;
import com.hewuzhe.ui.activity.JoinGroupActivity;
import com.hewuzhe.ui.activity.MainActivity;
import com.hewuzhe.ui.activity.MakeWarriorsActivity;
import com.hewuzhe.ui.activity.MegaGameActivity;
import com.hewuzhe.ui.activity.StoryActivity;
import com.hewuzhe.ui.base.BaseFragment;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.NotiMsg;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.FederalView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import de.greenrobot.event.EventBus;
import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.widget.provider.CameraInputProvider;
import io.rong.imkit.widget.provider.ImageInputProvider;
import io.rong.imkit.widget.provider.InputProvider;
import io.rong.imkit.widget.provider.LocationInputProvider;
import io.rong.imlib.model.Conversation;

/**
 * Created by xianguangjin on 15/12/8.
 */
public class FederalFragment extends BaseFragment<FederalPresenter> implements FederalView {

    protected Toolbar toolBar;
    protected ActionBar actionBar;
    @Bind(R.id.img_msg)
    ImageView imgMsg;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @Bind(R.id.lay_friends_condition)
    LinearLayout layFriendsCondition;
    @Bind(R.id.lay_group_condition)
    LinearLayout layGroupCondition;
    @Bind(R.id.federal_confition)
    LinearLayout federalConfition;
    @Bind(R.id.lay_make_warriors)
    LinearLayout layMakeWarriors;
    @Bind(R.id.lay_join_group)
    LinearLayout layJoinGroup;
    @Bind(R.id.lay_megagame)
    LinearLayout layMegagame;
    @Bind(R.id.lay_story)
    LinearLayout layStory;
    @Bind(R.id.lay_coorperation)
    LinearLayout layCoorperation;
    @Bind(R.id.lay_scan)
    LinearLayout layScan;
    @Bind(R.id.tv_unread_count)
    TextView _TvUnreadCount;
    @Bind(R.id.tv_friend_unread_count)
    TextView _TvFriendUnreadCount;
    @Bind(R.id.tv_megagame_unread_count)
    TextView _TvMegaGameUnreadCount;
    @Bind(R.id.lay_msg)
    FrameLayout _LayMsg;
    private ImageView imgBack;
    private TextView tvTitle;
    private AppBarLayout appBar;
    private boolean mIsHidden = false;
    private Handler handler = new Handler();
    private Timer timer;

    protected void initToolBar(View rootView) {
        toolBar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((MainActivity) getActivity()).setSupportActionBar(toolBar);
        imgBack = (ImageView) rootView.findViewById(R.id.img_back);
        tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        appBar = (AppBarLayout) rootView.findViewById(R.id.app_bar_layout);
        tvTitle.setText("功夫圈");

    }


    @Override
    public void initListeners() {

        layFriendsCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), FriendsConditionActivity.class));
            }
        });
        layGroupCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (new SessionUtil(getContext()).getUser().TeamId == 0) {
                    snb("请先加入战队", layGroupCondition);

                } else {
                    startActivity(GroupConditionActivity.class, new Bun().putInt("teamid", new SessionUtil(getContext()).getUser().TeamId).ok());
                }

            }
        });
        federalConfition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), FederalConditionActivity.class));
            }
        });
        layMakeWarriors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), MakeWarriorsActivity.class));
            }
        });
        layJoinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), JoinGroupActivity.class));
            }
        });
        layMegagame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), MegaGameActivity.class));
            }
        });
        layStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), StoryActivity.class));
            }
        });
        layCoorperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), CoorperationActivity.class));
            }
        });
        layScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), CaptureActivity.class));
            }
        });
        imgMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ConversationListActivity.class);
            }
        });
        _LayMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ConversationListActivity.class);
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ContactsActivity.class);
            }
        });
    }

    /**
     * 初始化一些事情
     *
     * @param v
     */
    @Override
    protected void initThings(View v) {
        EventBus.getDefault().register(this);
        initToolBar(v);


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                RongIM.getInstance().setOnReceiveUnreadCountChangedListener(new RongIM.OnReceiveUnreadCountChangedListener() {
                    @Override
                    public void onMessageIncreased(int i) {
                        NotiMsg msg = new NotiMsg();
                        msg.what = C.MSG_UNREAD_COUNT;
                        msg.msg1 = i;
                        EventBus.getDefault().post(msg);
                    }
                }, Conversation.ConversationType.PRIVATE, Conversation.ConversationType.GROUP);


                //扩展功能自定义
                InputProvider.ExtendProvider[] provider = {
                        new ImageInputProvider(RongContext.getInstance()),//图片
                        new CameraInputProvider(RongContext.getInstance()),//相机
                        new LocationInputProvider(RongContext.getInstance()),//地理位置
                };

                RongIM.getInstance().resetInputExtensionProvider(Conversation.ConversationType.PRIVATE, provider);
                RongIM.getInstance().resetInputExtensionProvider(Conversation.ConversationType.GROUP, provider);


            }
        }, 1000);

        presenter.GetNoReadCommentNumByUserId();
        presenter.SelectNoReadMatch();
    }

    private void startTask() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                presenter.GetNoReadCommentNumByUserId();
                presenter.SelectNoReadMatch();
            }
        }, 1000, 1500);
    }


    private void stopTask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * @return 提供LayoutId
     */
    @Override
    public int provideLayoutId() {
        return R.layout.fragment_federal;
    }

    /**
     * 绑定Presenter
     */
    @Override
    public FederalPresenter createPresenter() {
        return new FederalPresenter();
    }


    public void onEvent(NotiMsg msg) {
        if (msg.what == C.MSG_UNREAD_COUNT) {
            if (msg.msg1 <= 0) {
                _TvUnreadCount.setVisibility(View.GONE);

            } else {
                _TvUnreadCount.setVisibility(View.VISIBLE);
                _TvUnreadCount.setText(msg.msg1 + "");
            }

        }
    }


    @Override
    public void updateFriendNoReadNum(int count) {
        if (count > 0) {
            _TvFriendUnreadCount.setVisibility(View.VISIBLE);
            _TvFriendUnreadCount.setText(count + "");
        } else {
            _TvFriendUnreadCount.setVisibility(View.GONE);
        }
    }

    @Override
    public void updateMegaGameNoReadNum(int count) {
        if (count > 0) {
            _TvMegaGameUnreadCount.setVisibility(View.VISIBLE);
            _TvMegaGameUnreadCount.setText(count + "");
        } else {
            _TvMegaGameUnreadCount.setVisibility(View.GONE);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            presenter.GetNoReadCommentNumByUserId();
            presenter.SelectNoReadMatch();
        }
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        startTask();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//    }

}
