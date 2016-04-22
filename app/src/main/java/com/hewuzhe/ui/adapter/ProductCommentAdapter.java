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
import com.hewuzhe.model.ProductComment;
import com.hewuzhe.presenter.ProductCommentPresenter;
import com.hewuzhe.ui.adapter.base.BaseAdapter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widget.GlideCircleTransform;
import com.hewuzhe.utils.StringUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zycom on 2016/2/20.
 */
public class ProductCommentAdapter extends BaseAdapter<ProductCommentAdapter.VHolder, ProductComment, ProductCommentPresenter> {

    ProductComment productComment;

    public ProductCommentAdapter(Context context, ProductCommentPresenter productCommentPresenter) {
        super(context, productCommentPresenter);

    }

    @Override
    public int provideItemLayoutId() {
        return R.layout.comment_list;
    }

    @Override
    public VHolder createVH(ViewGroup parent, int viewType, View view) {
        return new VHolder(view);
    }

    @Override
    public void bindData(VHolder holder, int position) {

        productComment =(ProductComment) data.get(position);

        Glide.with(context)
                .load(productComment.PhotoPath.contains("UpLoad/Photo/")?C.BASE_URL + productComment.PhotoPath:productComment.PhotoPath)
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
