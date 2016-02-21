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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
            holder.list_product = (ListView) convertView.findViewById(R.id.list_product);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ProductSort productSort = productSorts.get(position);//外层商品分类的JSONObject对象
        holder.tv_sort_name.setText(productSort.getName());//商品分类名
//        final Product product = productSort.getProductList().get(position);
        final List<ProductScore> list = productSort.getProductList();//内层商品的JSONArray数组
//        list.add(product);
        EquipmentRecommendAdapter2 equipmentRecommendAdapter2 = new EquipmentRecommendAdapter2(context, list);
        holder.list_product.setAdapter(equipmentRecommendAdapter2);
        /**
         * ListView自适应高度
         */
        StringUtil.listAutoHeight(holder.list_product);

        holder.list_product.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new Bun().putInt("proid", Integer.parseInt(list.get(position).getId())).ok();
                context.startActivity(new Intent(context, ProductInfoActivity.class).putExtra("data", new Bun().putInt("proid", Integer.parseInt(list.get(position).getId())).ok()));
            }
        });
        return convertView;
    }

    class ViewHolder {
        LinearLayout ll_sort_name;//商品分类名的线性布局(名称和顶部条)
        TextView tv_sort_name;//商品分类名称
        ListView list_product;//商品的listview

    }
}
