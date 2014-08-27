/*
 * FileName: LogAOP.java
 * Copyright: Copyright 2014-6-9 Cfwx Tech. Co. Ltd.All right reserved.
 * Description: 
 *
 */
package com.fortune.trading.interceptor;

import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.fortune.trading.util.Constants;

/**
 * <code>LogAOP</code> 
 * 
 * @author Colin, Jimmy
 * @since Tebon Trading v0.0.1(June 9, 2014)
 * 
 */ 
@Component
@Aspect
public class LogAOP {
    protected Logger L = Logger.getLogger(getClass());
    @SuppressWarnings("unchecked")
    @Before("execution(* com.fortune.trading.service.impl.TradeServiceImpl.*(..))")
    public void log(JoinPoint oPoint) {
       // 不支持保存到数据库则返回
       if (!Constants.isSupportLogJDBC) {
    	   Object[] aArgs = oPoint.getArgs();
    	   if (aArgs == null) {
    		   return ;
    	   }
    	   // 记录保存到文件
    	   Map<String, String> hArgs = (Map<String, String>)aArgs[0];
    	   L.info("Operate:" + oPoint.getSignature().getName() + "," + getContent(oPoint, hArgs));
           return ;
       }
       // 记录保存到数据库
       Object[] aArgs = oPoint.getArgs();
       Map<String, String> hArgs = (Map<String, String>)aArgs[0];
       MDC.put("clientId", hArgs.get("fund_account"));
       MDC.put("operate", oPoint.getSignature().getName());
       MDC.put("description", hArgs);
       L.warn("");
    }
    
    private String getContent(JoinPoint oPoint, Map<String, String> hParam) {
    	Set<String> oKeys = hParam.keySet();
    	StringBuilder oSB = new StringBuilder();
    	for (String sKey : oKeys) {
    		String sContent = hParam.get(sKey);
    		if ("password".equals(sKey) || "password1".equals(sKey) || "fund_password".equals(sKey)
    			|| "bank_password".equals(sKey)) {
    			sContent = "******";
    		}
    		oSB.append(sKey)
    		.append(":")
    		.append(sContent)
    		.append(",")
    		;
    	}
    	return oSB.toString();
    }
}
