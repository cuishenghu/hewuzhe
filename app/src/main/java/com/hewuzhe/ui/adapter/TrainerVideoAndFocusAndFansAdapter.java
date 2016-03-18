package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.model.PrivateTrainer;
import com.hewuzhe.model.TrainerVideo;
import com.hewuzhe.ui.http.UrlContants;
import com.hewuzhe.utils.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/3/14 0014.
 */
public class TrainerVideoAndFocusAndFansAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<PrivateTrainer> privateTrainers;
    private int mType;

    public TrainerVideoAndFocusAndFansAdapter(Context mContext, List<PrivateTrainer> privateTrainers, int mType) {
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
            convertView = mLayoutInflater.inflate(R.layout.item_trainer_focus_fans, parent, false);
            holder.imagePath = (ImageView) convertView.findViewById(R.id.img_image);//顶部大头像
            holder.tv_username = (TextView) convertView.findViewById(R.id.tv_username);//关注和粉丝的昵称,视频里的名称
            holder.ll_video_content = (LinearLayout) convertView.findViewById(R.id.ll_video_content);
            holder.img_avatar = (ImageView) convertView.findViewById(R.id.img_fri_avatar);//发布者头像
            holder.tv_fri_username = (TextView) convertView.findViewById(R.id.tv_fri_username);//发布者昵称
//            holder.tv_fri_comment_num = (TextView) convertView.findViewById(R.id.tv_fri_comment_num);//评论数
//            holder.tv_fri_publish_time = (TextView) convertView.findViewById(R.id.tv_fri_publish_time);//发布时间
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PrivateTrainer privateTrainer = privateTrainers.get(position);
        holder.tv_username.setText(privateTrainer.getNicName());
        ImageLoader.getInstance().displayImage(StringUtil.toString(UrlContants.IMAGE_URL + privateTrainer.getPhotoPath(), "http://"), holder.imagePath);
//            holder.tv_fri_username.setText(privateTrainer.getNickname());
////            holder.tv_fri_comment_num.setText(privateTrainer.getCommentNum() + "条评论");
//            holder.tv_fri_publish_time.setText(privateTrainer.getPublishDate());

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
