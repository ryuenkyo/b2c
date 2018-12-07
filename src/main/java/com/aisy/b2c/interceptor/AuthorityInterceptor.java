package com.aisy.b2c.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.aisy.b2c.pojo.SUser;
import com.aisy.b2c.util.AnnotationInterceptor.Auth;
import com.aisy.b2c.util.SYConst;

import net.sf.json.JSONObject;

/**
 * 
 * 登录鉴权拦截器
 * 
 * 用于拦截所有需要登录的资源请求
 * 
 * @author cailongyang
 *
 */
public class AuthorityInterceptor extends HandlerInterceptorAdapter {
    /*@Override
    public boolean preHandle(
    		HttpServletRequest request,
            HttpServletResponse response, 
            Object handler) throws Exception {
		HttpSession session = request.getSession();
		SUser currentUser = (SUser) session.getAttribute(SYConst.SESSION.LOGIN_USER);
        if(handler.getClass().isAssignableFrom(HandlerMethod.class)){
            Auth authPassport = ((HandlerMethod) handler).getMethodAnnotation(Auth.class);
            //没有声明需要权限,或者声明不验证权限
            if(authPassport==null){
                return true;
            }else{                
                //权限验证逻辑
            	if (null == currentUser) {
        			String requestType = request.getHeader("X-Requested-With");
        			if (StringUtils.isNotEmpty(requestType) && "XMLHttpRequest".equals(requestType)) {
        				response.setCharacterEncoding("UTF-8");
        				response.setContentType("application/json; charset=utf-8");
        				JSONObject json = new JSONObject();
        				json.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.LOGIN_FAIL);
        				PrintWriter out = null; 
        				try {
        					out = response.getWriter();
        					out.append(json.toString());
        					return false;
        				} catch (IOException e) {
        					return false;
        				} finally {
        					if (out != null) {
        						out.close();
        					}
        				}
        			} else {
        				response.getWriter().append("<script type=\"application/javascript\">")
        				.append("window.parent.window.openLoginScreen();")
        				.append("</script>");
        				return false;
        			} 
            	}
            }
        } 
		return false;
    }*/
	
	@Override
	public boolean preHandle(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler) throws Exception {
		HttpSession session = request.getSession();
		SUser currentUser = (SUser) session.getAttribute(SYConst.SESSION.LOGIN_USER);
		
		if (null == currentUser) {
			String requestType = request.getHeader("X-Requested-With");
			if (StringUtils.isNotEmpty(requestType) && "XMLHttpRequest".equals(requestType)) {
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json; charset=utf-8");
				JSONObject json = new JSONObject();
				json.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.LOGIN_FAIL);
				PrintWriter out = null; 
				try {
					out = response.getWriter();
					out.append(json.toString());
					return false;
				} catch (IOException e) {
					return false;
				} finally {
					if (out != null) {
						out.close();
					}
				}
			} else {
				response.getWriter().append("<script type=\"application/javascript\">")
				.append("window.parent.window.openLoginScreen();")
				.append("</script>");
				return false;
			}
		} else {
			return true;
		}
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
