package com.hewuzhe.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hewuzhe.R;

/**
 * Created by xianguangjin on 16/1/2.
 */
public class Line extends TextView {

    public Line(Context context) {
        super(context);
    }

    public Line(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Line(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    void initView() {
        this.setBackgroundResource(R.color.colorLine);
        this.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,2));
    }
}
