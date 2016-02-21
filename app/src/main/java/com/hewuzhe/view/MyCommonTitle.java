package com.hewuzhe.view;

import com.hewuzhe.R;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyCommonTitle extends RelativeLayout {
    private LinearLayout ll_back_btn;
    private ImageView img_back;
    private TextView backEdit;
    private TextView titleText;
    private TextView subtitleText;
    private TextView title_edit;
    private ImageView share_btn;

    public MyCommonTitle(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.activity_mycommontitle, this);
        ll_back_btn = (LinearLayout) findViewById(R.id.ll_back_btn);// 后退
        img_back = (ImageView) findViewById(R.id.aci_back_btn);// 后退
        backEdit = (TextView) findViewById(R.id.aci_back_textview);
        title_edit = (TextView) findViewById(R.id.aci_edit_textview);// 编辑
        share_btn = (ImageView) findViewById(R.id.aci_share_btn);// 分享
        /**
         * 点击返回当前关闭
         */
        ll_back_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });
    }

    /**
     * 监听事件不为空则相应的控件显示
     *
     * @param backEditListener
     * @param editListener
     * @param shareListener
     */
    public void setListener(OnClickListener backEditListener, OnClickListener editListener,
                            OnClickListener shareListener) {
        if (backEditListener != null) {
            backEdit.setVisibility(View.VISIBLE);
            backEdit.setOnClickListener(backEditListener);
        }
        if (editListener != null) {
            title_edit.setVisibility(View.VISIBLE);
            title_edit.setOnClickListener(editListener);
        }
        if (shareListener != null) {
            share_btn.setVisibility(View.VISIBLE);
            share_btn.setOnClickListener(shareListener);
        }
    }

    public void setBackTitle(String backtitle) {
        backEdit = (TextView) findViewById(R.id.aci_back_textview);// 返回文字显示
        backEdit.setText(backtitle);
    }
    public TextView getBackTitle() {
        return backEdit;
    }

    public void setTitle(String title) {
        titleText = (TextView) findViewById(R.id.aci_title_textview);// 标题
        titleText.setText(title);
    }

    public TextView getTitle() {
        return titleText;
    }

    public void setSubTitle(String subtitle) {
        subtitleText = (TextView) findViewById(R.id.aci_subtitle_textview);// 副标题
        subtitleText.setVisibility(View.VISIBLE);
        subtitleText.setText(subtitle);
    }
    public TextView getSubTitle() {
        return subtitleText;
    }

    public void setTextEdit(String editText) {
        title_edit = (TextView) findViewById(R.id.aci_edit_textview);//编辑
        title_edit.setVisibility(View.VISIBLE);
        title_edit.setText(editText);
    }
    public TextView getTextEdit() {
        return title_edit;
    }
    /**
     * INVISIBLE是控件不可见,但其位置不被占用,其他控件位置不变 GONE 则是控件不可见,但其位置会被其他控件位置改变而占用
     *
     * @param flag
     */
    public void setBackVisible(Boolean flag) {
        if (flag) {
            ll_back_btn.setVisibility(View.VISIBLE);
        } else {
            ll_back_btn.setVisibility(View.INVISIBLE);
        }
    }
}
