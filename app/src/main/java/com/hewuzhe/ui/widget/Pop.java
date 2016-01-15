package com.hewuzhe.ui.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hewuzhe.R;

import cn.xm.weidongjian.popuphelper.PopupWindowHelper;

/**
 * Created by xianguangjin on 16/1/6.
 */
public class Pop {
    private Context _Context;
    private PopupWindowHelper popupWindowHelper;
    private TextView _TvItemOne;
    private TextView _TvItemTwo;
    private TextView _TvItemThree;

    public Pop(Context context) {
        _Context = context;

    }


    public void show(View anchor, View.OnClickListener listenerOne, View.OnClickListener listenerTwo, View.OnClickListener listenerThree) {
        if (popupWindowHelper == null) {
            View view = LayoutInflater.from(_Context).inflate(R.layout.pop_three_item, null);
            _TvItemOne = (TextView) view.findViewById(R.id.tv_item_one);
            _TvItemTwo = (TextView) view.findViewById(R.id.tv_item_two);
            _TvItemThree = (TextView) view.findViewById(R.id.tv_item_three);
            _TvItemOne.setOnClickListener(listenerOne);
            _TvItemOne.setOnClickListener(listenerTwo);
            _TvItemOne.setOnClickListener(listenerThree);
            popupWindowHelper = new PopupWindowHelper(view);
        }
        popupWindowHelper.showAsDropDown(anchor);

    }

    public void dismiss() {
        if (popupWindowHelper != null) {
            popupWindowHelper.dismiss();
        }
    }


}
