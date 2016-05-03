package com.hewuzhe.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;

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

    /**
     * <p>
     * 进行tostring操作，如果传入的是null，返回空字符串㿿
     * </p>
     *
     * <pre>
     * ObjectUtils.toString(null)         = ""
     * ObjectUtils.toString("")           = ""
     * ObjectUtils.toString("bat")        = "bat"
     * ObjectUtils.toString(Boolean.TRUE) = "true"
     * </pre>
     * @param obj
     *            溿
     * @return String
     */
    public static String toString(Object obj, String nullStr) {
        return obj == null ? nullStr : "".equals(obj) ? nullStr : obj.toString();
    }

    /**
     * 字符串验证
     * @param obj
     * @return 验证返回字符串
     */
    public static String toString(Object obj) {
        return null == obj?"":obj.toString();
    }


    /**
     * listView自适应高度
     * @param listView
     */
    public static void listAutoHeight(ListView listView){
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount()-1));
        ((ViewGroup.MarginLayoutParams)params).setMargins(0, 10, 0, 10);
        listView.setLayoutParams(params);
    }
    /**
     * 获取手机屏幕宽度
     * @param activity 当前Activity
     * @return 屏幕的宽度
     */
    public static int getScreenWidth(Activity activity){
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.widthPixels;
    }


    public static int getScreenHeight(Activity activity){
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.heightPixels;
    }
}
