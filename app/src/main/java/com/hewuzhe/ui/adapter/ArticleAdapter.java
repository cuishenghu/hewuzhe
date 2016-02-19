package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hewuzhe.R;
import com.hewuzhe.model.ArticleCollection;
import com.hewuzhe.presenter.ArticlesPresenter;
import com.hewuzhe.ui.adapter.base.BaseAdapter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.utils.TimeUtil;

import java.util.LinkedList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xianguangjin on 15/12/25.
 */
public class ArticleAdapter extends BaseAdapter<ArticleAdapter.VHolder, ArticleCollection, ArticlesPresenter> {

    /**
     * 是否显示checkBox
     */
    private boolean isNeedShow = false;
    private LinkedList<ArticleCollection> checkedList = new LinkedList<>();

    /**
     * @param context
     */
    public ArticleAdapter(Context context) {
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

        final ArticleCollection articleCollection = data.get(position);

        if (!StringUtil.isEmpty(articleCollection.OperateTime)) {
            holder._TvAddTime.setText(TimeUtil.timeAgo(articleCollection.OperateTime));
        }

        Glide.with(context)
                .load(C.BASE_URL + articleCollection.ImagePath)
                .fitCenter()
                .crossFade()
                .placeholder(R.mipmap.img_bg_videio)
                .into(holder._Img);
        holder._TvTitle.setText(articleCollection.Title);
        holder._TvConent.setText(articleCollection.Content);
//        holder._TvPraise.setText(articleCollection.CommentNum + "");
        holder._TvCollect.setText(articleCollection.FavoriteNum + "");
        if (articleCollection.IsFavorite) {
            holder._ImgCollect.setImageResource(R.mipmap.icon_collect_focus);
        } else {
            holder._ImgCollect.setImageResource(R.mipmap.icon_collect_gray);

        }

        if (articleCollection.IsFavorite) {
            holder._ImgCollect.setImageResource(R.mipmap.icon_collect_focus);
        } else {
            holder._ImgCollect.setImageResource(R.mipmap.icon_collect_gray);
        }

        holder._CbPlan.setChecked(articleCollection.isChecked);

        holder._CbPlan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (!ArticleAdapter.this.checkedList.contains(articleCollection)) {
                        articleCollection.isChecked = true;
                        ArticleAdapter.this.checkedList.add(articleCollection);
                    }
                } else {
                    if (ArticleAdapter.this.checkedList.contains(articleCollection)) {
                        articleCollection.isChecked = false;
                        ArticleAdapter.this.checkedList.remove(articleCollection);
                    }
                }
            }
        });

        if (this.isNeedShow) {
            holder._CbPlan.setVisibility(View.VISIBLE);
        } else {
            holder._CbPlan.setVisibility(View.GONE);
        }

    }


    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_article.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    class VHolder extends RecyclerView.ViewHolder {
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
        @Bind(R.id.tv_conent)
        TextView _TvConent;
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
        @Bind(R.id.cb_plan)
        CheckBox _CbPlan;

        VHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    /**
     * 设置是否显示checkBox
     *
     * @param isNeedShow
     */
    public void showCheck(boolean isNeedShow) {
        this.isNeedShow = isNeedShow;

        if (!isNeedShow) {
            for (ArticleCollection articleCollection : data) {
                articleCollection.isChecked = false;
            }
        }

        this.notifyDataSetChanged();
    }

    /**
     * 获取当前编辑状态
     *
     * @return
     */
    public boolean getCheckShowStatus() {
        return this.isNeedShow;
    }

    /**
     * 获取选中的Item
     *
     * @return
     */
    public LinkedList<ArticleCollection> getCheckedList() {
        return this.checkedList;
    }


}
