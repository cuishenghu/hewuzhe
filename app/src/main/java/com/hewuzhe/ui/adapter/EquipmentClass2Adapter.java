package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hewuzhe.R;
import com.hewuzhe.model.ChatList;
import com.hewuzhe.model.ProductScore;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.activity.ProductInfoActivity;
import com.hewuzhe.ui.adapter.base.BaseAdapter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.http.UrlContants;
import com.hewuzhe.ui.widget.GlideCircleTransform;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zycom on 2016/4/8.
 */
public class EquipmentClass2Adapter extends BaseAdapter<EquipmentClass2Adapter.ViewHolder, ProductScore, BasePresenterImp> {

    public EquipmentClass2Adapter(Context context) {
        super(context);
        isAddFooter = false;
    }

    @Override
    public int provideItemLayoutId() {
        return R.layout.product_item_new;
    }

    @Override
    public ViewHolder createVH(ViewGroup parent, int viewType, View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindData(ViewHolder holder, final int position) {
        final ProductScore product = data.get(position);
        Glide.with(context)
                .load(C.BASE_URL + product.getImagePath())
                .centerCrop()
                .crossFade()
                .placeholder(R.mipmap.img_avatar)
                .into(holder.img_product_pic);
        //设置imageview宽高
        WindowManager manager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        int width =display.getWidth();
        ViewGroup.LayoutParams para;
        para = holder.img_product_pic.getLayoutParams();

        para.height = (width- StringUtil.dip2px(context, 24))/2;
        holder.img_product_pic.setLayoutParams(para);
        holder.tv_product_name.setText(product.getName());//商品名称
        holder.tv_product_price.setText("¥" + product.getPrice());//商品价格
//        holder.tv_product_visit_sum.setText(product.getVisitNum()+"人");//商品关注度
        holder.tv_product_sale_sum.setText("销量："+product.getSaleNum());//商品销售量
        holder.chat_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ProductInfoActivity.class).putExtra("data", new Bun().putInt("proid", Integer.parseInt(product.getId())).ok()));
            }
        });
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @Nullable @Bind(R.id.pt_photo)
        ImageView img_product_pic;//商品图片
        @Nullable @Bind(R.id.chat_title)
        TextView tv_product_name;//商品名称
        @Nullable @Bind(R.id.chat_f_count)
        TextView tv_product_price;//商品价格
        @Nullable @Bind(R.id.chat_c_count)
        TextView tv_product_sale_sum;//商品销售量
        @Nullable @Bind(R.id.chat_item)
        LinearLayout chat_item;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
