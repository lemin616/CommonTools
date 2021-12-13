package com.lemin.commontools.helper;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;



public class DateHelper {


    public static String getLocalTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
       //获取当前时间
        Date date = new Date(System.currentTimeMillis());
       return  simpleDateFormat.format(date);
    }

    public static String getTimeStamp() {
        long time = System.currentTimeMillis() / 1000;//获取系统时间的10位的时间戳
        String str = String.valueOf(time);
        return str;
    }

    /**
     * 获取当前时间戳
     */
    public static String getTime() {
        long timeStamp = System.currentTimeMillis();
        return toStamp(timeStamp);
    }

    /**
     * 将时间转换为时间戳
     */
    @SuppressLint("SimpleDateFormat")
    public static String dateToStamp(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts = date.getTime();
        return String.valueOf(ts);
    }

    /**
     * 将时间戳转换为时间 yyyy-MM-dd
     */
    @SuppressLint("SimpleDateFormat")
    public static String stampToDateYMD(Integer timeMillis) {
        return stampToDateYMD(Long.valueOf(timeMillis));
    }

    /**
     * 将时间戳转换为时间 yyyy-MM-dd
     */
    @SuppressLint("SimpleDateFormat")
    public static String stampToDateYMD(Long timeMillis) {
        if (StringHelper.isNull(timeMillis)) return "0000-00-00";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Long temp = timeMillis * 1000;
        Date date = new Date(temp);
        return simpleDateFormat.format(date);
    }

    /**
     * 将时间戳转换为时间 yyyy-MM-dd HH:mm:ss
     */
    @SuppressLint("SimpleDateFormat")
    public static String stampToDateYMDHMS(Integer timeMillis) {
        return stampToDateYMDHMS(Long.valueOf(timeMillis));
    }

    /**
     * 将时间戳转换为时间 yyyy-MM-dd HH:mm
     */
    @SuppressLint("SimpleDateFormat")
    public static String stampToDateYMDHM(Integer timeMillis) {
        return stampToDateYMDHM(Long.valueOf(timeMillis));
    }

    /**
     * 将时间戳转换为时间 yyyy-MM-dd HH:mm:ss
     */
    @SuppressLint("SimpleDateFormat")
    public static String stampToDateYMDHM(Long timeMillis) {
        if (StringHelper.isNull(timeMillis)) return "0000-00-00 00:00";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Long temp = timeMillis * 1000;
        Date date = new Date(temp);
        return simpleDateFormat.format(date);
    }

    /**
     * 将时间戳转换为时间 yyyy-MM-dd HH:mm:ss
     */
    @SuppressLint("SimpleDateFormat")
    public static String stampToDateYMDHMS(Long timeMillis) {
        if (StringHelper.isNull(timeMillis)) return "0000-00-00 00:00:00";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Long temp = timeMillis * 1000;
        Date date = new Date(temp);
        return simpleDateFormat.format(date);
    }

    /**
     * 将时间戳转换为时间 MM-dd HH:mm:ss
     */
    @SuppressLint("SimpleDateFormat")
    public static String stampToDateMDHMS(Integer timeMillis) {
        return stampToDateMDHMS(Long.valueOf(timeMillis));
    }

    /**
     * 将时间戳转换为时间 MM-dd HH:mm:ss
     */
    @SuppressLint("SimpleDateFormat")
    public static String stampToDateMDHMS(Long timeMillis) {
        if (StringHelper.isNull(timeMillis)) return "00-00 00:00:00";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
        Date date = new Date(timeMillis);
        return simpleDateFormat.format(date);
    }

    /**
     * 将时间戳转换为时间
     */
    @SuppressLint("SimpleDateFormat")
    public static String toStamp(long timeMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(timeMillis);
        return simpleDateFormat.format(date);
    }

    /**
     * 时间格式转换 yyyy-MM-dd HH:mm:ss -> MM-dd HH:mm:ss
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String setFormatMDHMS(String date) {
        try {
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date format = s.parse(date);
            SimpleDateFormat formatNew = new SimpleDateFormat("MM-dd HH:mm:ss");
            return formatNew.format(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 时间格式转换 yyyy-MM-dd HH:mm:ss -> yyyy-MM-dd
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String setFormatYMD(String date) {
        if (date == null) {
            return "";
        }
        try {
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date format = s.parse(date);
            SimpleDateFormat formatNew = new SimpleDateFormat("yyyy-MM-dd");
            return formatNew.format(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 时间格式转换 yyyy-MM-dd -> MM月dd日
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String setFormatCCMD(String date) {
        try {
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
            Date format = s.parse(date);
            SimpleDateFormat formatNew = new SimpleDateFormat("MM月dd日");
            return formatNew.format(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 时间格式转换 yyyy-MM-dd HH:mm:ss -> MM月dd日
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String setFormatCMD(String date) {
        try {
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date format = s.parse(date);
            SimpleDateFormat formatNew = new SimpleDateFormat("MM月dd日");
            return formatNew.format(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 时间格式转换 yyyy-MM-dd -> MM-dd
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String setFormatMD(String date) {
        try {
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
            Date format = s.parse(date);
            SimpleDateFormat formatNew = new SimpleDateFormat("MM-dd");
            return formatNew.format(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 时间格式转换 yyyy-MM -> yyyyy
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String setFormatY(String date) {
        try {
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM");
            Date format = s.parse(date);
            SimpleDateFormat formatNew = new SimpleDateFormat("yyyy");
            return formatNew.format(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 时间格式转换 yyyy-MM -> MM
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String setFormatM(String date) {
        try {
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM");
            Date format = s.parse(date);
            SimpleDateFormat formatNew = new SimpleDateFormat("MM");
            return formatNew.format(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取当前时间 yyyyMM
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getyMCurrentTime() {
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM");
        return s.format(new Date());
    }

    /**
     * 获取当前时间 yyyyMMdd
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getyMdCurrentTime() {
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        return s.format(new Date());
    }
    /**
     * 获取当前时间 MMdd
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getMdCurrentTime() {
        SimpleDateFormat s = new SimpleDateFormat("MM-dd");
        return s.format(new Date());
    }
    /**
     * 获取当前时间 yyyy
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getYCurrentTime() {
        SimpleDateFormat s = new SimpleDateFormat("yyyy");
        return s.format(new Date());
    }
    @SuppressLint("SimpleDateFormat")
    public static String getDateLastDay(String year, String month) {
        return getDateLastDay(Integer.parseInt(year), Integer.parseInt(month));
    }

    @SuppressLint("SimpleDateFormat")
    public static String getDateLastDay(int year, int month) {

        //year="2018" month="2"
        Calendar calendar = Calendar.getInstance();
        // 设置时间,当前时间不用设置
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(calendar.getTime());
    }

    @SuppressLint("SimpleDateFormat")
    public static String getDateFirstDay(String year, String month) {
        return getDateFirstDay(Integer.parseInt(year), Integer.parseInt(month));
    }

    @SuppressLint("SimpleDateFormat")
    public static String getDateFirstDay(int year, int month) {

        //year="2018" month="2"
        Calendar calendar = Calendar.getInstance();
        // 设置时间,当前时间不用设置
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, 1);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(calendar.getTime());
    }

    public static int getDateDays(String year, String month) {
        return getDateDays(Integer.parseInt(year), Integer.parseInt(month));
    }

    public static int getDateDays(int year, int month) {
        //year="2018" month="2"
        Calendar calendar = Calendar.getInstance();
        // 设置时间,当前时间不用设置
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, 1);
        return calendar.getActualMaximum(Calendar.DATE);
    }

    public static int getYear() {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTime(new Date());
        return calendar.get(Calendar.YEAR);
    }

    public static int getMonth() {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTime(new Date());
        return calendar.get(Calendar.MONTH) + 1;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getYMTime(int year, int month) {
        //year="2018" month="2"
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM");
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.YEAR, year);
        instance.set(Calendar.MONTH, month);
        instance.set(Calendar.DATE, 1);
        return s.format(instance.getTime());
    }

    @SuppressLint("SimpleDateFormat")
    public static String getYMDTime(int year, int month, int day) {
        //year="2018" month="2"
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.YEAR, year);
        instance.set(Calendar.MONTH, month);
        instance.set(Calendar.DAY_OF_MONTH, day);
        instance.set(Calendar.DATE, day);
        return s.format(instance.getTime());
    }

    @SuppressLint("SimpleDateFormat")
    public static String getMDTime( int month, int day) {
        //year="2018" month="2"
        SimpleDateFormat s = new SimpleDateFormat("MM-dd");
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.MONTH, month);
        instance.set(Calendar.DAY_OF_MONTH, day);
        instance.set(Calendar.DATE, day);
        return s.format(instance.getTime());
    }
}
