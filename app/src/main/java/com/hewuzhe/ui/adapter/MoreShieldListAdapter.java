package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.model.Friend;
import com.hewuzhe.model.Friend2;
import com.hewuzhe.ui.http.UrlContants;
import com.hewuzhe.utils.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/1/23 0023.
 */
public class MoreShieldListAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private List<Friend2> friends;

    public MoreShieldListAdapter(Context context, List<Friend2> friends) {
        super();
        mLayoutInflater = LayoutInflater.from(context);
        this.friends = friends;
    }

    @Override
    public int getCount() {
        return friends.size();
    }

    @Override
    public Object getItem(int position) {
        return friends.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.item_shield_jilu, null);
            holder.img_friend_pic = (ImageView) convertView.findViewById(R.id.img_friend_pic);
            holder.tv_friend_nickname = (TextView) convertView.findViewById(R.id.tv_friend_nickname);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Friend2 friend = friends.get(position);
        ImageLoader.getInstance().displayImage(
                StringUtil.toString(UrlContants.IMAGE_URL + friend.getPhotoPath(), "http://"),
                holder.img_friend_pic);
        holder.tv_friend_nickname.setText(friend.getNicName());

        return convertView;
    }

    class ViewHolder {
        ImageView img_friend_pic;//装备图片
        TextView tv_friend_nickname;//装备名称
    }
}
