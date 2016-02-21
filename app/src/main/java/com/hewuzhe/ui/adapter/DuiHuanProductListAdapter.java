package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.model.OrderChild;
import com.hewuzhe.model.ProductScore;
import com.hewuzhe.ui.http.UrlContants;
import com.hewuzhe.utils.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/1/23 0023.
 */
public class DuiHuanProductListAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private List<ProductScore> products;

    public DuiHuanProductListAdapter(Context context, List<ProductScore> products) {
        super();
        mLayoutInflater = LayoutInflater.from(context);
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
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
            convertView = mLayoutInflater.inflate(R.layout.item_duihuan_jilu, null);
            holder.img_product_pic = (ImageView) convertView.findViewById(R.id.img_product_pic);
            holder.tv_product_name = (TextView) convertView.findViewById(R.id.tv_product_name);
            holder.tv_product_need_score = (TextView) convertView.findViewById(R.id.tv_product_need_score);
            holder.tv_product_state = (TextView) convertView.findViewById(R.id.tv_product_state);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ProductScore product = products.get(position);
        ImageLoader.getInstance().displayImage(
                StringUtil.toString(UrlContants.IMAGE_URL + product.getImagePath(), "http://"),
                holder.img_product_pic);
        holder.tv_product_name.setText(product.getName());
        holder.tv_product_need_score.setText(product.getCreditTotal()+"积分");
        holder.tv_product_state.setText("兑换成功");


        return convertView;
    }

    class ViewHolder {
        ImageView img_product_pic;//装备图片
        TextView tv_product_name;//装备名称
        TextView tv_product_need_score;//装备规格
        TextView tv_product_state;//装备价格
    }
}
