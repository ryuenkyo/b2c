package com.aisy.b2c.aop.log;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.stereotype.Component;

/**
 * 日志捕捉组件
 * 
 * 用于后台捕捉业务逻辑执行日志
 * 
 * @author YanqingLiu
 *
 */
@Component
@Aspect
public class LogAopComponent implements MethodBeforeAdvice, AfterReturningAdvice, MethodInterceptor, ThrowsAdvice {

	@Override
	public void before(Method method, Object[] args, Object target) throws Throwable {
		
	}
	
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		
		System.out.println("around before!");
		System.out.println(invocation.getArguments());
		Object result = invocation.proceed();
		System.out.println("around after!");
		
		return result;
	}

	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		
	}
	
	public void afterThrowing(Method method, Object[] args, Object target, Exception ex) {

	}

}
