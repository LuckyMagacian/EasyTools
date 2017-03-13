package com.lanxi.tools;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;
/**
 * xml与bean转换工具类,不建议使用 
 * @author yangyuanjian
 *
 */
@Deprecated
public class XmlUtil {

	/**
	 * 简单处理(不考虑map list collection 等复杂情况) bean转xml
	 * 
	 * @param obj
	 *            对象
	 * @param charset
	 *            字符集
	 * @return 
	 * 			  xml字符串
	 */
	public static String easyToXml(Object obj, String charset) {
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding(charset);
		doc.add(easyToElement(obj, null));
		return doc.asXML();
	}

	/**
	 * 简单处理(不考虑map list collection 等复杂情况) bean转Element(实际上为 DOMElement)
	 * 
	 * @param obj
	 *            对象
	 * @param rootName
	 *            根节点名称
	 * @return xml节点
	 */
	public static Element easyToElement(Object obj, String rootName) {
		try {
			if (Class.class.isInstance(obj))
				throw new AppException("传入的对象不能为class实例");
			Class<?> clazz = obj.getClass();
			Map<String, Field> fields = BeanUtil.getFieldsNoStatic(clazz);
			DOMElement element;
			if (rootName == null) {
				Field NAME = null;
				try {
					NAME = clazz.getDeclaredField("NAME");
				} catch (NoSuchFieldException e) {
					NAME = null;
				}
				if (NAME != null)
					NAME.setAccessible(true);
				element = new DOMElement(NAME == null ? clazz.getSimpleName() : NAME.get(obj) + "");
			} else {
				element = new DOMElement(rootName);
			}
			for (Entry<String, Field> each : fields.entrySet()) {
				String name = each.getKey();
				Field field = each.getValue();
				field.setAccessible(true);
				DOMElement tempEle = new DOMElement(name);
				Object value = field.get(obj);
				tempEle.setText(value == null ? "" : value + "");
				element.appendChild(tempEle);
			}
			return element;
		} catch (Exception e) {
			throw new AppException("通过bean创建Element时异常", e);
		}
	}

	/**
	 * 将传入的对象转换为Element形式返回(实际为DOMElement)
	 * 
	 * @param obj
	 *            对象
	 * @return xml节点
	 */
	public static DOMElement toElement(Object obj) {
		return toElement(obj, "");
	}

	/**
	 * 将传入的对象转换为Element形式返回(实际为DOMElement) 可以指定节点名称(主要用于处理字段时名称问题)
	 * 
	 * @param obj
	 *            对象
	 * @param name
	 *            不能为null 会与另外方法冲突, 不设置时传入空白,或空
	 * @return xml节点对象
	 */
	@SuppressWarnings("unchecked")
	public static DOMElement toElement(Object obj, String name) {
		if (obj == null)
			throw new AppException("传入的对象为null");
		if (Class.class.isInstance(obj))
			throw new AppException("传入的对象不能为class实例");

		Class<?> clazz = obj.getClass();
		DOMElement element = null;
		// 设置节点名称, 指定名称优先级最高,对象中NAME字段内容其次,最后使用类名简写
		try {
			// 查看对象中是否包含NAME字段,若有作为节点名称
			if (name == null || name.trim().equals("")) {
				Field NAME = null;
				try {
					NAME = clazz.getDeclaredField("NAME");
				} catch (NoSuchFieldException e) {
					NAME = null;
				}
				if (NAME != null)
					NAME.setAccessible(true);
				element = new DOMElement(NAME == null ? clazz.getSimpleName() : NAME.get(obj) + "");
			} else {
				element = new DOMElement(name);
			}
			// 处理传入的是map的情况
			if (Map.class.isAssignableFrom(clazz)) {
				element = toElement((Map<String, Object>) obj, element);
				return element;
			}
			// 处理传入的是集合的情况
			if (Collection.class.isAssignableFrom(clazz)) {
				element = toElement((Collection<Object>) obj, element);
				return element;
			}
			// 处理数字,字符串,和Object的情况
			if (Number.class.isAssignableFrom(clazz) || CharSequence.class.isAssignableFrom(clazz)
					|| clazz.equals(Object.class)) {
				element.setText(obj + "");
				return element;
			}
			// 处理bean 情况
			element = toElement(obj, element);
			return element;
		} catch (Exception e) {
			throw new AppException("Object转Element时异常", e);
		}
	}

	/**
	 * 将传入的对象转换为Element并添加到指定的父节点中并返回(实际为DOMElement)
	 * 
	 * @param obj
	 *            对象
	 * @param parent
	 *            父节点
	 * @return xml节点
	 */
	public static DOMElement toElement(Object obj, DOMElement parent) {
		if (obj == null)
			return parent;
		if (Class.class.isInstance(obj))
			throw new AppException("传入的对象不能为class实例");
		Class<?> clazz = obj.getClass();
		try {
			Map<String, Field> fields = BeanUtil.getFieldsNoStatic(clazz);
			for (Entry<String, Field> each : fields.entrySet()) {
				String name = each.getKey();
				Field field = each.getValue();
				field.setAccessible(true);
				Object value = field.get(obj);
				if (value == null) {
					parent.appendChild(new DOMElement(name));
					continue;
				}
				parent.appendChild(toElement(value, field.getName()));
			}
			return parent;
		} catch (Exception e) {
			throw new AppException("Object转Element时异常", e);
		}
	}

	/**
	 * 将传入的Map转换为Element并添加到指定的父节点中并返回(实际为DOMElement)
	 * 
	 * @param map
	 *            属性 值 映射
	 * @param parent
	 *            父节点
	 * @return xml节点对象
	 */
	public static DOMElement toElement(Map<String, Object> map, DOMElement parent) {
		if (parent == null)
			throw new AppException("Map转Element时,父节点为null");
		try {
			for (Map.Entry<String, Object> each : map.entrySet()) {
				String key = each.getKey();
				Object value = each.getValue();
				DOMElement child = new DOMElement(key);
				if (value instanceof Number || value.getClass().equals(Object.class) || value instanceof String)
					child.setText(value + "");
				else
					child.appendChild(toElement(value, child));
				parent.appendChild(child);
			}
			return parent;
		} catch (Exception e) {
			throw new AppException("Map转Element异常");
		}
	}

	/**
	 * 将传入的Collection转换为Element并添加到指定的父节点中并返回(实际为DOMElement)
	 * 
	 * @param collection
	 *            集合
	 * @param parent
	 *            父节点
	 * @return xml节点
	 */
	public static DOMElement toElement(Collection<Object> collection, DOMElement parent) {
		if (parent == null)
			throw new AppException("Collection转Element时,父节点为null");
		try {
			for (Object each : collection)
				parent.appendChild(toElement(each, ""));
			return parent;
		} catch (Exception e) {
			throw new AppException("Collection转Element异常");
		}
	}

	/**
	 * 将传入的对象转换为指定编码方式的xml字符串
	 * 
	 * @param obj
	 *            对象
	 * @param charset
	 *            编码字符集
	 * @return xml字符串
	 */
	public static String toXml(Object obj, String charset) {
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding(charset);
		doc.add(toElement(obj));
		return doc.asXML();
	}
}
