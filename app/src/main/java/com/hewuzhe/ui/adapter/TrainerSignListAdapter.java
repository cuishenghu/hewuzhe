package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.model.TrainerLessonSigner;
import com.hewuzhe.ui.http.UrlContants;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.view.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/3/14 0014.
 */
public class TrainerSignListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<TrainerLessonSigner> trainerLessonSigners;

    public TrainerSignListAdapter(Context mContext, List<TrainerLessonSigner> trainerLessonSigners) {
        this.mContext = mContext;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.trainerLessonSigners = trainerLessonSigners;
    }

    @Override
    public int getCount() {
        return trainerLessonSigners.size();
    }

    @Override
    public Object getItem(int position) {
        return trainerLessonSigners.get(position);
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
            convertView = mLayoutInflater.inflate(R.layout.item_trainer_sign_list, parent, false);
            holder.imagePath = (ImageView) convertView.findViewById(R.id.img_image);//视频图片
            holder.tv_sign_title = (TextView) convertView.findViewById(R.id.tv_sign_title);//报名科目标题
            holder.tv_sign_date = (TextView) convertView.findViewById(R.id.tv_sign_date);//报名科目开始日期
            holder.tv_sign_num = (TextView) convertView.findViewById(R.id.tv_sign_num);//报名该科目的人数
            holder.img_avatar = (CircleImageView) convertView.findViewById(R.id.img_avatar);
            holder.tv_username = (TextView) convertView.findViewById(R.id.tv_username);
            holder.tv_prefession = (TextView) convertView.findViewById(R.id.tv_prefession);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        TrainerLessonSigner trainerLessonSigner = trainerLessonSigners.get(position);
        ImageLoader.getInstance().displayImage(StringUtil.toString(UrlContants.IMAGE_URL + trainerLessonSigner.getImagePath(), "http://"), holder.imagePath);
        holder.tv_sign_title.setText(trainerLessonSigner.getLesson().getTitle());
        holder.tv_sign_date.setText("报名时间：" + trainerLessonSigner.getJoinTime());
        holder.tv_sign_num.setText(trainerLessonSigner.getJoinNum() + "人参加");
        ImageLoader.getInstance().displayImage(StringUtil.toString(UrlContants.IMAGE_URL+trainerLessonSigner.getTeacher().getPhotoPath(),"http://"),holder.img_avatar);
        holder.tv_username.setText(trainerLessonSigner.getTeacher().getNicName());
        holder.tv_prefession.setText(trainerLessonSigner.getTeacher().getSpeciality());
        return convertView;
    }

    class ViewHolder {
        ImageView imagePath;
        TextView tv_sign_title;
        TextView tv_sign_date;
        TextView tv_sign_num;
        CircleImageView img_avatar;
        TextView tv_username;
        TextView tv_prefession;

    }

}
