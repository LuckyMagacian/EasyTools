package com.lanxi.tools;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
/**
 * 类|对象  工具类
 * @author yangyuanjian
 *
 */
public class BeanUtil {
	/**
	 * get/set 封装过的 get|set方法对, 未使用
	 * 
	 * @author 1
	 *
	 */
	public static class GetSetPair {
		/** 字段名 */
		private String name;
		/** get方法 */
		private Method get;
		/** set方法 */
		private Method set;

		public Method getGet() {
			return get;
		}

		public void setGet(Method get) {
			this.get = get;
		}

		public Method getSet() {
			return set;
		}

		public void setSet(Method set) {
			this.set = set;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return BeanUtil.toString(this);
		}
	}

	/**
	 * 获取类中所有属性
	 * 
	 * @param obj
	 *            对象或者类
	 * @return 类中所有属性 包括静态属性 Map key为属性名称,Field为对应的Field对象
	 */
	public static Map<String, Field> getAllFields(Object obj) {
		Class<?> clazz = null;
		if (obj instanceof Class<?>)
			clazz = (Class<?>) obj;
		else
			clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		Map<String, Field> map = new LinkedHashMap<String, Field>();
		for (Field each : fields) {
			each.setAccessible(true);
			map.put(each.getName(), each);
		}
		return map;
	}

	/**
	 * 获取类中所有属性
	 * 
	 * @param obj
	 *            对象或者类
	 * @return List 类中所有属性 包括静态属性
	 */
	public static List<Field> getFiledsList(Object obj) {
		Class<?> clazz = null;
		if (obj instanceof Class<?>)
			clazz = (Class<?>) obj;
		else
			clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		List<Field> list = new ArrayList<>();
		for (Field each : fields) {
			each.setAccessible(true);
			list.add(each);
		}
		return list;
	}

	/**
	 * 获取类中 非static 字段 以linkedHashMap形式返回
	 * 
	 * @param obj
	 *            对象或者类
	 * @return map key为属性名称,value为对应的Filed对象
	 */
	public static Map<String, Field> getFieldsNoStatic(Object obj) {
		Class<?> clazz = null;
		if (obj instanceof Class<?>)
			clazz = (Class<?>) obj;
		else
			clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		Map<String, Field> map = new LinkedHashMap<String, Field>();
		for (Field each : fields) {
			each.setAccessible(true);
			if (Modifier.isStatic(each.getModifiers()))
				continue;
			map.put(each.getName(), each);
		}
		return map;
	}

	/**
	 * 获取类中 非static 字段
	 * 
	 * @param obj
	 *            对象或者类
	 * @return map key为属性名称,value为对应的Filed对象
	 */
	public static List<Field> getFieldListNoStatic(Object obj) {
		Class<?> clazz = null;
		if (obj instanceof Class<?>)
			clazz = (Class<?>) obj;
		else
			clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		List<Field> list = new ArrayList<>();
		for (Field each : fields) {
			each.setAccessible(true);
			if (Modifier.isStatic(each.getModifiers()))
				continue;
			list.add(each);
		}
		return list;
	}

	/**
	 * 获取类中所有方法
	 * 
	 * @param obj
	 *            对象或者类
	 * @return Map key为方法名称,value为对应的method对象
	 */
	public static Map<String, Method> getAllMethods(Object obj) {
		Class<?> clazz = null;
		if (obj instanceof Class<?>)
			clazz = (Class<?>) obj;
		else
			clazz = obj.getClass();
		Map<String, Method> map = new LinkedHashMap<String, Method>();
		Method[] methods = clazz.getDeclaredMethods();
		for (Method each : methods) {
			each.setAccessible(true);
			map.put(each.getName(), each);
		}
		return map;
	}

	/**
	 * 获取类中 非static 方法 以linkedHashMap形式返回
	 * 
	 * @param obj
	 *            对象或者类
	 * @return map key为方法名称,value为对应的method对象
	 */
	public static Map<String, Method> getMethodsNoStatic(Object obj) {
		Class<?> clazz = null;
		if (obj instanceof Class<?>)
			clazz = (Class<?>) obj;
		else
			clazz = obj.getClass();
		Map<String, Method> map = new LinkedHashMap<String, Method>();
		Method[] methods = clazz.getDeclaredMethods();
		for (Method each : methods) {
			each.setAccessible(true);
			if (Modifier.isStatic(each.getModifiers()))
				continue;
			map.put(each.getName(), each);
		}
		return map;
	}

	/**
	 * 从请求bean中获取参数 已map形式返回
	 * 
	 * @param bean
	 *            对象
	 * @return key为属性名称,value为对应的值
	 */
	public static Map<String, String> getParamMap(Object bean) {
		Map<String, String> rs = new LinkedHashMap<String, String>();
		Map<String, Field> map = getFieldsNoStatic(bean.getClass());
		try {
			for (Map.Entry<String, Field> each : map.entrySet()) {
				String name = each.getKey();
				Field field = each.getValue();
				field.setAccessible(true);
				String value = (String) field.get(bean);
				rs.put(name, value);
			}
		} catch (Exception e) {
			throw new AppException("获取属性异常", e);
		}
		return rs;
	}

	/**
	 * 获取一个对象中所有的get方法 get方法判定为 get开头且拥有对应的set的方法
	 * 
	 * @param obj
	 *            可以是对象或者类对象
	 * @return get方法的list
	 */
	public static List<Method> getGetters(Object obj) {
		Class<?> clazz = null;
		if (obj instanceof Class<?>)
			clazz = (Class<?>) obj;
		else
			clazz = obj.getClass();
		List<Method> getters = new ArrayList<>();
		Method[] methods = clazz.getMethods();
		for (Method each : methods) {
			if (each.getName().startsWith("get")
					&& getSetterMethods(obj).containsKey("set" + each.getName().substring(3)))
				getters.add(each);
		}
		return getters;
	}

	/**
	 * 获取类|对象中所有的get方法
	 * 
	 * @param obj
	 *            被获取方法的对象
	 * @return key为方法名称 value为方法对象
	 */
	public static Map<String, Method> getGetterMethods(Object obj) {
		Class<?> clazz = null;
		if (obj instanceof Class<?>)
			clazz = (Class<?>) obj;
		else
			clazz = obj.getClass();
		Map<String, Method> getters = new LinkedHashMap<>();
		Method[] methods = clazz.getMethods();
		for (Method each : methods) {
			if (each.getName().startsWith("get"))
				getters.put(each.getName(), each);
		}
		return getters;
	}

	/**
	 * 获取一个对象中所有的set方法 set方法的判定 set开头且拥有对应的get方法
	 * 
	 * @param obj
	 *            可以是对象或者类对象
	 * @return set方法的list
	 */
	public static List<Method> getSetters(Object obj) {
		Class<?> clazz = null;
		if (obj instanceof Class<?>)
			clazz = (Class<?>) obj;
		else
			clazz = obj.getClass();
		List<Method> setters = new ArrayList<>();
		Method[] methods = clazz.getMethods();
		for (Method each : methods) {
			if (each.getName().startsWith("set")
					&& getGetterMethods(obj).containsKey("get" + each.getName().substring(3)))
				setters.add(each);
		}
		return setters;
	}

	/**
	 * 获取类|对象中的所有set方法
	 * 
	 * @param obj
	 *            被获取方法的对象
	 * @return Map key为方法名称 value为方法对象
	 */
	public static Map<String, Method> getSetterMethods(Object obj) {
		Class<?> clazz = null;
		if (obj instanceof Class<?>)
			clazz = (Class<?>) obj;
		else
			clazz = obj.getClass();
		Map<String, Method> setters = new LinkedHashMap<>();
		Method[] methods = clazz.getMethods();
		for (Method each : methods) {
			if (each.getName().startsWith("set"))
				setters.put(each.getName(), each);
		}
		return setters;
	}

	/**
	 * 通用的toString 方法,通过调用method映射来获取真正可以获得的属性值
	 * 
	 * @param obj
	 *            对象
	 * @return string
	 */
	public static String toString(Object obj) {
		if (obj instanceof Class<?>)
			throw new AppException("传入的参数不能为class!");
		StringBuffer buffer = new StringBuffer(obj.getClass().getSimpleName() + ":[");
		try {
			List<Method> getters = getGetters(obj);
			for (Method each : getters) {
				if (!buffer.toString().endsWith("["))
					buffer.append(",");
				buffer.append(CheckReplaceUtil.firstCharLowcase(each.getName().substring(3)) + "=");
				buffer.append(each.invoke(obj));
			}
			buffer.append("]");
		} catch (Exception e) {
			throw new AppException("构建bean字符串信息异常", e);
		}
		return buffer.toString();
	}

	/**
	 * 通用的equals方法,通过映射调用同一个类的不同对象的get方法来比较两个对象的内容是否相同
	 * 
	 * @param obj1
	 *            对象1
	 * @param obj2
	 *            对象2
	 * @return 比较结果
	 */
	public static boolean equals(Object obj1, Object obj2) {
		boolean result = true;
		try {
			List<Method> getters = getGetters(obj1);
			for (Method each : getters) {
				Object temp1 = each.invoke(obj1);
				Object temp2 = each.invoke(obj2);
				result &= temp1 == null ? temp1 == temp2 : temp1.equals(temp2);
			}
		} catch (Exception e) {
			throw new AppException("映射equals方法异常", e);
		}
		return result;
	}

	/**
	 * 通用的浅copy方法
	 * 
	 * @param t1
	 *            被复制的参数
	 * @param t2
	 *            复制的结果
	 * @param <T> 传入的对象的类
	 * @return  完成复制的t2
	 */
	public static <T> T copy(T t1, T t2) {
		try {
			if (!t2.getClass().equals(t1.getClass()))
				throw new RuntimeException("传入的不是同一个类的实现");
			Map<String, Method> getters = getGetterMethods(t1);
			List<Method> setters = getSetters(t1);
			for (Method each : setters) {
				if (getters.containsKey("get" + each.getName().substring(3)))
					each.invoke(t2, getters.get("get" + each.getName().substring(3)).invoke(t1));
			}
			return t2;
		} catch (Exception e) {
			throw new RuntimeException("复制异常", e);
		}
	}

	/**
	 * 使用json数据设置对象要求json数据中的key与对象的字段名称要一致
	 * 
	 * @param obj
	 *            传入的对象
	 * @param str
	 *            传入的json字符串
	 */
	public static void fromJsonStr(Object obj, String str) {
		try {
			if (obj instanceof Class<?>)
				throw new AppException("传入的参数不能为class!");
			JSONObject jobj = JSONObject.parseObject(str);
			List<Method> setters = BeanUtil.getSetters(obj);
			for (Method each : setters)
				each.invoke(obj, jobj.getString(CheckReplaceUtil.firstCharLowcase(each.getName().substring(3))));
		} catch (Exception e) {
			throw new AppException("字符串转对象异常!", e);
		}
	}

	/**
	 * 映射一个对象的get方法,
	 * 
	 * @param obj
	 *            需要调用的get方法的对象
	 * @param name
	 *            调用get的属性名称
	 * @return get的结果 属性对应的值
	 */
	public static Object get(Object obj, String name) {
		try {
			Method get = obj.getClass().getMethod("get" + CheckReplaceUtil.firstCharUpcase(name));
			return get.invoke(obj);
		} catch (Exception e) {
			throw new RuntimeException("映射" + obj.getClass() + "get方法异常", e);
		}
	}

	/**
	 * 映射一个对象的set方法
	 * 
	 * @param obj
	 *            需要调用set方法的对象
	 * @param name
	 *            调用set的属性名称
	 * @param value
	 *            被set的属性的值
	 */
	public static void set(Object obj, String name, Object value) {
		try {
			Method get = obj.getClass().getMethod("set" + CheckReplaceUtil.firstCharUpcase(name), value.getClass());
			get.invoke(obj, value);
		} catch (Exception e) {
			throw new RuntimeException("映射" + obj.getClass() + "set方法异常", e);
		}
	}
	// public static Map<String, GetSetPair> getGetSetPair(Object obj){
	// Class<?> clazz=null;
	// if(obj instanceof Class<?>)
	// clazz=(Class<?>) obj;
	// else
	// clazz=obj.getClass();
	// Map<String , GetSetPair> map=new HashMap<>();
	// clazz.getme
	//
	// return map;
	// }
	/**
	 * 获取异常的堆栈信息
	 * @param e 捕获的异常
	 * @return 异常的堆栈信息
	 */
	public static String getStackInfo(Exception e){
			StringWriter stringWriter=new StringWriter();
			PrintWriter  printWriter =new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			printWriter.close();
			return stringWriter.toString();
	}
}
