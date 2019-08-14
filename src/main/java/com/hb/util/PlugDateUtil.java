package com.hb.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class PlugDateUtil {
	public PlugDateUtil() {
	}

	/**
	 * Description :取得当前日期（格式为：yyyy-MM-dd） 
	 * @return String
	 */
	public static String getCurDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sDate = sdf.format(new Date());
		return sDate;
	}

	/**
	 * Description :取得当前时间（格式为：yyy-MM-dd HH:mm:ss） 
	 * @return String
	 */
	public static String getCurDateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sDate = sdf.format(new Date());
		return sDate;
	}

	/**
	 * Description :取得当前时间（格式为：yyyyMMdd24HHmmss） 
	 * @return String
	 */
	public static String getCurDateTimeS() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String sDate = sdf.format(new Date());
		return sDate;
	}

	/**
	 * Description :取得当前时间（格式为：yyyyMMdd） 
	 * @return String
	 */
	public static String getCurDateTimeD() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String sDate = sdf.format(new Date());
		return sDate;
	}

	/**
	 * Description :取得当前时间（格式为：yyyyMMdd24HHmmss） 
	 * @return String
	 */
	public static String getCurDateTimeHS() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String sDate = sdf.format(new Date());
		return sDate;
	}

	/**
	 * Description :取得当前时间（格式为：yyy-MM-dd HH:mm:ss） 
	 * @return Date
	 */
	public static Date getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sDate = sdf.format(new Date());
		Date date = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = df.parse(sDate);
		} catch (ParseException e) {
		}
		return date;
	}

	/**
	 * Description :取得当前年（格式为：yyyy） 
	 * @return String
	 */
	public static String getCurYear() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String sYear = sdf.format(new Date());
		return sYear;
	}

	/**
	 * Description :返回年份
	 * @param date :日期
	 * @return int
	 */
	public static int getYear(Date date) {
		Calendar cal = java.util.Calendar.getInstance();
		cal.setTime(date);
		return cal.get(java.util.Calendar.YEAR);
	}

	/**
	 * Description :取得当前月（格式为：MM） 
	 * @return String
	 */
	public static String getCurMonth() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		String sMonth = sdf.format(new Date());
		return sMonth;
	}

	/**
	 * Description :返回月份 
	 * @param date :日期
	 * @return int
	 */
	public static int getMonth(Date date) {
		Calendar cal = java.util.Calendar.getInstance();
		cal.setTime(date);
		return cal.get(java.util.Calendar.MONTH) + 1;
	}

	/**
	 * Description :取得当前日（格式为：dd） 
	 * @return String
	 */
	public static String getCurDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		String sDay = sdf.format(new Date());
		return sDay;
	}

	/**
	 * Description :返回日
	 * @param date :日期
	 * @return int
	 */
	public static int getDay(Date date) {
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.setTime(date);
		return cal.get(java.util.Calendar.DAY_OF_MONTH);
	}

	/**
	 * Description :取得现在小时
	 * @return String
	 */
	public static String getCurHour() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH");
		String hour = sdf.format(new Date());
		return hour;
	}

	/**
	 * Description :返回小时 
	 * @param date :日期
	 * @return int
	 */
	public static int getHour(Date date) {
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.setTime(date);
		return cal.get(java.util.Calendar.HOUR_OF_DAY);
	}

	/**
	 * Description :取得现在分钟
	 * @return String
	 */
	public static String getCurMinute() {
		SimpleDateFormat sdf = new SimpleDateFormat("mm");
		String min = sdf.format(new Date());
		return min;
	}

	/**
	 * Description :返回分钟
	 * @param date :日期
	 * @return int
	 */
	public static int getMinute(Date date) {
		Calendar cal = java.util.Calendar.getInstance();
		cal.setTime(date);
		return cal.get(java.util.Calendar.MINUTE);
	}

	/**
	 * Description :得到现在秒
	 * @return String
	 */
	public static String getCurSecond() {
		SimpleDateFormat sdf = new SimpleDateFormat("ss");
		String sec = sdf.format(new Date());
		return sec;
	}

	/**
	 * Description :返回秒
	 * @param date :日期
	 * @return int
	 */
	public static int getSecond(Date date) {
		Calendar cal = java.util.Calendar.getInstance();
		cal.setTime(date);
		return cal.get(java.util.Calendar.SECOND);
	}

	/**
	 * Description :返回毫秒
	 * @param date :日期
	 * @return long
	 */
	public static long getMillis(Date date) {
		Calendar cal = java.util.Calendar.getInstance();
		cal.setTime(date);
		return cal.getTimeInMillis();
	}

	/**
	 * Description ：按指定格式取得当前时间()
	 * @return String
	 */
	public String getTimeFormat(String strFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
		String sDate = sdf.format(new Date());
		return sDate;
	}

	/**
	 * Description : 取得指定时间的给定格式() 
	 * @param strDate
	 * @return String
	 */
	public static String setDateFormat(String strDate, String strFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
		String sDate = "";
		try {
			sDate = sdf.format(sdf.parse(strDate));
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return sDate;
	}

	/**
	 * Description :  判断某年是否为闰年 
	 * @param yearNum
	 * @return boolean
	 */
	public static boolean isLeapYear(int yearNum) {
		boolean isLeep = false;
		if ((yearNum % 4 == 0) && (yearNum % 100 != 0)) {
			isLeep = true;
		} else if (yearNum % 400 == 0) {
			isLeep = true;
		} else {
			isLeep = false;
		}
		return isLeep;
	}

	/**
	 * Description :  计算某年某月的开始日期 
	 * @param yearNum
	 * @param monthNum
	 * @return String
	 */
	public String getYearMonthFirstDay(int yearNum, int monthNum) {
		String tempYear = Integer.toString(yearNum);
		String tempMonth = Integer.toString(monthNum);
		String tempDay = "1";
		String tempDate = tempYear + "-" + tempMonth + "-" + tempDay;
		return setDateFormat(tempDate, "yyyy-MM-dd");
	}

	/**
	 * Description :   计算某年某月的结束日期 
	 * @param yearNum
	 * @param monthNum
	 * @return String
	 */
	public String getYearMonthEndDay(int yearNum, int monthNum) {
		String tempYear = Integer.toString(yearNum);
		String tempMonth = Integer.toString(monthNum);
		String tempDay = "31";
		if (tempMonth.equals("1") || tempMonth.equals("3") || tempMonth.equals("5") || tempMonth.equals("7") || tempMonth.equals("8") || tempMonth.equals("10") || tempMonth.equals("12")) {
			tempDay = "31";
		}
		if (tempMonth.equals("4") || tempMonth.equals("6") || tempMonth.equals("9") || tempMonth.equals("11")) {
			tempDay = "30";
		}
		if (tempMonth.equals("2")) {
			if (isLeapYear(yearNum)) {
				tempDay = "29";
			} else {
				tempDay = "28";
			}
		}
		String tempDate = tempYear + "-" + tempMonth + "-" + tempDay;
		return setDateFormat(tempDate, "yyyy-MM-dd");
	}

	/**
	 * Description : 计算指定日期某年的第几周
	 * @return int
	 */
	public static int getWeekNumOfYearDay(String strDate) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date curDate = format.parse(strDate);
			calendar.setTime(curDate);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}

		int iWeekNum = calendar.get(Calendar.WEEK_OF_YEAR);
		return iWeekNum;
	}

	/**
	 * Description : 计算某年某周的开始日期
	 * @return String
	 */
	public static String getYearWeekFirstDay(int yearNum, int weekNum) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, yearNum);
		cal.set(Calendar.WEEK_OF_YEAR, weekNum);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		// 分别取得当前日期的年、月、日
		String tempYear = Integer.toString(yearNum);
		String tempMonth = Integer.toString(cal.get(Calendar.MONTH) + 1);
		String tempDay = Integer.toString(cal.get(Calendar.DATE));
		String tempDate = tempYear + "-" + tempMonth + "-" + tempDay;
		return setDateFormat(tempDate, "yyyy-MM-dd");
	}

	/**
	 * Description : 计算某年某周的结束日期
	 * @return String
	 */
	public static String getYearWeekEndDay(int yearNum, int weekNum) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, yearNum);
		cal.set(Calendar.WEEK_OF_YEAR, weekNum + 1);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		// 分别取得当前日期的年、月、日
		String tempYear = Integer.toString(yearNum);
		String tempMonth = Integer.toString(cal.get(Calendar.MONTH) + 1);
		String tempDay = Integer.toString(cal.get(Calendar.DATE));
		String tempDate = tempYear + "-" + tempMonth + "-" + tempDay;
		return setDateFormat(tempDate, "yyyy-MM-dd");
	}

	/**
	 * Description : 得到某一年周的总数
	 * @param year
	 * @return int
	 */
	public static int getMaxWeekNumOfYear(int year) {
		Calendar cal = new GregorianCalendar();
		cal.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
		return getWeekOfYear(cal.getTime());
	}

	/**
	* Description : 计算当前日期某年的第几周
	* @return int
	*/
	public static int getWeekNumOfYear() {
		Calendar cal = new GregorianCalendar();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setMinimalDaysInFirstWeek(7);
		cal.setTime(new Date());
		;
		int iWeekNum = cal.get(Calendar.WEEK_OF_YEAR);
		return iWeekNum;
	}

	/**
	* Description : 计算指定日期某年的第几周
	* @param date
	* @return int
	*/
	public static int getWeekOfYear(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setMinimalDaysInFirstWeek(7);
		cal.setTime(date);
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	* 得到某年某周的第一天
	* 
	* @param year 
	* @param week
	* @return
	*/
	public static String getFirstDayOfWeek(int year, int week) {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);

		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, week * 7);

		return getFirstDayOfWeek(cal.getTime());
	}

	/**
	* 得到某年某周的最后一天
	* 
	* @param year
	* @param week
	* @return
	*/
	public static String getLastDayOfWeek(int year, int week) {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);

		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, week * 7);

		return getLastDayOfWeek(cal.getTime());
	}

	/**
	* 取得当前日期所在周的第一天
	* 
	* @param date
	* @return
	*/
	public static String getFirstDayOfWeek(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek()); // Monday
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sDate = sdf.format(cal.getTime());
		return sDate;
	}

	/**
	* 取得当前日期所在周的最后一天
	* 
	* @param date
	* @return
	*/
	public static String getLastDayOfWeek(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek() + 6); // Sunday
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sDate = sdf.format(cal.getTime());
		return sDate;
	}

	/**
	* 取得当前日期是多少周
	* 
	* @param date
	* @return
	*/
	public static int getWeekOfYear1(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setMinimalDaysInFirstWeek(7);
		cal.setTime(date);
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	public static Calendar parseDateTime(String baseDate) {
		Calendar cal = null;
		cal = new GregorianCalendar();
		int yy = Integer.parseInt(baseDate.substring(0, 4));
		int mm = Integer.parseInt(baseDate.substring(5, 7)) - 1;
		int dd = Integer.parseInt(baseDate.substring(8, 10));
		int hh = 0;
		int mi = 0;
		int ss = 0;
		if (baseDate.length() > 12) {
			hh = Integer.parseInt(baseDate.substring(11, 13));
			mi = Integer.parseInt(baseDate.substring(14, 16));
			ss = Integer.parseInt(baseDate.substring(17, 19));
		}
		cal.set(yy, mm, dd, hh, mi, ss);
		return cal;
	}

	public static int getWeekDay(String strDate) {
		Calendar cal = parseDateTime(strDate);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	public static String getWeekDayName(String strDate) {
		String mName[] = { "日", "一", "二", "三", "四", "五", "六" };
		int iWeek = getWeekDay(strDate);
		iWeek = iWeek - 1;
		return "星期" + mName[iWeek];
	}

	public static Date getDateByStr(String strDate, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	// Java秒转换成小时分钟秒函数
	public static String splitTime(int second) {
		String result = "";
		int hourTime = second / 3600;
		int minuteTime = (second % 3600) / 60;
		int secondTime = (second % 3600) % 60;
		if (hourTime > 0) {
			result = hourTime + "时" + minuteTime + "分" + secondTime + "秒";
		} else {
			result = minuteTime + "分" + secondTime + "秒";
		}

		return result;

	}

	// 秒转换成天小时分钟秒函数（8小时为1天）
	public static String splitTime2(int second) {
		String result = "";
		int hourTime = second / 3600;
		int dayTime = hourTime / 8;
		hourTime = hourTime % 8;
		int minuteTime = (second % 3600) / 60;
		int secondTime = (second % 3600) % 60;

		if (dayTime > 0) {
			result = dayTime + "天";
		}
		if (hourTime > 0) {
			result += hourTime + "小时";
		}
		if (minuteTime > 0) {
			result += minuteTime + "分";
		}
		if (secondTime > 0) {
			result += secondTime + "秒";
		}

		return result;

	}

	/**
	 * 获得指定日期前后的某一天日期
	 * 
	 * @param specifiedDay指定日期
	 * @param format为输出格式(指定日期和输出格式要一致，例如：日期为2012-08-17，输出格式为yyyy-MM-dd)
	 * @param sp为天数
	 * @return
	 */
	public static String getSpecifiedDay(String specifiedDay, String format, int sp) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat(format).parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + sp);

		String rtnday = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return rtnday;
	}

	public static String getSpecifiedDayYYYYMMDD(Date specifiedDay, int sp) {
		Calendar c = Calendar.getInstance();
		c.setTime(specifiedDay);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + sp);

		String rtnday = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return rtnday;
	}

	public static Date getSpecifiedDayYYYYMMDD2(Date specifiedDay, int sp) {
		Calendar c = Calendar.getInstance();
		c.setTime(specifiedDay);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + sp);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sDate = sdf.format(c.getTime());
		Date date = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = df.parse(sDate);
		} catch (ParseException e) {
		}

		return date;
	}

	public static Date getSpecifiedDay(Date specifiedDay, int sp) {
		Calendar c = Calendar.getInstance();
		c.setTime(specifiedDay);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + sp);
		return c.getTime();
	}

	/*
	 * 获取两个日期之间的间隔天数 结束日期要大于起算日期 参数 startdate起算日期 enddate结束日期
	 */
	public static Long getDaysBetween(String startdate, String enddate) {
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = format1.parse(startdate);
			endDate = format1.parse(enddate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Calendar fromCalendar = Calendar.getInstance();
		fromCalendar.setTime(startDate);
		fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
		fromCalendar.set(Calendar.MINUTE, 0);
		fromCalendar.set(Calendar.SECOND, 0);
		fromCalendar.set(Calendar.MILLISECOND, 0);

		Calendar toCalendar = Calendar.getInstance();
		toCalendar.setTime(endDate);
		toCalendar.set(Calendar.HOUR_OF_DAY, 0);
		toCalendar.set(Calendar.MINUTE, 0);
		toCalendar.set(Calendar.SECOND, 0);
		toCalendar.set(Calendar.MILLISECOND, 0);

		return (toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24);
	}

	public static Date addSeconde(Date curDate, int sp) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(curDate);
		calendar.add(Calendar.SECOND, sp);
		return calendar.getTime();
	}

	/*
	 * 把 yyyy-MM-dd hh:mm:ss 改成 yyyy-MM-dd
	 */
	public static Date getTimeFormat(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sDate = sdf.format(date);
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = null;
		try {
			startDate = format1.parse(sDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return startDate;
	}

	public static Date getStartTime() {
		Calendar todayStart = Calendar.getInstance();
		todayStart.set(Calendar.HOUR_OF_DAY, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		todayStart.set(Calendar.MILLISECOND, 0);
		Date date = new Date(todayStart.getTime().getTime());
		return date;
	}

	public static Date getEndTime() {
		Calendar todayEnd = Calendar.getInstance();
		todayEnd.set(Calendar.HOUR_OF_DAY, 23);
		todayEnd.set(Calendar.MINUTE, 59);
		todayEnd.set(Calendar.SECOND, 59);
		todayEnd.set(Calendar.MILLISECOND, 999);
		Date date = new Date(todayEnd.getTime().getTime());
		return date;
	}

	public static Date formatDay(String specifiedDay, String format) throws Exception {
		Date date = new SimpleDateFormat(format).parse(specifiedDay);
		return date;
	}

	public static Date dateCalculate(Date date, int count) throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, count);// 对小时数进行+2操作,同理,减2为-2
		return calendar.getTime();
	}

	public static String date2str(Date date, String fomat) {
		SimpleDateFormat sdf = new SimpleDateFormat(fomat);
		String str = sdf.format(date);
		return str;
	}

	public static void main(String[] args) throws Exception {
	}
}
