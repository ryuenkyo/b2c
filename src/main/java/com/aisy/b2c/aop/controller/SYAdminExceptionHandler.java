package com.aisy.b2c.aop.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.aisy.b2c.exception.SYException;
import com.aisy.b2c.util.SYConst;

/**
 * 后台统一处理异常
 * 
 * @author YanqingLiu
 */
@ControllerAdvice
public class SYAdminExceptionHandler {
	// ExceptionHandler(xxx.class)这个注解的value的值是一个Class[]类型的，这里的ExceptionClass是自己指定的，
	// 也可以指定多个需要处理的异常类型，比如这样@ExceptionHandler(value = {xxx.class,xxx.class})，这样就会处理多个异常了。
	@ExceptionHandler(value = {SYException.class})
	public ResponseEntity<Map<String, Object>> handleOtherExceptions(final SYException ex, final WebRequest req) {
		Map<String, Object> mapResult;
		if (null == ex.getParam()) {
			mapResult = new HashMap<String, Object>();
		} else {
			mapResult = ex.getParam();
		}
		mapResult.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.FAIL);
		mapResult.put(SYConst.SERVICE.MESSAGE_BODY, ex.getMessageBody());
		return new ResponseEntity<Map<String, Object>>(mapResult, HttpStatus.OK);
	}
	
	@ExceptionHandler(value = {Exception.class})
	public String handleOtherExceptions(final Exception ex, final WebRequest req) {
		// 获取来访者地址。只有通过链接访问当前页的时候，才能获取上一页的地址
		// request.getHeader 一下三种情况下可以取到值，1：通过链接跳过来 ，2：在地址栏中输入URL，打回车，3：刷新 。
		// 而X-Requested-With 是用来判断是否是Ajax发来的请求
		// 如果 request.getHeader("X-Requested-With") 的值为 XMLHttpRequest，则为 Ajax 异步请求。为 null，则为传统同步请求。
		String requestType = req.getHeader("X-Requested-With");
		if (StringUtils.isNotEmpty(requestType) && "XMLHttpRequest".equals(requestType)) {
			System.out.println("ajax exception");
		} else {
		}
		ex.printStackTrace();
		return "back/common/fail";
	}
}
