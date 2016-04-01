package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hewuzhe.R;
import com.hewuzhe.model.New;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.adapter.base.BaseAdapter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.TimeUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/3/24 0024.
 */
public class SaishiAdapter extends BaseAdapter<SaishiAdapter.ViewHolder, New, BasePresenterImp> {
    public int i;
    public static final int TYPE_HEADER = 0;

    public SaishiAdapter(Context context, BasePresenterImp basePresenterImp, View header) {
        super(context, basePresenterImp, header);
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    i = getItemViewType(position);
                    return getItemViewType(position) == TYPE_HEADER ? gridManager.getSpanCount() : 1;
                }
            });
        }

    }

    /**
     * @return ItemView的LayoutId
     */
    @Override
    public int provideItemLayoutId() {
        return R.layout.item_new_zixun;
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
    public void bindData(final ViewHolder holder, int position) {
        New news = data.get(position);
        getData(holder, news);
    }

    private void getData(ViewHolder holder, New news) {
        holder.tvPubTime.setText(TimeUtil.timeAgo(news.PublishTime) + "发布");
        holder.tvContent.setText(news.Title);
        holder.tvNewsCome.setText("来源：" + news.MessageCame);
        holder.tvVisitNum.setText(news.VisitNum + "");

        ViewGroup.LayoutParams pageParms = holder.imgBg.getLayoutParams();
//        pageParms.height = isChecked ? screenWidth * 2 / 3 : screenWidth / 3;
        Glide.with(context)
                .load(C.BASE_URL + news.ImagePath)
                .crossFade()
                .placeholder(R.mipmap.jianghugushi2)
                .into(holder.imgBg);
    }


    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'video_item.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        @Bind(R.id.img_news_image)//封面图片
                ImageView imgBg;
        @Nullable
        @Bind(R.id.tv_news_publish_time)//发布时间
                TextView tvPubTime;
        @Nullable
        @Bind(R.id.tv_news_content)//内容
                TextView tvContent;
        @Nullable
        @Bind(R.id.tv_news_come_from)//来源
                TextView tvNewsCome;
        @Nullable
        @Bind(R.id.tv_news_visit_num)//访问数
                TextView tvVisitNum;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}