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
import com.hewuzhe.model.TrainerLesson;
import com.hewuzhe.ui.http.UrlContants;
import com.hewuzhe.utils.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/3/14 0014.
 */
public class TrainerSignAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<TrainerLesson> trainerLessons;
    private int mType;

    public TrainerSignAdapter(Context mContext, List<TrainerLesson> trainerLessons, int mType) {
        this.mContext = mContext;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.trainerLessons = trainerLessons;
        this.mType = mType;
    }

    @Override
    public int getCount() {
        return trainerLessons.size();
    }

    @Override
    public Object getItem(int position) {
        return trainerLessons.get(position);
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
            convertView = mLayoutInflater.inflate(R.layout.item_trainer_sign, parent, false);
            holder.imagePath = (ImageView) convertView.findViewById(R.id.img_image);//视频图片
            holder.tv_sign_title = (TextView) convertView.findViewById(R.id.tv_sign_title);//报名科目标题
            holder.tv_sign_date = (TextView) convertView.findViewById(R.id.tv_sign_date);//报名科目开始日期
            holder.tv_sign_num = (TextView) convertView.findViewById(R.id.tv_sign_num);//报名该科目的人数
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        TrainerLesson trainerLesson = trainerLessons.get(position);
//        ImageLoader.getInstance().displayImage(StringUtil.toString(UrlContants.IMAGE_URL + privateTrainer.getImagePath(), "http://"), holder.imagePath);
//        holder.tv_sign_title.setText(privateTrainer.getUsername());
//        holder.tv_sign_date.setText(privateTrainer.getUsername());
//        holder.tv_sign_num.setText(privateTrainer.getUsername());


        return convertView;
    }

    class ViewHolder {
        ImageView imagePath;
        TextView tv_sign_title;
        TextView tv_sign_date;
        TextView tv_sign_num;

    }

}
