package yyj.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * 时间工具类
 * @author 1
 *
 */
public class TimeUtil {
	/**默认格式*/
	private static SimpleDateFormat defFormat	 = new SimpleDateFormat("yyyyMMddHHmmss",Locale.CHINA);
	/**默认日期格式*/
	private static SimpleDateFormat defDateFormat= new SimpleDateFormat("yyyyMMdd",Locale.CHINA);		
	/**默认时间格式*/
	private static SimpleDateFormat defTimeFormat= new SimpleDateFormat("HHmmss",Locale.CHINA);		
	/**
	 * 私有的构造方法
	 */
	private TimeUtil(){
		
	}
	/**
	 * 根据给定的日期格式,格式化日期时间
	 * @param sdf	格式对象
	 * @param date	日期时间
	 * @return
	 */
	public static String format(SimpleDateFormat sdf,Date date){
		return sdf.format(date);
	}
	/**
	 * 根据给定的日期格式表达式,格式化日期时间
	 * @param regex	格式表达式
	 * @param date	日期时间
	 * @return
	 */
	public static String format(String regex,Date date){
		return new SimpleDateFormat(regex).format(date);
	}
	/**
	 * 根据给定的日期格式,将字符串转日期时间
	 * @param sdf	格式化对象
	 * @param date	字符串形式日期时间
	 * @return
	 */
	public static Date parse(SimpleDateFormat sdf,String date){
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			throw new AppException("日期转换异常",e);
		}
	}
	/**
	 * 根据给定的日期格式表达式,将字符串转日期时间
	 * @param regex	格式表达式
	 * @param date	字符串形式日期时间
	 * @return
	 */
	public static Date parse(String regex,String date){
		try {
			return new SimpleDateFormat(regex).parse(date);
		} catch (ParseException e) {
			throw new AppException("时间转换异常",e);
		}
	}
	/**
	 * 使用默认日期格式,生成日期字符串
	 * @param date	日期
	 * @return
	 */
	public static String formatDate(Date date){
		return format(defDateFormat, date);
	}
	/**
	 * 使用默认时间格式,生成时间字符串
	 * @param time	时间
	 * @return
	 */
	public static String formatTime(Date time){
		return format(defTimeFormat, time);
	}
	/**
	 * 使用默认格式格式化日期时间为字符串
	 * @param date
	 * @return
	 */
	public static String formatDateTime(Date date){
		return format(defFormat, date);
	}
	/**
	 * 使用默认时间格式,将字符串形式日期转为日期
	 * @param time	字符串形式日期
	 * @return
	 */
	public static Date parseDate(String date){
		return parse(defDateFormat, date);
	}
	/**
	 * 使用默认时间格式,将字符串形式时间转为时间
	 * @param time	字符串形式时间
	 * @return
	 */
	public static Date parseTime(String time){
		return parse(defTimeFormat, time);
	}
	/**
	 * 使用默认格式格式化字符串为日期时间
	 * @param dateTime
	 * @return
	 */
	public static Date parseDateTime(String dateTime){
		return parse(defFormat, dateTime);
	}
	/**
	 * 获取当前日期(字符串)
	 * @return
	 */
	public static String getDate(){
		return format(defDateFormat,new Date());
	}
	/**
	 * 获取当前时间(字符串)
	 * @return
	 */
	public static String getTime(){
		return format(defTimeFormat, new Date());
	}
	/**
	 * 获取当前时间日期
	 * @return
	 */
	public static String getDateTime(){
		return format(defFormat, new Date());
	}
	/**
	 * 日期 时间 拼成 日期+时间
	 * @param date
	 * @param time
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Date  getDateTime(Date date,Date time){
		return new Date(date.getYear(),date.getMinutes(),date.getDate(),time.getHours(),time.getMinutes(),time.getSeconds());
	}
	/**
	 * 日期 时间 字符串 拼成 日期+时间字符串
	 * @param date
	 * @param time
	 * @return
	 */
	public static String getDateTimeStr(String date,String time){
		return date+time;
	}
	/**
	 * 日期 时间 字符串 拼成 日期+时间
	 * @param date
	 * @param time
	 * @return
	 */
	public static Date getDateTime(String date,String time){
		return parse(defFormat, getDateTimeStr(date, time));
	}
	/**
	 * 日期 时间  拼成 日期+时间字符串
	 * @param date
	 * @param time
	 * @return
	 */
	public static String getDateTimeStr(Date date,Date time){
		return format(defFormat, getDateTime(date, time));
	}
	/**
	 * 设置默认日期时间格式
	 * @param format
	 */
	public static void setDefFormat(String format){
		defFormat=new SimpleDateFormat(format);
	}
	/**
	 * 设置默认日期格式
	 * @param format
	 */
	public static void setDefDateFormat(String format){
		defDateFormat=new SimpleDateFormat(format);
	}
	/**
	 * 设置默认时间格式
	 * @param format
	 */
	public static void setDefTimeFormat(String format){
		defTimeFormat=new SimpleDateFormat(format);
	}
}
