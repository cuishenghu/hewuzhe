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
import com.hewuzhe.model.Video;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.adapter.base.BaseAdapter;
import com.hewuzhe.ui.base.BaseActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widget.GlideCircleTransform;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.utils.TimeUtil;

import java.util.LinkedList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xianguangjin on 15/12/22.
 */
public class Videos3Adapter extends BaseAdapter<Videos3Adapter.ViewHolder, Video, BasePresenterImp> {

    private boolean isChecked = false;
    private int screenWidth = 10;

    public Videos3Adapter(Context context) {
        super(context);
        isAddFooter = true;
        this.screenWidth = StringUtil.getScreenWidth((BaseActivity) context);
    }

    /**
     * 是否显示checkBox
     */
    private boolean isNeedShow = false;
    private LinkedList<Video> checkedList = new LinkedList<>();

    /**
     * @return ItemView的LayoutId
     */
    @Override
    public int provideItemLayoutId() {
        return R.layout.item_video_3;
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
        final Video video = data.get(position);
//        ViewGroup.LayoutParams pageParms = holder.imgBg.getLayoutParams();
//        pageParms.height = isChecked ? screenWidth * 3 / 5 : screenWidth * 3 / 10;

        Glide.with(context)
                .load(C.BASE_URL + video.ImagePath)
                .crossFade()
                .placeholder(R.mipmap.img_default)
                .into(holder.imgBg);

        Glide.with(context)
                .load(R.mipmap.ic_launcher)
                .crossFade()
                .placeholder(R.mipmap.ic_launcher)
                .transform(new GlideCircleTransform(context))
                .into(holder.img_avatar);

        holder.tvAddTime.setText(TimeUtil.timeAgo(video.OperateTime));
        holder.tvCollectCount.setText(video.FavoriteNum + "");
        holder.tvPraiseCount.setText(video.LikeNum + "");
        holder.tvRepeatCount.setText(video.RepeatNum + "");
        holder.tvTitle.setText(video.Title);
        holder.tvDesc.setText(video.Content);

        if (video.IsFree) {
            holder.tvIsFree.setVisibility(View.GONE);
            holder.tvRepeatCount.setVisibility(View.VISIBLE);
            holder.imgRepeat.setVisibility(View.VISIBLE);
        } else {
            holder.tvIsFree.setVisibility(View.VISIBLE);
        }
        if (video.Islike) {
            holder.imgPraise.setImageResource(R.mipmap.icon_praise_focus);
        } else {
            holder.imgPraise.setImageResource(R.mipmap.icon_praise);
        }

        if (video.IsFavorite) {
            holder.imgCollect.setImageResource(R.mipmap.icon_collect_focus);
        } else {
            holder.imgCollect.setImageResource(R.mipmap.icon_collect);
        }
        holder.imgRepeat.setVisibility(View.GONE);

//        if (video.IsRepeat) {
//            holder.imgRepeat.setImageResource(R.mipmap.icon_share);
//        } else {
//            holder.imgRepeat.setImageResource(R.mipmap.icon_share_focus);
//        }

        holder._CbPlan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (!Videos3Adapter.this.checkedList.contains(video)) {
                        video.isChecked = true;
                        Videos3Adapter.this.checkedList.add(video);
                    }
                } else {
                    if (Videos3Adapter.this.checkedList.contains(video)) {
                        video.isChecked = false;
                        Videos3Adapter.this.checkedList.remove(video);
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
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_video_2.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        @Bind(R.id.img_bg)
        ImageView imgBg;
        @Nullable
        @Bind(R.id.img_avatar)
        ImageView img_avatar;
        @Nullable
        @Bind(R.id.tv_add_time)
        TextView tvAddTime;
        @Nullable
        @Bind(R.id.tv_is_free)
        TextView tvIsFree;
        @Nullable
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Nullable
        @Bind(R.id.tv_desc)
        TextView tvDesc;
        @Nullable
        @Bind(R.id.img_praise)
        ImageView imgPraise;
        @Nullable
        @Bind(R.id.tv_praise_count)
        TextView tvPraiseCount;
        @Nullable
        @Bind(R.id.img_collect)
        ImageView imgCollect;
        @Nullable
        @Bind(R.id.tv_collect_count)
        TextView tvCollectCount;
        @Nullable
        @Bind(R.id.img_repeat)
        ImageView imgRepeat;
        @Nullable
        @Bind(R.id.tv_repeat_count)
        TextView tvRepeatCount;
        @Nullable
        @Bind(R.id.cb_plan)
        CheckBox _CbPlan;

        ViewHolder(View view) {
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
    public LinkedList<Video> getCheckedList() {
        return this.checkedList;
    }


    public void changeViewHeight(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
