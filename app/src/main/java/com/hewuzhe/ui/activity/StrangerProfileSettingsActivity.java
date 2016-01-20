package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
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
import com.hewuzhe.ui.widget.YsnowEditDialog;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.utils.TimeUtil;
import com.hewuzhe.view.ProfileSettingsView;

import butterknife.Bind;

public class StrangerProfileSettingsActivity extends ToolBarActivity<ProfileSettingsPresenter> implements ProfileSettingsView {


    @Bind(R.id.img_back)
    ImageView _ImgBack;
    @Bind(R.id.tv_title)
    TextView _TvTitle;
    @Bind(R.id.tv_action)
    TextView _TvAction;
    @Bind(R.id.toolbar)
    Toolbar _Toolbar;
    @Bind(R.id.app_bar_layout)
    AppBarLayout _AppBarLayout;
    @Bind(R.id.img_avatar)
    ImageView _ImgAvatar;
    @Bind(R.id.tv_username)
    TextView _TvUsername;
    @Bind(R.id.tv_info)
    TextView _TvInfo;
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
    @Bind(R.id.btn_follow)
    Button _BtnFollow;
    @Bind(R.id.lay_friends_condition)
    LinearLayout _LayFriendsCondition;
    private User friend;

    /**
     * @return 提供标题
     */
    @Override
    protected String provideTitle() {
        return "陌生人资料";
    }

    /**
     * @return 提供LayoutId
     */
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_stranger_profile_settings;
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {

        _LayReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final YsnowEditDialog ysnowEditDialog = new YsnowEditDialog(getContext(), "举报事由", "");

                ysnowEditDialog.negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ysnowEditDialog.dismiss();
                    }
                });
                ysnowEditDialog.positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ysnowEditDialog.dismiss();

                        presenter.collectAndOther(getData(), 3, _LayReport, ysnowEditDialog.content.getText().toString());

                    }
                });

                ysnowEditDialog.show();

            }
        });


        _LayFriendsCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MyConditionActivity.class, new Bun().putInt("id", getData()).ok());

            }
        });


    }


    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        presenter.getFriendProfile();

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
        return getIntentData().getInt("id");
    }

    @Override
    public void followSuccess(boolean b) {
        if (b) {
            _BtnFollow.setText("取消关注");
            _BtnFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.unFollow(_BtnFollow);
                }
            });

        } else {
            _BtnFollow.setText("关注好友");
            _BtnFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.follow(_BtnFollow);
                }
            });

        }
    }

    @Override
    public void setData(User user) {
        friend = user;
        _TvUsername.setText(friend.NicName + "");
        _TvInfo.setText(StringUtil.getGender(friend.Sexuality) + " " + TimeUtil.timeHaved(friend.Birthday) + "岁 " + "   " + friend.HomeAreaprovinceName + " " + friend.HomeAreaCityName + " " + friend.HomeAreaCountyName);
        _TvRemark.setText(friend.RemarkName);

        Glide.with(getContext())
                .load(C.BASE_URL + friend.PhotoPath)
                .centerCrop()
                .crossFade()
                .transform(new GlideCircleTransform(getContext()))
                .into(_ImgAvatar);

        _BtnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.follow(_BtnFollow);
            }
        });


        _SwitchMsg.setOnCheckedChangeListener(new SwitchView.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, boolean isChecked) {
                if (isChecked) {
                    presenter.ShieldFriendNews();
                } else {
                    presenter.UnShieldFriendNews();
                }
            }
        });


        _SwitchMsg.setChecked(false);
    }


}
