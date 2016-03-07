package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hewuzhe.R;
import com.hewuzhe.model.ShopCar;
import com.hewuzhe.presenter.ShopCarPresenter;
import com.hewuzhe.ui.adapter.base.BaseAdapter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widget.GlideCircleTransform;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zycom on 2016/2/3.
 */
public class ShopCarAdapter extends BaseAdapter<ShopCarAdapter.VHolder, ShopCar, ShopCarPresenter> {

//    public int number=0;
    public int res_select=0;
    public int StockNum;

    public ShopCarAdapter(Context context, ShopCarPresenter shopCarPresenter) {
        super(context, shopCarPresenter);

    }

    @Override
    public int provideItemLayoutId() {
        return R.layout.shop_car;
    }

    @Override
    public VHolder createVH(ViewGroup parent, int viewType, View view) {
        return new VHolder(view);
    }

    @Override
    public void bindData(final VHolder holder, int position) {

        final ShopCar shopCar = data.get(position);
        Glide.with(context)
                .load(C.BASE_URL + shopCar.MiddleImagePath)
                .centerCrop()
                .crossFade()
                .placeholder(R.mipmap.img_bg)
                .into(holder.icon_title);

//        number=shopCar.Number;
        shopCar.AllPrice = shopCar.Number*shopCar.ProductPriceTotalPrice;
        holder.shopcar_buynum.setText("x" + shopCar.Number);
        DecimalFormat df = new DecimalFormat("########0.00");
        holder.pro_price.setText("￥"+df.format(shopCar.ProductPriceTotalPrice));
        holder.shopcar_guige.setText("规格：" +shopCar.ProductSizeName+"/"+shopCar.ProductColorName);
        holder.shopcar_title.setText( shopCar.ProductName);
        holder.pro_num.setText(shopCar.Number + "");
        holder.shopcar_select.setImageResource(shopCar.select_state ? R.mipmap.icon_select_click : R.mipmap.icon_select_normal);
        holder.shopcar_hint.setVisibility(shopCar.is_show ? View.VISIBLE : View.GONE);
        holder.trash_button.setVisibility(shopCar.is_show ? View.VISIBLE : View.INVISIBLE);
        holder.shopcar_buynum.setVisibility(shopCar.is_show?View.GONE:View.VISIBLE);
        this.StockNum = shopCar.StockNum;
        holder.trash_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _presenter.deleteShopCar(shopCar.Id + "");
                Toast.makeText(context, "商品删除成功！！", Toast.LENGTH_SHORT).show();
//                for (int i=0;i<data.size();i++){
//                    data.get(i).is_show=true;
//                }
            }
        });

        holder.shopcar_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shopCar.Number >= StockNum) {
                    Toast.makeText(context,"您选择的商品数量超过库存数量，已不能添加",Toast.LENGTH_SHORT).show();
                    return;
                }
                shopCar.Number++;
                holder.shopcar_buynum.setText("x" + shopCar.Number);
                holder.pro_num.setText(shopCar.Number + "");
            }
        });

        holder.shopcar_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(shopCar.Number==1)
                    return;
                shopCar.Number--;
                holder.shopcar_buynum.setText("x"+shopCar.Number);
                holder.pro_num.setText(shopCar.Number+"");
            }
        });

        holder.shopcar_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(shopCar.select_state){
//                    holder.shopcar_select.setImageResource(R.mipmap.icon_select_click);
//                    shopCar.select_state=false;
//                    notifyDataSetChanged();
//                }else{
//                    holder.shopcar_select.setImageResource(R.mipmap.icon_select_normal);
//                    shopCar.select_state=true;
//                    notifyDataSetChanged();
//                }
//

            }
        });

    }

    class VHolder extends RecyclerView.ViewHolder {

        @Nullable
        @Bind(R.id.shopcar_hint)
        LinearLayout shopcar_hint;

        @Nullable
        @Bind(R.id.shopcar_select)
        ImageView shopcar_select;

        @Nullable
        @Bind(R.id.icon_title)
        ImageView icon_title;

        @Nullable
        @Bind(R.id.shopcar_add)
        ImageView shopcar_add;

        @Nullable
        @Bind(R.id.shopcar_sub)
        ImageView shopcar_sub;

        @Nullable
        @Bind(R.id.shopcar_buynum)
        TextView shopcar_buynum;

        @Nullable
        @Bind(R.id.pro_price)
        TextView pro_price;

        @Nullable
        @Bind(R.id.shopcar_guige)
        TextView shopcar_guige;

        @Nullable
        @Bind(R.id.shopcar_title)
        TextView shopcar_title;

        @Nullable
        @Bind(R.id.pro_num)
        TextView pro_num;

        @Nullable
        @Bind(R.id.trash_button)
        LinearLayout trash_button;

        VHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
