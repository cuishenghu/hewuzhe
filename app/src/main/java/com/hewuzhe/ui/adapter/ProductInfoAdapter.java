package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hewuzhe.R;
import com.hewuzhe.model.Product;
import com.hewuzhe.model.ProductCategory;
import com.hewuzhe.model.ProductColor;
import com.hewuzhe.model.ProductComment;
import com.hewuzhe.presenter.ProductInfoPresenter;
import com.hewuzhe.ui.activity.ProductInfoActivity;
import com.hewuzhe.ui.adapter.base.BaseAdapter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widget.GlideCircleTransform;
import com.hewuzhe.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zycom on 2016/2/16.
 */
public class ProductInfoAdapter extends RecyclerView.Adapter<ProductInfoAdapter.VHolder> {

    ProductComment productComment;

    Context context;
    ArrayList<ProductComment> data;

    public ProductInfoAdapter(){}
    public ProductInfoAdapter(Context context,ArrayList<ProductComment> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public VHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        VHolder holder = new VHolder(LayoutInflater.from(
                context).inflate(R.layout.comment_list, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(VHolder holder, int position)
    {
        productComment =(ProductComment) data.get(position);

        Glide.with(context)
                .load(C.BASE_URL + productComment.PhotoPath)
                .centerCrop()
                .crossFade()
                .placeholder(R.mipmap.img_bg)
                .transform(new GlideCircleTransform(context))
                .into(holder.head_portrait);


        holder.user_name.setText(productComment.NicName);
        holder.user_content.setText(productComment.Content);
        holder.user_date.setText(productComment.PublishTime);
        holder.user_sale.setText("规格：" + productComment.ColorAndSize);
    }

    private String getPhone(String phone) {
        if (StringUtil.isMobile(phone)) {
            String start = phone.substring(0, 3);
            String end = phone.substring(8, 11);
            return start + "*****" + end;
        } else if (!StringUtil.isEmpty(phone)) {
            return phone;
        } else {
            return "";
        }
    }
    @Override
    public int getItemCount()
    {
        return data.size();
    }


    class VHolder extends RecyclerView.ViewHolder {

        @Nullable
        @Bind(R.id.head_portrait)
        ImageView head_portrait;

        @Nullable
        @Bind(R.id.user_name)
        TextView user_name;

        @Nullable
        @Bind(R.id.user_content)
        TextView user_content;

        @Nullable
        @Bind(R.id.user_date)
        TextView user_date;

        @Nullable
        @Bind(R.id.user_sale)
        TextView user_sale;



        VHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
