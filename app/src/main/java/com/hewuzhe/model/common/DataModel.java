package com.hewuzhe.model.common;

import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 * Created by xianguangjin on 16/1/2.
 */
public class DataModel<T, M> {
    public String title="";
    public String content="";

    @Nullable
    public M m;

    @Nullable
    public ArrayList<T> list;


}
