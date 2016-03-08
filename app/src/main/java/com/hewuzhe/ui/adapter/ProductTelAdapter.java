package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hewuzhe.R;
import com.hewuzhe.model.Tel;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zycom on 2016/2/29.
 */
public class ProductTelAdapter extends RecyclerView.Adapter<ProductTelAdapter.VHolder> {

    Tel tel;

    Context context;
    ArrayList<Tel> data;

    public ProductTelAdapter(){}
    public ProductTelAdapter(Context context,ArrayList<Tel> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public VHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        VHolder holder = new VHolder(LayoutInflater.from(
                context).inflate(R.layout.tel_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final VHolder holder, int position)
    {
        tel =(Tel) data.get(position);
        holder.pro_tel.setText(tel.Phone);
        holder.pro_tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context,tel.Phone,Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + holder.pro_tel.getText().toString())));
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }


    class VHolder extends RecyclerView.ViewHolder {

        @Nullable
        @Bind(R.id.pro_tel)
        TextView pro_tel;

        VHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
