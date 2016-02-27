package com.hewuzhe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.presenter.AssessSettingsPressenter;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.base.ToolBarActivity;
import com.hewuzhe.utils.SPUtil;
import com.socks.library.KLog;

import butterknife.Bind;
import io.rong.imlib.RongIMClient;

public class SettingsActivity extends ToolBarActivity<AssessSettingsPressenter> {

    @Bind(R.id.lay_friends_assess_notic)
    LinearLayout _LayFriendsAssessNotic;//\
    @Bind(R.id.switch_assess)
    CheckBox _SwitchAssess;//朋友圈评论提示开关
    @Bind(R.id.lay_noti)
    TextView _LayNoti;
    @Bind(R.id.switch_msg)
    CheckBox _SwitchMsg;//是否接受新的聊天
    @Bind(R.id.lay_settings)
    LinearLayout _LaySettings;
    @Bind(R.id.switch_sound)
    CheckBox _SwitchSound;
    @Bind(R.id.lay_sound)
    LinearLayout _LaySound;
    @Bind(R.id.switch_vibrate)
    CheckBox _SwitchVibrate;
    @Bind(R.id.lay_vibrate)
    LinearLayout _LayVibrate;
    @Bind(R.id.lay_shield_list)
    LinearLayout _LayShieldList;//屏蔽列表
    private SPUtil spUtil;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_settings;
    }


    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {
/**
 * 跳转屏蔽列表
 */
        _LayShieldList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, MoreShieldListActivity.class));
            }
        });

    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);

        spUtil = new SPUtil(getContext())
                .open("settings");
        _SwitchAssess.setChecked(spUtil.getBoolean("assess"));
        _SwitchMsg.setChecked(spUtil.getBoolean("msg"));
        _SwitchSound.setChecked(spUtil.getBoolean("sound"));
        _SwitchVibrate.setChecked(spUtil.getBoolean("vibrate"));

        _SwitchAssess.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                spUtil.putBoolean("assess", isChecked);
                if (isChecked) {
                    presenter.AssessState(true, SettingsActivity.this);
                } else {
                    presenter.AssessState(false, SettingsActivity.this);
                }
            }
        });


        _SwitchMsg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, final boolean isChecked) {
                spUtil.putBoolean("msg", isChecked);
//                RongIM.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
//                    @Override
//                    public boolean onReceived(Message message, int i) {
//                        return !isChecked;
//                    }
//                });

                if (!isChecked) {
                    RongIMClient.getInstance().setNotificationQuietHours("00:00:00", 1440, new RongIMClient.OperationCallback() {
                        @Override
                        public void onSuccess() {
                            KLog.d("add succes");

                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {
                            KLog.d("add error");

                        }
                    });
                } else {
                    RongIMClient.getInstance().removeNotificationQuietHours(new RongIMClient.OperationCallback() {
                        @Override
                        public void onSuccess() {
                            KLog.d("remove succes");
                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {
                            KLog.d("remove error");
                        }
                    });

                }
            }
        });
        _SwitchSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                spUtil.putBoolean("sound", isChecked);
            }
        });
        _SwitchVibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                spUtil.putBoolean("vibtrate", isChecked);
            }
        });


    }

    /**
     * 绑定Presenter
     */
    @Override
    public AssessSettingsPressenter createPresenter() {
        return new AssessSettingsPressenter();
    }

    @Override
    protected String provideTitle() {
        return "聊天通知设置";
    }

}
