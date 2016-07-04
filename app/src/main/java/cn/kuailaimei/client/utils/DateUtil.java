package cn.kuailaimei.client.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;

import cn.kuailaimei.client.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * handle date and datetime
 * <p>
 * Notice: All of the time operating here, please bsn
 */
public class DateUtil {
    public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    private static final int MINUTE = 1000 * 60;

    /**
     * 当前对话所显示的时间
     *
     * @param date
     * @return
     */
    @SuppressWarnings("deprecation")
    @SuppressLint("SimpleDateFormat")
    public static String parseTimeForCurrentChat(Date date) {
        SimpleDateFormat formatter = null;
        Date currentTime = new Date();
        if (currentTime.getYear() != date.getYear()) {
            formatter = new SimpleDateFormat("yy年M月d日", Locale.CHINESE);

        } else {
            formatter = new SimpleDateFormat("M月dd日 HH:mm", Locale.CHINESE);
            if (currentTime.getMonth() == date.getMonth()) {
                if (currentTime.getDate() == date.getDate()) {
                    formatter = new SimpleDateFormat("HH:mm");
                    long minutes = (currentTime.getTime() - date.getTime()) / MINUTE;
                    if (minutes < 60)
                        if (minutes == 0)
                            return "1分钟内";
                        else
                            return minutes + "分钟前";
                    // return

                } else if (currentTime.getDate() == date.getDate() + 1) {
                    formatter = new SimpleDateFormat("昨天  HH:mm", Locale.CHINESE);
                } else if (currentTime.getDate() == date.getDate() + 2) {
                    formatter = new SimpleDateFormat("前天  HH:mm", Locale.CHINESE);
                }
            }
        }
        return formatter.format(date);

    }

    /**
     * 最近联系人 界面所显示时间
     *
     * @param date
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    @SuppressWarnings("deprecation")
    public static String parseTimeForRecentList(Date date) {
        SimpleDateFormat formatter = null;
        Date currentTime = new Date();
        if (currentTime.getYear() != date.getYear()) {
            formatter = new SimpleDateFormat("yy年M月", Locale.CHINESE);
        } else if (currentTime.getMonth() == date.getMonth() && currentTime.getDate() == date.getDate()) {
            formatter = new SimpleDateFormat("HH:mm", Locale.CHINESE);
        } else if (currentTime.getMonth() == date.getMonth() && currentTime.getDate() == date.getDate() + 1) {
            formatter = new SimpleDateFormat("昨天  HH:mm", Locale.CHINESE);
        } else if (currentTime.getMonth() == date.getMonth() && currentTime.getDate() == date.getDate() + 2) {
            formatter = new SimpleDateFormat("前天  HH:mm", Locale.CHINESE);
        } else {
            formatter = new SimpleDateFormat("M月dd日", Locale.CHINESE);
        }
        return formatter.format(date);
    }

    /**
     * get current datetime
     *
     * @return
     */
    public static String formartTimeMillis() {
        Calendar calendar = Calendar.getInstance();
        return formart(calendar.getTime(), FORMAT_DATETIME);

    }

    /**
     * get Current date
     *
     * @return
     */
    public static String formartCurrentDate() {
        return formart(Calendar.getInstance());
    }

    public static String formart(Calendar calendar) {
        return formart(calendar.getTime(), FORMAT_DATE);
    }

    /**
     * formate date
     *
     * @param d
     * @param format
     * @return
     */
    public static String formart(Date d, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(d);
    }

    public static Date parse(String s, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(s);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * add certainDays to get certainDate
     *
     * @param datetime certainDate
     * @param days     add days
     * @return
     */
    public static String formartByOffset(String datetime, int days) {
        Date curDate = parse(datetime, FORMAT_DATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(curDate);
        calendar.add(Calendar.DATE, days);
        return formart(calendar.getTime(), FORMAT_DATE);
    }

    /**
     * set assign calendar
     */
    public static Calendar getAssignCalendar(String assignDateTime) {
        String[] data = assignDateTime.split("-");
        Calendar cl = Calendar.getInstance();
        cl.set(Calendar.YEAR, new Integer(data[0]).intValue());
        cl.set(Calendar.MONTH, new Integer(data[1]).intValue() - 1);
        cl.set(Calendar.DAY_OF_MONTH, new Integer(data[2]).intValue());
        cl.set(Calendar.HOUR_OF_DAY, new Integer(data[3]).intValue());
        cl.set(Calendar.MINUTE, 0);
        cl.set(Calendar.SECOND, 0);
        cl.set(Calendar.MILLISECOND, 0);
        return cl;
    }

    /**
     * get next trigger time
     *
     * @param timePoint
     * @return
     */
    public static long getNextTriggerTime(int timePoint) {
        String nextDay = DateUtil.formartByOffset(DateUtil.formartCurrentDate(), 1);
        return DateUtil.getAssignCalendar(nextDay + "-" + timePoint).getTimeInMillis();
    }

    /**
     * startDatetime format:yyyy-MM-dd HH:mm:ss
     */
    public static String getDiffDisc(long sStartTime, Context ctx) {
        return getDiffDiscChat(sStartTime, FORMAT_DATETIME, ctx);
    }

    /**
     * startDatetime format:yyyy-MM-dd HH:mm
     */
    public static String getDiffDiscMessage(long sStartTime, Context ctx) {
        return getDiffDiscChat(sStartTime, "yyyy-MM-dd HH:mm", ctx);
    }

    /**
     * startDatetime format:yyyy-MM-dd HH:mm:ss
     * <p>
     * bbs show time
     */
    public static String getDiffDiscBBs(String sStartTime, Context ctx) {
        if (TextUtils.isEmpty(sStartTime)) {
            return null;
        }
        return getDiffDisc(sStartTime, FORMAT_DATETIME, ctx);
    }

    /**
     * startDatetime format:yyyy-MM-dd
     * <p>
     * article show time
     */
    public static String getDiffDiscArticle(String sStartTime, Context ctx) {
        return getDiffDisc(sStartTime, "yyyy-MM-dd", ctx);
    }

    /**
     * 2015-05-02 12:12:12.0转成2015-05-02 12:12
     */
    public static String getPostTimeFormat(String startTime) {
        if (!TextUtils.isEmpty(startTime)) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                return df.format(df.parse(startTime));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                return startTime;
            }
        } else {
            return "";
        }

    }

    /**
     * 例如2015-05-02 12:12:12.0转成2015-05-02
     */
    public static String getTimeEndWithDayFormat(String startTime) {
        if (!TextUtils.isEmpty(startTime)) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return df.format(df.parse(startTime));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                return startTime;
            }
        } else {
            return "";
        }

    }

    /**
     * 2015-05-02 12:12:12.0转成05-02
     */
    public static String getMonthPostTimeFormat(String startTime) {
        if (!TextUtils.isEmpty(startTime)) {
            DateFormat df = new SimpleDateFormat("MM-dd");
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime);
                return df.format(date);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                return startTime;
            }
        } else {
            return "";
        }

    }

    /**
     * 2015-08-11 12:12:12 转成 2015年08月
     *
     * @param time
     * @return
     */
    public static String getDealTimeYearAndMonth(String time) {
        if (!TextUtils.isEmpty(time)) {
            DateFormat df = new SimpleDateFormat("yyyy年MM月");
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
                return df.format(date);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                return time;
            }
        } else {
            return "";
        }
    }

    /**
     * 2015-08-11 12:12:12 转成 08-11 12:12
     *
     * @param time
     * @return
     */
    public static String getDealDetailTime(String time) {
        if (!TextUtils.isEmpty(time)) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
                return df.format(date);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                return time;
            }
        } else {
            return "";
        }
    }

    /**
     * @param beginTime 2015- 格式化时间： 目前需要显示时间的都可以直接用此方式来格式化
     *                  2个格式化方式基本满足使用故不在定义枚举：唯一不同是格式化小时
     * @return
     */
    private static String getDiffDisc(String sStartTime, String dateFormat, Context ctx) {

        String disc = "";
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(parse(sStartTime, dateFormat));
        Calendar cal = Calendar.getInstance();

        int timeDiff = (int) Math.floor((cal.getTimeInMillis() - startCal.getTimeInMillis()) / 1000);//
        int minuteDiff = (int) Math.floor(timeDiff / 60);
        if (minuteDiff == 0) {
            disc = ctx.getString(R.string.rightnow);
            return disc;
        }

        int hourDiff = (int) Math.floor(timeDiff / 3600);
        if (hourDiff == 0) {
            disc = minuteDiff + ctx.getString(R.string.minuteBefore);
            return disc;
        }

        boolean sameDay = formart(cal).equals(formart(startCal));
        if (sameDay) {
            disc = preBqChar("" + startCal.get(Calendar.HOUR_OF_DAY), '0', 2) + ":"
                    + preBqChar("" + startCal.get(Calendar.MINUTE), '0', 2);
            return disc;
        }

        boolean sameYear = cal.get(Calendar.YEAR) == startCal.get(Calendar.YEAR);
        if (sameYear) {//
            disc = startCal.get(Calendar.YEAR) + ctx.getString(R.string.splithe)
                    + preBqChar("" + (startCal.get(Calendar.MONTH) + 1), '0', 2) + ctx.getString(R.string.splithe)
                    + preBqChar("" + startCal.get(Calendar.DAY_OF_MONTH), '0', 2);
            return disc;
        }

        disc = startCal.get(Calendar.YEAR) + ctx.getString(R.string.year) + (startCal.get(Calendar.MONTH) + 1)
                + ctx.getString(R.string.month) + startCal.get(Calendar.DAY_OF_MONTH) + ctx.getString(R.string.day)
                + preBqChar("" + startCal.get(Calendar.HOUR_OF_DAY), '0', 2) + ":"
                + preBqChar("" + startCal.get(Calendar.MINUTE), '0', 2);
        return disc;
    }

    /**
     * @param sStartTime
     * @param dateFormat
     * @param ctx
     * @param showHour
     * @return
     */
    private static String getDiffDiscChat(long sStartTime, String dateFormat, Context ctx) {
        String disc = "";
        Calendar startCal = Calendar.getInstance();
        // Date startDate=getDateFromString(sStartTime,dateFormat);
        // startCal.setTime(getDateFromString(sStartTime,dateFormat));
        startCal.setTimeInMillis(sStartTime);
        Calendar cal = Calendar.getInstance();

        int timeDiff = (int) Math.floor((cal.getTimeInMillis() - startCal.getTimeInMillis()) / 1000);//
        int minuteDiff = (int) Math.floor(timeDiff / 60);
        if (minuteDiff == 0) {
            return disc;
        }

        int hourDiff = (int) Math.floor(timeDiff / 3600);
        if (hourDiff == 0) {
            disc = minuteDiff + ctx.getString(R.string.minuteBefore);
            return disc;
        }

        boolean sameDay = formart(cal).equals(formart(startCal));
        if (sameDay) {
            disc = preBqChar("" + startCal.get(Calendar.HOUR_OF_DAY), '0', 2) + ":"
                    + preBqChar("" + startCal.get(Calendar.MINUTE), '0', 2);
            return disc;
        }

        Calendar ztCal = cal;
        ztCal.add(Calendar.DAY_OF_MONTH, -1);
        boolean isZt = formart(ztCal).equals(formart(startCal));
        if (isZt) {
            disc = ctx.getString(R.string.yestoday) + " " + preBqChar("" + startCal.get(Calendar.HOUR_OF_DAY), '0', 2) + ":"
                    + preBqChar("" + startCal.get(Calendar.MINUTE), '0', 2);
            return disc;
        }

        boolean sameYear = cal.get(Calendar.YEAR) == startCal.get(Calendar.YEAR);
        if (sameYear) {//
            disc = (startCal.get(Calendar.MONTH) + 1) + ctx.getString(R.string.month) + startCal.get(Calendar.DAY_OF_MONTH)
                    + ctx.getString(R.string.day) + preBqChar("" + startCal.get(Calendar.HOUR_OF_DAY), '0', 2) + ":"
                    + preBqChar("" + startCal.get(Calendar.MINUTE), '0', 2);
            return disc;
        }

        disc = startCal.get(Calendar.YEAR) + ctx.getString(R.string.year) + (startCal.get(Calendar.MONTH) + 1)
                + ctx.getString(R.string.month) + startCal.get(Calendar.DAY_OF_MONTH) + ctx.getString(R.string.day)
                + preBqChar("" + startCal.get(Calendar.HOUR_OF_DAY), '0', 2) + ":"
                + preBqChar("" + startCal.get(Calendar.MINUTE), '0', 2);
        return disc;
    }

    private static String preBqChar(String source, char ch, int length) {
        if (source == null) {
            return null;
        }
        String rStr = source;
        if (source.length() < length) {
            for (int i = 0; i < (length - source.length()); i++) {
                rStr = ch + rStr;
            }
        }
        return rStr;
    }

    public static int getYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    public static int getMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH);
    }

    public static int getDayOfMOnth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 根据时间显示打卡标题
     *
     * @return
     */
    public static String getDateSignTitle() {
        String SignTitle;
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if (hour >= 5 && hour < 9) {
            SignTitle = "起床打卡";
        } else if (hour >= 9 && hour < 11) {
            SignTitle = "懒虫打卡";

        } else if (hour >= 11 && hour < 13) {
            SignTitle = "中午打卡";

        } else if (hour >= 13 && hour < 14) {
            SignTitle = "中餐打卡";

        } else if (hour >= 14 && hour < 18) {
            SignTitle = "下午打卡";

        } else if (hour >= 18 && hour < 22) {
            SignTitle = "晚餐打卡";
        } else {
            SignTitle = "晚安打卡";

        }
        return SignTitle;
    }

    public static boolean isSameDay(long dateTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int today = calendar.get(Calendar.DAY_OF_YEAR);
        calendar.setTimeInMillis(dateTime);
        int saveDay = calendar.get(Calendar.DAY_OF_YEAR);
        if (today == saveDay) {
            return true;
        } else {
            return false;
        }
    }

    public static final String[] weeks = new String[]{"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    public static String getWeekStr(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Integer dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        return weeks[dayOfWeek - 1];
    }

    /**
     * 将以秒为单位的时间转换为时分秒即 00:00:00格式
     *
     * @param time 秒数
     * @return
     */
    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    /**
     * 将以秒为单位的时间转换为时分秒即 “0时0分0秒” 的格式
     *
     * @param time 秒数
     * @return
     */
    public static String hmsTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + "分" + unitFormat(second) + "秒";
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + "时" + unitFormat(minute) + "分" + unitFormat(second) + "秒";
            }
        }
        return timeStr;
    }

    private static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss格式的时间转换成毫秒值
     */
    public static long getTimeMillis(String time) {
       return getTimeMillis(time,FORMAT_DATETIME);
    }

    public static long getTimeMillis(String time,String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        long millionSeconds = 0;
        try {
            millionSeconds = sdf.parse(time).getTime();//毫秒
        } catch (ParseException e) {
            e.printStackTrace();
            return millionSeconds;
        }
        return millionSeconds;
    }

    public static int getMonth(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.MONTH);
    }

    public static int getDayOfMOnth(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }


}
