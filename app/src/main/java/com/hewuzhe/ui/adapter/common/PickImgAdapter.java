package com.hewuzhe.ui.adapter.common;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hewuzhe.R;
import com.hewuzhe.model.common.PickImg;
import com.hewuzhe.presenter.PublishConditionPresenter;
import com.hewuzhe.ui.adapter.base.BaseAdapter;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xianguangjin on 16/1/2.
 */
public class PickImgAdapter extends BaseAdapter<PickImgAdapter.VHolder, PickImg, PublishConditionPresenter> {


    public PickImgAdapter(Context context, PublishConditionPresenter publishConditionPresenter, boolean isAddFooter) {
        super(context, publishConditionPresenter, isAddFooter);
        this.isAddFooter = false;
    }

    /**
     * @return ItemView的LayoutId
     */
    @Override
    public int provideItemLayoutId() {
        return R.layout.item_pick_img;
    }

    /**
     * @param parent
     * @param viewType
     * @param view
     * @return 创建ViewHolder
     */
    @Override
    public VHolder createVH(ViewGroup parent, int viewType, View view) {
        return new VHolder(view);
    }

    /**
     * @param holder
     * @param position 绑定数据
     */
    @Override
    public void bindData(VHolder holder, final int position) {
        PickImg pickImg = data.get(position);
        if (pickImg.status == PickImg.STATUS_EMPTY) {
            holder._Img.setImageResource(R.mipmap.img_add_pic);
            holder._ImgDel.setVisibility(View.GONE);
            holder._Img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    _presenter.showPickDialog(view);
                }
            });
        } else {
            Glide.with(context)
                    .load(Uri.fromFile(new File(pickImg.filePath)))
                    .fitCenter()
                    .crossFade()
                    .into(holder._Img);

            holder._ImgDel.setVisibility(View.VISIBLE);
            holder._ImgDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    data.remove(position);
                    PickImgAdapter.this.notifyItemRemoved(position);
                }
            });
        }

    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_pick_img.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    class VHolder extends RecyclerView.ViewHolder {
        @Nullable
        @Bind(R.id.img)
        ImageView _Img;

        @Nullable
        @Bind(R.id.img_del)
        ImageView _ImgDel;

        VHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
