package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hewuzhe.R;

import java.util.List;

import butterknife.Bind;

/**
 * Created by csh on 16/01/31.
 */
public class ClassificationAdapter<T> extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private int resource;
    private List<T> mObjects;
    private int selectedPosition=-1;
    private int cate = -1;
    private int width = 0;
    private int[] colors = new int[]{0xFF292A2F,0xFF393A3F,0xFFEF9C00,0xFFEF9C00};

    public ClassificationAdapter(Context context, List<T> mObjects, int resource, int cate, int width) {
        this.layoutInflater = LayoutInflater.from(context);
        this.resource = resource;
        this.cate = cate;
        this.mObjects = mObjects;
        this.width = width;
    }

    @Override
    public int getCount() {
        return mObjects.size();
    }

    @Override
    public T getItem(int position) {
        return mObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setSelectedPosition(int position){
        this.selectedPosition = position;
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VHolder vHolder;
        if(convertView == null){
            convertView = layoutInflater.inflate(resource, parent, false);
            vHolder=new VHolder();
            vHolder.classification_name = (TextView)convertView.findViewById(R.id.classification_name);
            convertView.setTag(vHolder);
        }else{
            vHolder = (VHolder)convertView.getTag();
        }
        T t = mObjects.get(position);
        vHolder.classification_name.setText(t.toString());
        vHolder.classification_name.setHeight(cate == 0 ? width / 4 : width / 6);
        vHolder.classification_name.setBackgroundColor(selectedPosition==position?colors[cate+1]:colors[cate]);
        return convertView;
    }

    /**
     * 产品分类
     */
    class VHolder {
        @Bind(R.id.classification_name)
        TextView classification_name;
    }
}
