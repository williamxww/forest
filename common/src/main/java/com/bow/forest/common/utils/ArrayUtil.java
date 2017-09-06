package com.bow.forest.common.utils;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 数组工具
 * 
 * @author wwxiang
 * @since 2016/9/18.
 */
public class ArrayUtil {

	/**
	 * 给数组增加一个元素
	 * 
	 * @param array array
	 * @param element element
	 * @param <T> 泛型
	 * @return 添加元素后的数组
	 */
	public static <T> T[] add(T[] array, T element) {
		return ArrayUtils.add(array, element);
	}

	/**
	 * 判断数组是否为空
	 * 
	 * @param array array
	 * @return boolean
	 */
	public static boolean isEmpty(Object[] array) {
		return ArrayUtils.isEmpty(array);
	}

	/**
	 * array 是否包含目标元素
	 * 
	 * @param array array
	 * @param objectToFind 目标元素
	 * @return boolean
	 */
	public static boolean contains(Object[] array, Object objectToFind) {
		return ArrayUtils.contains(array, objectToFind);
	}

	/**
	 * 元素objectToFind 在数组array,第一次出现的位置 array为null 或找不到objectToFind 返回-1
	 * 
	 * @param array array
	 * @param objectToFind 目标元素
	 * @return 目标位置
	 */
	public static int indexOf(Object[] array, Object objectToFind) {
		return indexOf(array, objectToFind, 0);
	}

	/**
	 * 从startIndex开始查找，元素objectToFind所在位置 array为null 或找不到objectToFind 返回-1
	 * 
	 * @param array array
	 * @param objectToFind 目标元素
	 * @param startIndex 开始位置
	 * @return 目标位置
	 */
	public static int indexOf(Object[] array, Object objectToFind, int startIndex) {
		return ArrayUtils.indexOf(array, objectToFind, startIndex);
	}

	/**
	 * 在数组array中从后往前，元素objectToFind第一次出现的位置 array为null 或找不到objectToFind 返回-1
	 * 
	 * @param array array
	 * @param objectToFind 目标元素
	 * @return 目标位置
	 */
	public static int lastIndexOf(final Object[] array, final Object objectToFind) {
		return lastIndexOf(array, objectToFind, Integer.MAX_VALUE);
	}

	/**
	 * 从startIndex开始，从后向前找元素objectToFind所在位置 array为null 或找不到objectToFind 返回-1
	 * 
	 * @param array array
	 * @param objectToFind 目标元素
	 * @param startIndex 开始位置
	 * @return 目标索引
	 */
	public static int lastIndexOf(Object[] array, Object objectToFind, int startIndex) {
		return ArrayUtils.lastIndexOf(array, objectToFind, startIndex);
	}

	/**
	 * 截取子数组
	 * 
	 * @param array array
	 * @param startIndexInclusive 截取开始
	 * @param endIndexExclusive 截取的结束位置
	 * @param <T> 泛型
	 * @return 子数组
	 */
	public static <T> T[] subarray(T[] array, int startIndexInclusive, int endIndexExclusive) {
		return ArrayUtils.subarray(array, startIndexInclusive, endIndexExclusive);
	}

}
