/**
 * 
 */
package cn.com.cari.carilibrary.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Title: TimeUtil
 * @Description:
 * @Company: www.cari.com.cn
 * @author zhouzhou
 * @date 2015-11-4 下午3:09:18
 */
public class TimeUtil {

	/**
	 * 获取当前系统时间
	 * 
	 * @return
	 */
	public static String GetNowTime() {
		String reslut = "";
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		reslut = sDateFormat.format(new Date());
		return reslut;
	}
	/**
	 * 获取当前系统时间
	 *
	 * @return
	 */
	public static String GetNowTimeStr() {
		String reslut = "";
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		reslut = sDateFormat.format(new Date());
		return reslut;
	}
	/**
	 * 获取当前系统日期
	 *
	 * @return
	 */
	public static String GetNowDay() {
		String reslut = "";
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		reslut = sDateFormat.format(new Date());
		return reslut;
	}

	/**
	 * 获取当前系统时间前N天的时间 N为- 前n天 N为+ 后n天
	 *
	 * @return
	 */
	public static String getNextDay(int n, String time) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String reslut = "";
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sDateFormat.parse(time));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		calendar.add(Calendar.DAY_OF_MONTH, n);

		reslut = sDateFormat.format(calendar.getTime());
		return reslut;
	}

	/**
	 * 时间String to DateTime
	 *
	 * @param v
	 */
	public static Date ChangeStrToDateTime(String str) {
		Date date = new Date();
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {
			date = sDateFormat.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return date;
	}

	/**
	 * 时间String to DateTime
	 *
	 * @param v
	 */
	public static Date ChangeStrToDateTimeTime(String str) {
		Date date = new Date();
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			date = sDateFormat.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return date;
	}

	/**
	 * 获取当前系统日期
	 * 
	 * @return
	 */
	public static String ChangeDateTimeToStr(Date time) {
		String reslut = "";
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		reslut = sDateFormat.format(time);
		return reslut;
	}

}
