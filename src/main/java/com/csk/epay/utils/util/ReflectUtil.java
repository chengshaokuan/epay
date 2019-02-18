package com.csk.epay.utils.util;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @program: Cheng
 * @description: 反射工具类。
 * @author: Mr.Cheng
 * @create: 2018-09-05 17:03
 **/
public class ReflectUtil {

	/**
	 * @Description: 通过反射调用get方法。
	 * @param: obj 对象
     * @param: s 属性
	 * @return: java.lang.Object
	 * @Author: Mr.Cheng
	 * @Date: 15:21 2018/10/8
	 */
	public static Object invokeGetMethod(Object obj, String s) {
		try {
			String s1 = (new StringBuilder("get")).append(StringUtils.capitalize(s)).toString();
			Method method = obj.getClass().getMethod(s1, new Class[0]);
			return method.invoke(obj, new Object[0]);
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}

	/**
	 * @Description: 通过反射调用set方法
	 * @param: obj 对象
     * @param: s 属性
     * @param: obj1 对象
	 * @return: void
	 * @Author: Mr.Cheng
	 * @Date: 15:22 2018/10/8
	 */
	public static void invokeSetMethod(Object obj, String s, Object obj1) {
		Class<?> class1 = obj1.getClass();
		invokeSetMethod(obj, s, obj1, class1);
	}

	/**
	 * @Description: 通过反射调用set方法。
	 * @param: obj
     * @param: s
     * @param: obj1
     * @param: class1
	 * @return: void
	 * @Author: Mr.Cheng
	 * @Date: 15:23 2018/10/8
	 */
	public static void invokeSetMethod(Object obj, String s, Object obj1, Class<?> class1) {
		String s1 = (new StringBuilder("set")).append(StringUtils.capitalize(s)).toString();
		try {
			Method method = obj.getClass().getMethod(s1, new Class[] { class1 });
			method.invoke(obj, new Object[] { obj1 });
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * @Description: 通过属性名称，获取属性值。
	 * @param: obj
     * @param: s
	 * @return: java.lang.Object
	 * @Author: Mr.Cheng
	 * @Date: 15:24 2018/10/8
	 */
	public static Object getFieldValue(Object obj, String s) {
		Field field = getFiled(obj, s);
		if (field == null)
		{
			throw new IllegalArgumentException((new StringBuilder("Could not find field ")).append(s).toString());
		}
		Object obj1 = null;
		try {
			obj1 = field.get(obj);
		} catch (IllegalAccessException illegalaccessexception) {
		}
		return obj1;
	}

	/**
	 * @Description:  通过属性名称，为新对象设置属性值。
	 * @param: obj
	 * @param: s
	 * @param: obj1
	 * @return: void
	 * @Author: Mr.Cheng
	 * @Date: 15:24 2018/10/8
	 */
	public static void setFieldValue(Object obj, String s, Object obj1) {
		Field field = getFiled(obj, s);
		if (field == null)
		{
			throw new IllegalArgumentException((new StringBuilder("Could not find field ")).append(s).toString());
		}
		try {
			Class<?> type = field.getType();
			if (type.equals(Integer.class)) {
				field.set(obj, Integer.valueOf(obj1.toString()));
			} else if (type.equals(Boolean.class)) {
				field.set(obj, Boolean.valueOf(obj1.toString()));
			} else if (type.equals(Long.class)) {
				field.set(obj, Long.valueOf(obj1.toString()));
			} else {
				field.set(obj, obj1);
			}
		} catch (IllegalAccessException illegalaccessexception) {
		}
	}

	/**
	 * @Description:  通过属性名称获取对象属性
	 * @param: obj
	 * @param: s
	 * @return: java.lang.reflect.Field
	 * @Author: Mr.Cheng
	 * @Date: 15:24 2018/10/8
	 */
	private static Field getFiled(Object obj, String s) {
		Class<?> class1 = obj.getClass();
		Field field = null;
		while (class1 != Object.class) {

			try {
				field = class1.getDeclaredField(s);
				field.setAccessible(true);
				return field;
			} catch (NoSuchFieldException e) {
				class1 = class1.getSuperclass();
			}
		}
		return field;
	}

	/**
	 * @Description:  获取对象属性。
	 * @param: obj
	 * @return: java.util.List<java.lang.reflect.Field>
	 * @Author: Mr.Cheng
	 * @Date: 15:23 2018/10/8
	 */
	public static List<Field> getFields(Object obj) {
		Class<?> class1 = obj.getClass();
		Field[] filelds = null;
		List<Field> list = new ArrayList<Field>();
		while (class1 != Object.class) {
				filelds =class1.getDeclaredFields();
				list.addAll(Arrays.asList(filelds));
				class1 = class1.getSuperclass();
		}
		return list;
	}
	
	/**
	 * @Description: 反序列化具体的类信息
	 * @param: className
	 * @return: java.lang.Object
	 * @Author: Mr.Cheng
	 * @Date: 15:23 2018/10/8
	 */
	public static Object loadClassObject(String className) {
		Object object = null;
		try {
			object = Class.forName(className).newInstance();
		} catch (ClassNotFoundException ex) {
			object = null;
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			object = null;
			ex.printStackTrace();
		} catch (InstantiationException ex) {
			object = null;
			ex.printStackTrace();
		}
		return object;
	}
}
