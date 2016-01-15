package com.hewuzhe.utils;

import android.content.Context;

import com.hewuzhe.ui.cons.C;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xianguangjin on 15/7/15.
 */
public class StringUtil {


    public static boolean isEmpty(String str) {

        return null == str || "".equals(str);
    }


    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,8,7][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();

        return b;
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static boolean equal(Object str, Object str_2) {
        return str.equals(str_2);
    }

    public static String getGender(int sexuality) {
        if (sexuality == C.GENDER_MALE) {
            return "男";
        }else  if (sexuality == C.GENDER_FEMALE) {
            return "女";
        }else  if (sexuality == C.GENDER_UN_KNOWN) {
            return "未知";
        }

        return "";
    }
}
