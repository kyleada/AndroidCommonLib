package me.kkwang.commonlib.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtils {

// yyyy-MM-dd hh:mm:ss 12小时制
// yyyy-MM-dd HH:mm:ss 24小时制

    public static final String TYPE_01 = "yyyy-MM-dd HH:mm:ss";

    public static final String TYPE_02 = "yyyy-MM-dd";

    public static final String TYPE_03 = "HH:mm:ss";

    public static final String TYPE_04 = "yyyy年MM月dd日";

    private static final int MINUTE = 60;
    private static final int HOUR = MINUTE * 60;
    private static final int DAY = HOUR * 24;
    private static final int MONTH = DAY * 30;
    private static final int YEAR = MONTH * 12;

    private static final DateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String formatDate(long time, String format) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        return new SimpleDateFormat(format).format(cal.getTime());
    }

    public static String formatDate(String longStr, String format) {
        try {
            return formatDate(Long.parseLong(longStr), format);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static long formatStr(String timeStr, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(timeStr).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    //格式要求是上述"yyyy-MM-dd HH:mm:ss"
    public static final String trimp2YMD(String timeStr){
        return timeStr.substring(0,"2016-12-02".length());
    }

    public static String convertPublishTimeStamp(long time) {

        long s = TimeUnit.MILLISECONDS.toSeconds(
                new Date().getTime() - time);

        long count = s / YEAR;
        if (count != 0) {
            return count + "年前";
        }

        count = s / MONTH;
        if (count != 0) {
            return count + "月前";
        }

        count = s / DAY;
        if (count != 0) {
            return count + "天前";
        }

        count = s / HOUR;
        if (count != 0) {
            return count + "小时前";
        }

        count = s / MINUTE;
        if (count != 0) {
            return count + "分钟前";
        }

        return "刚刚";


    }

    public static String convertPublishTime(String time) {
        try {
            return convertPublishTimeStamp(FORMAT.parse(time).getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "未知时间";
    }

    public static boolean withinDays(String time, int days) {
        try {
            long s = TimeUnit.MILLISECONDS.toSeconds(
                    new Date().getTime() - FORMAT.parse(time).getTime());
            long count = s / DAY;
            if (0 < count && count <= days) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int compareTime(String a, String b) {
        try {
            long timeA = TimeUnit.MILLISECONDS.toSeconds(
                    new Date().getTime() - FORMAT.parse(a).getTime());
            long timeB = TimeUnit.MILLISECONDS.toSeconds(
                    new Date().getTime() - FORMAT.parse(b).getTime());

            return timeA >= timeB ? 1 : -1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}