package com.bow.forest.common.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * @author vv
 * @since 2016/9/17.
 */
public class DateUtil {

    private static final int ONE_MINUTE = 60;

    private static final int ONE_THOUSAND = 1000;

    private static final int ONE_HOUR = 60;

    private static int beginDay = 1;

    public static final int YEAR = 0;

    public static final int MONTH = 1;

    public static final int WEEK = 2;

    public static final int DAY = 3;

    public static final int HOUR = 4;

    public static final int MINUTE = 5;

    public static final int SECOND = 6;

    public static final int MILLISECOND = 7;

    public static final int MINUTEOFDAY = 8;
    public final static String DATE_FORMAT_14 = "yyyyMMddHHmmss";
    public final static String DATE_FORMAT_NEW_14 = "yyyy-MM-dd HH:mm:ss";

    public static final String DEFAULT_FORMAT = "yyyyMMddHHmmss"; // 缺省的日期字符串格式

    /**
     * 时间格式 : yyyy.MM.dd HH:mm
     */
    public final static String DATE_FORMAT_24HOUR_12 = "yyyy.MM.dd HH:mm";

    /**
     * 时间格式 : yyyyMMdd
     */
    public final static String DATE_FORMAT_8 = "yyyyMMdd";

    public static final Map<Integer, Integer> CALENDAR_MAP;

    static {
        CALENDAR_MAP = new HashMap<Integer, Integer>();
        CALENDAR_MAP.put(YEAR, Calendar.YEAR);
        CALENDAR_MAP.put(MONTH, Calendar.MONTH);
        CALENDAR_MAP.put(WEEK, Calendar.DAY_OF_WEEK);
        CALENDAR_MAP.put(DAY, Calendar.DAY_OF_MONTH);
        CALENDAR_MAP.put(HOUR, Calendar.HOUR);
        CALENDAR_MAP.put(MINUTE, Calendar.MINUTE);
        CALENDAR_MAP.put(SECOND, Calendar.SECOND);
        CALENDAR_MAP.put(MILLISECOND, Calendar.MILLISECOND);
    }

    /**
     * 千万别忘了
     */
    private DateUtil() {

    }

    public static String getCurrentTime(String pattern) {
        return format(new Date(),pattern);
    }

    /**
     * 把字符串格式化日期
     */
    public static Date parse(String dateStr, String formater) {
        formater = (null == formater) ? "yyyy-MM-dd HH:mm:ss" : formater;
        DateFormat formatter = new SimpleDateFormat(formater);
        Date date = null;
        try {
            date = formatter.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 格式化日期
     */
    public static String format(Date date, String formatStr) {
        if (date == null) {
            return null;
        }

        SimpleDateFormat sf = new SimpleDateFormat(formatStr);
        return sf.format(date);
    }

    /**
     * 指定时间，如指定到天就获取的是当天最早的时间
     * @param params
     * @return
     */
    public static Date getSpecifiedTime(int... params){
        Calendar c = Calendar.getInstance();
        return c.getTime();
    }




    /**
     * 获取当天结束时间
     *
     * @param date
     * @return Timestamp [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static Timestamp getEndDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);

        return new Timestamp(c.getTimeInMillis());
    }

    /**
     * 某个时间是否在两个时间段之间
     */
    public static boolean isBetweenTime(Timestamp begintime, Timestamp endtime,
                                        Timestamp nowTimes) {
        boolean flag = (nowTimes.compareTo(begintime) != -1 && nowTimes.compareTo(endtime) != 1);
        if (flag) {
            return true;
        }

        return false;
    }

    public static enum CompareDateFormate {
        year, month, day, hour, minute, second,

        yyyyMMddhhmmss, yyyyMMddhhmm, yyyyMMddhh, yyyyMMdd, yyyyMM,

        MMddhhmmss, MMddhhmm, MMddhh, MMdd, ddhhmmss, ddhhmm, ddhh, hhmmss, hhmm, mmss
    }

    private final static HashMap<CompareDateFormate, int[]> map =
            new HashMap<CompareDateFormate, int[]>();

    static {
        map.put(CompareDateFormate.year, new int[]{Calendar.YEAR});
        map.put(CompareDateFormate.month, new int[]{Calendar.MONTH});
        map.put(CompareDateFormate.day, new int[]{Calendar.DATE});
        map.put(CompareDateFormate.hour, new int[]{Calendar.HOUR_OF_DAY});
        map.put(CompareDateFormate.minute, new int[]{Calendar.MINUTE});
        map.put(CompareDateFormate.second, new int[]{Calendar.SECOND});

        map.put(CompareDateFormate.yyyyMMddhhmmss, new int[]{Calendar.YEAR,
                Calendar.MONTH, Calendar.DATE, Calendar.HOUR_OF_DAY,
                Calendar.MINUTE, Calendar.SECOND});
        map.put(CompareDateFormate.yyyyMMddhhmm, new int[]{Calendar.YEAR,
                Calendar.MONTH, Calendar.DATE, Calendar.HOUR_OF_DAY,
                Calendar.MINUTE});
        map.put(CompareDateFormate.yyyyMMddhh, new int[]{Calendar.YEAR,
                Calendar.MONTH, Calendar.DATE, Calendar.HOUR_OF_DAY});
        map.put(CompareDateFormate.yyyyMMdd, new int[]{Calendar.YEAR,
                Calendar.MONTH, Calendar.DATE});
        map.put(CompareDateFormate.yyyyMM, new int[]{Calendar.YEAR,
                Calendar.MONTH});

        map.put(CompareDateFormate.MMddhhmmss, new int[]{Calendar.MONTH,
                Calendar.DATE, Calendar.HOUR_OF_DAY, Calendar.MINUTE,
                Calendar.SECOND});
        map.put(CompareDateFormate.MMddhhmm, new int[]{Calendar.MONTH,
                Calendar.DATE, Calendar.HOUR_OF_DAY, Calendar.MINUTE});
        map.put(CompareDateFormate.MMddhh, new int[]{Calendar.MONTH,
                Calendar.DATE, Calendar.HOUR_OF_DAY});
        map.put(CompareDateFormate.MMdd, new int[]{Calendar.MONTH,
                Calendar.DATE});

        map.put(CompareDateFormate.ddhhmmss, new int[]{Calendar.DATE,
                Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND});
        map.put(CompareDateFormate.ddhhmm, new int[]{Calendar.DATE,
                Calendar.HOUR_OF_DAY, Calendar.MINUTE});
        map.put(CompareDateFormate.ddhh, new int[]{Calendar.DATE,
                Calendar.HOUR_OF_DAY});

        map.put(CompareDateFormate.hhmmss, new int[]{Calendar.HOUR_OF_DAY,
                Calendar.MINUTE, Calendar.SECOND});
        map.put(CompareDateFormate.hhmm, new int[]{Calendar.HOUR_OF_DAY,
                Calendar.MINUTE});
        map.put(CompareDateFormate.mmss, new int[]{Calendar.MINUTE,
                Calendar.SECOND});
    }

    /**
     * 根据CompareFields的格式（如只比较年月）比较两个日期先后，
     * <p>
     * 在比较字段内，若返回1，表示date1在date2之后，返回-1，表示date1在date2之前，0表示两者相等
     */
    public static int compare(Date date1, Date date2, CompareDateFormate cdf) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);

        int[] form = map.get(cdf);
        for (int field : form) {
            int t1 = c1.get(field);
            int t2 = c2.get(field);
            if (t1 > t2) {
                return 1;
            } else if (t1 < t2) {
                return -1;
            }
        }

        return 0;
    }


    /**
     * 获得东八时区的日历，并设置日历的当前日期 参数：date，Date，日期型
     */
    public static Calendar getLocalCalendar(Date date) {
        // 设置为GMT+08:00时区
        String[] ids =
                TimeZone.getAvailableIDs(8 * ONE_HOUR * ONE_MINUTE * ONE_THOUSAND);
        if (0 == ids.length) {
            throw new IllegalArgumentException(
                    "get id of GMT+08:00 time zone failed");
        }
        // 创建Calendar对象，并设置为指定时间
        Calendar calendar = new GregorianCalendar(TimeZone.getDefault());

        // 设置成宽容方式
        if (!calendar.isLenient()) {
            calendar.setLenient(true);
        }
        // 设置SUNDAY为每周的第一天
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);

        // 设置日历的当前时间
        calendar.setTime(date);
        return calendar;
    }




}
