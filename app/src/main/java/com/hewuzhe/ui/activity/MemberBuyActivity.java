package com.hewuzhe.ui.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hewuzhe.R;
import com.hewuzhe.model.GetChargeRequest;
import com.hewuzhe.presenter.BuyPresenter;
import com.hewuzhe.ui.base.ToolBarActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.BuyView;
import com.pingplusplus.android.PaymentActivity;

import butterknife.Bind;

public class MemberBuyActivity extends ToolBarActivity<BuyPresenter> implements BuyView {

    @Bind(R.id.img_one)
    ImageView _ImgOne;
    @Bind(R.id.img_two)
    ImageView _ImgTwo;
    @Bind(R.id.img_three)
    ImageView _ImgThree;
    @Bind(R.id.img_four)
    ImageView _ImgFour;
    @Bind(R.id.lay_alipay)
    LinearLayout _LayAlipay;
    @Bind(R.id.lay_wx)
    LinearLayout _LayWx;
    @Bind(R.id.btn_ensure)
    Button _BtnEnsure;
    @Bind(R.id.img_alipay)
    ImageView _ImgAlipay;
    @Bind(R.id.img_wx)
    ImageView _ImgWx;
    /**
     * 支付方式
     */
    private String _payWay = C.CHANNEL_ALIPAY;
    private int _anount = 1200 * 10 * 10;
    private String _desc="";

    /**
     * @return 提供标题
     */
    @Override
    protected String provideTitle() {
        return "会员付费";
    }

    /**
     * @return 提供LayoutId
     */
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_member_buy;

    }


    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {
        _ImgOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTime(1);
            }
        });
        _ImgTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTime(2);
            }
        });
        _ImgThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTime(3);
            }
        });
        _ImgFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTime(4);
            }
        });
        _LayAlipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPayWay(1);
            }
        });
        _LayWx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPayWay(2);
            }
        });

        _BtnEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.getCharge();
            }
        });
    }

    /**
     * 绑定Presenter
     */
    @Override
    public BuyPresenter createPresenter() {
        return new BuyPresenter();
    }

    private void selectPayWay(int i) {
        switch (i) {
            case 1:
                _payWay = C.CHANNEL_ALIPAY;
                _ImgAlipay.setImageResource(R.mipmap.icon_select_pay_focus);
                _ImgWx.setImageResource(R.mipmap.icon_select_pay);
                break;
            case 2:
                _payWay = C.CHANNEL_WECHAT;
                _ImgAlipay.setImageResource(R.mipmap.icon_select_pay);
                _ImgWx.setImageResource(R.mipmap.icon_select_pay_focus);
                break;
        }
    }

    private void selectTime(int i) {
        switch (i) {
            case 1:
                _anount = 300 * 10 * 10;
                _desc = "开通会员1个月";

                _ImgOne.setImageResource(R.mipmap.bg_buy_focus);
                _ImgTwo.setImageResource(R.mipmap.bg_buy);
                _ImgThree.setImageResource(R.mipmap.bg_buy);
                _ImgFour.setImageResource(R.mipmap.bg_buy);

                break;
            case 2:
                _anount = 600 * 10 * 10;
                _desc = "开通会员3个月";

                _ImgOne.setImageResource(R.mipmap.bg_buy);
                _ImgTwo.setImageResource(R.mipmap.bg_buy_focus);
                _ImgThree.setImageResource(R.mipmap.bg_buy);
                _ImgFour.setImageResource(R.mipmap.bg_buy);

                break;

            case 3:
                _anount = 900 * 10 * 10;
                _desc = "开通会员半年";

                _ImgOne.setImageResource(R.mipmap.bg_buy);
                _ImgTwo.setImageResource(R.mipmap.bg_buy);
                _ImgThree.setImageResource(R.mipmap.bg_buy_focus);
                _ImgFour.setImageResource(R.mipmap.bg_buy);

                break;

            case 4:
                _desc = "开通会员一年";
                _anount = 1200 * 10 * 10;
                _ImgOne.setImageResource(R.mipmap.bg_buy);
                _ImgTwo.setImageResource(R.mipmap.bg_buy);
                _ImgThree.setImageResource(R.mipmap.bg_buy);
                _ImgFour.setImageResource(R.mipmap.bg_buy_focus);
                break;

        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //支付页面返回处理
        if (requestCode == C.REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
            /* 处理返回值
             * "success" - payment succeed
             * "fail"    - payment failed
             * "cancel"  - user canceld
             * "invalid" - payment plugin not installed
             *
             * 如果是银联渠道返回 invalid，调用 UPPayAssistEx.installUPPayPlugin(this); 安装银联安全支付控件。
             */
                snb(result, _BtnEnsure);
                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
//                toast(errorMsg+"---"+extraMsg);
            }
        }
    }

    @Override
    public void toPay(String charge) {
        Intent intent = new Intent();
        String packageName = getPackageName();
        ComponentName componentName = new ComponentName(packageName, packageName + ".wxapi.WXPayEntryActivity");
        intent.setComponent(componentName);
        intent.putExtra(PaymentActivity.EXTRA_CHARGE, charge);
        startActivityForResult(intent, C.REQUEST_CODE_PAYMENT);
    }

    @Override
    public GetChargeRequest getData() {
        GetChargeRequest request = new GetChargeRequest();
        request.userId = new SessionUtil(getContext()).getUserId();
        request.channel = _payWay;
        request.amount = _anount;
        request.description = _desc;
        return request;
    }
}