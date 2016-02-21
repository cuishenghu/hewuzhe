package com.hewuzhe.ui.adapter;

import java.util.List;

import com.hewuzhe.R;
import com.hewuzhe.model.ProductScore;
import com.hewuzhe.ui.activity.MyScoretDuiHuanInfoActivity;
import com.hewuzhe.ui.activity.OrderConfirmSecondActivity;
import com.hewuzhe.ui.http.UrlContants;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DuiHuanProductAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private List<ProductScore> products;
    private Context context;

    public DuiHuanProductAdapter(Context context, List<ProductScore> products) {
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
            convertView = mLayoutInflater.inflate(R.layout.item_duihuan_product, parent, false);
            holder.img_product_pic = (ImageView) convertView.findViewById(R.id.img_product_pic);
            holder.tv_product_name = (TextView) convertView.findViewById(R.id.tv_product_name);
            holder.tv_product_score = (TextView) convertView.findViewById(R.id.tv_product_need_score);
            holder.tv_product_num = (TextView) convertView.findViewById(R.id.tv_product_remain_num);
            holder.tv_duihuan = (TextView) convertView.findViewById(R.id.tv_duihuan_product);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final ProductScore product = products.get(position);
        ImageLoader.getInstance().displayImage(StringUtil.toString(UrlContants.IMAGE_URL + product.getImagePath(), "http://"),holder.img_product_pic);//图片
        holder.tv_product_name.setText(product.getName());//名称
        holder.tv_product_score.setText("积分兑换："+product.getCreditTotal());//兑换所需积分
        holder.tv_product_num.setText("剩余："+product.getStockNum());//库存量
        holder.tv_duihuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myScore=new SessionUtil(context).getUser().Credit+"";//用户积分
                String needScore=product.getCreditTotal();//兑换需要的积分
                String score=Integer.parseInt(myScore)-Integer.parseInt(needScore)+"";
                context.startActivity(new Intent(context, MyScoretDuiHuanInfoActivity.class).putExtra("product", product).putExtra("myScore", myScore));
            }
        });
        return convertView;
    }

    class ViewHolder {
        ImageView img_product_pic;
        TextView tv_product_name;
        TextView tv_product_score;
        TextView tv_product_num;
        TextView tv_duihuan;

    }
}
