package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hewuzhe.R;
import com.hewuzhe.model.Comment;
import com.hewuzhe.model.FriendCondition;
import com.hewuzhe.model.Pic;
import com.hewuzhe.presenter.adapter.ConditionPresenter;
import com.hewuzhe.ui.activity.PicsActivity;
import com.hewuzhe.ui.adapter.base.BaseAdapter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widget.GlideCircleTransform;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.utils.TimeUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xianguangjin on 15/12/31.
 */
public class GroupConditionAdapter extends BaseAdapter<GroupConditionAdapter.VHolder, FriendCondition, ConditionPresenter> {
    /**
     * 每行有几张图片
     */
    private int count = 3;
    public String _NickName = "";
    public String _PhotoPath = "";

    public GroupConditionAdapter(Context context, ConditionPresenter conditionPresenter) {
        super(context, conditionPresenter);
    }

    /**
     * @return ItemView的LayoutId
     */
    @Override
    public int provideItemLayoutId() {
        return R.layout.item_friend_condition;
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
    public void bindData(final VHolder holder, final int position) {

        final FriendCondition condition = data.get(position);


        holder._TvUsername.setText(this._NickName);

        holder._TvContent.setText(condition.Content);
        holder._TvAddTime.setText(TimeUtil.timeAgo(condition.PublishTime));
        holder._TvPraise.setText(condition.LikeNum + "");

        Glide.with(context)
                .load(C.BASE_URL + this._PhotoPath)
                .fitCenter()
                .crossFade()
                .transform(new GlideCircleTransform(context))
                .placeholder(R.mipmap.img_avatar)
                .into(holder._Img);

        if (condition.IsLike) {
            holder._ImgPraise.setImageResource(R.mipmap.icon_praise_focus);
            holder._ImgPraise.setOnClickListener(null);
        } else {
            holder._ImgPraise.setImageResource(R.mipmap.icon_praise);
            holder._ImgPraise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    _presenter.collectAndOther(condition.Id, ConditionPresenter.PRAISE, view, position);
                }
            });
        }

        holder._TvCommentCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _presenter.showCommentInput(condition.Id, position, view);
            }
        });

        holder._LayPics.removeAllViews();//先清除之前的views
        holder._LayPics.setVisibility(View.GONE);
        if (condition.PicList.size() > 0) {
            holder._LayPics.setVisibility(View.VISIBLE);
            int pageCount = condition.PicList.size() / count + 1;
            for (int i = 0; i < pageCount; i++) {
                addImg(holder, condition, i);
            }
        }

        if (StringUtil.isEmpty(condition.VideoPath)) {
            holder._LayImg.setVisibility(View.GONE);

        } else {
            holder._LayImg.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(C.BASE_URL + condition.ImagePath)
                    .centerCrop()
                    .crossFade()
                    .into(holder._ImgVideo);
            holder._LayImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

        holder._TvShowAll.setText(condition.isShowingAll ? "关闭全部评论" : "显示全部评论");

        holder._TvShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                condition.isShowingAll = condition.isShowingAll ? false : true;
                holder._TvShowAll.setText(condition.isShowingAll ? "关闭全部评论" : "显示全部评论");
                GroupConditionAdapter.this.notifyItemChanged(position);
            }
        });

        holder._LayComment.removeViews(1, holder._LayComment.getChildCount() - 1);
        holder._LayComment.setVisibility(View.GONE);

        if (condition.ComList.size() > 0) {
            holder._LayComment.setVisibility(View.VISIBLE);
            if (condition.ComList.size() > 3) {
                holder._TvShowAll.setVisibility(View.VISIBLE);
                if (condition.isShowingAll) {
                    //显示全部
                    for (int i = 0; i < condition.ComList.size(); i++) {
                        final Comment comment = condition.ComList.get(i);
                        View view = _inflater.inflate(R.layout.item_coment_child, null);
                        holder._LayComment.addView(view);

                        TextView tvUserCommenter = (TextView) view.findViewById(R.id.tv_user_commenter);
                        TextView tvUserCommented = (TextView) view.findViewById(R.id.tv_user_commented);
                        TextView tvConent = (TextView) view.findViewById(R.id.tv_conent);

                        tvUserCommenter.setText(comment.NicName);
                        tvUserCommented.setText(comment.CommentedNicName);
                        tvConent.setText("：" + comment.Content);

                        if (comment.ParentId == 0) {
                            tvUserCommented.setVisibility(View.GONE);
                            TextView tv_reply = (TextView) view.findViewById(R.id.tv_reply);
                            tv_reply.setVisibility(View.GONE);
                        }

                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (comment.CommenterId == new SessionUtil(context).getUserId()) {
                                    _presenter.deleteComment(comment.Id, view);

                                } else {
                                    _presenter.showReplyInput(comment.Id, comment.NicName, comment.Id, position, view);
                                }
                            }
                        });
                    }


                } else {

                    for (int i = 0; i < 3; i++) {
                        final Comment comment = condition.ComList.get(i);
                        View view = _inflater.inflate(R.layout.item_coment_child, null);
                        holder._LayComment.addView(view);

                        TextView tvUserCommenter = (TextView) view.findViewById(R.id.tv_user_commenter);
                        TextView tvUserCommented = (TextView) view.findViewById(R.id.tv_user_commented);
                        TextView tvConent = (TextView) view.findViewById(R.id.tv_conent);

                        tvUserCommenter.setText(comment.NicName);
                        tvUserCommented.setText(comment.CommentedNicName);
                        tvConent.setText("：" + comment.Content);

                        if (comment.ParentId == 0) {
                            tvUserCommented.setVisibility(View.GONE);
                            TextView tv_reply = (TextView) view.findViewById(R.id.tv_reply);
                            tv_reply.setVisibility(View.GONE);
                        }

                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (comment.CommenterId == new SessionUtil(context).getUserId()) {
                                    _presenter.deleteComment(comment.Id, view);

                                } else {
                                    _presenter.showReplyInput(comment.Id, comment.NicName, comment.Id, position, view);
                                }
                            }
                        });
                    }

                }
            } else {
                holder._TvShowAll.setVisibility(View.GONE);
                for (int i = 0; i < condition.ComList.size(); i++) {
                    final Comment comment = condition.ComList.get(i);
                    View view = _inflater.inflate(R.layout.item_coment_child, null);
                    holder._LayComment.addView(view);

                    TextView tvUserCommenter = (TextView) view.findViewById(R.id.tv_user_commenter);
                    TextView tvUserCommented = (TextView) view.findViewById(R.id.tv_user_commented);
                    TextView tvConent = (TextView) view.findViewById(R.id.tv_conent);

                    tvUserCommenter.setText(comment.NicName);
                    tvUserCommented.setText(comment.CommentedNicName);
                    tvConent.setText("：" + comment.Content);

                    if (comment.ParentId == 0) {
                        tvUserCommented.setVisibility(View.GONE);
                        TextView tv_reply = (TextView) view.findViewById(R.id.tv_reply);
                        tv_reply.setVisibility(View.GONE);
                    }


                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (comment.CommenterId == new SessionUtil(context).getUserId()) {
                                _presenter.deleteComment(comment.Id, view);

                            } else {
                                _presenter.showReplyInput(comment.Id, comment.NicName, comment.Id, position, view);
                            }
                        }
                    });
                }
            }

        }

    }

    /**
     * 添加图片
     *
     * @param holder
     * @param condition
     * @param page
     */
    private void addImg(VHolder holder, final FriendCondition condition, final int page) {
        LinearLayout linearLayout = (LinearLayout) _inflater.inflate(R.layout.component_comment_lay, null);
        holder._LayPics.addView(linearLayout);

        /**
         * 要添加的图片
         * */
        List<Pic> subList = condition.PicList.subList(page * count, page * count + count >= condition.PicList.size() ? condition.PicList.size() : page * count + count);
        for (int i = 0; i < subList.size(); i++) {
            Pic pic = subList.get(i);

            LinearLayout imgWraper = (LinearLayout) _inflater.inflate(R.layout.component_comment_pic, null);
            linearLayout.addView(imgWraper);

            Glide.with(context)
                    .load(C.BASE_URL + pic.ImagePath)
                    .fitCenter()
                    .crossFade()
                    .into((ImageView) imgWraper.findViewById(R.id.img));

            final int finalI = i;
            imgWraper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /**
                     * 到图片集页
                     * */
                    for (Pic pic1 : condition.PicList) {
                        pic1.PictureUrl = pic1.ImagePath;
                    }


                    Intent intent = new Intent(context, PicsActivity.class);
                    intent.putExtra("data", new Bun().putInt("pos", finalI + (page * count)).
                            putString("pics", new Gson().toJson(condition.PicList)).ok());
                    context.startActivity(intent);
                }
            });
        }

    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_friend_condition.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class VHolder extends RecyclerView.ViewHolder {
        @Nullable
        @Bind(R.id.img)
        ImageView _Img;
        @Nullable
        @Bind(R.id.tv_username)
        TextView _TvUsername;
        @Nullable
        @Bind(R.id.tv_content)
        TextView _TvContent;
        @Nullable
        @Bind(R.id.tv_add_time)
        TextView _TvAddTime;
        @Nullable
        @Bind(R.id.tv_praise)
        TextView _TvPraise;
        @Nullable
        @Bind(R.id.img_praise)
        ImageView _ImgPraise;
        @Nullable
        @Bind(R.id.img_collect)
        ImageView _ImgCollect;
        @Nullable
        @Bind(R.id.tv_comment_count)
        TextView _TvCommentCount;
        @Nullable
        @Bind(R.id.lay_status)
        LinearLayout _LayStatus;
        @Nullable
        @Bind(R.id.tv_show_all)
        TextView _TvShowAll;
        @Nullable
        @Bind(R.id.lay_comment)
        LinearLayout _LayComment;
        @Nullable
        @Bind(R.id.lay_pics)
        LinearLayout _LayPics;
        @Nullable
        @Bind(R.id.img_video)
        ImageView _ImgVideo;
        @Nullable
        @Bind(R.id.lay_img)
        FrameLayout _LayImg;

        VHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
