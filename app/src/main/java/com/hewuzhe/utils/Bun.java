package com.hewuzhe.utils;

import android.os.Bundle;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 对Bundle进行响应式的封装
 * Created by xianguangjin on 15/12/23.
 */
public class Bun {

    private Bundle bundle;

    public Bun() {
        this.bundle = new Bundle();
    }

    public Bun putString(String name, String value) {
        this.bundle.putString(name, value);
        return this;
    }

    public Bun putInt(String name, int value) {
        this.bundle.putInt(name, value);
        return this;
    }

    public Bun putBoolean(String name, boolean value) {
        this.bundle.putBoolean(name, value);
        return this;
    }

    public Bun putSR(String name, Serializable value) {
        this.bundle.putSerializable(name, value);
        return this;
    }

    public Bun putP(String name, Parcelable value) {
        this.bundle.putParcelable(name, value);
        return this;
    }


    /**
     * @return 操作完成
     */
    public Bundle ok() {
        return this.bundle;
    }

}
