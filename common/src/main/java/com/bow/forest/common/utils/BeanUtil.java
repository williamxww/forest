package com.bow.forest.common.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.SerializationUtils;

/**
 * BeanUtil
 * 
 * @author wwxiang
 * @since 2016/9/18.
 */
public class BeanUtil {

	/**
	 * 给bean设置属性值
	 * 
	 * @param bean bean
	 * @param name 属性名称
	 * @param value 属性值
	 */
	public static void setProperty(Object bean, String name, Object value) {
		try {
			BeanUtils.setProperty(bean, name, value);
		} catch (Exception e) {
			throw new IllegalArgumentException("can not set property for bean", e);
		}
	}

	/**
	 * 根据属性名字获取bean对象中的值
	 * 
	 * @param bean bean
	 * @param name 属性名称
	 * @return 属性值
	 */
	public static Object getProperty(Object bean, String name) {
		try {
			Object value = BeanUtils.getProperty(bean, name);
			return value;
		} catch (Exception e) {
			throw new IllegalArgumentException("can not get property from bean", e);
		}
	}

	/**
	 * bean属性有setter方法就是可写的
	 * 
	 * @param bean bean
	 * @param name 属性名称
	 * @return boolean
	 */
	public static boolean isWritable(Object bean, String name) {
		return PropertyUtils.isWriteable(bean, name);
	}

	/**
	 * 复制bean的内容到map中
	 * 
	 * @param objBean bean对象
	 * @return 返回结果 map
	 */
	public static Map bean2Map(Object objBean) {
		Map mapRet = new HashMap();
		try {
			if (objBean != null) {
				PropertyDescriptor[] props = Introspector.getBeanInfo(objBean.getClass())
						.getPropertyDescriptors();

				for (PropertyDescriptor property : props) {
					String propertyName = property.getName();
					String propertyClassName = property.getPropertyType().getCanonicalName();
					Object valueList = BeanUtil.getProperty(objBean, propertyName);

					if (propertyClassName.equals("java.util.List")) {
						if (StringUtil.isNotEmpty(valueList)) {
							List colNew = new ArrayList();
							for (Object objItem : ((List) valueList)) {
								Map mapDataItem = bean2Map(objItem);
								colNew.add(mapDataItem);
							}
							mapRet.put(propertyName, colNew);
						}
					} else {
						mapRet.put(propertyName, valueList);
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return mapRet;
	}

	/**
	 * 根据properties给bean赋值
	 * 
	 * @param bean bean
	 * @param properties properties
	 */
	public static void populate(Object bean, Map properties) {
		try {
			BeanUtils.populate(bean, properties);
		} catch (Exception e) {
			throw new IllegalArgumentException("can not populate bean", e);
		}
	}

	/**
	 * 复制bean的内容到bean中
	 * 
	 * @param srcBean srcBean
	 * @param desBean desBean
	 */
	public static void bean2Bean(Object srcBean, Object desBean) {
		if (desBean != null && srcBean != null) {
			try {
				PropertyUtils.copyProperties(desBean, srcBean);
			} catch (Exception e) {
				throw new IllegalArgumentException("can not copyProperties for bean2Bean", e);
			}
		}
	}

	/**
	 * 获取Bean信息
	 * 
	 * @param beanClass beanClass
	 * @return BeanInfo
	 */
	public static BeanInfo getBeanInfo(Class<?> beanClass) {
		try {
			return Introspector.getBeanInfo(beanClass.getClass());
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 获取Bean属性描述
	 * 
	 * @param beanClass beanClass
	 * @return PropertyDescriptor[]
	 */
	public static PropertyDescriptor[] getBeanPropertyDescriptors(Class<?> beanClass) {
		try {
			return Introspector.getBeanInfo(beanClass).getPropertyDescriptors();
		} catch (IntrospectionException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 深度拷贝
	 * 
	 * @param bean bean
	 * @param <T> T
	 * @return clone的bean
	 */
	public static <T extends Serializable> T deepClone(T bean) {
		return SerializationUtils.clone(bean);
	}
}
