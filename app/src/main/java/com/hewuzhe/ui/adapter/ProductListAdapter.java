package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hewuzhe.R;
import com.hewuzhe.model.Product;
import com.hewuzhe.presenter.ProductListPresenter;
import com.hewuzhe.ui.adapter.base.BaseAdapter;
import com.hewuzhe.ui.cons.C;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/1/23 0023.
 */
public class ProductListAdapter extends BaseAdapter<ProductListAdapter.VHolder, Product, ProductListPresenter> {

    public ProductListAdapter(Context context, ProductListPresenter productListPresenter) {
        super(context, productListPresenter);

    }

    @Override
    public int provideItemLayoutId() {
        return R.layout.product_list;
    }

    @Override
    public VHolder createVH(ViewGroup parent, int viewType, View view) {
        return new VHolder(view);
    }

    @Override
    public void bindData(VHolder holder, int position) {

        final Product product = data.get(position);

        Glide.with(context)
                .load(C.BASE_URL + product.ImagePath)
                .centerCrop()
                .crossFade()
                .placeholder(R.mipmap.img_bg)
                .into(holder.icon_title);

        holder.pro_name.setText(product.Name);
        holder.pro_price.setText("￥"+product.Price);
        holder.pro_visitnum.setText(product.VisitNum+"人");
        holder.pro_salenum.setText("销售量" + product.SaleNum);
    }


    class VHolder extends RecyclerView.ViewHolder {

        @Nullable
        @Bind(R.id.icon_title)
        ImageView icon_title;

        @Nullable
        @Bind(R.id.pro_name)
        TextView pro_name;

        @Nullable
        @Bind(R.id.pro_price)
        TextView pro_price;

        @Nullable
        @Bind(R.id.pro_visitnum)
        TextView pro_visitnum;

        @Nullable
        @Bind(R.id.pro_salenum)
        TextView pro_salenum;


        VHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
