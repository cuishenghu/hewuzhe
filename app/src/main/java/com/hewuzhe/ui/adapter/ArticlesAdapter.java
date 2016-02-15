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
import com.hewuzhe.model.Article;
import com.hewuzhe.presenter.ArticlesPresenter;
import com.hewuzhe.ui.adapter.base.BaseAdapter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.TimeUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xianguangjin on 15/12/31.
 */
public class ArticlesAdapter extends BaseAdapter<ArticlesAdapter.VHolder, Article, ArticlesPresenter> {
    /**
     * RecycleView的头部
     *
     * @param context
     */
    public ArticlesAdapter(Context context) {
        super(context);
    }

    /**
     * @return ItemView的LayoutId
     */
    @Override
    public int provideItemLayoutId() {
        return R.layout.item_article;
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

        Article article = data.get(position);
        holder._TvAddTime.setText(TimeUtil.timeAgo(article.PublishTime));
        holder._TvTitle.setText(article.Title);
        holder._TvPraise.setText(article.LikeNum + "");
        holder._TvCollect.setText(article.FavoriteNum + "");
        holder._TvContent.setText(article.Content);

        if (article.IsFavorite) {
            holder._ImgCollect.setImageResource(R.mipmap.icon_collect_focus);
        } else {
            holder._ImgCollect.setImageResource(R.mipmap.icon_collect_gray);
        }


        Glide.with(context)
                .load(C.BASE_URL + article.ImagePath)
                .centerCrop()
                .crossFade()
                .placeholder(R.mipmap.img_bg_videio)
                .into(holder._Img);
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_article.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class VHolder extends RecyclerView.ViewHolder {
        @Nullable
        @Bind(R.id.img)
        ImageView _Img;
        @Nullable
        @Bind(R.id.tv_title)
        TextView _TvTitle;
        @Nullable
        @Bind(R.id.tv_add_time)
        TextView _TvAddTime;
        @Nullable
        @Bind(R.id.img_collect)
        ImageView _ImgCollect;
        @Nullable
        @Bind(R.id.tv_collect)
        TextView _TvCollect;
        @Nullable
        @Bind(R.id.img_praise)
        ImageView _ImgPraise;
        @Nullable
        @Bind(R.id.tv_praise)
        TextView _TvPraise;
        @Nullable
        @Bind(R.id.tv_conent)
        TextView _TvContent;

        VHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
