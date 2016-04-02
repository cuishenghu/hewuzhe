package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
//    public static final int TYPE_HEADER = 0;
    private boolean isChecked = true;
    private int screenWidth = 10;
    private String who="";
    private int i;

    public Videos3Adapter(Context context, String who) {
        super(context);
        isAddFooter = true;
        this.who = who;
        this.screenWidth = StringUtil.getScreenWidth((BaseActivity) context);
    }
//    @Override
//    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
//        if (manager instanceof GridLayoutManager) {
//            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
//            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//                @Override
//                public int getSpanSize(int position) {
//                    i = getItemViewType(position);
//                    return getItemViewType(position) == TYPE_HEADER ? gridManager.getSpanCount():1;
//                }
//            });
//        }
//    }
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

        if (isChecked) {
            holder.ll_single_item.setVisibility(View.VISIBLE);
            holder.ll_double_item.setVisibility(View.GONE);
            getData1(holder, video);
        } else {
            holder.ll_double_item.setVisibility(View.VISIBLE);
            holder.ll_single_item.setVisibility(View.GONE);
            getData2(holder, video);
        }
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

    private void getData1(ViewHolder holder, Video video) {
        holder.tvTitle1.setText(video.Title);

        if (!StringUtil.isEmpty(who)&&who.equals("kecheng")) {
            holder.tvBlank.setVisibility(View.VISIBLE);
            holder.layKeCheng.setVisibility(View.VISIBLE);
//            holder.imgPraise.setVisibility(View.VISIBLE);
//            holder.imgCollect.setVisibility(View.VISIBLE);
//            holder.tvPraiseCount.setVisibility(View.VISIBLE);
//            holder.tvCollectCount.setVisibility(View.VISIBLE);
            holder.tvUsername1.setVisibility(View.GONE);
            holder.tvVisitSum1.setVisibility(View.GONE);
            holder.tvPraiseCount.setText(video.LikeNum+"");
            holder.tvCollectCount.setText(video.FavoriteNum+"");
        }
        holder.tvAddTime1.setText(TimeUtil.timeAgo(video.PublishTime) + "发布");
        holder.tvVisitSum1.setText(video.VisitNum + "人已浏览");
        if (StringUtil.isEmpty(video.NicName)) {
            holder.tvUsername1.setText(video.UserNicName);
        } else {
            holder.tvUsername1.setText(video.NicName);
        }

        ViewGroup.LayoutParams pageParms = holder.imgBg1.getLayoutParams();
        pageParms.height = isChecked ? screenWidth * 2 / 3 : screenWidth / 3;
        Glide.with(context)
                .load(C.BASE_URL + video.ImagePath)
                .crossFade()
                .placeholder(R.mipmap.img_bg_videio)
                .into(holder.imgBg1);

        if (video.UserId == 0) {
//            holder.imgAvatar.setImageResource(R.mipmap.ic_launcher);

            Glide.with(context)
                    .load(R.mipmap.ic_launcher)
                    .centerCrop()
                    .crossFade()
                    .transform(new GlideCircleTransform(context))
                    .placeholder(R.mipmap.img_avatar)
                    .into(holder.imgAvatar1);


        } else {
            Glide.with(context)
                    .load(C.BASE_URL + video.PhotoPath)
                    .centerCrop()
                    .crossFade()
                    .transform(new GlideCircleTransform(context))
                    .placeholder(R.mipmap.img_avatar)
                    .into(holder.imgAvatar1);
        }
    }

    private void getData2(ViewHolder holder, Video video) {
        holder.tvTitle2.setText(video.Title);
        if (!StringUtil.isEmpty(who)&&who.equals("kecheng")) {
            holder.tvBlank.setVisibility(View.VISIBLE);
            holder.layKeCheng2.setVisibility(View.VISIBLE);
//            holder.imgPraise2.setVisibility(View.VISIBLE);
//            holder.imgCollect2.setVisibility(View.VISIBLE);
//            holder.tvPraiseCount2.setVisibility(View.VISIBLE);
//            holder.tvCollectCount2.setVisibility(View.VISIBLE);
            holder.tvUsername2.setVisibility(View.GONE);
            holder.tvVisitSum2.setVisibility(View.GONE);
            holder.tvPraiseCount2.setText(video.LikeNum+"");
            holder.tvCollectCount2.setText(video.FavoriteNum+"");
        }

//        holder.tvAddTime2.setText(TimeUtil.timeAgo(video.PublishTime) + "发布");
        holder.tvVisitSum2.setText(video.VisitNum + "人已浏览");
        if (StringUtil.isEmpty(video.NicName)) {
            holder.tvUsername2.setText(video.UserNicName);
        } else {
            holder.tvUsername2.setText(video.NicName);
        }

        ViewGroup.LayoutParams pageParms = holder.imgBg2.getLayoutParams();
        pageParms.height = isChecked ? screenWidth * 2 / 3 : screenWidth / 3;
        Glide.with(context)
                .load(C.BASE_URL + video.ImagePath)
                .crossFade()
                .placeholder(R.mipmap.img_bg_videio)
                .into(holder.imgBg2);

        if (video.UserId == 0) {
//            holder.imgAvatar.setImageResource(R.mipmap.ic_launcher);

            Glide.with(context)
                    .load(R.mipmap.ic_launcher)
                    .centerCrop()
                    .crossFade()
                    .transform(new GlideCircleTransform(context))
                    .placeholder(R.mipmap.img_avatar)
                    .into(holder.imgAvatar2);


        } else {
            Glide.with(context)
                    .load(C.BASE_URL + video.PhotoPath)
                    .centerCrop()
                    .crossFade()
                    .transform(new GlideCircleTransform(context))
                    .placeholder(R.mipmap.img_avatar)
                    .into(holder.imgAvatar2);
        }
    }
//        ViewGroup.LayoutParams pageParms = holder.imgBg.getLayoutParams();
//        pageParms.height = isChecked ? screenWidth * 3 / 5 : screenWidth * 3 / 10;

//        Glide.with(context)
//                .load(C.BASE_URL + video.ImagePath)
//                .crossFade()
//                .placeholder(R.mipmap.img_default)
//                .into(holder.imgBg);
//
//        Glide.with(context)
//                .load(R.mipmap.ic_launcher)
//                .crossFade()
//                .placeholder(R.mipmap.ic_launcher)
//                .transform(new GlideCircleTransform(context))
//                .into(holder.img_avatar);
//
//        holder.tvAddTime.setText(TimeUtil.timeAgo(video.OperateTime));
//        holder.tvCollectCount.setText(video.FavoriteNum + "");
//        holder.tvPraiseCount.setText(video.LikeNum + "");
//        holder.tvRepeatCount.setText(video.RepeatNum + "");
//        holder.tvTitle.setText(video.Title);
//        holder.tvDesc.setText(video.Content);
//
//        if (video.IsFree) {
//            holder.tvIsFree.setVisibility(View.GONE);
//            holder.tvRepeatCount.setVisibility(View.VISIBLE);
//            holder.imgRepeat.setVisibility(View.VISIBLE);
//        } else {
//            holder.tvIsFree.setVisibility(View.VISIBLE);
//        }
//        if (video.Islike) {
//            holder.imgPraise.setImageResource(R.mipmap.icon_praise_focus);
//        } else {
//            holder.imgPraise.setImageResource(R.mipmap.icon_praise);
//        }
//
//        if (video.IsFavorite) {
//            holder.imgCollect.setImageResource(R.mipmap.icon_collect_focus);
//        } else {
//            holder.imgCollect.setImageResource(R.mipmap.icon_collect);
//        }
//        holder.imgRepeat.setVisibility(View.GONE);
//        holder.tvRepeatCount.setVisibility(View.GONE);
//
////        if (video.IsRepeat) {
////            holder.imgRepeat.setImageResource(R.mipmap.icon_share);
////        } else {
////            holder.imgRepeat.setImageResource(R.mipmap.icon_share_focus);
////        }
//
//        holder._CbPlan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    if (!Videos3Adapter.this.checkedList.contains(video)) {
//                        video.isChecked = true;
//                        Videos3Adapter.this.checkedList.add(video);
//                    }
//                } else {
//                    if (Videos3Adapter.this.checkedList.contains(video)) {
//                        video.isChecked = false;
//                        Videos3Adapter.this.checkedList.remove(video);
//                    }
//                }
//            }
//        });
//
//        if (this.isNeedShow) {
//            holder._CbPlan.setVisibility(View.VISIBLE);
//        } else {
//            holder._CbPlan.setVisibility(View.GONE);
//        }
//    }


    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_video_2.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        @Bind(R.id.img_bg1)//视频封面图片
                ImageView imgBg1;
        @Nullable
        @Bind(R.id.img_bg2)//视频封面图片
                ImageView imgBg2;
        @Nullable
        @Bind(R.id.tv_title1)//视频标题
                TextView tvTitle1;
        @Nullable
        @Bind(R.id.tv_title2)//视频标题
                TextView tvTitle2;
        @Nullable
        @Bind(R.id.img_avatar1)//发布者头像
                ImageView imgAvatar1;
        @Nullable
        @Bind(R.id.img_avatar2)//发布者头像
                ImageView imgAvatar2;
        @Nullable
        @Bind(R.id.tv_username1)//发布者昵称
                TextView tvUsername1;
        @Nullable
        @Bind(R.id.tv_username2)//发布者昵称
                TextView tvUsername2;

        @Nullable
        @Bind(R.id.tv_add_time1)//发布时间
                TextView tvAddTime1;
        @Nullable
        @Bind(R.id.tv_add_time2)//发布时间
                TextView tvAddTime2;
        @Nullable
        @Bind(R.id.tv_visit_sum1)//浏览量
                TextView tvVisitSum1;
        @Nullable
        @Bind(R.id.tv_visit_sum2)//浏览量
                TextView tvVisitSum2;
        @Nullable
        @Bind(R.id.ll_single_item)//单列显示布局
                RelativeLayout ll_single_item;
        @Nullable
        @Bind(R.id.ll_double_item)//双列显示布局
                RelativeLayout ll_double_item;


        @Nullable
        @Bind(R.id.tv_is_free)
        TextView tvIsFree;

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
        @Bind(R.id.img_praise2)
        ImageView imgPraise2;
        @Nullable
        @Bind(R.id.tv_praise_count2)
        TextView tvPraiseCount2;
        @Nullable
        @Bind(R.id.img_collect2)
        ImageView imgCollect2;
        @Nullable
        @Bind(R.id.tv_collect_count2)
        TextView tvCollectCount2;
        @Nullable
        @Bind(R.id.img_repeat)
        ImageView imgRepeat;
        @Nullable
        @Bind(R.id.tv_repeat_count)
        TextView tvRepeatCount;
        @Nullable
        @Bind(R.id.cb_plan)
        CheckBox _CbPlan;
        @Nullable
        @Bind(R.id.tv_blank)
        TextView tvBlank;
        @Nullable
        @Bind(R.id.ll_kecheng)
        LinearLayout layKeCheng;
        @Nullable
        @Bind(R.id.ll_kecheng2)
        LinearLayout layKeCheng2;

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
