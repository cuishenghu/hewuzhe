package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.base.ToolBarActivity;
import com.hewuzhe.utils.SPUtil;
import com.socks.library.KLog;

import butterknife.Bind;
import io.rong.imlib.RongIMClient;

public class SettingsActivity extends ToolBarActivity {

    @Bind(R.id.lay_friends_condition)
    LinearLayout _LayFriendsCondition;
    @Bind(R.id.lay_noti)
    TextView _LayNoti;
    @Bind(R.id.switch_msg)
    CheckBox _SwitchMsg;
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
    LinearLayout _LayShieldList;
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

    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);

        spUtil = new SPUtil(getContext())
                .open("settings");

        _SwitchMsg.setChecked(spUtil.getBoolean("msg"));
        _SwitchSound.setChecked(spUtil.getBoolean("sound"));
        _SwitchVibrate.setChecked(spUtil.getBoolean("vibrate"));


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
    public BasePresenterImp createPresenter() {
        return null;
    }

    @Override
    protected String provideTitle() {
        return "聊天通知设置";
    }

}
