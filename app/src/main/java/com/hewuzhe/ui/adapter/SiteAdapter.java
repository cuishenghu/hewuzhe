package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.model.Site;
import com.hewuzhe.presenter.SitePresenter;
import com.hewuzhe.ui.activity.SiteActivity;
import com.hewuzhe.ui.adapter.base.BaseAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by csh on 2016/02/01.
 * 收货地址Adapter
 */
public class SiteAdapter extends BaseAdapter<SiteAdapter.VHolder, Site, SitePresenter> {

    private int sel = 0;

    public SiteAdapter(Context context, SitePresenter presenter, int sel) {
        super(context, presenter, false);
        this.sel = sel;
    }

    @Override
    public int provideItemLayoutId() {
        return R.layout.item_site;
    }

    @Override
    public VHolder createVH(ViewGroup parent, int viewType, View view) {
        return new VHolder(view);
    }

    @Override
    public void bindData(VHolder holder, int position) {
        final Site site = data.get(position);

        if(holder.tvName != null)
            holder.tvName.setText(site.ReceiverName);
        if(holder.tvPhone != null)
            holder.tvPhone.setText(site.Phone);
        if(holder.tvAdress != null)
            holder.tvAdress.setText(site.toString());
        if(holder.tvIco != null)
            holder.tvIco.setImageResource("true".equals(site.IsDefault) ? R.drawable.adress_choose : R.drawable.adress_choose_normal);
        if(holder.tvDivider != null && position == data.size()-1)
            holder.tvDivider.setVisibility(View.GONE);
        if(holder.tvDefault != null){
            holder.tvDefault.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    _presenter.setDefault(site.Id);
                    if(sel == 0){
                        ((SiteActivity)context).setResult(9, new Intent().putExtra("site", site));
                        ((SiteActivity)context).finish();
                    }
                }
            });
        }
    }

    class VHolder extends RecyclerView.ViewHolder{
        @Nullable
        @Bind(R.id.tv_name)
        TextView tvName;/**收货人*/
        @Nullable
        @Bind(R.id.tv_phone)
        TextView tvPhone;/**收货联系方式*/
        @Nullable
        @Bind(R.id.tv_adress)
        TextView tvAdress;/**收货人地址*/
        @Nullable
        @Bind(R.id.tv_default)
        LinearLayout tvDefault;/**收货地址默认*/
        @Nullable
        @Bind(R.id.tv_default_ico)
        ImageView tvIco;/**默认图标*/
        @Nullable
        @Bind(R.id.tv_divider)
        TextView tvDivider;/**分隔线*/

        VHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
