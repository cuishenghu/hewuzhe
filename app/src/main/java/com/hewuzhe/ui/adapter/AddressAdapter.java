package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.model.Address;
import com.hewuzhe.ui.adapter.base.BeeBaseAdapter;
import com.hewuzhe.ui.adapter.base.BeeCellHolder;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xianguangjin on 15/12/31.
 */
public class AddressAdapter extends BeeBaseAdapter<AddressAdapter.VHolder, Address> {


    public AddressAdapter(Context c, ArrayList dataList) {
        super(c, dataList);
    }

    @Override
    protected VHolder createCellHolder(View cellView) {
        return new VHolder(cellView);
    }

    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, VHolder h) {
        h._TvName.setText(dataList.get(position).Name);

        return null;
    }

    @Override
    public View createCellView() {
        return mInflater.inflate(R.layout.item_address, null);
    }


    class VHolder extends BeeCellHolder {
        @Nullable
        @Bind(R.id.tv_name)
        TextView _TvName;

        VHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
