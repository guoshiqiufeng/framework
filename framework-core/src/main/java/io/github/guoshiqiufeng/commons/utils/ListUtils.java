package io.github.guoshiqiufeng.commons.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * list工具类
 *
 * @author yanghq
 * @version 1.0
 * @date 2021-01-12 11:39
 * @email fubluesky@foxmail.com
 */
public class ListUtils {

	/**
	 * 判断是否不为空
	 * @param list
	 * @return boolean
	 */
	public static boolean isNotEmpty(List<?> list) {
		return list != null && list.size() > 0;
	}

	/**
	 * 判断是否为空
	 * @param list
	 * @return boolean
	 */
	public static boolean isEmpty(List<?> list) {
		return list == null || list.size() == 0;
	}

	/**
	 * 转化
	 * @param obj list对象
	 * @param clazz 转换的类
	 * @return list
	 */
	public static <T> List<T> castList(Object obj, Class<T> clazz) {
		List<T> result = new ArrayList<T>();
		if (obj instanceof List<?>) {
			for (Object o : (List<?>) obj) {
				result.add(clazz.cast(o));
			}
			return result;
		}
		return null;
	}

}
