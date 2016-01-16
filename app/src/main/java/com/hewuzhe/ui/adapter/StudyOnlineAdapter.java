package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hewuzhe.R;
import com.hewuzhe.model.StudyOnlineCatItem;
import com.hewuzhe.ui.adapter.base.BeeBaseAdapter;
import com.hewuzhe.ui.adapter.base.BeeCellHolder;
import com.hewuzhe.ui.cons.C;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xianguangjin on 16/1/16.
 */
public class StudyOnlineAdapter extends BeeBaseAdapter<StudyOnlineAdapter.VHolder, StudyOnlineCatItem> {
    public StudyOnlineAdapter(Context c, ArrayList dataList) {
        super(c, dataList);
    }

    @Override
    protected VHolder createCellHolder(View cellView) {
        return new VHolder(cellView);
    }

    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, VHolder h) {
        StudyOnlineCatItem item = dataList.get(position);
        h._TvName.setText(item.Name);

        Glide.with(mContext)
                .load(C.BASE_URL + item.ImagePath)
                .centerCrop()
                .placeholder(R.mipmap.img_bg_videio)
                .into(h._Img);

        return null;
    }

    @Override
    public View createCellView() {
        return mInflater.inflate(R.layout.item_study_one, null);
    }


    class VHolder extends BeeCellHolder {
        @Nullable
        @Bind(R.id.img)
        ImageView _Img;
        @Nullable
        @Bind(R.id.tv_name)
        TextView _TvName;

        VHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
