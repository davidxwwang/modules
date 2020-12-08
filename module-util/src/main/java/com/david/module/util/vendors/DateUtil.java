package com.david.module.util.vendors;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * 关于日期的
 */
public class DateUtil {

    TimeZone china = TimeZone.getTimeZone("GMT+:-8:00");

    static final String format1 = "yyyy-MM-dd HH:mm:ss";

    private static SimpleDateFormat simpleDateFormat;

    private static SimpleDateFormat getSimpleDateFormat() {

        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat();
        }
        return simpleDateFormat;
    }


    public static Date parseString(String dateStr, String parttern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(parttern);

        try {
            Date date = simpleDateFormat.parse(dateStr);
            return date;
        } catch (ParseException e) {
            return null;
        }
    }

    public static String parseDate(Date date, String parttern) {
        if (date == null) {
            return null;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(parttern);
        return simpleDateFormat.format(date);

    }

    /**
     * 获取一天的开始
     *
     * @return
     */
    public static Date getBeginningOfday() {

        return getBeginningOfday(new Date());
    }

    public static String getWeek(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
        String week = simpleDateFormat.format(date);
        return week;
    }

    public static Date getBeginningOfday(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();

    }

    /**
     * 获取一天的结束
     *
     * @return
     */
    public static Date getEndOfday() {
        return getEndOfday(new Date());
    }

    public static Date getEndOfday(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        return calendar.getTime();

    }

    /**
     * 几天后的日期
     *
     * @param date
     * @param day  > 0 几天后 ； < 0 几天前
     * @return
     */
    public static Date getEndOfday(Date date, Integer day) {
        if (day == null) {
            return date;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + day);

        return calendar.getTime();
    }


    public static String getDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        Integer year = calendar.get(Calendar.YEAR);
        Integer month = calendar.get(Calendar.MONTH) + 1;
        Integer day = calendar.get(Calendar.MONDAY);
        Integer week = calendar.get(Calendar.WEEK_OF_MONTH);
        // ...
        return "todo";
    }


}
