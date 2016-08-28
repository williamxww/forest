package com.bow.forest.frontend.common.servlet.snsserver;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.rmi.ServerException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


public class SnsServerServlet extends HttpServlet
{

	private static final long serialVersionUID = 1L;

	private static Logger dlog = LoggerFactory.getLogger(SnsServerServlet.class);

	private static String webRoot;

	private static ApplicationContext ctx;


	@Override
	public void init() throws ServletException
	{

		// 初始WebRoot
		webRoot = getServletContext().getRealPath("/");

		// 获取spring容器的引用
		ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());

		// 初始化dsf客户端
        //DsfClientUtil.initDsf();

		ServerConfig.getInstance().init(getServletContext());

		try
		{
			HandlerCenter.getInstance().init();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}


		// 初始化memcached
        //initMemcached();
	}

	/**
	 * <一句话功能简述><br>
	 * <功能详细描述>
	 * 
	 * @see [类、类#方法、类#成员]
	 */
	private void initMemcached()
	{
		String wwwRoot = getServletContext().getRealPath("/");
		// 初始化 JspRunConfig.realPath
		setRealPath();

		// memcache 的初始化
		//MemcachedClientManager.getInstance().init();
		//CachedDAOFactory.init(getCacheConfigFilePath(wwwRoot));
	}

	private static String getCacheConfigFilePath(String wwwRoot)
	{
		String separator = System.getProperty("file.separator");
		return wwwRoot + "WEB-INF" + separator + "classes" + separator + "snsserver" + separator + "common" + separator
				+ "cache_config.xml";
	}

	/**
	 * GET请求处理
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		doPost(request, response);
	}

	/**
	 * POST请求处理
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String servletName = request.getPathInfo();

		if (StringUtils.isEmpty(servletName))
		{
			response.sendError(ResultCode.ERROR_REQUEST_INTERFACE);
			return;
		}

		servletName = servletName.trim();

		// 增加时延话单

		// 添加消息跟踪
//		if (MessageTraceHelper.needTrace(request))
//		{
//			MessageTraceHelper.getInstance().startTrace();
//		}
		
		int resultCode = ResultCode.SUCCESS;
		try
		{
			// 处理请求
			resultCode = handle(servletName, request, response);
		}
		finally
		{
			MessageTraceHelper.getInstance().stopTrace();
		}
	}

	/*
	 * 处理所有的任务
	 */
	private int handle(String name, HttpServletRequest request, HttpServletResponse response) throws IOException
	{

		int result = ResultCode.SUCCESS;
		HandlerElement he = HandlerCenter.getInstance().getHandlerElement(name);

		// 获取不到相应的处理单元，则表明请求错误
		if (he == null)
		{
			response.sendError(ResultCode.ERROR_REQUEST_INTERFACE);

			return ResultCode.ERROR_REQUEST_INTERFACE;
		}

		try
		{
			// 请求的核心处理语句
			Object resp = he.handle(request);
			if (MessageTraceHelper.getInstance().isTraced())
			{
				MessageTraceHelper.sendTraceResultResponse(response, MessageTraceHelper.getInstance().getTraceStrings());
			}

			if (resp instanceof Response)
			{
				he.writeTo((Response) resp, response);
				result = ResultCode.SUCCESS;
			}
			else if (resp instanceof Integer)
			{
				int code = (Integer) resp;
				if (code != ResultCode.SUCCESS)
				{
					response.sendError(code);
				}
				result = code;
			}
			else if (resp instanceof String)
			{
				response.getWriter().write((String) resp);
				result = ResultCode.OTHER_SERVER_ERROR;
			}
		}
		catch (ServerException e)
		{
			response.sendError(ResultCode.OTHER_SERVER_ERROR);

			result = ResultCode.OTHER_SERVER_ERROR;
		}
		catch (Exception e)
		{
			response.sendError(ResultCode.OTHER_SERVER_ERROR);
			result = ResultCode.OTHER_SERVER_ERROR;

			dlog.error("exception occurs while generating response xml message", e);
		}

		return result;
	}


	/**
	 * 初始化JspRunConfig的realPath 获取工程运行的根目录
	 */
	private void setRealPath()
	{
		String realPath = this.getServletContext().getRealPath("/");
		ServerConfig.getInstance().init(realPath);
		SystemConfig.getInstance().setWwwroot(realPath);
	}



	/**
	 * 获取spring context<br>
	 * by wangwei <功能详细描述>
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static ApplicationContext getApplicationContext()
	{
		return ctx;
	}

	/**
	 * @return 返回 webRoot
	 */
	public static String getWebRoot()
	{
		return webRoot;
	}

}
