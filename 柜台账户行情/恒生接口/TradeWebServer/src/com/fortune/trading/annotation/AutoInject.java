package com.fortune.trading.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * <code>AutoInject</code> 这个注解的作用标记需要自动为拦截的参数设置值.
 * <p>
 * Example:<br>
 * <pre>
 * @AutoInject(paramName="hParams",keys={"clientId"},scope="request")
 * public String doFun(Map<String, String> hParams) {
 * 	// do some work
 * }
 * </pre>
 * @author Colin, Jimmy
 * @since Tebon Trading v0.0.1
 * 
 * @see com.fortune.trading.interceptor.AutoInjectAOP
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AutoInject {
	 /** 参数的名字*/
	 String paramName();
	 String[] keys();
	 /** 两种可能: request | session*/
	 String scope() default "session";
}
