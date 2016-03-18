package com.hewuzhe.utils;

import android.text.format.Time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间操作类
 */
public class TimeUtil {

    public static long MINUTE = 1000 * 60;
    public static long HOUR = 1000 * 60 * 60;
    public static long DAY = 1000 * 60 * 60 * 24;
    private static String hoursString = "";
    private static String minutesString = "";
    private static String secondsString = "";

    public static int getCurrentYear() {
        Time t = new Time();
        t.setToNow();
        return t.year;
    }

    public static int getCurrentMonth() {
        Time t = new Time();
        t.setToNow();
        return t.month + 1;
    }

    public static int getCurrentDay() {
        Time t = new Time();
        t.setToNow();
        return t.monthDay;
    }

    /**
     * @return 今天的格式化
     */
    public static String getCurrentDayFormat() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date currentTime = new Date();
        return format.format(currentTime);
    }

    /**
     * @param day
     * @return day天之后的格式化
     */
    public static String getDayAfterFormat(int day) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date currentTime = new Date();
        currentTime.setTime(currentTime.getTime() + DAY * day);
        return format.format(currentTime);
    }


    public static String timeAgo(String timeStr) {
        Date date = null;
        if (StringUtil.isEmpty(timeStr)) {
            return "";
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = format.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }

        long timeStamp = date.getTime();

        Date currentTime = new Date();
        long currentTimeStamp = currentTime.getTime();
        long seconds = (currentTimeStamp - timeStamp) / 1000;

        long minutes = Math.abs(seconds / 60);
        long hours = Math.abs(minutes / 60);
        long days = Math.abs(hours / 24);


        if (seconds <= 15) {
            return "刚刚";
        } else if (seconds < 60) {
            return seconds + "秒前";
        } else if (seconds < 120) {
            return "1分钟前";
        } else if (minutes < 60) {
            return minutes + "分钟前";
        } else if (minutes < 120) {
            return "1小时前";
        } else if (hours < 24) {
            return hours + "小时前";
        } else if (hours < 24 * 2) {
            return "1天前";
        } else if (days < 30) {
            return days + "天前";
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
            String dateString = formatter.format(date);
            return dateString;
        }

    }

    public static String timeLeft(String timeStr) {
        Date date = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = format.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }


        long timeStamp = date.getTime();

        Date currentTime = new Date();
        long currentTimeStamp = currentTime.getTime();

        long total_seconds = (timeStamp - currentTimeStamp) / 1000;

        if (total_seconds <= 0) {
            return "";
        }

        long days = Math.abs(total_seconds / (24 * 60 * 60));

        long hours = Math.abs((total_seconds - days * 24 * 60 * 60) / (60 * 60));
        long minutes = Math.abs((total_seconds - days * 24 * 60 * 60 - hours * 60 * 60) / 60);
        long seconds = Math.abs((total_seconds - days * 24 * 60 * 60 - hours * 60 * 60 - minutes * 60));
        String leftTime;
        if (days > 0) {
            leftTime = days + "天" + hours + "小时" + minutes + "分" + seconds + "秒";
        } else if (hours > 0) {
            leftTime = hours + "小时" + minutes + "分" + seconds + "秒";
        } else if (minutes > 0) {
            leftTime = minutes + "分" + seconds + "秒";
        } else if (seconds > 0) {
            leftTime = seconds + "秒";
        } else {
            leftTime = "0秒";
        }

        return leftTime;
    }


    /**
     * @param timeStr 已经过了多长时间
     * @return
     */
    public static String timeHaved(String timeStr) {
        Date date = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            date = format.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }

        long timeStamp = date.getTime();

        Date currentTime = new Date();
        long currentTimeStamp = currentTime.getTime();

        long total = currentTimeStamp - timeStamp;

        if (total <= 0) {
            return "";
        }

        int years = Math.round(total / (DAY * 365));

        return years + "";
    }


    /**
     * @param timeStr 已经过了多长时间
     * @return
     */
    public static String timeHavedDay(String timeStr) {
        Date date = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            date = format.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }

        long timeStamp = date.getTime();

        Date currentTime = new Date();
        long currentTimeStamp = currentTime.getTime();

        long total = currentTimeStamp - timeStamp;

        if (total <= 0) {
            return "";
        }

        int days = Math.round(total / (DAY));

        return days + "";
    }

    public static String timeFormat(long time) {
        Date date = null;


        long timeStamp = time;


        long total_seconds = timeStamp / 1000;

        if (total_seconds <= 0) {
            return "";
        }

        long hours = Math.abs(time / HOUR);
        long minutes = Math.abs((time - hours * HOUR) / MINUTE);
        long seconds = Math.abs((time - (minutes * MINUTE + hours * HOUR)) / 1000);

        if (hours < 10) {
            hoursString = "0" + hours;
        } else {
            hoursString = "" + hours;

        }
        if (minutes < 10) {
            minutesString = "0" + minutes;
        } else {
            minutesString = "" + minutes;

        }
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = seconds+"";
        }
        String leftTime = hoursString + ":" + minutesString + ":" + secondsString;

        return leftTime;
    }


    public static String timeFormatTwo(String timeStr) {
        Date date = null;
        if (StringUtil.isEmpty(timeStr)) {
            return "";
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = format.parse(timeStr);
            SimpleDateFormat formatReverse = new SimpleDateFormat("yyyy-MM-dd");
            return formatReverse.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }


    public static String timeFormatThree(String timeStr) {
        Date date = null;
        if (StringUtil.isEmpty(timeStr)) {
            return "";
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = format.parse(timeStr);
            SimpleDateFormat formatReverse = new SimpleDateFormat("HH:mm");
            return formatReverse.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String timeFormatFour(String timeStr) {
        Date date = null;
        if (StringUtil.isEmpty(timeStr)) {
            return "";
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = format.parse(timeStr);
            SimpleDateFormat formatReverse = new SimpleDateFormat("MM-dd HH:mm");
            return formatReverse.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String timeFormatFive(String timeStr) {
        Date date = null;
        if (StringUtil.isEmpty(timeStr)) {
            return "";
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = format.parse(timeStr);
            SimpleDateFormat formatReverse = new SimpleDateFormat("MM-dHH:mm");
            return formatReverse.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * @param timeStr
     * @return 传入的比当前时间大为True
     */
    public static boolean timeComparedNow(String timeStr) {
        Date date = null;
        if (StringUtil.isEmpty(timeStr)) {
            return false;
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = format.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        long timeStamp = date.getTime();

        Date currentTime = new Date();
        long currentTimeStamp = currentTime.getTime();

        return timeStamp > currentTimeStamp;
    }


    /**
     * @param timeStr
     * @return 传入的比当前时间大为True
     */
    public static long timeGap(String timeStr) {
        Date date = null;
        if (StringUtil.isEmpty(timeStr)) {
            return 0;
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = format.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }

        long timeStamp = date.getTime();

        Date currentTime = new Date();
        long currentTimeStamp = currentTime.getTime();

        return timeStamp - currentTimeStamp;
    }


}
