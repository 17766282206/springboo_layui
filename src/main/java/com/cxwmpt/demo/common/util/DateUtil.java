package com.cxwmpt.demo.common.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 2018/11/15 Create by 郭文梁
 * DateUtil
 * 时间日期工具类
 *
 * @author 郭文梁
 * @date 2018/11/15
 */
public class DateUtil {
    /**
     * 获取现在时间
     *
     * @return Date
     */
    public static Date now() {
        return new Date();
    }

    /**
     * 获取昨天日期
     *
     * @param date 当前日期
     * @return 昨天日期
     */
    public static Date yesterday(Date date) {
        Calendar calendar = getCalendar(date);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }

    /**
     * 本月第一天
     *
     * @param date 时间
     * @return 本月第一天日期
     */
    public static Date firstDayOfMonth(Date date) {
        Calendar calendar = getCalendar(date);
        clearTime(calendar);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 本月最后一天
     *
     * @param date 日期
     * @return 最后一天
     */
    public static Date lastDayOfMonth(Date date) {
        Calendar calendar = getCalendar(date);
        clearTime(calendar);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     * 清空时间
     *
     * @param calendar 日历
     */
    private static void clearTime(Calendar calendar) {
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    /**
     * 获取日期为当月第几天
     *
     * @param date 日期
     * @return 天数
     */
    public static int getDayOfMonth(Date date) {
        Calendar calendar = getCalendar(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当月有多少天
     *
     * @param date 日期
     * @return 天数
     */
    public static int maxDayOfMonth(Date date) {
        Calendar calendar = getCalendar(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取日历工具
     *
     * @param date 日期
     * @return 日历
     */
    private static Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Shanghai"));
        calendar.setTime(date);
        return calendar;
    }

    /**
     * data转年月日String
     *
     * @param date
     * @return
     */
    public static String dateChangeString(Date date) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * data转年月日时分秒String
     *
     * @param date
     * @return
     */
    public static String dateTimeChangeString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * String转年月日
     *
     * @param
     * @return
     */
    public static Date stringChangeDateTime(String str) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(str, pos);
        return strtodate;
    }

    /**
     * String转年月日时分秒
     *
     * @param
     * @return
     */
    public static Date stringChangeDate(String str) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(str, pos);
        return strtodate;
    }

    /**
     * 获取当前网络时间年月日
     *
     * @return
     * @throws IOException
     */
    public static String NOWTime() throws IOException {

        URL url = new URL("http://www.taobao.com");// 取得资源对象
        try {
            URLConnection conn = url.openConnection();// 生成连接对象
            conn.connect();// 发出连接
            long dateL = conn.getDate();// 读取网站日期时间
            Date date = new Date(dateL);// 转换为标准时间对象
            SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
            System.out.println("成功");
            return dateFormat.format(date);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);// 输出北京时间
            return sdf.format(date);
        } catch (IOException e) {
            e.printStackTrace();
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);// 输出北京时间
            return sdf.format(date);
        }
    }

    public static String NOWDateTime() throws IOException {
        URL url = new URL("http://www.taobao.com");// 取得资源对象
        try {
            URLConnection conn = url.openConnection();// 生成连接对象
            conn.connect();// 发出连接
            long dateL = conn.getDate();// 读取网站日期时间
            Date date = new Date(dateL);// 转换为标准时间对象
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:s");
            return dateFormat.format(date);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:s", Locale.CHINA);// 输出北京时间
            return sdf.format(date);
        } catch (IOException e) {
            e.printStackTrace();
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:s", Locale.CHINA);// 输出北京时间
            return sdf.format(date);
        }
    }

    /**
     * 获取系统年月日string
     *
     * @return
     */
    public static String localDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }


    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串
     * @param
     * @return
     */
    public static String timeStampToStringDate(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }
    public static Date timeStampToDate(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return null;
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return new Date(Long.valueOf(seconds + "000"));
    }
    /**
     * 日期格式字符串转换成时间戳
     * @param date_str 字符串日期
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2TimeStamp(String date_str,String format){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime()/1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 取得当前时间戳（精确到秒）
     * @return
     */
    public static String timeStamp(){
        long time = System.currentTimeMillis();
        String t = String.valueOf(time/1000);
        return t;
    }

    /**
     * 时间戳转时间
     * @param s
     * @return
     */
    public static Date  stampToDate(String s){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        long lt = new Long(s);

        Date date = new Date(lt);

        //String res = simpleDateFormat.format(date);

        return date;

    }
    /**
     * 获取当月几天
     * @return
     */
    public static int getDayOfMonth(){
        LocalDate today = LocalDate.now();
        //本月的第一天
        LocalDate firstday = LocalDate.of(today.getYear(),today.getMonth(),1);
        //本月的最后一天
        LocalDate lastDay =today.with(TemporalAdjusters.lastDayOfMonth());

        //System.out.println(firstday.getMonth().getValue()+"月");
        //System.out.println("最后一天："+lastDay.getDayOfMonth());
        return lastDay.getDayOfMonth();
    }
    public static void main(String[] args) throws Exception {
        System.out.println(stampToDate("1589850471105"));

    }


}
