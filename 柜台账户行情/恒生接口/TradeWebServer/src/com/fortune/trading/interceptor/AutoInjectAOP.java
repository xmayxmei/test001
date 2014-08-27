package com.fortune.trading.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortune.trading.annotation.AutoInject;
import com.fortune.trading.util.Constants;
/**
 * <code>AutoInjectAOP</code> 根据方法注解类型为<code>AutoInject</code>来自动设置值.
 * 
 * @author Colin, Jimmy
 * @since Tebon Trading v0.0.1
 * 
 * @see com.fortune.trading.annotation.AutoInject
 */ 
@Component
@Aspect
public class AutoInjectAOP {
	@Autowired(required=true)
	HttpServletRequest oReq;
	@Autowired(required=true)
	HttpSession oSession;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Before("execution(* com.fortune.trading.controller.*.*Controller.*(..))")
	public void autoInject(JoinPoint oPoint) {
		if (Constants.mode == 0) {
			if (oSession.getAttribute("fund_account") == null) {
				oSession.setAttribute("fund_account", "110002318");
			}
		}
		MethodSignature oThSign= (MethodSignature) oPoint.getSignature();
		AutoInject oAuto = oThSign.getMethod().getAnnotation(AutoInject.class);
		if (oAuto == null) {
			return ;
		}
		String[] asNames= oThSign.getParameterNames();
		String[] asKeys = oAuto.keys();
		if (asNames == null || asNames.length < 1 || asKeys.length < 1) {
			return ;
		}
		String sParamName = oAuto.paramName();
		String sScope = oAuto.scope();
		Object[] asArgs = oPoint.getArgs();
		int iLen = asNames.length;
		for(int i = 0; i < iLen; i++) {
			Object obj = asArgs[i];
			if (!asNames[i].equals(sParamName) || obj == null) {
				continue;
			}
			for (String sKey : asKeys) {
				String sVal = null;
				if("session".equals(sScope)) {
					sVal = (String)oSession.getAttribute(sKey);
				} else if ("request".equals(sScope)) {
					sVal = (String)oReq.getAttribute(sKey);
				}
				if (sVal == null) {
					continue;
				}
				if (obj instanceof Map) {
					((Map)obj).put(sKey, sVal);
				} else if (obj instanceof String) {
					obj = sVal;
				}
			}
		}
	}
}
