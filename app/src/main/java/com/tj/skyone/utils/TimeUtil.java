package com.tj.skyone.utils;

import com.blankj.utilcode.util.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @describe 时间工具类
 * @anthor zhaoyong
 * @time 2019/3/11 14:41
 */
public class TimeUtil {
    public static final SimpleDateFormat FORMAT1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat FORMAT2 = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat FORMAT3 = new SimpleDateFormat("yyyy年MM月dd日");
    public static final SimpleDateFormat FORMAT33 = new SimpleDateFormat("yyyy/MM/dd");
    public static final SimpleDateFormat FORMAT4 = new SimpleDateFormat("HH:mm");

    public static final SimpleDateFormat FORMAT5 = new SimpleDateFormat("MM月dd日");





    public static String dateToStrings (){

        return TimeUtils.date2String(TimeUtils.getNowDate(),FORMAT33);
    }
    public static String dateToString (){

        return TimeUtils.date2String(TimeUtils.getNowDate(),FORMAT3);
    }

    public static String dateToStrings (String date){

        return TimeUtils.date2String(DateUtils.parseDate(date, DateUtils.DATE),FORMAT5);

    }

    public static String timeToString (){

        return TimeUtils.date2String(TimeUtils.getNowDate(),FORMAT4);

    }

    public static String timeStyle(String date){

//        String  time = TimeUtils.date2String(TimeUtils.getNowDate(),FORMAT4);
       int hour  =  DateUtils.parseDate(date).getHours();

       String style ="";

        if (hour >= 0 && hour < 12) {

            style = "上午";

        } else if (hour >= 12) {

            style = "下午";

        }

        String  time = TimeUtils.date2String(DateUtils.parseDate(date),FORMAT4);

        return style + "  " + time;
    }

    /**
     * long转标标准时间
     */
    public static String long2String(long time, SimpleDateFormat format) {
        try {
            if (time == 0) return "";
            Date date = new Date(time);
            return format.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 标准时间转long
     */
    public static long string2Long(SimpleDateFormat format, String time) {
        try {
            Date date = format.parse(time);
            return date.getTime();
        } catch (Exception e) {
            return 0;
        }
    }

    //前几天
    public static String getDayAgo(Date date, int day, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        calendar.add(Calendar.DATE, day);
        return sdf.format(calendar.getTime());
    }

    //前几天
    public static String getDayAgo1(Date date, int day, SimpleDateFormat format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        calendar.add(Calendar.DATE, day);
        return format.format(calendar.getTime());
    }

    //上/下一周的当日
    public static String getNextWeek(Date date, int week, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 7 * week);
        return sdf.format(calendar.getTime());
    }

    //上/下一周的当日
    public static String getNextWeek1(Date date, int week, SimpleDateFormat format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 7 * week);
        return format.format(calendar.getTime());
    }

    //周的开始日期
    public static String getWeekStart(Date date, int week, SimpleDateFormat format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 7 * week);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);//星期日
        String start = format.format(calendar.getTime());
        return start;
    }

    //周的结束日期
    public static String getWeekEnd(Date date, int week, SimpleDateFormat format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 7 * week);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);//星期六
        String end = format.format(calendar.getTime());
        return end;
    }

    //前几周
    public static String getWeekAgo(Date date, int week, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 7 * week);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);//星期日
        String start = sdf.format(calendar.getTime());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);//星期六
        String end = sdf.format(calendar.getTime());
        return start + "/" + end;
    }

    //前几周
    public static String getWeekAgo1(Date date, int week, SimpleDateFormat format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 7 * week);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);//星期日
        String start = format.format(calendar.getTime());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);//星期六
        String end = format.format(calendar.getTime());
        return start + "/" + end;
    }

    //前几周
    public static String getMonthAgo(Date date, int month, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        calendar.add(Calendar.MONTH, month);
        return sdf.format(calendar.getTime());
    }

    //前几周
    public static String getMonthAgo1(Date date, int month, SimpleDateFormat format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        calendar.add(Calendar.MONTH, month);
        return format.format(calendar.getTime());
    }
}
