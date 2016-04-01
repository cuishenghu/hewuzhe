package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hewuzhe.R;
import com.hewuzhe.model.PrivateTrainer;
import com.hewuzhe.model.PrivateTrainerList;
import com.hewuzhe.presenter.PrivateTrainerListPresenter;
import com.hewuzhe.ui.adapter.base.BaseAdapter;
import com.hewuzhe.ui.adapter.base.BaseNoMoreAdapter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.StringUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zycom on 2016/3/16.
 */
public class PrivateTrainerListAdapter extends BaseNoMoreAdapter<PrivateTrainerListAdapter.ViewHolder, PrivateTrainerList, PrivateTrainerListPresenter> {
    public PrivateTrainerListAdapter(Context context, PrivateTrainerListPresenter privateTrainerListPresenter) {
        super(context, privateTrainerListPresenter);
    }

    @Override
    public int provideItemLayoutId() {
        return R.layout.private_item;
    }

    @Override
    public ViewHolder createVH(ViewGroup parent, int viewType, View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindData(ViewHolder holder, int position) {
        PrivateTrainerList privateTrainerList = data.get(position);

        //设置imageview宽高
        WindowManager manager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        int width =display.getWidth();
        ViewGroup.LayoutParams para;
        para = holder.pt_photo.getLayoutParams();

        para.height = (width- StringUtil.dip2px(context, 30))/2-StringUtil.dip2px(context, 50);
        holder.pt_photo.setLayoutParams(para);
        Glide.with(context)
                .load(C.BASE_URL + privateTrainerList.PhotoPath)
                .centerCrop()
                .crossFade()
                .placeholder(R.mipmap.img_bg)
                .into(holder.pt_photo);
        holder.pt_name.setText(privateTrainerList.NicName);
        holder.pt_juli.setText(privateTrainerList.Distance+"");

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @Nullable @Bind(R.id.pt_photo) ImageView pt_photo;
        @Nullable @Bind(R.id.pt_work) TextView pt_work;
        @Nullable @Bind(R.id.pt_name) TextView pt_name;
        @Nullable @Bind(R.id.pt_juli) TextView pt_juli;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
