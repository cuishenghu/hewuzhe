package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.model.TrainerLesson;
import com.hewuzhe.model.TrainerVideo;
import com.hewuzhe.ui.http.UrlContants;
import com.hewuzhe.utils.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/3/14 0014.
 */
public class TrainerVideoAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<TrainerVideo> trainerVideos;
    private int mType;

    public TrainerVideoAdapter(Context mContext, List<TrainerVideo> trainerVideos, int mType) {
        this.mContext = mContext;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.trainerVideos = trainerVideos;
        this.mType = mType;
    }

    @Override
    public int getCount() {
        return trainerVideos.size();
    }

    @Override
    public Object getItem(int position) {
        return trainerVideos.get(position);
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
            convertView = mLayoutInflater.inflate(R.layout.item_trainer_video, parent, false);
            holder.imagePath = (ImageView) convertView.findViewById(R.id.img_image);//视频图片
            holder.tv_video_title = (TextView) convertView.findViewById(R.id.tv_username);//报名科目标题
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        TrainerVideo trainerVideo = trainerVideos.get(position);
        ImageLoader.getInstance().displayImage(StringUtil.toString(UrlContants.IMAGE_URL + trainerVideo.getImagePath(), "http://"), holder.imagePath);
        holder.tv_video_title.setText(trainerVideo.getTitle());
//        holder.tv_sign_date.setText(privateTrainer.getUsername());
//        holder.tv_sign_num.setText(privateTrainer.getUsername());


        return convertView;
    }

    class ViewHolder {
        ImageView imagePath;
        TextView tv_video_title;
        TextView tv_sign_date;
        TextView tv_sign_num;

    }

}
