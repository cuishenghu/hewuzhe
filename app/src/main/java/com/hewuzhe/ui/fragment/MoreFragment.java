package com.hewuzhe.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.model.AboutUs;
import com.hewuzhe.presenter.WarriorFragmentPresenter;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.activity.AboutActivity;
import com.hewuzhe.ui.activity.FeedBackActivity;
import com.hewuzhe.ui.activity.SettingsActivity;
import com.hewuzhe.ui.activity.SignInActivity;
import com.hewuzhe.ui.activity.SiteActivity;
import com.hewuzhe.ui.base.ToolBarFragment;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widget.Line;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.WarriorFragmentView;

import butterknife.Bind;
import io.rong.imkit.RongIM;
import materialdialogs.DialogAction;
import materialdialogs.GravityEnum;
import materialdialogs.MaterialDialog;

/**
 * Created by xianguangjin on 15/12/8.
 */
public class MoreFragment extends ToolBarFragment<WarriorFragmentPresenter> implements WarriorFragmentView {

    @Bind(R.id.lay_settings)
    LinearLayout laySettings;
    @Bind(R.id.lay_check_update)
    LinearLayout layCheckUpdate;
    @Bind(R.id.lay_clear_data)
    LinearLayout layClearData;
    @Bind(R.id.lay_feedback)
    LinearLayout lay_feedback;
    @Bind(R.id.lay_service)
    LinearLayout ll_service;
    @Bind(R.id.lay_address)
    LinearLayout layAddress;
    @Bind(R.id.btn_exit)
    Button btnExit;
    @Bind(R.id.tele_more)
    TextView tele_more;

    private AboutUs au;

    @Override
    protected String provideTitle() {
        return "更多";
    }

    @Override
    public void initListeners() {
        presenter.getIndexImg();

        laySettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), SettingsActivity.class));
            }
        });

        layClearData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(getActivity())
                        .title("清除缓存?")
                        .titleColor(Color.WHITE)
                        .contentColor(Color.WHITE)
                        .positiveColor(C.COLOR_YELLOW)
                        .negativeColor(C.COLOR_YELLOW)
                        .content("您确定要清除缓存吗？")
                        .positiveText("确定")
                        .negativeText("取消")
                        .backgroundColor(C.COLOR_BG)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                                snb("清除缓存成功", layClearData);

                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });

        layCheckUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(AboutActivity.class);

            }
        });

        lay_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), FeedBackActivity.class));
            }
        });
        ll_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getActivity().startActivity(new Intent(getActivity(), FeedBackActivity.class));

                MaterialDialog materialDialog = new MaterialDialog.Builder(getActivity())
                        .title("拨打客服电话")
                        .titleGravity(GravityEnum.CENTER)
                        .titleColor(Color.WHITE)
                        .contentColor(Color.WHITE)
                        .positiveColor(C.COLOR_YELLOW)
                        .negativeColor(C.COLOR_YELLOW)
                        .content("确定拨打客服电话吗？")
                        .backgroundColor(C.COLOR_BG)
                        .positiveText("确定")
                        .negativeText("取消")
                        .backgroundColor(C.COLOR_BG)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                                Intent intent = new Intent(Intent.ACTION_CALL);
                                intent.setData(Uri.parse("tel:"+au.TelePhone));
                                startActivity(intent);
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .show();


            }
        });


        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SessionUtil(getContext())
                        .removeUserInfoStr();

                /**
                 * 退出融云
                 * */
                RongIM.getInstance().logout();

                startActivity(SignInActivity.class);
                getActivity().finish();
            }
        });

        layAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), SiteActivity.class).putExtra("sel", 1));
            }
        });
    }


    /**
     * @return 提供LayoutId
     */
    @Override
    public int provideLayoutId() {
        return R.layout.fragment_more;
    }

    /**
     * 绑定Presenter
     */
    @Override
    public WarriorFragmentPresenter createPresenter() {
        return new WarriorFragmentPresenter();
    }

    @Override
    public boolean canBack() {
        return false;
    }

    @Override
    public void setUserData() {

    }

    @Override
    public void isWuYou(Boolean data, int userid) {

    }

    @Override
    public void setIndexImg(AboutUs data) {
        this.au=data;
        tele_more.setText("联系客服（"+au.TelePhone+"）");
    }
}
