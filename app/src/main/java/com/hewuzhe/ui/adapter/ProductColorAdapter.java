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
import com.hewuzhe.model.ProductColor;
import com.hewuzhe.model.ProductComment;
import com.hewuzhe.ui.cons.C;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zycom on 2016/2/18.
 */
public class ProductColorAdapter extends RecyclerView.Adapter<ProductColorAdapter.VHolder> {

    ProductColor productColor;

    Context context;
    ArrayList<ProductColor> data;

    public ProductColorAdapter(){}
    public ProductColorAdapter(Context context,ArrayList<ProductColor> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public VHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        VHolder holder = new VHolder(LayoutInflater.from(
                context).inflate(R.layout.product_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(VHolder holder, int position)
    {
        productColor =(ProductColor) data.get(position);

        holder.product_text.setText(productColor.Name);
    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }


    class VHolder extends RecyclerView.ViewHolder {

        @Nullable
        @Bind(R.id.product_text)
        TextView product_text;

        VHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
