package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
public class FocusAndFansAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<PrivateTrainer> privateTrainers;

    public FocusAndFansAdapter(Context mContext, List<PrivateTrainer> privateTrainers) {
        this.mContext = mContext;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.privateTrainers = privateTrainers;
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
            convertView = mLayoutInflater.inflate(R.layout.item_fans, parent, false);
            holder.img_avatar = (ImageView) convertView.findViewById(R.id.img_avatar);
            holder.tv_username = (TextView) convertView.findViewById(R.id.tv_nick_name);
            convertView.setLayoutParams(new AbsListView.LayoutParams((int) (parent.getWidth() / 3) - 1, (int) (parent.getHeight() / 2)));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PrivateTrainer privateTrainer = privateTrainers.get(position);
        holder.tv_username.setText(privateTrainer.getUsername());
        ImageLoader.getInstance().displayImage(StringUtil.toString(UrlContants.IMAGE_URL + privateTrainer.getAvatar(), "http://"), holder.img_avatar);

        return convertView;
    }

    class ViewHolder {
        TextView tv_username;
        ImageView img_avatar;
    }

}
