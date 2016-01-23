package com.hewuzhe.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.hewuzhe.R;
import com.hewuzhe.presenter.ConversationPresenter;
import com.hewuzhe.ui.base.ToolBarActivity;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.view.ConversationView;

import java.util.Locale;

import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.model.Conversation;

public class ConversationActivity extends ToolBarActivity<ConversationPresenter> implements ConversationView {


    /**
     * 目标 Id
     */
    private String mTargetId;

    /**
     * 刚刚创建完讨论组后获得讨论组的id 为targetIds，需要根据 为targetIds 获取 targetId
     */
    private String mTargetIds;

    /**
     * 会话类型
     */
    private Conversation.ConversationType mConversationType;
    private String title;
    public static boolean isNeedShowInput = true;


    /**
     * @return 提供标题
     */
    @Override
    protected String provideTitle() {

        return "";
    }

    /**
     * @return 提供LayoutId
     */
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_conversation;
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);

        getIntentDate(getIntent());
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
    public ConversationPresenter createPresenter() {
        return new ConversationPresenter();
    }

    /**
     * 展示如何从 Intent 中得到 融云会话页面传递的 Uri
     */
    private void getIntentDate(Intent intent) {

        mTargetId = intent.getData().getQueryParameter("targetId");
        presenter.isWuyouTitle(Integer.parseInt(mTargetId));

        mTargetIds = intent.getData().getQueryParameter("targetIds");
        //intent.getData().getLastPathSegment();//获得当前会话类型
        mConversationType = Conversation.ConversationType.valueOf(intent.getData().getLastPathSegment().toUpperCase(Locale.getDefault()));

        enterFragment(mConversationType, mTargetId);


    }

    /**
     * 加载会话页面 ConversationFragment
     *
     * @param mConversationType 会话类型
     * @param mTargetId         目标 Id
     */
    private void enterFragment(Conversation.ConversationType mConversationType, String mTargetId) {

        ConversationFragment fragment = (ConversationFragment) getSupportFragmentManager().findFragmentById(R.id.conversation);

        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversation").appendPath(mConversationType.getName().toLowerCase())
                .appendQueryParameter("targetId", mTargetId).build();

        fragment.setUri(uri);

        //隐藏输入框
        if (!isNeedShowInput) {
            fragment.getChildFragmentManager().getFragments().get(0).getView().setVisibility(View.GONE);
            isNeedShowInput = true;
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

    @Override
    public void isWuYouTitle(Boolean data) {
        if (mConversationType == Conversation.ConversationType.PRIVATE) {
            if (data) {
                title = getIntent().getData().getQueryParameter("title");
                tvTitle.setText(title);
            } else {
                tvTitle.setText("陌生人");
            }

        } else {
            title = getIntent().getData().getQueryParameter("title");
            tvTitle.setText(title);
        }

    }
}
