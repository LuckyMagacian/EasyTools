package yyj.tools;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
/**
 * 日志工具类
 * @author yangyuanjian
 *
 */
public class LoggerUtil {
	/**日志文件已存在时是否清空原有日志*/
	private static boolean clear = false;
	static {

	}
	/**
	 * 日志配置初始化
	 */
	public static void init() {
		try {
			String path = LoggerUtil.class.getClassLoader().getResource("").toURI().getPath();
			if (path.contains("target"))
				path = path.substring(0, path.indexOf("target")) + "src/main/resources/log/daily.log";
			else
				path = path.substring(0, path.indexOf("WEB-INF")) + "WEB-INF/classes/log/daily.log";
			String tempPath = path.substring(0, path.indexOf("daily.log"));
			File logDir = new File(tempPath);
			if (!logDir.exists())
				logDir.mkdirs();
			File temp = new File(tempPath + "daily.log");

			if (clear) {
				if (temp.exists())
					temp.delete();
			}

			if (!temp.exists())
				temp.createNewFile();

			Properties properties = new Properties();
			properties.setProperty("log4j.rootLogger", "INFO,stdout,logfile");
			properties.setProperty("INFO,stdout,logfile", "org.apache.log4j.ConsoleAppender");
			properties.setProperty("log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout");
			properties.setProperty("log4j.appender.stdout.layout.ConversionPattern", "%d %p [%c] - <%m>%n");
			properties.setProperty("log4j.appender.logfile", "org.apache.log4j.DailyRollingFileAppender");
			properties.setProperty("log4j.appender.logfile.Encoding", "UTF-8");
			properties.setProperty("log4j.appender.logfile.File", path);
			properties.setProperty("log4j.appender.logfile.DatePattern", "yyyy-MM-dd'.log'");
			properties.setProperty("log4j.appender.logfile.File.Append", "true");
			properties.setProperty("log4j.appender.CONSOLE", "org.apache.log4j.ConsoleAppender");
			properties.setProperty("log4j.appender.CONSOLE.layout", "org.apache.log4j.PatternLayout");
			properties.setProperty("log4j.appender.logfile.layout", "org.apache.log4j.PatternLayout");
			properties.setProperty("log4j.appender.logfile.layout.ConversionPattern", "%d %p [%c] - <%m>%n");
			PropertyConfigurator.configure(properties);
		} catch (Exception e) {
			throw new RuntimeException("加载log4j配置异常", e);
		}
	}

	public static boolean isClear() {
		return clear;
	}
	
	/**
	 * 设置清空日志
	 * @param    clear  清空日志的标记 
	 */
	public static void setClear(boolean clear) {
		LoggerUtil.clear = clear;
	}

}
