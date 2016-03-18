package framework.picker;

import android.app.Activity;

import framework.util.DateUtils;


/**
 * 月份选择器
 *
 * @author 李玉江[QQ:1032694760]
 * @since 2015/12/15
 * Created By Android Studio
 */
public class MonthPicker extends OptionPicker {

    public MonthPicker(Activity activity) {
        super(activity, new String[]{});
        for (int i = 1; i <= 12; i++) {
            options.add(DateUtils.fillZero(i));
        }
    }

}