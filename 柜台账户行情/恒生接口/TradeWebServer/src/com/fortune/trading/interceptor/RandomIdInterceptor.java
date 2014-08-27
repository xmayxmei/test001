package com.fortune.trading.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fortune.trading.entity.ClientResponse;
import com.fortune.trading.util.U;

import static com.fortune.trading.util.Constants.mode;
import static com.fortune.trading.util.Constants.SK.*;

/**
 * <code>RandonIDInterceptor</code> 验证页面的随机数
 * 
 * @author Colin, Jimmy
 * @since Tebon Trading v0.0.1
 */
public class RandomIdInterceptor extends HandlerInterceptorAdapter{
	@Override
	public boolean preHandle(HttpServletRequest oReq, HttpServletResponse oResp, Object oHandler) throws Exception {
		// Skip if the current mode is testing 
		if (mode  == 0) {
			return true;
		}
		HttpSession oSession = oReq.getSession();
		oResp.setContentType("text/html;charset=utf-8");
		oResp.setCharacterEncoding("UTF-8");
		final String sURI = oReq.getRequestURI();
		// 验证下单页面的随机数
		if (sURI.endsWith("placeOrder.do")) {
			String sParamRanId = oReq.getParameter("ranID");
			String sSessionRanId = (String)oReq.getSession().getAttribute(PLACEORDER_PAGE_RANID);
			oSession.removeAttribute(PLACEORDER_PAGE_RANID);
			if (U.STR.isEmpty(sParamRanId) || U.STR.isEmpty(sSessionRanId) || !sParamRanId.equals(sSessionRanId)) {
				processIlleagePost(oResp);
				return false;
			}
			return true;
		}
		// 验证撤单页面的随机数
		if (sURI.endsWith("cancelOrder.do")) {
			String sParamRanID = oReq.getParameter("ranID");
			String sSessionID = (String)oSession.getAttribute(CANCELORDER_PAGE_RANID);
			if (U.STR.isEmpty(sParamRanID) || U.STR.isEmpty(sSessionID) || !sParamRanID.equals(sSessionID)) {
				return false;
			}
			return true;
		}
		// 验证修改密码页面的随机数
		if (sURI.endsWith("changePassword.do")) {
			String sParamRanID = oReq.getParameter("ranID");
			String sSessionID = (String)oReq.getSession().getAttribute(CHANGEPSW_PAGE_RANID);
			if (U.STR.isEmpty(sParamRanID) || U.STR.isEmpty(sSessionID) || !sParamRanID.equals(sSessionID)) {
				return false;
			}
			return true;
		}
		return true;
	}
	/*
	 * 
	 */
	private void processIlleagePost(HttpServletResponse oResp) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		ClientResponse<?> oClient = new ClientResponse<String[]>();
		oClient.setErrMsg("重复提交或非法提交.");
		mapper.writeValue(oResp.getOutputStream(), oClient);
	}
}
