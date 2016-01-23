package com.hewuzhe.ui.adapter;

import android.content.Context;

import com.baidu.mapapi.search.core.PoiInfo;
import com.hewuzhe.R;
import com.hewuzhe.ui.adapter.base.BaseViewHolder;
import com.hewuzhe.ui.adapter.base.MyBaseAdapter;

import java.util.List;


/**
 * @desc 搜索的地址列表适配器
 * @author Nate
 * @date 2015-12-20
 */
public class SearchAddressAdapter extends MyBaseAdapter<PoiInfo> {

	public SearchAddressAdapter(Context context, int resource, List<PoiInfo> list) {
		super(context, resource, list);
	}

	@Override
	public void setConvert(BaseViewHolder viewHolder, PoiInfo info) {
		viewHolder.setTextView(R.id.item_address_name_tv, info.name);
		viewHolder.setTextView(R.id.item_address_detail_tv, info.address);
	}

}
