/*
 * 文 件 名:  HandlerCenter.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  吴飞00106856
 * 修改时间:  2009-1-20
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.bow.forest.frontend.common.servlet.snsserver;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thoughtworks.xstream.XStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HandlerElement {
	private static final Logger log = LoggerFactory
			.getLogger(HandlerElement.class);

	private static final String lineSep = System.getProperty("line.separator");

	/** xml文件头 */
	private static final String xmlHead = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n";

	/** 处理实例 */
	private Object handler;

	/** 处理方法实例 */
	private Method handleMethod;

	/** 请求类实例 */
	private Class<?> requestClass;

	/** 请求处理的XStream对象 */
	private XStream requestXStream;

	/** 处理后返回的XStream对象 */
	private XStream responseXStream;

	/**
	 * 默认构造函数
	 */
	HandlerElement(Object handler, Method handleMethod) {
		this.handler = handler;
		this.handleMethod = handleMethod;
	}

	/**
	 * 返回处理结果
	 * 
	 * @param request
	 * @return
	 * @throws InvocationTargetException
	 * @throws IOException
	 *             [参数说明]
	 * 
	 * @see [类、类#方法、类#成员]
	 */
	public Object handle(HttpServletRequest request) {
		Object obj = request;

		try {
			if (requestClass != null) {
				obj = getRequest(request);
			}
			return handleMethod.invoke(handler, obj);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}



	/**
	 * 返回解析后的request对象
	 *
	 * @return Request
	 * @throws IOException
	 */
	private Request getRequest(HttpServletRequest request) throws IOException {
		if (requestXStream == null) {
			requestXStream = XStreamUtil.baseParseXStream();
			setRequestAlias();
		}

		Reader reader = request.getReader();

		Request req = (Request) requestXStream.fromXML(reader);

		return req;
	}

	/**
	 * 若request类中含有setAlias(XStream xstream)方法，则调用该方法
	 */
	private void setRequestAlias() {
		requestXStream.alias("Request", requestClass);

		try {
			Method m = requestClass
					.getDeclaredMethod("setAlias", XStream.class);
			if (!m.isAccessible()) {
				m.setAccessible(true);
			}
			m.invoke(requestClass.newInstance(), requestXStream);
		} catch (Exception e) {
		}
	}

	/**
	 * 把响应写到writer去
	 * 
	 * @param response
	 *            [参数说明]
	 * @throws IOException
	 */
	public void writeTo(Response response, HttpServletResponse httpResponse)
			throws IOException {
		if (responseXStream == null) {
			responseXStream = new XStream();
			responseXStream.alias("Response", response.getClass());
			response.setAlias(responseXStream);
		}

		Writer writer = httpResponse.getWriter();


		httpResponse.setCharacterEncoding("UTF-8");
		writer.write(xmlHead);
		responseXStream.toXML(response, writer);

	}

	/**
	 */
	void setRequestClass(Class<?> requestClass) {
		this.requestClass = requestClass;
	}

	/**
	 * 返回 requestClass
	 */
	public Class<?> getRequestClass() {
		return requestClass;
	}
}
