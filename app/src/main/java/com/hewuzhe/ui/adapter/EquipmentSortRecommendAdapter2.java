package com.hewuzhe.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import com.hewuzhe.R;
import com.hewuzhe.model.ProductScore;
import com.hewuzhe.model.ProductSort;
import com.hewuzhe.ui.activity.MyScoretDuiHuanInfoActivity;
import com.hewuzhe.ui.activity.OrderConfirmSecondActivity;
import com.hewuzhe.ui.activity.OrderDetailsActivity;
import com.hewuzhe.ui.activity.ProductInfoActivity;
import com.hewuzhe.ui.http.UrlContants;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class EquipmentSortRecommendAdapter2 extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private List<ProductSort> productSorts;
    private Context context;

    public RecyclerView.LayoutManager layoutManager;

    public EquipmentSortRecommendAdapter2(Context context, List<ProductSort> productSorts) {
        super();
        mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.productSorts = productSorts;
    }

    @Override
    public int getCount() {
        return productSorts.size();
    }

    @Override
    public Object getItem(int position) {
        return productSorts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.item_equipment_recommend, parent, false);

            holder.ll_sort_name = (LinearLayout) convertView.findViewById(R.id.ll_item_sort_name);
            holder.tv_sort_name = (TextView) convertView.findViewById(R.id.tv_equip_sort_name);
            holder.recyclerView = (RecyclerView) convertView.findViewById(R.id.recycler_view);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ProductSort productSort = productSorts.get(position);//外层商品分类的JSONObject对象
        holder.tv_sort_name.setText(productSort.getName());//商品分类名
//        final Product product = productSort.getProductList().get(position);
        final ArrayList<ProductScore> list =(ArrayList) productSort.getProductList();//内层商品的JSONArray数组
//        list.add(product);
        this.layoutManager = new GridLayoutManager(context,2);
        holder.recyclerView.setLayoutManager(layoutManager);
//        EquipmentRecommendAdapter2 equipmentRecommendAdapter2 = new EquipmentRecommendAdapter2(context, list);
        EquipmentClass2Adapter equipmentClass2Adapter = new EquipmentClass2Adapter(context);
        holder.recyclerView.setAdapter(equipmentClass2Adapter);
        equipmentClass2Adapter.addDatas(list);
        WindowManager manager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        ViewGroup.LayoutParams para;
        para = holder.recyclerView.getLayoutParams();
        Display display = manager.getDefaultDisplay();
        int width =display.getWidth();
        width = (width- StringUtil.dip2px(context, 24))/2;
        int height = width+StringUtil.dip2px(context, 76);
        para.height = (int)(Math.ceil(list.size() / 2.0))*height;
        holder.recyclerView.setLayoutParams(para);

        return convertView;
    }

    class ViewHolder {
        LinearLayout ll_sort_name;//商品分类名的线性布局(名称和顶部条)
        TextView tv_sort_name;//商品分类名称
        RecyclerView recyclerView;//商品的listview

    }
}
