/**
 * 
 */
package cn.com.cari.carilibrary.Util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Title: ReflectionHelper
 * @Description: 
 * @Company: www.cari.com.cn
 * @author zhouzhou
 * @date 2015-9-24 下午4:01:53
 */
public class ReflectionHelper {
	/**
	 * 快捷填充对象(标准化的对象)
	 * @param MethodName
	 * @param o
	 *            调用此方法的对象
	 * @param paras
	 *            调用的这个方法的参数参数列表
	 */
	public static void getMethod(String MethodName, Object o, Object[] paras) {
		Class c[] = null;
		if (paras != null) {// 存在
			int len = paras.length;
			c = new Class[len];
			for (int i = 0; i < len; ++i) {
				c[i] = paras[i].getClass();
			}
		}
		try {
			Method method = o.getClass().getDeclaredMethod(MethodName, c);
			try {
				method.invoke(o, paras);// 调用o对象的方法
			} catch (IllegalAccessException ex) {
				ex.fillInStackTrace();
			} catch (IllegalArgumentException ex) {
				ex.fillInStackTrace();
			} catch (InvocationTargetException ex) {
				ex.fillInStackTrace();
			}
		} catch (NoSuchMethodException ex) {
			ex.fillInStackTrace();
		} catch (SecurityException ex) {
			ex.fillInStackTrace();
		}

	}

}
