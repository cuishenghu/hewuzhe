package com.hewuzhe.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.hewuzhe.R;
import com.hewuzhe.model.AboutUs;
import com.hewuzhe.ui.base.BaseActivity2;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.http.HttpErrorHandler;
import com.hewuzhe.ui.http.HttpUtils;
import com.hewuzhe.ui.http.UrlContants;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.Tools;
import com.hewuzhe.view.MyCommonTitle;

import io.rong.imkit.RongIM;
import materialdialogs.DialogAction;
import materialdialogs.MaterialDialog;

/**
 * Created by Administrator on 2016/3/24 0024.
 */
public class MoreActivity extends BaseActivity2 {
    private MyCommonTitle myCommonTitle;
    private LinearLayout ll_noticeSetting;
    private LinearLayout ll_clearCache;
    private LinearLayout ll_about;
    private LinearLayout ll_address;
    private LinearLayout ll_onLineService;
    private TextView tvTel;
    private Button btn_exit;
    private AboutUs aboutUs;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        initView();
        requestData();
    }

    private void initView() {
        myCommonTitle = (MyCommonTitle) findViewById(R.id.aci_mytitle);
        myCommonTitle.setTitle("更多");
        ll_noticeSetting = (LinearLayout) findViewById(R.id.lay_settings);
        ll_clearCache = (LinearLayout) findViewById(R.id.lay_clear_data);
        ll_about = (LinearLayout) findViewById(R.id.lay_check_update);
        ll_address = (LinearLayout) findViewById(R.id.lay_address);
        ll_onLineService = (LinearLayout) findViewById(R.id.lay_service);
        tvTel = (TextView) findViewById(R.id.tele_more);
        btn_exit = (Button) findViewById(R.id.btn_exit);
        if(new SessionUtil(this).isLogin())
            btn_exit.setText("退出");
        else
            btn_exit.setText("登录");
        setListener(ll_noticeSetting, ll_clearCache, ll_about, ll_address, ll_onLineService, btn_exit);
    }

    /**
     * 获取关于我们
     */
    public void requestData() {
        HttpUtils.getAboutUs(new HttpErrorHandler() {
            @Override
            public void onRecevieSuccess(JSONObject json) {
                JSONObject jsonObject = json.getJSONObject(UrlContants.jsonData);
                phoneNumber = jsonObject.getString("TelePhone");
                tvTel.setText("联系客服(" + phoneNumber + ")");
            }
        });
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.lay_settings://聊天通知设置
                if(new SessionUtil(this).isLogin()) {
                    startActivity(new Intent(MoreActivity.this, SettingsActivity.class));
                }else
                    startActivity(new Intent(MoreActivity.this,SignInActivity.class));
                break;
            case R.id.lay_clear_data://清除缓存
                new MaterialDialog.Builder(MoreActivity.this)
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
                                Tools.toast(MoreActivity.this, "清除缓存成功");

                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.lay_check_update://关于
                startActivity(new Intent(MoreActivity.this, AboutActivity.class));
                break;
            case R.id.lay_feedback://反馈
                startActivity(new Intent(MoreActivity.this, FeedBackActivity.class));
            case R.id.lay_address://收货地址
                if(new SessionUtil(this).isLogin()) {
                    startActivity(new Intent(MoreActivity.this, SiteActivity.class).putExtra("sel", 1));
                }else
                    startActivity(new Intent(MoreActivity.this,SignInActivity.class));
                break;
            case R.id.lay_service://在线服务
                new MaterialDialog.Builder(MoreActivity.this)
                        .title("拨打客服电话")
                        .titleColor(Color.WHITE)
                        .content("确定拨打客服电话？")
                        .contentColor(Color.WHITE)
                        .positiveColor(C.COLOR_YELLOW)
                        .negativeColor(C.COLOR_YELLOW)
                        .positiveText("确定")
                        .negativeText("取消")
                        .backgroundColor(C.COLOR_BG)
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber)));
                            }
                        }).show();
                break;
            case R.id.btn_exit://退出
                new SessionUtil(MoreActivity.this).removeUserInfoStr();
                /**
                 * 退出融云
                 * */
                RongIM.getInstance().logout();
                startActivity(new Intent(MoreActivity.this, SignInActivity.class));
                break;
        }
    }
}
