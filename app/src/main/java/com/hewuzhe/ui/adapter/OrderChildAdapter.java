package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.model.OrderChild;
import com.hewuzhe.model.OrderContent;
import com.hewuzhe.model.ProductSort;
import com.hewuzhe.ui.http.UrlContants;
import com.hewuzhe.utils.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class OrderChildAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private List<OrderContent> orderContents;
    private Context context;

    public OrderChildAdapter(Context context, List<OrderContent> orderContents) {
        super();
        mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.orderContents = orderContents;
    }

    @Override
    public int getCount() {
        return orderContents.size();
    }

    @Override
    public Object getItem(int position) {
        return orderContents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.item_order_child, parent, false);
            holder.img_product_pic = (ImageView) convertView.findViewById(R.id.img_equip_pic);
            holder.tv_product_name = (TextView) convertView.findViewById(R.id.tv_equip_name);
            holder.tv_product_spcial = (TextView) convertView.findViewById(R.id.tv_equip_special);
            holder.tv_product_price = (TextView) convertView.findViewById(R.id.tv_equip_price);
            holder.tv_product_num = (TextView) convertView.findViewById(R.id.tv_equip_buy_num);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        OrderContent orderContent = orderContents.get(position);//外层商品分类的JSONObject对象

        if (orderContent.getProductId() == null) {

            holder.tv_product_name.setText(orderContent.getProductName());//商品名称
            holder.tv_product_name.setTextSize(18);
            holder.tv_product_spcial.setVisibility(View.GONE);
            holder.tv_product_price.setVisibility(View.GONE);
            holder.tv_product_num.setVisibility(View.GONE);
        } else {
            ImageLoader.getInstance().displayImage(StringUtil.toString(UrlContants.IMAGE_URL +
                    orderContent.getMiddleImagePath(), "http://"), holder.img_product_pic);
            holder.tv_product_name.setText(orderContent.getProductName());//商品名称
            holder.tv_product_spcial.setText("规格：" + orderContent.getProductSizeName() + "/" + orderContent.getProductColorName());
            holder.tv_product_price.setText("¥" + orderContent.getProductPriceTotalPrice());
            holder.tv_product_num.setText("x" + orderContent.getNumber());
        }
        return convertView;
    }

    class ViewHolder {
        ImageView img_product_pic;//商品图片
        TextView tv_product_name;//商品名称
        TextView tv_product_spcial;//商品规格
        TextView tv_product_price;//商品价格
        TextView tv_product_num;//购买商品数量

    }
}
