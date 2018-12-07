package com.aisy.b2c.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


import com.aisy.b2c.util.SYConst;

import net.sf.json.JSONObject;

public class AuthorityFrontInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(
			//接口，获取客户端请求
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler) throws Exception  {
		//取得登陆时用户存入的session
		HttpSession session = request.getSession();
		Short clientModel = (Short) request.getSession().getAttribute(SYConst.PRODUCT_PARAM.CLIENT_ID);
		
		//判断session是否为空，也就是判断是否有过登陆或者session是否过期
		if (null == clientModel) {
			//获取来访者地址。只有通过链接访问当前页的时候，才能获取上一页的地址
			//request.getHeader 一下三种情况下可以取到值，1：通过链接跳过来 ，2：在地址栏中输入URL，打回车，3：刷新 。
			//而X-Requested-With 是用来判断是否是Ajax发来的请求
			// 如果 request.getHeader("X-Requested-With") 的值为 XMLHttpRequest，则为 Ajax 异步请求。为 null，则为传统同步请求。
			String requestType = request.getHeader("X-Requested-With");
			//判断requestType 是否非空也就是是否是同步请求，在这里，空格一样是为非空。 和判断是否是异步请求。
			if (StringUtils.isNotEmpty(requestType) && "XMLHttpRequest".equals(requestType)) {
				//指定response容器存储数据的格式
				response.setCharacterEncoding("UTF-8");
				//response.setContentType作用是使客户端浏览器，区分不同种类的数据，并根据不同的MIME调用浏览器内不同的程序嵌入模块来处理相应的数据。
				//例如web浏览器就是通过MIME类型来判断文件是GIF图片。通过MIME类型来处理json字符串。
				response.setContentType("application/json; charset=utf-8");
				//创建一个空的JSONObject对象
				JSONObject json = new JSONObject();
				//如果Ajax被拦截，res会显示LOGIN_FAIL。
				json.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.LOGIN_FAIL);
				//定义一个输出流
				PrintWriter out = null; 
				try {
					//防止乱码，response.getWriter(); 同样是一个输出流。
					out = response.getWriter();
					//append，在末尾插入指定内容。json.toString()，把一个JSON里的逻辑值转换为字符串，并返回结果。
					out.append(json.toString());
					return false;
				} catch (IOException e) {
					return false;
				} finally {
					if (out != null) {
						//关闭输出流
						out.close();
					}
				}
			} else {
				//如果不是ajax请求就直接跳转到登陆页面
				String requestUrl = request.getRequestURL().toString();
				session.setAttribute(SYConst.SESSION.CURRENT_URL, requestUrl);
				response.sendRedirect("/b2c/front/admin.html");
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public void postHandle(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler, 
			ModelAndView modelAndView) throws Exception {
	}
	
	@Override
	public void afterCompletion(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler, 
			Exception ex) throws Exception {
		
	}
	
	@Override
	public void afterConcurrentHandlingStarted(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler) throws Exception {
	}
}
