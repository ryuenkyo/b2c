package com.aisy.b2c.util;

import org.springframework.context.ApplicationContext;

/**
 * 全局上下文工具类
 * 
 * @author cailongyang
 *
 */
public class ApplicationContextUtil {
	private static ApplicationContextUtil instance;
	
	public static ApplicationContextUtil getInstance() {
		if (null == instance) {
			instance = new ApplicationContextUtil();
		}
		
		return instance;
	}
	
	private ApplicationContext context;
	
	public void init(ApplicationContext context) {
		this.context = context;
	}
	
	public ApplicationContext getContext() {
		return this.context;
	}
	
	public <T> T getBean(Class<T> requiredType) {
		return this.context.getBean(requiredType);
	}
}
