package yyj.tools;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;

@SuppressWarnings("serial")
public class AppException extends RuntimeException{
	/**异常等级-系统错误*/
	public 	static	String	EXCEPTION_LEVEL_SYSTEM		="9999";
	/**异常等级-网络错误*/
	public	static	String	EXCEPTION_LEVEL_NET			="9998";
	/**异常等级-数据库错误*/
	public	static	String	EXCEPTION_LEVEL_DATABASE	="9997";
	/**异常等级-业务逻辑错误*/
	public	static	String	ExCEPTION_LEVEL_BUSINESS	="8999";
	/**异常等级-java基础错误*/
	public 	static	String	EXCEPTION_LEVEL_BASE		="7999";
	/**异常等级-数据错误*/
	public	static	String	EXCEPTION_LEVEL_DATA		="6999";
	/**异常等级-成功*/
	public 	static	String	EXCEPTION_LEVEL_SUCCESS		="0000";
	/**日志记录*/
	private static Logger  logger=Logger.getLogger(AppException.class);
	/**测试标记*/
	private static boolean testFlag=true;
	
	@SuppressWarnings("unused")
	private AppException() {
		super();
		logger.error("异常信息:"+getMessage());
		logger.error("异常原因:"+getStackInfo());
	}

	public AppException(String message, Throwable cause) {
		super(message, cause);
		logger.error("异常信息:"+getMessage());
		logger.error("异常原因:"+getStackInfo());
	}
	
	public AppException(String message) {
		super(message);
		logger.error("异常信息:"+getMessage());
	}
	/**
	 * 获取堆栈信息
	 * @return 
	 */
	public String getStackInfo(){
		StringWriter stringWriter=new StringWriter();
		PrintWriter  printWriter =new PrintWriter(stringWriter);
		getCause().printStackTrace(printWriter);
		printWriter.close();
		return stringWriter.toString();
	}
	/**
	 * 启用测试模式
	 * 测试模式:会直接在控制台输出堆栈信息
	 */
	public static void enTest(){
		testFlag=true;
	}
	/**
	 * 关闭测试模式
	 * 测试模式:会直接在控制台输出堆栈信息
	 */
	public static void unTest(){
		testFlag=false;
	}
	/**
	 * 查看测试模式状态
	 * @return 	true ->测试模式开启
	 * 			false->测试模式关闭
	 */
	public static boolean getTestFlag(){
		return testFlag;
	}
	
}
