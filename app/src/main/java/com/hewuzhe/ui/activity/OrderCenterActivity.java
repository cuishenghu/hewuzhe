package com.hewuzhe.ui.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.RadioGroup;

import com.hewuzhe.R;
import com.hewuzhe.ui.fragment.OrderFragment;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.view.MyCommonTitle;

/**
 * 我的订单
 * Created by Administrator on 2016/1/26 0026.
 */
public class OrderCenterActivity extends FragmentActivity {

    private FragmentManager fm;
    private FragmentTransaction ft;

    private MyCommonTitle myCommonTitle;
    private OrderFragment waitToPayFragment, waitToSendFragment, waitToReceiveFragment, receivedFragment;
    private RadioGroup tab_order;
//    private int checkedId = R.id.rb_wait_to_pay;
    private String mType = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mType = (String) getIntent().getSerializableExtra("mType");
        setContentView(R.layout.activity_order_center);
        initView();
//        requestData();
    }

    /**
     * 初始化
     */
    public void initView() {
        myCommonTitle = (MyCommonTitle) findViewById(R.id.aci_mytitle);
        myCommonTitle.setTitle("确认订单");
        tab_order = (RadioGroup) findViewById(R.id.tab_my_order);

        //获得分页模块管理器
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        //加载第一个模块
        switch (Integer.valueOf(StringUtil.toString(mType,"1"))){
            case 1:
                waitToPayFragment = OrderFragment.getInstance(1);
                ft.add(R.id.order_framelayout, waitToPayFragment, "one");
                break;
            case 2:
                waitToSendFragment = OrderFragment.getInstance(2);
                ft.add(R.id.order_framelayout, waitToSendFragment, "two");

                break;
            case 3:
                waitToReceiveFragment = OrderFragment.getInstance(3);
                ft.add(R.id.order_framelayout, waitToReceiveFragment, "three");
                break;
            case 4:
                receivedFragment = OrderFragment.getInstance(4);
                ft.add(R.id.order_framelayout, receivedFragment, "four");
                break;
        }
        ft.commit();//提交
        tab_order.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ft = fm.beginTransaction();//分页管理启动,隐藏所有页面
                if (fm.findFragmentByTag("one") != null)
                    ft.hide(fm.findFragmentByTag("one"));
                if (fm.findFragmentByTag("two") != null)
                    ft.hide(fm.findFragmentByTag("two"));
                if (fm.findFragmentByTag("three") != null)
                    ft.hide(fm.findFragmentByTag("three"));
                if (fm.findFragmentByTag("four") != null)
                    ft.hide(fm.findFragmentByTag("four"));

                if (checkedId == R.id.rb_wait_to_pay) {
                    if (fm.findFragmentByTag("one") != null) {
                        ft.show(fm.findFragmentByTag("one"));
                    } else {
                        waitToPayFragment = OrderFragment.getInstance(1);
                        ft.add(R.id.order_framelayout, waitToPayFragment, "one");
                    }
                } else if (checkedId == R.id.rb_wait_to_send) {
                    if (fm.findFragmentByTag("two") != null) {
                        ft.show(fm.findFragmentByTag("two"));
                    } else {
                        waitToSendFragment = OrderFragment.getInstance(2);
                        ft.add(R.id.order_framelayout, waitToSendFragment, "two");
                    }
                } else if (checkedId == R.id.rb_wait_to_receive) {
                    if (fm.findFragmentByTag("three") != null) {
                        ft.show(fm.findFragmentByTag("three"));
                    } else {
                        waitToReceiveFragment = OrderFragment.getInstance(3);
                        ft.add(R.id.order_framelayout, waitToReceiveFragment, "three");
                    }
                } else if (checkedId == R.id.rb_received_goods) {
                    if (fm.findFragmentByTag("four") != null) {
                        ft.show(fm.findFragmentByTag("four"));
                    } else {
                        receivedFragment = OrderFragment.getInstance(4);
                        ft.add(R.id.order_framelayout, receivedFragment, "four");
                    }
                }
                ft.commit();//提交
            }
        });
    }

    /**
     * 请求数据
     */
//    public void requestData() {
//        getSupportFragmentManager().beginTransaction().add(R.id.order_framelayout, waitToPayFragment).add(R.id.order_framelayout, waitToSendFragment)
//                .add(R.id.order_framelayout, waitToReceiveFragment).add(R.id.order_framelayout, receivedFragment).show(waitToPayFragment)
//                .hide(waitToSendFragment).hide(waitToReceiveFragment).hide(receivedFragment).commit();
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 11) {
//            if (resultCode == 99) {
//                mType = (String) data.getSerializableExtra("mType");
//                initView();
//            }
//        }
//    }
}