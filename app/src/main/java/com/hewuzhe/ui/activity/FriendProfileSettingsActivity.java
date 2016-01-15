package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hewuzhe.R;
import com.hewuzhe.model.User;
import com.hewuzhe.presenter.ProfileSettingsPresenter;
import com.hewuzhe.ui.base.ToolBarActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widget.GlideCircleTransform;
import com.hewuzhe.ui.widget.SwitchView;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.utils.TimeUtil;
import com.hewuzhe.view.ProfileSettingsView;

import butterknife.Bind;

public class FriendProfileSettingsActivity extends ToolBarActivity<ProfileSettingsPresenter> implements ProfileSettingsView {


    @Bind(R.id.img_avatar)
    ImageView _ImgAvatar;
    @Bind(R.id.tv_username)
    TextView _TvUsername;
    @Bind(R.id.tv_info)
    TextView _TvInfo;
    @Bind(R.id.tv_acount)
    TextView _TvAcount;
    @Bind(R.id.switch_condition)
    SwitchView _SwitchCondition;
    @Bind(R.id.tv_remark)
    TextView _TvRemark;
    @Bind(R.id.tv_weight)
    TextView _TvWeight;
    @Bind(R.id.lay_report)
    LinearLayout _LayReport;
    @Bind(R.id.tv_time)
    TextView _TvTime;
    @Bind(R.id.switch_msg)
    SwitchView _SwitchMsg;
    @Bind(R.id.tv_desc)
    TextView _TvDesc;
    @Bind(R.id.lay_record)
    LinearLayout _LayRecord;
    @Bind(R.id.btn_follow)
    Button _BtnFollow;

    private User friend;

    /**
     * @return 提供标题
     */
    @Override
    protected String provideTitle() {
        return getIntentData().getString("title");
    }

    /**
     * @return 提供LayoutId
     */
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_friend_profile_settings;
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {

    }


    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);

        initData();
    }

    private void initData() {
        friend = getIntentData().getParcelable("user");
        _TvUsername.setText(friend.NicName + "");
        _TvInfo.setText(StringUtil.getGender(friend.Sexuality) + " " + TimeUtil.timeHaved(friend.Birthday) + "岁 " + "   " + friend.HomeAreaprovinceName + " " + friend.HomeAreaCityName + " " + friend.HomeAreaCountyName);

        Glide.with(getContext())
                .load(C.BASE_URL + friend.PhotoPath)
                .centerCrop()
                .crossFade()
                .transform(new GlideCircleTransform(getContext()))
                .into(_ImgAvatar);

        _BtnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.unFollow(_BtnFollow);
            }
        });

    }

    /**
     * 绑定Presenter
     */
    @Override
    public ProfileSettingsPresenter createPresenter() {
        return new ProfileSettingsPresenter();
    }

    @Override
    public Integer getData() {
        return friend.Id;
    }

    @Override
    public void followSuccess(boolean b) {
        if (b) {
            _BtnFollow.setText("已关注");
            _BtnFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.unFollow(_BtnFollow);
                }
            });


        } else {
            _BtnFollow.setText("取消关注");
            _BtnFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.follow(_BtnFollow);
                }
            });

        }
    }
}
