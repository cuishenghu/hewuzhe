package com.hewuzhe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.model.VipPrice;
import com.hewuzhe.presenter.MemberPresenter;
import com.hewuzhe.ui.base.ToolBarActivity;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.MemberView;

import java.util.ArrayList;

import butterknife.Bind;

public class MemberActivity extends ToolBarActivity<MemberPresenter> implements MemberView {

    @Bind(R.id.tv_vip_price1)
    TextView _TvVipPrice1;//会员套餐一的价格
    @Bind(R.id.tv_vip_price2)
    TextView _TvVipPrice2;//会员套餐二的价格
    @Bind(R.id.tv_vip_price3)
    TextView _TvVipPrice3;//会员套餐三的价格
    @Bind(R.id.tv_vip_price4)
    TextView _TvVipPrice4;//会员套餐四的价格
    @Bind(R.id.btn_to_member)
    Button _BtnToMember;//点击续费
    @Bind(R.id.tv_over_time)
    TextView _TvOverTime;//当前时间
    private boolean _IsVip;
    private ArrayList<VipPrice> list = null;

    /**
     * @return 提供标题
     */
    @Override
    protected String provideTitle() {
        return "会员详情";
    }

    /**
     * @return 提供LayoutId
     */
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_member;
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        _IsVip = new SessionUtil(getContext()).getUser().isVip();
        _BtnToMember.setText(_IsVip ? "点击续费" : "成为会员");
        _TvOverTime.setVisibility(_IsVip ? View.VISIBLE : View.INVISIBLE);

        if (_IsVip) {
            presenter.GetPayOverTime();
        }

        presenter.GetPriceToVipMember();//获取会员价格列表

    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {
        _BtnToMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MemberActivity.this, MemberBuyActivity.class);
//                intent.putExtra("list", list);
//                startActivity(intent);
                startActivity(new Intent(MemberActivity.this, MemberBuyActivity.class).putExtra("list", list));
            }
        });

    }

    /**
     * 绑定Presenter
     */
    @Override
    public MemberPresenter createPresenter() {
        return new MemberPresenter();
    }

    @Override
    public void setData(String s) {
        _TvOverTime.setText(s);

    }

    @Override
    public void bindData(ArrayList<VipPrice> list) {
        this.list = list;
        _TvVipPrice1.setText("套餐一：充值" + list.get(0).Price + "元，" + list.get(0).Description);
        _TvVipPrice2.setText("套餐二：充值" + list.get(1).Price + "元，" + list.get(1).Description);
        _TvVipPrice3.setText("套餐三：充值" + list.get(2).Price + "元，" + list.get(2).Description);
        _TvVipPrice4.setText("套餐四：充值" + list.get(3).Price + "元，" + list.get(3).Description);
    }
}
