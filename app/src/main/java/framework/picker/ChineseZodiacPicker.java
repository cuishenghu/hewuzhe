package framework.picker;

import android.app.Activity;

/**
 * 生肖选择器
 *
 * @author 李玉江[QQ:1032694760]
 * @since 2015/12/15
 * Created By Android Studio
 */
public class ChineseZodiacPicker extends OptionPicker {

    public ChineseZodiacPicker(Activity activity) {
        super(activity, new String[]{
                "鼠",
                "牛",
                "虎",
                "兔",
                "龙",
                "蛇",
                "马",
                "羊",
                "猴",
                "鸡",
                "狗",
                "猪",
        });
    }

}
