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
import com.hewuzhe.ui.widget.YsnowEditDialog;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.EmojsUtils;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.utils.TimeUtil;
import com.hewuzhe.view.ProfileSettingsView;
import com.socks.library.KLog;

import butterknife.Bind;
import de.greenrobot.event.EventBus;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;


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

    public static boolean isNeedShowInput = true;

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
        _LayRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConversationActivity.isNeedShowInput = false;
                RongIM.getInstance().startPrivateChat(getContext(), friend.Id + "", friend.NicName);
            }
        });

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


        _TvRemark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final YsnowEditDialog ysnowEditDialog = new YsnowEditDialog(getContext(), "好友备注", "请输入好友备注");

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
                        String content = ysnowEditDialog.content.getText().toString();


                        if (EmojsUtils.containsEmoji(content)) {
                            toast("备注名不能包含表情");
                            return;
                        }

                        presenter.ChangeFriendRName(content);

                    }
                });

                ysnowEditDialog.show();

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
            _BtnFollow.setText("删除好友");
            _BtnFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.unFollow(_BtnFollow);
                }
            });

        } else {
//            _BtnFollow.setText("关注好友");
//            _BtnFollow.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    presenter.follow(_BtnFollow);
//                }
//            });

            EventBus.getDefault().post(C.MSG_CLOSE_FRIEND_PROFILE);
            startActivity(StrangerProfileSettingsActivity.class, new Bun().putInt("id", friend.Id).ok());
            finishActivity();

        }
    }

    @Override
    public void setRemarkSuccess(String rName) {
        toast("设置成功");
        _TvRemark.setText(rName);
        _TvUsername.setText(friend.NicName + "(" + rName + ")");
    }
    
    @Override
    public void setData(User user) {
        friend = user;
        _TvUsername.setText(friend.RemarkName.equals(friend.NicName) ? friend.NicName : friend.NicName + "(" + friend.RemarkName + ")");
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
                presenter.unFollow(_BtnFollow);
            }
        });

        _SwitchCondition.setChecked(user.IsShield);
        _SwitchMsg.setChecked(user.IsShieldNews);


        _SwitchCondition.setOnCheckedChangeListener(new SwitchView.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, boolean isChecked) {
                if (isChecked) {
                    presenter.ShieldFriend();
                } else {
                    presenter.UnShieldFriend();
                }
            }
        });


        _SwitchMsg.setOnCheckedChangeListener(new SwitchView.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, boolean isChecked) {
                if (isChecked) {
                    RongIMClient.getInstance().addToBlacklist(friend.Id + "", new RongIMClient.OperationCallback() {
                        @Override
                        public void onSuccess() {
                            KLog.d("onSuccess");

                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {
                            KLog.d("onError");

                        }
                    });
                    presenter.ShieldFriendNews();
                } else {
                    RongIMClient.getInstance().removeFromBlacklist(friend.Id + "", new RongIMClient.OperationCallback() {
                        @Override
                        public void onSuccess() {

                            KLog.d("onSuccess");
                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {
                            KLog.d("onError");

                        }
                    });

                    presenter.UnShieldFriendNews();

                }
            }
        });
    }
}
