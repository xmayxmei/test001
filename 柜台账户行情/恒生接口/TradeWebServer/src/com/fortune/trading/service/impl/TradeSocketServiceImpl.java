package com.fortune.trading.service.impl;

import java.util.Map;

import org.apache.log4j.Logger;

import com.fortune.client.main.GatewayClient;
import com.fortune.trading.service.ITradeService;

/**
 * <code>TradeSocketServiceImpl</code>
 *
 * @author Jimmy
 * @since Trading v0.0.1 (May 18, 2014)
 */
public class TradeSocketServiceImpl implements ITradeService{
	public Logger L = Logger.getLogger(getClass());
	@Override
	public String loginHandler(Map<String, String> hReqParams) {
		return getResult("loginAuthen", hReqParams);
	}

	@Override
	public String holdStockHandler(Map<String, String> hReqParams) {
		return getResult("holdStock", hReqParams);
	}

	@Override
	public String todayOrdersHandler(Map<String, String> hReqParams) {
		return getResult("todayOrder", hReqParams);
	}

	@Override
	public String hisOrderHandler(Map<String, String> hReqParams) {
		return getResult("historyOrder", hReqParams);
	}

	@Override
	public String todayTransactionHandler(Map<String, String> hReqParams) {
		return getResult("todayTransaction", hReqParams);
	}

	@Override
	public String hisTransactionHandler(Map<String, String> hReqParams) {
		return getResult("historyTransaction", hReqParams);
	}

	@Override
	public String queryZJHandler(Map<String, String> hReqParams) {
		return getResult("queryFunds", hReqParams);
	}

	@Override
	public String queryBankDmHandler(Map<String, String> hReqParams) {
		return getResult("queryBankDm", hReqParams);
	}

	@Override
	public String queryZJMXHandler(Map<String, String> hReqParams) {
		return getResult("queryFundsMX", hReqParams);
	}

	@Override
	public String placeOrderHandler(Map<String, String> hReqParams) {
		return getResult("placeOrder", hReqParams);
	}

	@Override
	public String cancleOrderHandler(Map<String, String> hReqParams) {
		return getResult("cancelOrder", hReqParams);
	}

	@Override
	public String bankToZQHandler(Map<String, String> hReqParams) {
		return getResult("bankToZQ", hReqParams);
	}

	@Override
	public String zjToBank(Map<String, String> hReqParams) {
		return getResult("ZqToBank", hReqParams);
	}

	@Override
	public String zjToBankReQuery(Map<String, String> hReqParams) {
		return getResult("bankZqReQuery", hReqParams);
	}

	@Override
	public String zjToBankQuery(Map<String, String> hReqParams) {
		return getResult("bankZqQuery", hReqParams);
	}

	@Override
	public String userInfHandler(Map<String, String> hReqParams) {
		return getResult("userInfo", hReqParams);
	}

	@Override
	public String checkFPwdHandler(Map<String, String> hReqParams) {
		return getResult("checkFPwd", hReqParams);
	}

	@Override
	public String checkTPwdHandler(Map<String, String> hReqParams) {
		return getResult("checkTPwd", hReqParams);
	}

	@Override
	public String checkTPwdByGDHHandler(Map<String, String> hReqParams) {
		return getResult("checkTPwdByGDH", hReqParams);
	}

	@Override
	public String modiTPwdHandler(Map<String, String> hReqParams) {
		return getResult("modiTPwd", hReqParams);
	}

	@Override
	public String modiFPwdHandler(Map<String, String> hReqParams) {
		return getResult("modiFPwd", hReqParams);
	}

	@Override
	public String quoteHandler(Map<String, String> hReqParams) {
		return getResult("quote", hReqParams);
	}

	@Override
	public String zjNZBiz(Map<String, String> hReqParams) {
		return getResult("FundsNZ", hReqParams);
	}

	@Override
	public String gdhQuery(Map<String, String> hReqParams) {
		return getResult("queryGDH", hReqParams);
	}
	
	@Override
	public String queryCancleOrderHandler(Map<String, String> hReqParams) {
		return getResult("queryCancelOrder", hReqParams);
	}

	private String getResult(String sActionName, Map<String, String> hReqParams) {
		hReqParams.put("actionName", sActionName);
		String sRsp = GatewayClient.getInstance().tradeRequest(hReqParams);
		L.debug("getResult : " + sRsp);
		return sRsp;
	}

	@Override
	public String queryPeihao(Map<String, String> hReqParams) {
		return getResult("queryPeihao", hReqParams);
	}

	@Override
	public String queryBallot(Map<String, String> hReqParams) {
		return getResult("queryBallot", hReqParams);
	}

	@Override
	public String queryHistoryZJHandler(Map<String, String> hReqParams) {
		return getResult("queryHistoryZJ", hReqParams);
	}
}
