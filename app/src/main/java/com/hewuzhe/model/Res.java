package com.hewuzhe.model;

import android.support.annotation.Nullable;

/**
 * Created by xianguangjin on 15/12/14.
 */
public class Res<T> {

    /**
     * 数据
     */
    @Nullable
    public T data;

    @Nullable
    public T date;

    /**
     * 状态吗
     */
    public int code;

    /**
     * 数据总条数
     */
    @Nullable
    public int recordcount = 0;

    /**
     * 评论插入的ID
     */
    @Nullable
    public int insertid = 0;

    /**
     *
     */
    @Nullable
    public boolean UpdateRank;

    /**
     * 图片路径
     */
    @Nullable
    public String ImagePath;

    @Nullable
    public int count = 0;
}
