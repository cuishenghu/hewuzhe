/*
 * Copyright 2014 trinea.cn All right reserved. This software is the confidential and proprietary information of
 * trinea.cn ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with trinea.cn.
 */
package com.hewuzhe.banner;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.hewuzhe.model.Bannar;
import com.hewuzhe.model.New;
import com.hewuzhe.model.Pic;
import com.hewuzhe.model.ProductPic;
import com.hewuzhe.ui.activity.BasicWebActivity;
import com.hewuzhe.ui.activity.MegaGameDetailActivity;
import com.hewuzhe.ui.activity.PicsActivity;
import com.hewuzhe.ui.activity.ProductInfoActivity;
import com.hewuzhe.utils.Bun;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.hewuzhe.R;

/**
 * @author http://blog.csdn.net/finddreams
 * @Description: 图片适配
 */
public class ImagePagerAdapter extends BaseAdapter {

    private Context context;
    private List<String> imageIdList;
    private List<String> linkUrlArray;
    private List<String> urlTitlesList;
    List<ProductPic> piclist;
    List<New> saiShiPics;
    private int size;
    private boolean isInfiniteLoop;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private boolean isshow;
    private List<Bannar> bannar;


    public ImagePagerAdapter(Context context, List<String> imageIdList,
                             List<String> urllist, List<String> urlTitlesList) {
        this.isshow = true;
        this.context = context;
        this.imageIdList = imageIdList;
        if (imageIdList != null) {
            this.size = imageIdList.size();
        }
        this.linkUrlArray = urllist;
        this.urlTitlesList = urlTitlesList;
        isInfiniteLoop = false;
        // 初始化imageLoader 否则会报
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.nopic) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.nopic) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.nopic) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                .build();


    }

    public ImagePagerAdapter(Context context, List<String> imageIdList,
                             List<String> urllist, List<String> urlTitlesList, List<Bannar> bannar) {
        this.bannar = bannar;
        this.isshow = false;
        this.context = context;
        this.imageIdList = imageIdList;
        if (imageIdList != null) {
            this.size = imageIdList.size();
        }
        this.linkUrlArray = urllist;
        this.urlTitlesList = urlTitlesList;
        isInfiniteLoop = false;
        // 初始化imageLoader 否则会报
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.nopic) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.nopic) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.nopic) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                .build();


    }

    public ImagePagerAdapter(Context context, List<String> imageIdList,
                             List<String> urllist, List<String> urlTitlesList, List<ProductPic> piclist, boolean isshow) {
        this.isshow = isshow;
        this.piclist = piclist;
        this.context = context;
        this.imageIdList = imageIdList;
        if (imageIdList != null) {
            this.size = imageIdList.size();
        }
        this.linkUrlArray = urllist;
        this.urlTitlesList = urlTitlesList;
        isInfiniteLoop = false;
        // 初始化imageLoader 否则会报
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.nopic) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.nopic) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.nopic) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                .build();


    }

    public ImagePagerAdapter(Context context, List<String> imageIdList,
                             List<String> urllist, List<String> urlTitlesList, List<New> saiShiPics, int size) {
//		this.isshow=isshow;
        this.saiShiPics = saiShiPics;
        this.context = context;
        this.size = size;
        this.imageIdList = imageIdList;
        if (imageIdList != null) {
            this.size = imageIdList.size();
        }
        this.linkUrlArray = urllist;
        this.urlTitlesList = urlTitlesList;
        isInfiniteLoop = false;
        // 初始化imageLoader 否则会报
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.nopic) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.nopic) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.nopic) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                .build();

    }

    //图片合集
    private void addImg(int pos) {
        ArrayList<Pic> newPics = new ArrayList<Pic>();

        for (int i = 0; i < piclist.size(); i++) {
            Pic pic = new Pic();
            pic.PictureUrl = piclist.get(i).Path;
            pic.ImagePath = "";
            newPics.add(pic);
        }

        Intent intent = new Intent(context, PicsActivity.class);
        intent.putExtra("data", new Bun().putInt("pos", pos).
                putString("pics", new Gson().toJson(newPics)).ok());
        context.startActivity(intent);
    }

    @Override
    public int getCount() {
        // Infinite loop
        return isInfiniteLoop ? Integer.MAX_VALUE : imageIdList.size();
    }

    /**
     * get really position
     *
     * @param position
     * @return
     */
    private int getPosition(int position) {
        return isInfiniteLoop ? position % size : position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup container) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = holder.imageView = new ImageView(context);
            holder.imageView
                    .setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        imageLoader.displayImage(
                (String) this.imageIdList.get(getPosition(position)),
                holder.imageView, options);

        holder.imageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String url = linkUrlArray.get(ImagePagerAdapter.this
                        .getPosition(position));
                String title = urlTitlesList.get(ImagePagerAdapter.this
                        .getPosition(position));
                /*
                 * if (TextUtils.isEmpty(url)) {
				 * holder.imageView.setEnabled(false); return; }
				 */

                if (!TextUtils.isEmpty(url)) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    if (!url.contains("http://"))
                        url="http://"+url;
                    intent.setData(Uri.parse(url));
                    context.startActivity(intent);
                    return;
                }
//				Toast.makeText(context, "点击了第" + getPosition(position) + "个图片",
//						Toast.LENGTH_SHORT).show();


                if (isshow)
                    addImg(getPosition(position));
                else if (bannar != null) {//点击购物里面的轮播图跳转
                    context.startActivity(new Intent(context, ProductInfoActivity.class).putExtra("data", new Bun().putInt("proid", bannar.get(getPosition(position)).getProductId()).ok()));
                } else if (saiShiPics != null) {//点击赛事轮播图进入赛事详情
//                    int id = saiShiPics.get(getPosition(position)).Id;
                    context.startActivity(new Intent(context, MegaGameDetailActivity.class).putExtra("data", new Bun().putInt("id", saiShiPics.get(getPosition(position)).Id).ok()));
                }
            }
        });
        return view;
    }

    private static class ViewHolder {

        ImageView imageView;
    }

    /**
     * @return the isInfiniteLoop
     */
    public boolean isInfiniteLoop() {
        return isInfiniteLoop;
    }

    /**
     * @param isInfiniteLoop the isInfiniteLoop to set
     */
    public ImagePagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
        return this;
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

}

