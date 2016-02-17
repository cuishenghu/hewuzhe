package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hewuzhe.R;
import com.hewuzhe.model.Dojo;
import com.hewuzhe.presenter.DojoRecommendPresenter;
import com.hewuzhe.ui.adapter.base.BaseAdapter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.StringUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xianguangjin on 15/12/25.
 */
public class DojoRecommendAdapter extends BaseAdapter<DojoRecommendAdapter.ViewHolder, Dojo, DojoRecommendPresenter> {

    public DojoRecommendAdapter(Context context) {
        super(context);
    }

    /**
     * @return ItemView的LayoutId
     */
    @Override
    public int provideItemLayoutId() {
        return R.layout.dojo_item;
    }

    /**
     * @param parent
     * @param viewType
     * @param view
     * @return 创建ViewHolder
     */
    @Override
    public ViewHolder createVH(ViewGroup parent, int viewType, View view) {
        return new ViewHolder(view);
    }

    /**
     * @param holder
     * @param position
     */
    @Override
    public void bindData(ViewHolder holder, int position) {
        Dojo dojo = data.get(position);

        holder.tvDesc.setText(dojo.Content);
        holder.tvLoc.setText(getAddress(dojo.Address));
        holder.tvName.setText(dojo.Title);
        holder.tvCall.setText(dojo.TelePhone);
        Glide.with(context)
                .load(C.BASE_URL + dojo.ImagePath)
                .centerCrop()
                .crossFade()
                .placeholder(R.mipmap.img_bg)
                .into(holder.img);

    }

    private String getAddress(String address) {

        if (!StringUtil.isEmpty(address)) {
            int index = address.indexOf(",");
            address = address.substring(index + 1);
            index = address.indexOf(",");
            address = address.substring(index + 1);
            index = address.indexOf(",");

            return address.substring(0, index);
        } else {
            return "";
        }
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'dojo_item.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        @Bind(R.id.img)
        ImageView img;
        @Nullable
        @Bind(R.id.tv_name)
        TextView tvName;
        @Nullable
        @Bind(R.id.tv_desc)
        TextView tvDesc;
        @Nullable
        @Bind(R.id.tv_loc)
        TextView tvLoc;
        @Nullable
        @Bind(R.id.tv_call)
        TextView tvCall;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
