package com.bow.forest.common.utils;

import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang3.time.DateUtils;

/**
 * 日志工具
 * 
 * @author wwxiang
 * @since 2016/9/13.
 */
public class DateUtil {

	/**
	 * 日期格式：yyyy-MM-dd
	 */
	public static final String NORMAL_DATE_FORMAT = "yyyy-MM-dd";
	/**
	 * 日期格式：yyyy-MM-dd
	 */
	public static final String COMPACT_DATE_FORMAT = "yyyyMMdd";
	/**
	 * 日期格式：yyyy-MM-dd hh:mm:ss
	 */
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd hh:mm:ss";
	/**
	 * 一天的毫秒数
	 */
	private static final long DAY_IN_MILLIS = 86400000L;

	/**
	 * 构造方法私有
	 */
	private DateUtil() {

	}

	/**
	 * 字符串解析为Date
	 * 
	 * @param dateStr dateStr
	 * @param formatPattern 格式
	 * @return Date
	 */
	public static Date parse(String dateStr, String formatPattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatPattern);
		Date date;
		try {
			date = simpleDateFormat.parse(dateStr);
		} catch (ParseException e) {
			throw new IllegalArgumentException("invalid param " + dateStr + "," + formatPattern);
		}
		return date;
	}

	/**
	 * Date转换为字符串
	 * 
	 * @param date 日期
	 * @param formatPattern 格式化样本
	 * @return String
	 */
	public static String format(Date date, String formatPattern) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatPattern);
		return simpleDateFormat.format(date);
	}

	/**
	 * 增加时间
	 * 
	 * @param begin 开始时间
	 * @param amount 增加数量
	 * @param calendarField 增加数量对应的单位，常用单位如下
	 * @see Calendar#YEAR
	 * @see Calendar#MONTH
	 * @see Calendar#WEEK_OF_YEAR
	 * @see Calendar#DATE
	 * @see Calendar#HOUR
	 * @see Calendar#HOUR_OF_DAY
	 * @see Calendar#MINUTE
	 * @see Calendar#SECOND
	 * @see Calendar#MILLISECOND
	 * @return Date
	 */
	public static Date add(Date begin, int amount, int calendarField) {
		if (begin == null) {
			throw new IllegalArgumentException("The date must not be null");
		} else {
			Calendar c = Calendar.getInstance();
			c.setTime(begin);
			c.add(calendarField, amount);
			return c.getTime();
		}
	}

	/**
	 * 获取指定的最早时间
	 * 
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @param time 时分秒
	 * @return 日期
	 */
	public static Date getSpecificDate(int year, int month, int day, int... time) {
		Calendar calendar = Calendar.getInstance();
		// 参数不符合常规，直接报错,如13月32日
		calendar.setLenient(false);
		List<Integer> types = new ArrayList<Integer>();
		types.add(Calendar.YEAR);
		types.add(Calendar.MONTH);
		types.add(Calendar.DAY_OF_MONTH);
		types.add(Calendar.HOUR_OF_DAY);
		types.add(Calendar.MINUTE);
		types.add(Calendar.SECOND);
		types.add(Calendar.MILLISECOND);
		calendar.set(types.get(0), year);
		calendar.set(types.get(1), month - 1);
		calendar.set(types.get(2), day);
		calendar.set(types.get(3), 0);
		calendar.set(types.get(4), 0);
		calendar.set(types.get(5), 0);
		calendar.set(types.get(6), 0);

		if (time != null) {
			if (time.length > 4) {
				throw new UnsupportedOperationException("unsupported specify time less than millis");
			}
			int i = 3;
			for (int t : time) {
				calendar.set(types.get(i), t);
				i++;
			}
		}
		return calendar.getTime();
	}

	/**
	 * 获取今天的日期，没有时分秒
	 * 
	 * @return 今天的日期
	 */
	public static Date getToday() {
		return truncate(new Date(), Calendar.DAY_OF_MONTH);
	}

	/**
	 * 截取到指定的日期
	 * 
	 * @param date date
	 * @param field 截取到日 小时 or分钟
	 * @return 日期
	 */
	public static Date truncate(Date date, int field) {
		return DateUtils.truncate(date, field);
	}

	/**
	 * 比较date1 date2
	 * 
	 * @param date1 date1
	 * @param date2 date2
	 * @return 1表示date1gt;date2 -1表示date1lt;date2 0表示date1eq;date2
	 */
	public static int compareTo(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			throw new InvalidParameterException("parameter must not be null");
		}
		return date1.compareTo(date2);
	}

	/**
	 * 比较日期大小，精确度由type决定
	 * 
	 * @param date1 date1
	 * @param date2 date2
	 * @param field 精确度类型，如 日 时 分
	 * @return 1 date1 &gt; date2
	 */
	public static int truncatedCompareTo(Date date1, Date date2, int field) {
		return DateUtils.truncatedCompareTo(date1, date2, field);
	}

	/**
	 * 相差1天为 间隔时间 大于等于24h 小于48h
	 * 
	 * @param date1 date1
	 * @param date2 date2
	 * @return 相差的天数
	 */
	public static int intervalDays(Date date1, Date date2) {
		Date tmp;
		if (date1.compareTo(date2) < 0) {
			tmp = date2;
			date2 = date1;
			date1 = tmp;
		}
		return (int) ((date1.getTime() - date2.getTime()) / DAY_IN_MILLIS);
	}

	/**
	 * 根据时区转换日期时间
	 * 
	 * @param srcDate 源日期
	 * @param srcTz 源时区
	 * @param desTz 目标时区
	 * @return 目标日期
	 */
	public static Date transDateByTz(Date srcDate, TimeZone srcTz, TimeZone desTz) {
		if (srcDate != null && srcTz != null && !srcTz.equals(desTz)) {
			int srcOffset = srcTz.getRawOffset();
			int desOffset = desTz.getRawOffset();
			int offset = desOffset - srcOffset;
			return new Date(srcDate.getTime() + offset);
		} else {
			return srcDate;
		}
	}

	/**
	 * 获取月的第一天
	 * 
	 * @param year year
	 * @param month month
	 * @return Date
	 */
	public static Date getFirstDayOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.setLenient(false);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return truncate(calendar.getTime(), Calendar.DATE);
	}

	/**
	 * 获取月最后一天
	 * 
	 * @param year year
	 * @param month month
	 * @return Date
	 */
	public static Date getLastDayOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.setLenient(false);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return truncate(calendar.getTime(), Calendar.DATE);
	}

	/**
	 * get First Day Of Year
	 * 
	 * @param year year
	 * @return Date
	 */
	public static Date getFirstDayOfYear(int year) {
		Calendar calendar = Calendar.getInstance();
		// calendar.setLenient(false);
		calendar.set(Calendar.YEAR, year);
		return truncate(calendar.getTime(), Calendar.YEAR);
	}

	/**
	 * get Last Day Of Year
	 * 
	 * @param year year
	 * @return Date
	 */
	public static Date getLastDayOfYear(int year) {
		return getLastDayOfMonth(year, 12);
	}

	/**
	 * 获取指定日期的年
	 * 
	 * @param date date
	 * @return 年
	 */
	public static int getYear(Date date) {
		return getField(date, Calendar.YEAR);
	}

	/**
	 * 获取指定日期的月
	 * 
	 * @param date date
	 * @return 月
	 */
	public static int getMonth(Date date) {
		return getField(date, Calendar.MONTH) + 1;
	}

	/**
	 * 获取指定日期的周 WEEK_OF_YEAR
	 * 
	 * @param date date
	 * @return WEEK_OF_YEAR
	 */
	public static int getWeekOfYear(Date date) {
		return getField(date, Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 获取日期，指定字段的值
	 * 
	 * @param date date
	 * @param field 指定字段名称，如年，月，日
	 * @see Calendar#YEAR
	 * @return 指定字段的值
	 */
	public static int getField(Date date, int field) {
		if (null == date) {
			date = new Date();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(field);
	}

	/**
	 * 是否为闰年 四年一闰年，一般百年不是但400年是
	 * 
	 * @param year year
	 * @return boolean
	 */
	public static boolean isLeapYear(int year) {
		if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
			return true;
		}
		return false;
	}

}
