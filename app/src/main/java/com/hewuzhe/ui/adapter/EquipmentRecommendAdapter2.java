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
import com.hewuzhe.model.ProductScore;
import com.hewuzhe.ui.http.UrlContants;
import com.hewuzhe.utils.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class EquipmentRecommendAdapter2 extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private List<ProductScore> products;
    private Context context;

    public EquipmentRecommendAdapter2(Context context, List<ProductScore> products) {
        super();
        mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.product_item_new, parent, false);
            holder.img_product_pic = (ImageView) convertView.findViewById(R.id.pt_photo);
            holder.tv_product_name = (TextView) convertView.findViewById(R.id.chat_title);
            holder.tv_product_price = (TextView) convertView.findViewById(R.id.chat_f_count);
//            holder.tv_product_visit_sum = (TextView) convertView.findViewById(R.id.tv_equip_visit_sum);
            holder.tv_product_sale_sum = (TextView) convertView.findViewById(R.id.chat_c_count);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ProductScore product = products.get(position);
        ImageLoader.getInstance().displayImage(
                StringUtil.toString(UrlContants.IMAGE_URL + product.getImagePath(), "http://"),
                holder.img_product_pic);//图片
        holder.tv_product_name.setText(product.getName());//商品名称
        holder.tv_product_price.setText("¥" + product.getPrice());//商品价格
//        holder.tv_product_visit_sum.setText(product.getVisitNum()+"人");//商品关注度
        holder.tv_product_sale_sum.setText("销量："+product.getSaleNum());//商品销售量

        return convertView;
    }

    class ViewHolder {
        ImageView img_product_pic;//商品图片
        TextView tv_product_name;//商品名称
        TextView tv_product_price;//商品价格
//        TextView tv_product_visit_sum;//商品关注度
        TextView tv_product_sale_sum;//商品销售量

    }
}
