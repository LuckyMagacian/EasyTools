package yyj.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

@Deprecated()
public class SqlUtil {
	/**
	 * 私有的构造方法 禁止实例化
	 */
	private SqlUtil() {
	};

	/**
	 * 根据类对象和表名 创建 insert sql语句
	 * 
	 * @param clazz
	 *            类对象
	 * @param tableName
	 *            表名
	 * @return 生成的insert语句
	 */
	public static String createInsert(Class<?> clazz, String tableName) {
		// logger.info("生成mybatis插入语句->类名:"+clazz.getName()+"->表名:"+tableName);
		StringBuffer temp = new StringBuffer();
		String tName = clazz.getName(); // 类全名
		String sName = clazz.getSimpleName(); // 类名
		temp.append("<insert id=\"add" + sName.replace("Bean", "") + "\" " + "parameterType=\"" + tName + "\">\n");
		temp.append("insert into " + tableName + " \n(");
		Field[] fields = clazz.getDeclaredFields();
		List<Field> list = new ArrayList<Field>();
		for (Field each : fields) {
			each.setAccessible(true);
			int modifiers = each.getModifiers();
			if (Modifier.isStatic(modifiers))
				continue;
			list.add(each);
			char[] chars = each.getName().toCharArray();
			String name = each.getName();
			for (int i = 0; i < chars.length; i++) {
				if (chars[i] <= 'Z' && chars[i] >= 'A')
					name = name.replaceFirst("" + chars[i], "_" + (char) (chars[i] + 32));
			}
			temp.append(name + ",");
		}
		temp.replace(temp.length() - 1, temp.length(), "");
		temp.append(")\nvalues\n(");
		for (Field each : list) {
			each.setAccessible(true);
			int modifiers = each.getModifiers();
			if (Modifier.isStatic(modifiers))
				continue;
			temp.append("#{" + each.getName() + "},");
		}
		temp.replace(temp.length() - 1, temp.length(), "");
		temp.append(")");
		temp.append("\n</insert>\n");
		return temp.toString();
	}

	/**
	 * 根据类对象和表名 创建 insert sql语句 所有值采用默认值 需要修改Id为正常的取值
	 * 
	 * @param clazz
	 *            类对象
	 * @param tableName
	 *            表名
	 * @return 生成的insert语句
	 */
	public static String createInsertDefault(Class<?> clazz, String tableName) {
		// logger.info("生成mybatis插入语句->类名:"+clazz.getName()+"->表名:"+tableName);
		StringBuffer temp = new StringBuffer();
		String tName = clazz.getName(); // 类全名
		String sName = clazz.getSimpleName(); // 类名
		temp.append(
				"<insert id=\"add" + sName.replace("Bean", "") + "Default\" " + "parameterType=\"" + tName + "\">\n");
		temp.append("insert into " + tableName + " \n(");
		Field[] fields = clazz.getDeclaredFields();
		List<Field> list = new ArrayList<Field>();
		for (Field each : fields) {
			each.setAccessible(true);
			int modifiers = each.getModifiers();
			if (Modifier.isStatic(modifiers))
				continue;
			list.add(each);
			char[] chars = each.getName().toCharArray();
			String name = each.getName();
			for (int i = 0; i < chars.length; i++) {
				if (chars[i] <= 'Z' && chars[i] >= 'A')
					name = name.replaceFirst("" + chars[i], "_" + (char) (chars[i] + 32));
			}
			temp.append(name + ",");
		}
		temp.replace(temp.length() - 1, temp.length(), "");
		temp.append(")\nvalues\n(");
		for (Field each : list) {
			each.setAccessible(true);
			int modifiers = each.getModifiers();
			if (Modifier.isStatic(modifiers))
				continue;
			temp.append("DEFAULT,");
		}
		temp.replace(temp.length() - 1, temp.length(), "");
		temp.append(")");
		temp.append("\n</insert>\n");
		return temp.toString();
	}

	/**
	 * 根据 类对象和表名 创建 delete sql语句
	 * 
	 * @param clazz
	 *            类对象
	 * @param tableName
	 *            表名
	 * @return 生成的select语句
	 */
	public static String createDelete(Class<?> clazz, String tableName) {
		// logger.info("生成mybatis删除语句->类名:"+clazz.getName()+"->表名:"+tableName);
		StringBuffer temp = new StringBuffer();
		String tName = clazz.getName(); // 类全名
		String sName = clazz.getSimpleName(); // 类名
		temp.append("<delete id=\"delete" + sName.replace("Bean", "") + "\" " + "parameterType=\"" + tName + "\">\n");
		temp.append("delete from " + tableName);
		temp.append("\n<where>\n");
		Field[] fields = clazz.getDeclaredFields();
		for (Field each : fields) {
			each.setAccessible(true);
			int modifiers = each.getModifiers();
			if (Modifier.isStatic(modifiers))
				continue;
			char[] chars = each.getName().toCharArray();
			String name = each.getName();
			for (int i = 0; i < chars.length; i++) {
				if (chars[i] <= 'Z' && chars[i] >= 'A')
					name = name.replaceFirst("" + chars[i], "_" + (char) (chars[i] + 32));
			}
			temp.append("<if test=\"" + each.getName() + " != null\"> and " + name + " = #{" + each.getName()
					+ "}  </if>  \n");
		}
		temp.append("</where>\n</delete>\n");
		return temp.toString();
	}

	/**
	 * 根据 类对象和表名 创建 update sql语句 where部分请根据需要手动删除!!! 若仅保留一个条件 请把 if
	 * 标签也去掉,否则将会更新所有记录
	 * 
	 * @param clazz
	 *            类对象
	 * @param tableName
	 *            表名称
	 * @return 生成的update语句
	 */
	public static String createUpdate(Class<?> clazz, String tableName) {
		// logger.info("生成mybatis更新语句->类名:"+clazz.getName()+"->表名:"+tableName);
		StringBuffer temp = new StringBuffer();
		String tName = clazz.getName(); // 类全名
		String sName = clazz.getSimpleName(); // 类名
		List<Field> list = new ArrayList<Field>();
		temp.append("<update id=\"update" + sName.replace("Bean", "") + "\" " + "parameterType=\"" + tName + "\">\n");
		temp.append("update " + tableName + "\n<set> \n");
		Field[] fields = clazz.getDeclaredFields();
		for (Field each : fields) {
			each.setAccessible(true);
			int modifiers = each.getModifiers();
			if (Modifier.isStatic(modifiers))
				continue;
			list.add(each);
			char[] chars = each.getName().toCharArray();
			String name = each.getName();
			for (int i = 0; i < chars.length; i++) {
				if (chars[i] <= 'Z' && chars[i] >= 'A')
					name = name.replaceFirst("" + chars[i], "_" + (char) (chars[i] + 32));
			}
			temp.append(
					"<if test=\"" + each.getName() + " != null\">" + name + " = #{" + each.getName() + "},</if>  \n");
		}
		temp.append("</set>\n<where>\n");
		for (Field each : list) {
			each.setAccessible(true);
			int modifiers = each.getModifiers();
			if (Modifier.isStatic(modifiers))
				continue;
			char[] chars = each.getName().toCharArray();
			String name = each.getName();
			for (int i = 0; i < chars.length; i++) {
				if (chars[i] <= 'Z' && chars[i] >= 'A')
					name = name.replaceFirst("" + chars[i], "_" + (char) (chars[i] + 32));
			}
			temp.append("<if test=\"" + each.getName() + " != null\"> and " + name + " = #{" + each.getName()
					+ "}  </if>  \n");
		}
		temp.append("</where>\n</update>\n");
		return temp.toString();
	}

	/**
	 * 根据 类对象和表名 创建 select sql语句 where部分需要根据需要手动删除
	 * 
	 * @param clazz
	 *            类对象
	 * @param tableName
	 *            表名
	 * @return 生成的select语句
	 */
	public static String createSelect(Class<?> clazz, String tableName) {
		// logger.info("生成mybatis查询语句->类名:"+clazz.getName()+"->表名:"+tableName);
		StringBuffer temp = new StringBuffer();
		String tName = clazz.getName(); // 类全名
		String sName = clazz.getSimpleName(); // 类名
		List<Field> list = new ArrayList<Field>();
		temp.append("<select id=\"select" + sName.replace("Bean", "") + "\" " + "resultMap=\""
				+ sName.replace("Bean", "").replace(sName.charAt(0), (char) (sName.charAt(0) + 32)) + "Map\" "
				+ "parameterType=\"" + tName + "\" resultType=\"" + tName + "\">\n");
		temp.append("select\n");
		Field[] fields = clazz.getDeclaredFields();
		for (Field each : fields) {
			each.setAccessible(true);
			int modifiers = each.getModifiers();
			if (Modifier.isStatic(modifiers))
				continue;
			list.add(each);
			char[] chars = each.getName().toCharArray();
			String name = each.getName();
			for (int i = 0; i < chars.length; i++) {
				if (chars[i] <= 'Z' && chars[i] >= 'A')
					name = name.replaceFirst("" + chars[i], "_" + (char) (chars[i] + 32));
			}
			temp.append(name + ",");
		}
		temp.replace(temp.length() - 1, temp.length(), "");
		temp.append("\nfrom " + tableName);
		temp.append("\n<where> \n");
		for (Field each : list) {
			each.setAccessible(true);
			int modifiers = each.getModifiers();
			if (Modifier.isStatic(modifiers))
				continue;
			char[] chars = each.getName().toCharArray();
			String name = each.getName();
			for (int i = 0; i < chars.length; i++) {
				if (chars[i] <= 'Z' && chars[i] >= 'A')
					name = name.replaceFirst("" + chars[i], "_" + (char) (chars[i] + 32));
			}
			temp.append("<if test=\"" + each.getName() + " != null\"> and " + name + " = #{" + each.getName()
					+ "}  </if>  \n");
		}
		temp.append("</where>\n</select>\n");
		return temp.toString();
	}

	/**
	 * 根据 类对象和表名 创建 resultMap
	 * 
	 * @param clazz
	 *            类对象
	 * @return 生成的resultMap语句
	 */
	public static String createResultMap(Class<?> clazz) {
		// logger.info("生成mybatis ResiltMap语句->类名:"+clazz.getName());
		StringBuffer temp = new StringBuffer();
		String tName = clazz.getName(); // 类全名
		String sName = clazz.getSimpleName(); // 类名
		temp.append(
				"<resultMap id=\"" + sName.replace("Bean", "").replace(sName.charAt(0), (char) (sName.charAt(0) + 32))
						+ "Map\" " + "type=\"" + tName + "\">\n");
		Field[] fields = clazz.getDeclaredFields();
		for (Field each : fields) {
			each.setAccessible(true);
			int modifiers = each.getModifiers();
			if (Modifier.isStatic(modifiers))
				continue;
			char[] chars = each.getName().toCharArray();
			String name = each.getName();
			for (int i = 0; i < chars.length; i++) {
				if (chars[i] <= 'Z' && chars[i] >= 'A')
					name = name.replaceFirst("" + chars[i], "_" + (char) (chars[i] + 32));
			}
			temp.append("<result property=\"" + each.getName() + "\" 			column=\"" + name + "\"></result>\n");
		}
		temp.append("</resultMap>\n");
		return temp.toString();
	}

	/**
	 * 根据 类对象和表名 创建 mybatis映射文件,命名空间将根据类名取前 3 段作为 公司域名+项目名
	 * 
	 * @param clazz
	 *            类对象
	 * @param tableName
	 *            表名
	 */
	public static void createMapperFile(Class<?> clazz, String tableName) {
		// logger.info("生成mybatis映射文件->类名:"+clazz.getName()+"->表名:"+tableName);
		StringBuffer temp = new StringBuffer();
		temp.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<!DOCTYPE mapper PUBLIC "
				+ "\"-//ibatis.apache.org//DTD Mapper 3.0//EN\" "
				+ "\"http://ibatis.apache.org/dtd/ibatis-3-mapper.dtd\">\n");
		temp.append("<mapper namespace=\"");
		String[] strs = clazz.getName().split("\\.");
		temp.append(strs[0] + ".");
		temp.append(strs[1] + ".");
		temp.append(strs[2] + ".");
		temp.append("dao.");
		temp.append(clazz.getSimpleName().replace("Bean", "") + "Dao\">\n");
		temp.append(createInsert(clazz, tableName));
		temp.append(createInsertDefault(clazz, tableName));
		temp.append(createDelete(clazz, tableName));
		temp.append(createUpdate(clazz, tableName));
		temp.append(createSelect(clazz, tableName));
		temp.append(createResultMap(clazz));
		temp.append("</mapper>");
		try {
			// 获取classpath路径
			String path = SqlUtil.class.getClassLoader().getResource(File.separator).toURI().getPath();
			// 设置保存文件夹位置
			path = path + File.separatorChar + "mapper";
			// 判断文件夹是否存在,若不存在 创建mapper文件夹
			File dir = new File(path);
			if (!dir.exists())
				dir.mkdir();
			// 设置生成文件路径及名称
			path = path + File.separatorChar + clazz.getSimpleName().replace("Bean", "") + "Mapper.xml";
			File file = new File(path);
			if (!file.exists())
				file.createNewFile();
			FileOutputStream foStream = new FileOutputStream(file);
			OutputStreamWriter osWriter = new OutputStreamWriter(foStream, "utf-8");
			PrintWriter printWriter = new PrintWriter(osWriter);
			printWriter.print(temp.toString());
			printWriter.flush();
			printWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
			new AppException("生成mapper映射文件异常", e);
		}
	}

	/**
	 * 生成默认的方法名称
	 * 
	 * @param clazz
	 *            类对象
	 * @return 生成的方法名称列表
	 */
	public static List<String> createMethodName(Class<?> clazz) {
		List<String> list = new ArrayList<>();
		String name = clazz.getSimpleName();
		list.add("public void add" + name + "(" + name + " "
				+ name.replaceFirst("" + name.charAt(0), "" + (char) (name.charAt(0) + 32)) + ");");
		list.add("public void add" + name + "Default(" + name + " "
				+ name.replaceFirst("" + name.charAt(0), "" + (char) (name.charAt(0) + 32)) + ");");
		list.add("public void delete" + name + "(" + name + " "
				+ name.replaceFirst("" + name.charAt(0), "" + (char) (name.charAt(0) + 32)) + ");");
		list.add("public void update" + name + "(" + name + " "
				+ name.replaceFirst("" + name.charAt(0), "" + (char) (name.charAt(0) + 32)) + ");");
		list.add("public List<" + name + "> select" + name + "(" + name + " "
				+ name.replaceFirst("" + name.charAt(0), "" + (char) (name.charAt(0) + 32)) + ");");
		list.add("public " + name + " select" + name + "ById(String id);");
		return list;
	}

	/**
	 * 全部调用一次
	 * 
	 * @param clazz
	 *            传入的类对象
	 * @param tableName
	 *            表名
	 */
	public static void createAll(Class<?> clazz, String tableName) {
		System.out.println(createInsert(clazz, tableName));
		System.out.println(createInsertDefault(clazz, tableName));
		System.out.println(createDelete(clazz, tableName));
		System.out.println(createUpdate(clazz, tableName));
		System.out.println(createSelect(clazz, tableName));
		System.out.println(createResultMap(clazz));
		System.out.println(createMethodName(clazz).toString().replaceAll(",", ""));
	}
}
