package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.model.PrivateTrainer;
import com.hewuzhe.ui.http.UrlContants;
import com.hewuzhe.utils.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/3/14 0014.
 */
public class VideoAndFocusAndFansAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<PrivateTrainer> privateTrainers;
    private int mType;

    public VideoAndFocusAndFansAdapter(Context mContext, List<PrivateTrainer> privateTrainers, int mType) {
        this.mContext = mContext;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.privateTrainers = privateTrainers;
        this.mType = mType;
    }

    @Override
    public int getCount() {
        return privateTrainers.size();
    }

    @Override
    public Object getItem(int position) {
        return privateTrainers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.item_video_focus_fans, parent, false);
            holder.imagePath = (ImageView) convertView.findViewById(R.id.img_image);
            holder.tv_username = (TextView) convertView.findViewById(R.id.tv_nick_name);
            holder.ll_video_content = (LinearLayout) convertView.findViewById(R.id.ll_video_content);
            holder.img_avatar = (ImageView) convertView.findViewById(R.id.img_fri_avatar);
            holder.tv_fri_username = (TextView) convertView.findViewById(R.id.tv_fri_username);
            holder.tv_fri_comment_num = (TextView) convertView.findViewById(R.id.tv_fri_comment_num);
            holder.tv_fri_publish_time = (TextView) convertView.findViewById(R.id.tv_fri_publish_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PrivateTrainer privateTrainer = privateTrainers.get(position);
        holder.tv_username.setText(privateTrainer.getUsername());
        ImageLoader.getInstance().displayImage(StringUtil.toString(UrlContants.IMAGE_URL + privateTrainer.getImagePath(), "http://"), holder.imagePath);
        if (mType == 1) {
            ImageLoader.getInstance().displayImage(StringUtil.toString(UrlContants.IMAGE_URL + privateTrainer.getAvatar(), "http://"), holder.img_avatar);
            holder.tv_fri_username.setText(privateTrainer.getNickname());
            holder.tv_fri_comment_num.setText(privateTrainer.getCommentNum() + "条评论");
            holder.tv_fri_publish_time.setText(privateTrainer.getPublishDate());
        } else {
            holder.ll_video_content.setVisibility(View.GONE);
        }
        return convertView;
    }

    class ViewHolder {
        TextView tv_username;
        ImageView imagePath;
        LinearLayout ll_video_content;//视频里显示的线性布局,相册/关注/粉丝里面不显示
        ImageView img_avatar;//视频里显示的头像,相册/关注/粉丝里面不显示
        TextView tv_fri_username;//视频里显示的线性布局里的昵称,相册/关注/粉丝里面不显示
        TextView tv_fri_comment_num;//视频里显示的线性布局里的评论条数,相册/关注/粉丝里面不显示
        TextView tv_fri_publish_time;//视频里显示的线性布局里的发布时间 ,相册/关注/粉丝里面不显示

    }

}
