package com.pinelet.utp.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期工具类。<br>
 * 
 * @author xiansheng_liu
 * @version 1.0
 */
public class DateUtil {

	/**
	 * 得到当前时间的格式化字符串
	 * 
	 * @param type
	 *            日期格式
	 */
	public static String getFormatDate(String type) {
		return (formatDate(now(), type));
	}

	/**
	 * 得到当前时间的格式化字符串，格式为"yyyy-MM-dd HH:mm:ss"格式字符串
	 * 
	 * @return
	 */
	public static String getDefaultDate() {
		return (formatDate(now(), "yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * 得到当前时间的格式化字符串，格式为"yyyy-MM-dd"
	 * 
	 * @return
	 */
	public static String getFormatDate() {
		return (formatDate(now(), "yyyy-MM-dd"));
	}

	/**
	 * 得到当前时间的格式化字符串，格式为"yyyyMM"
	 * 
	 * @return
	 */
	public static String getFormatDate1() {
		return (formatDate(now(), "yyyyMM"));
	}

	/**
	 * 根据传入的日期类型格式化日期
	 * 
	 * @param date
	 *            日期类
	 * @param pattern
	 *            日期类型
	 * @return 日期字符串
	 */
	public static String formatDate(Date date, String pattern) {
		if (date == null)
			return "";
		if (pattern == null)
			pattern = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return (sdf.format(date));
	}

	/**
	 * 根据传入的日期类型格式化日期字符串
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @param pattern
	 *            日期类型
	 * @return 日期类
	 */
	public static Date formatDate(String dateStr, String pattern) {
		if (pattern == null || "".equals(pattern)) {
			pattern = "yyyy-MM-dd";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		Date d = new Date();
		try {
			if (dateStr == null) {
				d = null;
			} else {
				d = sdf.parse(dateStr);
			}
		} catch (ParseException e) {
			return null;
		}
		return d;
	}

	/**
	 * 将传入的日期格式化为yyyy-MM-dd HH:mm:ss格式字符串
	 * 
	 * @param date
	 *            日期类
	 * @return 日期字符串
	 */
	public static String formatDateTime(Date date) {
		return (formatDate(date, "yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * 将当前日期格式化为yyyy-MM-dd HH:mm:ss格式字符串
	 * 
	 * @return 日期字符串
	 */
	public static String formatDateTime() {
		return (formatDate(now(), "yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * 返回当前日期类
	 * 
	 * @return 日期类
	 */
	public static Date now() {
		return (new Date());
	}

	/**
	 * 将字符串转换成日期类(yyyy-MM-dd HH:mm:ss)
	 * 
	 * @param datetime
	 *            字符串
	 * @return 日期类
	 */
	public static Date parseDateTime(String datetime) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if ((datetime == null) || (datetime.equals(""))) {
			return null;
		} else {
			try {
				return formatter.parse(datetime);
			} catch (ParseException e) {
				return parseDate(datetime);
			}
		}
	}

	/**
	 * 将字符串转换成日期类(yyyy-MM-dd)
	 * 
	 * @param datetime
	 *            字符串
	 * @return 日期类
	 */
	public static Date parseDate(String date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		if ((date == null) || (date.equals(""))) {
			return null;
		} else {
			try {
				return formatter.parse(date);
			} catch (ParseException e) {
				return null;
			}
		}
	}

	/**
	 * 将字符串转换成日期类(yyyyMMdd)
	 * 
	 * @param datetime
	 *            字符串
	 * @return 日期类
	 */
	public static Date parseDate2(String date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		if ((date == null) || (date.equals(""))) {
			return null;
		} else {
			try {
				return formatter.parse(date);
			} catch (ParseException e) {
				return null;
			}
		}
	}

	/**
	 * 格式化日期类为yyyy-MM-dd格式日期类
	 * 
	 * @param datetime
	 *            日期类
	 * @return 日期类
	 */
	public static Date parseDate(Date datetime) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		if (datetime == null) {
			return null;
		} else {
			try {
				return formatter.parse(formatter.format(datetime));
			} catch (ParseException e) {
				return null;
			}
		}
	}

	/**
	 * 将传入的对象转成日期字符串（对象为空不转换）
	 * 
	 * @param o
	 *            对象
	 * @return 字符串
	 */
	public static String formatDate(Object o) {
		if (o == null)
			return "";
		if (o.getClass() == String.class)
			return formatDate((String) o);
		else if (o.getClass() == Date.class)
			return formatDate((Date) o);
		else if (o.getClass() == Timestamp.class) {
			return formatDate(new Date(((Timestamp) o).getTime()));
		} else
			return o.toString();
	}

	/**
	 * 给时间加上或减去指定毫秒，秒，分，时，天、月或年等，返回变动后的时间
	 * 
	 * @param date
	 *            要加减前的时间，如果不传，则为当前日期
	 * @param field
	 *            时间域，有Calendar.MILLISECOND,Calendar.SECOND,Calendar.MINUTE,<br>
	 *            Calendar.HOUR,Calendar.DATE, Calendar.MONTH,Calendar.YEAR
	 * @param amount
	 *            按指定时间域加减的时间数量，正数为加，负数为减。
	 * @return 变动后的时间
	 */
	public static Date add(Date date, int field, int amount) {
		if (date == null) {
			date = new Date();
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amount);

		return cal.getTime();
	}

	/**
	 * 返回所传时间加上微秒的时间结果
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addMilliSecond(Date date, int amount) {
		return add(date, Calendar.MILLISECOND, amount);
	}

	/**
	 * 返回所传时间加上秒的时间结果
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addSecond(Date date, int amount) {
		return add(date, Calendar.SECOND, amount);
	}

	/**
	 * 返回所传时间加上分钟的时间结果
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addMiunte(Date date, int amount) {
		return add(date, Calendar.MINUTE, amount);
	}

	/**
	 * 返回所传时间加上小时的时间结果
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addHour(Date date, int amount) {
		return add(date, Calendar.HOUR, amount);
	}

	/**
	 * 返回所传时间加上天的时间结果
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addDay(Date date, int amount) {
		return add(date, Calendar.DATE, amount);
	}

	/**
	 * 返回所传时间加上月的时间结果
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addMonth(Date date, int amount) {
		return add(date, Calendar.MONTH, amount);
	}

	/**
	 * 返回所传时间加上年的时间结果
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addYear(Date date, int amount) {
		return add(date, Calendar.YEAR, amount);
	}

	/**
	 * 返回格式化为yyyy-MM-dd HH:mm:ss的字符串转化成的日期类
	 * 
	 * @return
	 */
	public static Date getDateTime() {
		return parseDateTime(formatDate(now(), "yyyy-MM-dd HH:mm:ss"));
	}
	
	/**
	 * 返回格式化的字符串转化成的日期类
	 * 
	 * @return
	 */
	public static Date getDateTime(Date d,String pattern) {
		if (pattern == null) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		return parseDateTime(formatDate(d, pattern));
	}

	/**
	 * 时间格式化
	 * 
	 * @param Date
	 *            "20090214" or "20091225134520"
	 * @return Date "2009-02-14" or "2009-12-25 13:45:20"
	 */
	public static String formatDate2(String Date) {
		if ((null != Date) && (!(Date.equals(""))) && (Date.length() == 8)
				&& !isNaN(Date)) {
			Date = Date.substring(0, 4) + "-" + Date.substring(4, 6) + "-"
					+ Date.substring(6, 8);
		} else if ((null != Date) && (!(Date.equals("")))
				&& (Date.length() == 14) && !isNaN(Date)) {
			Date = Date.substring(0, 4) + "-" + Date.substring(4, 6) + "-"
					+ Date.substring(6, 8) + " " + Date.substring(8, 10) + ":"
					+ Date.substring(10, 12) + ":" + Date.substring(12, 14);
		}
		return Date;
	}

	/**
	 * 判断所传入的字符串是否是数字，不是：true；是：false
	 * 
	 * @param num
	 * @return
	 */
	public static boolean isNaN(String num) {
		String number = "1234567890";
		for (int i = 0; i < num.length(); i++) {
			if (number.indexOf(num.charAt(i)) == -1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 返回dateString指定部分的值，如果是week则返回周的第几天
	 * 
	 * @param dateString
	 * @param part
	 * @return
	 */
	public static String getPart(String dateString, String part) {
		// yyyy-MM-dd HH:mm:ss
		String result = "";
		String[] parts = dateString.split("-| |:");
		if (parts.length >= 0 && part.equalsIgnoreCase("year")) {
			result = parts[0];
		}
		if (parts.length >= 1 && part.equalsIgnoreCase("month")) {
			result = parts[1];
		}
		if (part.equalsIgnoreCase("week")) {
			try {
				Calendar c = Calendar.getInstance();
				c.setTime(DateUtil.parseDate(dateString));
				result = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
			} catch (Exception e) {
				return "0";
			}
		}
		if (parts.length >= 2 && part.equalsIgnoreCase("day")) {
			result = parts[2];
		}
		if (parts.length >= 3 && part.equalsIgnoreCase("Hour")) {
			result = parts[3];
		}
		if (parts.length >= 4 && part.equalsIgnoreCase("minute")) {
			result = parts[4];
		}
		if (parts.length >= 5 && part.equalsIgnoreCase("second")) {
			result = parts[5];
		}
		return result;
	}

	/**
	 * 将yyyy-MM-dd hh:mm:ss转换成yyyymmddhhmmss格式
	 * 
	 * @param dateString
	 * @return
	 */
	public static String formateToNumber(String dateString) {
		String result = null;
		if (dateString == null || dateString.trim().equals("")) {
			result = "";
		} else {
			result = dateString.replaceAll("[- :]", "");
		}
		return result;
	}

	/**
	 * 验证yyyyMMddHHmmss型字符串是否为合法的时间
	 * 
	 * @param time
	 * @return
	 */
	public static boolean isTrueTime(String time) {

		boolean b = true;

		// 判断是否全为数字
		if (isNaN(time))
			return false;

		// 根据位数做不同的验证
		int length = time.length();
		if (length == 12) {// 不包含秒
			b = isTrueTimeExceptSecond(time);
		} else if (length == 14) {
			boolean bSecond = true;
			int second = Integer.parseInt(time.substring(12, 14));// 获取秒
			if (second < 0 || second > 59) {// 秒超出范围
				bSecond = false;
			}
			b = isTrueTimeExceptSecond(time) && bSecond;
		} else {
			b = false;
		}
		return b;
	}

	/**
	 * 验证yyyyMMddHHmm型字符串是否为合法的时间
	 * 
	 * @param time
	 * @return
	 */
	private static boolean isTrueTimeExceptSecond(String time) {

		// 年月日 时 分
		int year = Integer.parseInt(time.substring(0, 4));
		int month = Integer.parseInt(time.substring(4, 6));
		int day = Integer.parseInt(time.substring(6, 8));
		int hour = Integer.parseInt(time.substring(8, 10));
		int minute = Integer.parseInt(time.substring(10, 12));

		if (month < 1 || month > 12 || day < 1 || day > 31) {// 月份和日期超出范围
			return false;
		}

		if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {// 时分超出范围
			return false;
		}

		if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {// 闰年
			return checkDay(true, month, day);
		} else {
			return checkDay(false, month, day);
		}

	}

	/**
	 * 验证小月天数是否超出范围
	 * 
	 * @param isLeapYear 是否为闰年
	 * @param month 月份
	 * @param day 日期
	 * @return
	 */
	private static boolean checkDay(boolean isLeapYear, int month, int day) {
		boolean b = true;
		int count = 28;
		if (isLeapYear == true) {
			count = 29;
		}
		switch (month) {
		case 2:
			if (day > count) {
				b = false;
			}
			break;
		case 4:
			if (day > 30) {
				b = false;
			}
			break;
		case 6:
			if (day > 30) {
				b = false;
			}
			break;
		case 9:
			if (day > 30) {
				b = false;
			}
			break;
		case 11:
			if (day > 30) {
				b = false;
			}
			break;
		default:
			break;
		}
		return b;
	}
	
	/**
	 * 比较d1与d2相差天数
	 * @param d1 格式为yyyy-mm-dd
	 * @param d2格式为yyyy-mm-dd
	 * @return 相差天数
	 */
	public long compare (String d1, String d2) {
		 long time1 = parseDate(d1).getTime();
		 long time2 = parseDate(d2).getTime();
		 return Math.abs((time1 - time2) / (1000 * 60 * 60 * 24));
		}
	
	/**
	 * 比较与目前相差的天数
	 * @param d1
	 * @return
	 */
	public static long compare(String d1) {
		long time1 = parseDate(d1).getTime();
		return (time1 - System.currentTimeMillis()) / (1000 * 60 * 60 * 24);
	}
}
