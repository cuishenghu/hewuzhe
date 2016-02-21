package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.model.OrderContent;
import com.hewuzhe.ui.http.UrlContants;
import com.hewuzhe.utils.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/1/23 0023.
 */
public class OrderConfirmFirstAdaptet extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private List<OrderContent> orders;

    public OrderConfirmFirstAdaptet(Context context, List<OrderContent> orders) {
        super();
        mLayoutInflater = LayoutInflater.from(context);
        this.orders = orders;
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int position) {
        return orders.get(position);
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
            convertView = mLayoutInflater.inflate(R.layout.item_order_details_item, null);
            holder.img_equipment_pic = (ImageView) convertView.findViewById(R.id.img_equip_pic);
            holder.tv_equipment_name = (TextView) convertView.findViewById(R.id.tv_equip_name);
            holder.tv_equipment_special = (TextView) convertView.findViewById(R.id.tv_equip_special);
            holder.tv_equipment_price = (TextView) convertView.findViewById(R.id.tv_equip_price);
            holder.tv_equipment_num = (TextView) convertView.findViewById(R.id.tv_equip_num);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        OrderContent order = orders.get(position);
        ImageLoader.getInstance().displayImage(
                StringUtil.toString(UrlContants.IMAGE_URL+order.getMiddleImagePath(), "http://"),
                holder.img_equipment_pic);
        holder.tv_equipment_name.setText(order.getProductName());
        holder.tv_equipment_special.setText(order.getProductSizeName() + "/" + order.getProductColorName());
        holder.tv_equipment_price.setText("¥" + order.getProductPriceTotalPrice());
        holder.tv_equipment_num.setText("x" + order.getNumber());


        return convertView;
    }

    class ViewHolder {
        ImageView img_equipment_pic;//装备图片
        TextView tv_equipment_name;//装备名称
        TextView tv_equipment_special;//装备规格
        TextView tv_equipment_price;//装备价格
        TextView tv_equipment_num;//订单装备数量


    }
}
