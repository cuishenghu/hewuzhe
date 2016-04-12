package com.hewuzhe.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Zxing.CaptureActivity;
import com.hewuzhe.R;
import com.hewuzhe.model.ChatList;
import com.hewuzhe.model.RecommendUser;
import com.hewuzhe.presenter.ChatPresenter;
import com.hewuzhe.rongc.provider.VideoInputProvider;
import com.hewuzhe.ui.activity.AddWarriorsActivity2;
import com.hewuzhe.ui.activity.ContactsActivity;
import com.hewuzhe.ui.activity.ConversationListActivity;
import com.hewuzhe.ui.activity.FriendsConditionActivity;
import com.hewuzhe.ui.activity.GroupConditionActivity;
import com.hewuzhe.ui.activity.JoinGroupActivity;
import com.hewuzhe.ui.activity.NearPeopleActivity;
import com.hewuzhe.ui.adapter.BindUserAdapter;
import com.hewuzhe.ui.adapter.ChatAdapter;
import com.hewuzhe.ui.base.SwipeRecycleViewFragment;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.NotiMsg;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.view.ChatView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import cn.xm.weidongjian.popuphelper.PopupWindowHelper;
import de.greenrobot.event.EventBus;
import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.widget.provider.CameraInputProvider;
import io.rong.imkit.widget.provider.ImageInputProvider;
import io.rong.imkit.widget.provider.InputProvider;
import io.rong.imkit.widget.provider.LocationInputProvider;
import io.rong.imlib.model.Conversation;

/**
 * Created by zycom on 2016/3/29.
 */
public class ChatFragment extends SwipeRecycleViewFragment<ChatPresenter,ChatAdapter, ChatList> implements ChatView {

    //header里的控件定义
    private View headerView;
    private TextView chat_count;
    private TextView chat_friends;
    private LinearLayout chat_list_op;
    private LinearLayout chat_friends_list;
    private LinearLayout ll_wuyouquan;
    private LinearLayout ll_xingququan;
    private LinearLayout ll_near_person;
    @Bind(R.id.tv_action)
    TextView tv_action;

    ArrayList<ChatList> chatLists;

    private RecyclerView re_others;
    private RecyclerView recycler_view_one;

    private int chatCount;

    private Handler handler = new Handler();
    private boolean isfirst = true;

    private TextView action_1;
    private TextView action_2;

    @Override
    protected void initThings(View view) {
        super.initThings(view);
        initHeader();
//        presenter.SelectFriends(this.getContext());
//        presenter.getData(page, count);
//        presenter.SelectRecommendUser();
//        presenter.getTopFourData();

    }

    @OnClick(R.id.tv_action)
    protected void action() {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.popview, null);
        final PopupWindowHelper popupWindowHelper = new PopupWindowHelper(view);

        action_1 = (TextView) view.findViewById(R.id.tv_local_video);
        action_2 = (TextView) view.findViewById(R.id.tv_take_video);
        action_1.setText("添加好友");
        action_2.setText("扫一扫");
        action_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(AddWarriorsActivity2.class);
                popupWindowHelper.dismiss();
            }
        });

        action_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), CaptureActivity.class));
                popupWindowHelper.dismiss();
            }
        });

        popupWindowHelper.showAsDropDown(tv_action);
    }

    @Override
    protected ChatAdapter provideAdapter() {
        headerView = getActivity().getLayoutInflater().inflate(R.layout.fragment_chat_top, null);
        return new ChatAdapter(getContext(),presenter,headerView);
    }

    private void initHeader(){
        chat_count = (TextView)headerView.findViewById(R.id.chat_count);
        chat_friends = (TextView)headerView.findViewById(R.id.chat_friends);
        chat_list_op = (LinearLayout)headerView.findViewById(R.id.chat_list_op);
        chat_friends_list = (LinearLayout)headerView.findViewById(R.id.chat_friends_list);
        ll_wuyouquan = (LinearLayout)headerView.findViewById(R.id.ll_wuyouquan);
        ll_xingququan = (LinearLayout)headerView.findViewById(R.id.ll_xingququan);
        ll_near_person = (LinearLayout)headerView.findViewById(R.id.ll_near_person);
        re_others = (RecyclerView)headerView.findViewById(R.id.re_others);
        recycler_view_one = (RecyclerView)headerView.findViewById(R.id.recycler_view_one);
    }

    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new GridLayoutManager(getContext(),2);
    }

    @Override
    public void initListeners() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                RongIM.getInstance().setOnReceiveUnreadCountChangedListener(new RongIM.OnReceiveUnreadCountChangedListener() {
                    @Override
                    public void onMessageIncreased(int i) {
                        NotiMsg msg = new NotiMsg();
                        msg.what = C.MSG_UNREAD_COUNT;
                        msg.msg1 = i;
                        chatCount = i;
                        chat_count.setText(i==0?"暂无消息":"您共有"+i+"条消息未读");
                        EventBus.getDefault().post(msg);
                    }
                }, Conversation.ConversationType.PRIVATE, Conversation.ConversationType.GROUP);


                //扩展功能自定义
                InputProvider.ExtendProvider[] provider = {
                        new ImageInputProvider(RongContext.getInstance()),//图片
                        new CameraInputProvider(RongContext.getInstance()),//相机
                        new LocationInputProvider(RongContext.getInstance()),//地理位置
                        new VideoInputProvider(RongContext.getInstance()),
                };

                RongIM.getInstance().resetInputExtensionProvider(Conversation.ConversationType.PRIVATE, provider);
                RongIM.getInstance().resetInputExtensionProvider(Conversation.ConversationType.GROUP, provider);


            }
        }, 1000);

        //打开聊天列表
        chat_list_op.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ConversationListActivity.class);
            }
        });
        /**
         * 武友列表
         */
        //
        chat_friends_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ContactsActivity.class);
            }
        });

        /**
         * 武友圈
         */
        ll_wuyouquan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FriendsConditionActivity.class));
            }
        });
        /**
         * 兴趣圈
         */
        ll_xingququan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new SessionUtil(getContext()).getUser().TeamId == 0) {
                    snb("请先加入兴趣圈", ll_xingququan);
                    startActivity(new Intent(getActivity(), JoinGroupActivity.class));
                } else {
                    startActivity(GroupConditionActivity.class, new Bun().putInt("teamid", new SessionUtil(getContext()).getUser().TeamId).ok());
                }
            }
        });
        /**
         * 附近人
         */
        ll_near_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NearPeopleActivity.class));
            }
        });


    }



    @Override
    public void onResume() {
        super.onResume();
        requestDataRefresh();
    }

    @Override
    public int provideLayoutId() {
        return R.layout.fragment_chat;
    }

    @Override
    public ChatPresenter createPresenter() {
        return new ChatPresenter();
    }

    @Override
    public void onItemClick(View view, int pos, ChatList item) {

    }

    @Override
    public void bindData(ArrayList<ChatList> data) {

    }

    @Override
    public void bindCount(int count) {
        chat_friends.setText(count == 0 ? "暂无好友" : "您有" + count + "个好友");
    }


    @Override
    public void bindTuijian(ArrayList<ChatList> data) {
        bd(data);
    }

    @Override
    public void bindTopFourTuijian(ArrayList<ChatList> data) {
        recycler_view_one.setLayoutManager(new GridLayoutManager(getContext(),2));
        ChatAdapter chatAdapter=new ChatAdapter(getContext());
        //设置imageview宽高
        WindowManager manager = (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        int width =display.getWidth();
        ViewGroup.LayoutParams para;
        para = recycler_view_one.getLayoutParams();

        para.height = (((width- StringUtil.dip2px(getContext(), 20))/2)+StringUtil.dip2px(getContext(),125))*2;
        recycler_view_one.setLayoutParams(para);

        recycler_view_one.setAdapter(chatAdapter);

        chatAdapter.data.addAll(data);
        isfirst = false;
    }

    @Override
    public void bindUsers(ArrayList<RecommendUser> data) {
        re_others.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        BindUserAdapter bindUserAdapter = new BindUserAdapter(getContext());

        re_others.setAdapter(bindUserAdapter);

        bindUserAdapter.data.addAll(data);

    }

    /**
     *
     */
    @Override
    public void requestDataRefresh() {
        presenter.SelectFriends(this.getContext());
        presenter.getData(page, count);
        presenter.SelectRecommendUser();
        presenter.getTopFourData();

    }

}
