package yyj.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;


public class ConfigUtil {
	private static Properties properties;
	/**
	 * 初始化
	 */
	static {
		try {
			File[] files = null;
			files = FileUtil.getFilesOppositeClassPath("/properties", ".properties");
			for (File each : files)
				loadProperties(each);
		} catch (Exception e) {
			throw new AppException("初始化配置文件设置异常", e);
		}

	}

	/**
	 * 加载文件
	 * 
	 * @param file
	 * @return
	 */
	public static Properties loadProperties(File file) {
		try {
			if (!file.getName().endsWith(".properties"))
				return properties;
			properties = properties == null ? new Properties() : properties;
			FileInputStream fin = new FileInputStream(file);
			InputStreamReader reader = new InputStreamReader(fin, "utf-8");
			properties.load(reader);
			return properties;
		} catch (Exception e) {
			throw new AppException("加载配置文件异常", e);
		}
	}

	/**
	 * 从输入流中加载配置文件
	 * 
	 * @param inStream
	 * @return
	 */
	public static Properties loadProperties(InputStream inStream) {
		try {
			properties = properties == null ? new Properties() : properties;
			properties.load(inStream);
			return properties;
		} catch (Exception e) {
			throw new AppException("加载配置文件异常", e);
		}
	}

	/**
	 * 从默认的路径加载配置文件 默认路径为 classpath/properties/
	 * 
	 * @return
	 */
	public static Properties load() {
		try {
			properties = properties == null ? new Properties() : properties;
			String path = ConfigUtil.class.getClassLoader().getResource("/").getPath();
			return load(path);
		} catch (Exception e) {
			throw new AppException("加载配置文件异常", e);
		}
	}

	/**
	 * 从指定路径加载配置文件 指定路径为文件,则加载文件 指定路径为目录,加载下所有peroperties文件
	 * 
	 * @param path
	 * @return
	 */
	public static Properties load(String path) {
		try {
			properties = properties == null ? new Properties() : properties;
			File file = new File(path);
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (File each : files) {
					if (!each.getName().endsWith(".properties"))
						continue;
					FileInputStream fin = new FileInputStream(each);
					InputStreamReader reader = new InputStreamReader(fin, "utf-8");
					properties.load(reader);
				}
				return properties;
			} else {
				FileInputStream fin = new FileInputStream(file);
				InputStreamReader reader = new InputStreamReader(fin, "utf-8");
				properties.load(reader);
				return properties;
			}
		} catch (Exception e) {
			throw new AppException("加载配置文件异常", e);
		}
	}

	/**
	 * 获取配置文件
	 * 
	 * @return
	 */
	public static Properties getProperties() {
		return properties;
	}

	/**
	 * 获取配置参数 若配置未初始化将会采用默认形式初始化配置文件
	 * 
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		return null == properties ? load().getProperty(key) : properties.getProperty(key);
	}

}
