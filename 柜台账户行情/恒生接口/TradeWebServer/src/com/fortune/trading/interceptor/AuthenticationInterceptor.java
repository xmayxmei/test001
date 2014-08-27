package com.fortune.trading.interceptor;

import static com.fortune.trading.util.Constants.mode;
import static com.fortune.trading.util.Constants.SK.LOGIN_USER;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fortune.trading.entity.User;

/**
 * <code>AuthenticationInterceptor</code> 拦截所有没有登录成功的请求。
 * 
 * @author Colin, Jimmy
 * @since Tebon Trading v0.0.1
 */
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// Skip if the current mode is testing 
		if (mode  == 0) {
			return true;
		}
		User user = (User) request.getSession().getAttribute(LOGIN_USER);
		String url = request.getRequestURI();
		if (user == null) {
			String str = request.getQueryString();
			if(StringUtils.isNotBlank(str)){
				url += "?" + str;
			}
			response.sendRedirect("loginPage.do?url="+url);
			return false;
		}
		return true;
	}
}
