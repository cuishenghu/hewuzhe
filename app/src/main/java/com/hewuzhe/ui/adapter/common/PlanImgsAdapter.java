package com.hewuzhe.ui.adapter.common;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hewuzhe.R;
import com.hewuzhe.model.UploadImage;
import com.hewuzhe.presenter.PlanDetailPresenter;
import com.hewuzhe.ui.adapter.base.BaseAdapter;
import com.hewuzhe.ui.cons.C;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xianguangjin on 16/1/2.
 */
public class PlanImgsAdapter extends BaseAdapter<PlanImgsAdapter.VHolder, UploadImage, PlanDetailPresenter> {


    /**
     * RecycleView的头部
     *
     * @param context
     */
    public PlanImgsAdapter(Context context) {
        super(context);
        isAddFooter = false;
    }

    /**
     * @return ItemView的LayoutId
     */
    @Override
    public int provideItemLayoutId() {
        return R.layout.item_plan_img;
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
    public void bindData(VHolder holder, int position) {
        UploadImage uploadImage = data.get(position);
        Glide.with(context)
                .load(C.BASE_URL+uploadImage.ImagePath)
                .fitCenter()
                .crossFade()
                .into(holder._Img);

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

        VHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
