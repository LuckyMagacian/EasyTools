package yyj.tools;

import java.util.Random;
import java.util.UUID;

public class RandomUtil {
	private static  Random random = new Random();
	/**
	 * 生成随机字母(小写)
	 * @return  随机字符
	 */
	public static char getRandomChar(){
		return (char) (random.nextInt(26)+'a');
	}
	/**
	 * 生成随机数字
	 * @return 随机一个数字
	 */
	public static char getRandomNum(){
		return (char) (random.nextInt(10)+'0');
	}
	/**
	 * 生成指定数量的字母字符串(小写)
	 * @param count 字符串的字符个数
	 * @return 随机英文字符串
	 */
	public static String getRandomChar(int count){
		StringBuilder rs=new StringBuilder();
		while(count-->0)
			rs.append(getRandomChar());
		return rs.toString();
	}
	/**
	 * 生成指定数量的随数字 字符串
	 * @param count	字符数
	 * @return 随机数字符串
	 */
	public static String getRandomNumber(int count){
		StringBuilder rs=new StringBuilder();
		while(count-->0)
			rs.append(getRandomNum());
		return rs.toString();
	}
	/**
	 * 获取uuid
	 * @return uuid字符串
	 */
	public static String getUUID(){
		return UUID.randomUUID().toString();
	}

}
