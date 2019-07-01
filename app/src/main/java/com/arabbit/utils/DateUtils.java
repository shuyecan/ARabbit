package com.arabbit.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by net8 on 2017/5/26.
 */

public class DateUtils {
    /**
     * 获取 当前日期
     *
     * @return
     */
    public static String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date curDate = new Date(System.currentTimeMillis());
        String date = formatter.format(curDate);
        return date;
    }


    /**
     * 获取 当前日期(包含时间)
     *
     * @return
     */
    public static String getDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddssSSS");
        Date curDate = new Date(System.currentTimeMillis());
        String date = formatter.format(curDate);
        return date;
    }

    /**
     * 获取 转化后的时间
     *
     * @return
     */
    public static String getFormatTime(int time) {
        SimpleDateFormat formatter = new SimpleDateFormat("m:ss");
        String date = formatter.format(time);
        return date;
    }

    /**
     * 获取 转化后的时间
     *
     * @return
     */
    public static String getFormatTime(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("m:ss");
        String date = formatter.format(time);
        return date;
    }


    /*
     * 将时间戳转换为时间(带年月日)
     */
    public static String timeToDate(String time) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        if (time.length() == 10) {
            time += "000";
        }
        long lt = Long.valueOf(time);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 获取系统时间，返回特定的时间格式
     * yyyy.MM.dd
     *
     * @return time 指定的时间格式
     */
    public static String getSystemTime(String time) {
        String res;
        if (time.length() == 10) {
            time += "000";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        long lt = Long.valueOf(time);
        Date date = new Date(lt);
        res = sdf.format(date);
        return res;
    }


    /*
     * 将时间戳转换为时间 yyyy-MM-dd HH:mm:ss
     */
    public static String getTimeHHmmss(long time) {
        String res;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        res = df.format(date);
        return res;
    }


    /*
     * 获取当前时间与传入的时间间隔
     */
    public static String getDifferTime(String time) {

        if (time.length() == 10) {
            time += "000";
        }

        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long day = 0;
        long hour = 0;
        long min = 0;

        String res;
        long lt = Long.valueOf(time);
        long current = System.currentTimeMillis();
        long diff = current - lt;

        day = diff / nd;// 计算差多少天
        hour = diff % nd / nh + day * 24;// 计算差多少小时
        min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟

        if (day >= 1) {
            res = day + "" + "天前";
        } else if (hour >= 1) {
            res = hour + "" + "小时前";
        } else if (min >= 1) {
            res = min + "" + "分钟前";
        } else {
            res = "1" + "分钟内";
        }
        return res;
    }


    /*
     * 获取 倒计时 天  时分
	 */
    public static String getDifferTime2(long time) {

        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long day = 0;
        long hour = 0;
        long min = 0;

        String res = "";

        day = time / nd;
        hour = time % nd / nh;
        min = time % nd % nh / nm;// 计算差多少分钟

        if (day >= 1) {
            res = day + "" + "天";
        }
        if (hour >= 1) {
            res = res + hour + "" + "小时";
        }
        if (min >= 1) {
            res = res + min + "" + "分钟";
        } else {
            res = res + "1" + "分钟内";
        }
        return res;
    }

    /*
    * 将时间转换为时间戳(精确到秒)
    */
    public static String dateToStamp(String str) {
        String res = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:ss");
            Date date = simpleDateFormat.parse(str);
            long ts = date.getTime();
            res = String.valueOf(ts);
            res = res.substring(0, res.length() - 3);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    /**
     * 两个时间之间相差距离多少天
     *
     * @return 相差天数
     */
    public static long getDistanceDays(String str1, String str2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date one;
        Date two;
        long days = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            days = diff / (1000 * 60 * 60 * 24);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return days;
    }


    /**
     * 比较两个时间(如果第一个时间大于第二个，则返回true)
     *
     * @return
     */
    public static boolean compareDay(String str1, String str2) {
        boolean result = false;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date one;
        Date two;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            if (time1 < time2) {
                result = false;
            } else {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    //与当前时间比较，若小于当前时间，则返回true
    public static boolean compareCurrentDay(String time) {
        boolean result = false;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date one;

        try {
            one = df.parse(time);
            long time1 = one.getTime();
            long time2 = System.currentTimeMillis();
            if (time1 < time2) {
                result = true;
            } else {
                result = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 判断当前日期是星期几<br>
     * <br>
     *
     * @param pTime 修要判断的时间<br>
     * @return dayForWeek 判断结果<br>
     * @Exception 发生异常<br>
     */
    public static String dayForWeek(String pTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dayForWeek = "";
        int dys = c.get(Calendar.DAY_OF_WEEK);
        if (dys == Calendar.SUNDAY) {
            dayForWeek = "周日";
        } else {
            switch (dys) {
                case Calendar.MONDAY:
                    dayForWeek = "周一";
                    break;
                case Calendar.TUESDAY:
                    dayForWeek = "周二";
                    break;
                case Calendar.WEDNESDAY:
                    dayForWeek = "周三";
                    break;
                case Calendar.THURSDAY:
                    dayForWeek = "周四";
                    break;
                case Calendar.FRIDAY:
                    dayForWeek = "周五";
                    break;
                case Calendar.SATURDAY:
                    dayForWeek = "周六";
                    break;
            }
        }


        return dayForWeek;
    }


    /**
     * 根据日期 判断是几月几号  格式为 yyyy-MM-dd hh:mm:ss
     * 2017-06-22 15:51:37
     */
    public static String getDate(String date) {
        String monthDay = "";
        if (CommonUtils.isNull(date)) {
            return monthDay;
        }
        monthDay = date.substring(date.indexOf("-") + 1, date.lastIndexOf("-") + 3);
        return monthDay;
    }


    /*
     * 根据时间戳 判断是几月几号  格式为
	 */
    public static String longToDate(String time) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        if (time.length() == 10) {
            time += "000";
        }
        long lt = Long.valueOf(time);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /*
     * 根据时间戳 判断是几年几月几号  格式为 yyyy-MM-dd
	 */
    public static String longToDate2(String time) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (time.length() == 10) {
            time += "000";
        }
        long lt = Long.valueOf(time);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }


    /**
     * 比较两个日期的大小
     *
     * @param DATE1
     * @param DATE2
     * @return
     */
    public static int compareDate(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("MM-dd ");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 在dt2前");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取系统时间，返回特定的时间格式
     *
     * @return time 指定的时间格式
     */
    public static String getSystemTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
        Date curDate = new Date(System.currentTimeMillis());
        return sdf.format(curDate);
    }


    //判断选择的日期是否是本周
    public static boolean isThisWeek(long time) {
        Calendar calendar = Calendar.getInstance();
        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        calendar.setTime(new Date(time));
        int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        if (paramWeek == currentWeek) {
            return true;
        }
        return false;
    }


    //判断选择的日期是否是今天
    public static boolean isToday(long time) {
        return isThisTime(time, "yyyy-MM-dd");
    }

    //判断选择的日期是否是本月
    public static boolean isThisMonth(long time) {
        return isThisTime(time, "yyyy-MM");
    }

    private static boolean isThisTime(long time, String pattern) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String param = sdf.format(date);//参数时间
        String now = sdf.format(new Date());//当前时间
        if (param.equals(now)) {
            return true;
        }
        return false;
    }


}
