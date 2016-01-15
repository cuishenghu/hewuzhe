

package com.hewuzhe.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Base64;

import com.mob.tools.MobLog;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Sharepreference 常用的操作类
 */
public class SPUtil {
    private Context context;
    private SharedPreferences prefrence;

    public SPUtil(Context c) {
        this.context = c.getApplicationContext();
    }

    public SPUtil open(String name) {
        this.open(name, 0);
        return this;

    }

    public SPUtil open(String name, int version) {
        String fileName = name + "_" + version;
        this.prefrence = this.context.getSharedPreferences(fileName, 0);
        return this;
    }

    public SPUtil putString(String key, String value) {
        Editor editor = this.prefrence.edit();
        editor.putString(key, value);
        editor.commit();

        return this;
    }

    public String getString(String key) {
        return this.prefrence.getString(key, "");
    }

    public SPUtil putBoolean(String key, Boolean value) {
        Editor editor = this.prefrence.edit();
        editor.putBoolean(key, value.booleanValue());
        editor.commit();
        return this;
    }

    public boolean getBoolean(String key) {
        return this.prefrence.getBoolean(key, false);
    }

    public void putLong(String key, Long value) {
        Editor editor = this.prefrence.edit();
        editor.putLong(key, value.longValue());
        editor.commit();
    }

    public long getLong(String key) {
        return this.prefrence.getLong(key, 0L);
    }

    public SPUtil putInt(String key, Integer value) {
        Editor editor = this.prefrence.edit();
        editor.putInt(key, value.intValue());
        editor.commit();

        return this;
    }

    public int getInt(String key) {
        return this.prefrence.getInt(key, 0);
    }

    public void putFloat(String key, Float value) {
        Editor editor = this.prefrence.edit();
        editor.putFloat(key, value.floatValue());
        editor.commit();
    }

    public float getFloat(String key) {
        return this.prefrence.getFloat(key, 0.0F);
    }

    public void put(String key, Object value) {
        if (value != null) {
            try {
                ByteArrayOutputStream t = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(t);
                oos.writeObject(value);
                oos.flush();
                oos.close();
                byte[] data = t.toByteArray();
                String base64 = Base64.encodeToString(data, 2);
                this.putString(key, base64);
            } catch (Throwable var7) {
                MobLog.getInstance().w(var7);
            }

        }
    }

    public Object get(String key) {
        try {
            String t = this.getString(key);
            if (TextUtils.isEmpty(t)) {
                return null;
            } else {
                byte[] data = Base64.decode(t, 2);
                ByteArrayInputStream bais = new ByteArrayInputStream(data);
                ObjectInputStream ois = new ObjectInputStream(bais);
                Object value = ois.readObject();
                ois.close();
                return value;
            }
        } catch (Throwable var7) {
            MobLog.getInstance().w(var7);
            return null;
        }
    }

    public void remove(String key) {
        Editor editor = this.prefrence.edit();
        editor.remove(key);
        editor.commit();
    }

}
