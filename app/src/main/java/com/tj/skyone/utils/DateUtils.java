package com.tj.skyone.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by suntongwei on 16/6/17.
 */
public class DateUtils {
    public static final long ONE_SECOND_TIME = 1l * 1000;
    public static final long ONE_MINUTE_TIME = ONE_SECOND_TIME * 60;
    public static final long ONE_HOUR_TIME = ONE_MINUTE_TIME * 60;
    public static final long ONE_DAY_TIME = ONE_HOUR_TIME * 24;

    /**
     * 转换日期形式
     *
     * @param date
     * @param format
     * @return
     */
    public static final String DATE = "yyyy-MM-dd";
    public static final String TIME = "HH:mm:ss";
    public static final String TIME_S = "HH:mm";
    public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_S = "yyyy-MM-dd HH:mm";
    public static final String DATE_TIME_X = "yyyy/MM/dd HH:mm:ss";
    public static final String DATE_X = "yyyy/MM/dd";
    public static final String DATE_T = "yyyyMMddHHmmss";

    /**
     * 将时间字符串转为Date对象，时间字符串格式要求：yyyy-MM-dd HH:mm:ss
     *
     * @param str
     * @return
     */
    public static Date parseDate(String str) {
        return parseDate(str, DATE_TIME);
    }

    public static Date parseDate(String str, String format) {
        if (null == str)
            return null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date = new Date(sdf.parse(str).getTime());
            return date;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Long parseDateLong(String str) {
        return parseDateLong(str, DATE_TIME);
    }

    public static Long parseDateLong(String str, String format) {
        if (null == str)
            return null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(str).getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getOrderCreateTime(long time) {
        long t = System.currentTimeMillis() - time;
        for (int i = 6; i > 0; i--) {
            if (t > i * ONE_DAY_TIME) {
                return i + "天前 " + formatDate(time, TIME_S);
            }
        }
        return "今天 " + formatDate(time, TIME_S);
    }

    public static String getOrderExecTime(long time) {
        long t = time - System.currentTimeMillis();
        for (int i = 6; i > 0; i--) {
            if (t > i * ONE_DAY_TIME) {
                return i + "天后 " + formatDate(time, TIME_S);
            }
        }
        return "今天 " + formatDate(time, TIME_S);
    }

    public static final int RANGE_DAY = 0x0;
    public static final int RANGE_MONTH = 0x1;
    public static final int RANGE_YEAR = 0x2;

    /**
     * 取某一日期前后范围内的日期 直接调用格式为 DateUtil.getRangeDate("2010-01-01",-1,DateUtil.RANGE_DAY);
     * 第3个参数可以不输入
     *
     * @param date  日期格式(0000-00-00)
     * @param range 可以接受2个参数，一个为必须输入，第二个为可选参数 第一个参数表示时间前后，大于0表示后，小于0表示前
     *              第二个参数表示时间前后的年月日，0表示日，1表示月，2表示年，默认为0
     * @author suntongwei
     */
    public static Long getRangeDate(Long date, int... range) {

        try {
            if (null == date || "".equals(date))
                return null;

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(date);

            int rangeflag = 0;
            if (range.length > 1)
                rangeflag = range[1];

            if (rangeflag == RANGE_DAY)
                cal.add(Calendar.DATE, range[0]);
            else if (rangeflag == RANGE_MONTH)
                cal.add(Calendar.MONTH, range[0]);
            else if (rangeflag == RANGE_YEAR)
                cal.add(Calendar.YEAR, range[0]);
            else
                return null;

            return cal.getTimeInMillis();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getRangeDate(String date, int... range) {
        return formatDateTime(getRangeDate(parseDate(date).getTime(), range));
    }

    /**
     * 剩余时间
     *
     * @return
     */
    public static String getRemainderTime(long time) {
        String ret = "";
        if (time > ONE_DAY_TIME) {
            int dayNum = (int) (time / ONE_DAY_TIME);
            ret += dayNum + "天";
            time -= dayNum * ONE_DAY_TIME;
        }
        if (time > ONE_HOUR_TIME) {
            int hourNum = (int) (time / ONE_HOUR_TIME);
            ret += hourNum + "小时";
            time -= hourNum * ONE_HOUR_TIME;
        }
        if (time > ONE_MINUTE_TIME) {
            int minuteNum = (int) (time / ONE_MINUTE_TIME);
            ret += minuteNum + "分钟";
            time -= minuteNum * ONE_MINUTE_TIME;
        }
        if (time > ONE_SECOND_TIME) {
            int secondNum = (int) (time / ONE_SECOND_TIME);
            ret += secondNum + "秒";
//            time -= secondNum * ONE_SECOND_TIME;
        }
        return ret;
    }

    public static String getReplyTime(long time) {
        long t = System.currentTimeMillis() - time;
        // 一个月以前
        if (t > ONE_DAY_TIME * 30) {
            return "1月前";
        }
        // 三周以前
        if (t > ONE_DAY_TIME * 21) {
            return "3周前";
        }
        // 三周以前
        if (t > ONE_DAY_TIME * 14) {
            return "2周前";
        }
        // 三周以前
        if (t > ONE_DAY_TIME * 7) {
            return "1周前";
        }
        for (int i = 6; i > 0; i--) {
            if (t > i * ONE_DAY_TIME) {
                return i + "天前";
            }
        }
        return "今天";
    }

    /**
     * 返回消息时间字符串
     *
     * @param time
     * @return
     */
    public static String getMessageTime(long time) {
        String timeBeforStr = "", timeStr = "";
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        // 判断是否是当天
        if (isDate(formatDate(time), getNowDate())) {
            if (cal.get(Calendar.HOUR_OF_DAY) > 12) {
                timeBeforStr = "下午";
            } else {
                timeBeforStr = "上午";
            }
        } else {
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            switch (dayOfWeek) {
                case Calendar.SUNDAY:
                    timeBeforStr = "周日";
                    break;
                case Calendar.MONDAY:
                    timeBeforStr = "周一";
                    break;
                case Calendar.TUESDAY:
                    timeBeforStr = "周二";
                    break;
                case Calendar.WEDNESDAY:
                    timeBeforStr = "周三";
                    break;
                case Calendar.THURSDAY:
                    timeBeforStr = "周四";
                    break;
                case Calendar.FRIDAY:
                    timeBeforStr = "周五";
                    break;
                case Calendar.SATURDAY:
                    timeBeforStr = "周六";
                    break;
                default:
                    break;
            }
        }
        timeStr = formatDate(time, "HH:mm");
        return timeBeforStr + " " + timeStr;
    }


    /**
     * 比较日期是否相等,参数个数无限制，可以比较多个时间是否是同一天 ,日期用-间隔 调用方法
     * StringTool.isDate("2000-01-01","2000-1-1");
     */
    public static boolean isDate(String... date) {
        if (date.length < 1)
            return false;
        String val = date[0];
        for (int i = 1; i < date.length; i++)
            if (!val.equals(date[i]))
                return false;
        return true;
    }

    /**
     * 判断是否是今天
     *
     * @return
     */
    public static boolean isToday(long time) {
        return isDate(formatDate(time), formatDate(System.currentTimeMillis()));
    }

    public static boolean isToday(Calendar cal) {
        Calendar today = Calendar.getInstance();
        return cal.get(Calendar.YEAR) == today.get(Calendar.YEAR)
                && cal.get(Calendar.MONTH) == today.get(Calendar.MONTH)
                && cal.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH);
    }

    public static String formatDate(Date date) {
        return DateUtils.formatDate(date, DATE);
    }

    public static String formatDate(Long date) {
        Date d = new Date(date);
        return DateUtils.formatDate(d);
    }

    public static String formatTime(Date date) {
        return DateUtils.formatDate(date, TIME);
    }

    public static String formatTime(Long date) {
        Date d = new Date(date);
        return DateUtils.formatTime(d);
    }

    public static String formatDateTime(Date date) {
        return DateUtils.formatDate(date, DATE_TIME);
    }

    public static String formatDateTime(Long date) {
        Date d = new Date(date);
        return DateUtils.formatDateTime(d);
    }

    public static String formatDate(Date date, String type) {
        SimpleDateFormat sdf = new SimpleDateFormat(type);
        return sdf.format(date);
    }

    public static String formatDate(Long date, String type) {
        SimpleDateFormat sdf = new SimpleDateFormat(type);
        return sdf.format(date);
    }

    public static String formatStringTime(String dateTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return output.format(date);
    }


    /**
     * 获取当前日期 yyyy-MM-dd
     */
    public static String getNowDate() {
        Calendar cal = Calendar.getInstance();
        String ret = cal.get(Calendar.YEAR) + "-"
                + (cal.get(Calendar.MONTH) + 1) + "-"
                + cal.get(Calendar.DAY_OF_MONTH);
        return ret;
    }

    /**
     * 获取当前时间 HH:mm:ss
     */
    public static String getNowTime() {
        Calendar cal = Calendar.getInstance();
        String ret = cal.get(Calendar.HOUR_OF_DAY) + ":"
                + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
        return ret;
    }

    /**
     * 获取日期+时间 yyyy-MM-dd HH:mm:ss
     */
    public static String getFullNowTime() {

        Calendar cal = Calendar.getInstance();

        String ret = cal.get(Calendar.YEAR) + "-"
                + (cal.get(Calendar.MONTH) + 1) + "-"
                + cal.get(Calendar.DAY_OF_MONTH) + " "
                + cal.get(Calendar.HOUR_OF_DAY) + ":"
                + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);

        return ret;
    }

    /**
     * 将秒转化成小时分钟秒
     * @param miss
     * @param isShowZN 是否显示中文
     * @return
     */
    public static String formatMiss(long miss, boolean isShowZN){

        String hh = miss / 3600 > 9 ? miss / 3600 + "" : "0" + miss / 3600;

        String mm = (miss % 3600) / 60 > 9 ? (miss % 3600) / 60 + "" : "0" + (miss % 3600)/60;

        String ss = (miss % 3600) % 60 > 9 ? (miss % 3600) % 60+"" : "0" + (miss % 3600) % 60;

        if (isShowZN) return hh+"小时" + mm + "分钟";
        else return hh+" : "+mm+" : "+ss;
    }

    /**
     * 将时分秒转成秒
     * @param seconds
     * @return
     */
    public static long formatSeconds(String seconds){
        String[] array = seconds.split(":");
        String hour = array[0];
        String minute = array[1];
        String second = array[2];
        return Integer.parseInt(hour) * 60 * 60 + Integer.parseInt(minute) * 60 + Integer.parseInt(second);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 指定格式返回当前系统时间
     */
    public static String getDataTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }
}
