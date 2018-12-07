package com.aisy.b2c.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 自定义注解
 * 作用@Auth注解下的类都需要通过鉴权
 * @author YanqingLiu
 */
public class AnnotationInterceptor {
	
	@Documented
	@Inherited
	@Target({ElementType.METHOD,ElementType.TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Auth {
		boolean validate() default true;
	}
}
