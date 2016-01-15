package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hewuzhe.R;
import com.hewuzhe.model.Cate;
import com.hewuzhe.presenter.CatPresenter;
import com.hewuzhe.ui.adapter.base.BaseAdapter;
import com.hewuzhe.ui.cons.C;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xianguangjin on 15/12/25.
 */
public class CatAdapter extends BaseAdapter<CatAdapter.ViewHolder, Cate, CatPresenter> {


    private int height = 0;
    private int width = 0;
    private WindowManager windowManager;

    /**
     * RecycleView的头部
     *
     * @param context
     */
    public CatAdapter(Context context) {
        super(context);
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        width = windowManager.getDefaultDisplay().getWidth();
        height = windowManager.getDefaultDisplay().getHeight();

    }

    /**
     * @return ItemView的LayoutId
     */
    @Override
    public int provideItemLayoutId() {
        return R.layout.item_cat;
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
     * @param position 绑定数据
     */
    @Override
    public void bindData(ViewHolder holder, int position) {
        Cate cate = data.get(position);
        holder.name.setText(cate.Name);
        Glide.with(context)
                .load(C.BASE_URL + cate.ImagePath)
                .centerCrop()
                .placeholder(R.mipmap.img_bg_videio)
                .crossFade()
                .into(holder.img);

//        StaggeredGridLayoutManager.LayoutParams params = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, StringUtil.dip2px(context, 100) * (position % 100));
//        params.setFullSpan();
//        holder.itemView.setLayoutParams(params);
    }


    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_cat.xml'
     * for easy to all layout elements.
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        @Bind(R.id.img)
        ImageView img;
        @Nullable
        @Bind(R.id.name)
        TextView name;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
