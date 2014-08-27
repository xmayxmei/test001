package com.fortune.trading.service.impl;

import java.util.Map;

import com.fortune.trading.service.ITradeService;
import com.fortune.trading.util.Constants;
import com.fortune.trading.util.U;

/**
 * <code> TradeServiceImpl</code>
 * 
 * @author Colin, Jimmy
 *
 */
public class TradeServiceImpl implements ITradeService{
	private String charset;
	private Map<String, String> hUrls;
	
	@Override
	public String loginHandler(Map<String, String> hReqParams) {
		String sFullURL = fullUrl (hUrls.get("loginAuthen"));
		String sResp = U.HTTP.post(sFullURL, hReqParams, charset);
		return sResp;
	}

	@Override
	public String holdStockHandler(Map<String, String> hReqParams) {
		String sFullURL = fullUrl (hUrls.get("holdStock"));
		String sResp = U.HTTP.post(sFullURL, hReqParams, charset);
		return sResp;
	}

	@Override
	public String todayOrdersHandler(Map<String, String> hReqParams) {
		String sFullURL = fullUrl (hUrls.get("todayOrder"));
		String sResp = U.HTTP.post(sFullURL, hReqParams, charset);
		return sResp;
	}

	@Override
	public String hisOrderHandler(Map<String, String> hReqParams) {
		String sFullURL = fullUrl (hUrls.get("historyOrder"));
		String sResp = U.HTTP.post(sFullURL, hReqParams, charset);
		return sResp;
	}

	@Override
	public String todayTransactionHandler(Map<String, String> hReqParams) {
		String sFullURL = fullUrl (hUrls.get("todayTransaction"));
		String sResp = U.HTTP.post(sFullURL, hReqParams, charset);
		return sResp;
	}

	@Override
	public String hisTransactionHandler(Map<String, String> hReqParams) {
		String sFullURL = fullUrl (hUrls.get("historyTransaction"));
		String sResp = U.HTTP.post(sFullURL, hReqParams, charset);
		return sResp;
	}
	
	@Override
	public String queryZJHandler(Map<String, String> hReqParams) {
		String sFullURL = fullUrl (hUrls.get("queryFunds"));
		String sResp = U.HTTP.post(sFullURL, hReqParams, charset);
		return sResp;
	}

	@Override
	public String queryBankDmHandler(Map<String, String> hReqParams) {
		String sFullURL = fullUrl (hUrls.get("queryBankDm"));
		String sResp = U.HTTP.post(sFullURL, hReqParams, charset);
		return sResp;
	}

	@Override
	public String queryZJMXHandler(Map<String, String> hReqParams) {
		String sFullURL = fullUrl (hUrls.get("queryZJMX"));
		String sResp = U.HTTP.post(sFullURL, hReqParams, charset);
		return sResp;
	}

	@Override
	public String placeOrderHandler(Map<String, String> hReqParams) {
		String sFullURL = fullUrl (hUrls.get("placeOrder"));
		String sResp = U.HTTP.post(sFullURL, hReqParams, charset);
		return sResp;
	}

	@Override
	public String cancleOrderHandler(Map<String, String> hReqParams) {
		String sFullURL = fullUrl (hUrls.get("cancelOrder"));
		String sResp = U.HTTP.post(sFullURL, hReqParams, charset);
		return sResp;
	}

	@Override
	public String bankToZQHandler(Map<String, String> hReqParams) {
		String sFullURL = fullUrl (hUrls.get("bankToSecurities"));
		String sResp = U.HTTP.post(sFullURL, hReqParams, charset);
		return sResp;
	}

	@Override
	public String zjToBank(Map<String, String> hReqParams) {
		String sFullURL = fullUrl (hUrls.get("securitieToBank"));
		String sResp = U.HTTP.post(sFullURL, hReqParams, charset);
		return sResp;
	}

	@Override
	public String zjToBankReQuery(Map<String, String> hReqParams) {
		String sFullURL = fullUrl (hUrls.get("zqToBankReQuery"));
		String sResp = U.HTTP.post(sFullURL, hReqParams, charset);
		return sResp;
	}

	@Override
	public String zjToBankQuery(Map<String, String> hReqParams) {
		String sFullURL = fullUrl (hUrls.get("zqToBankQuery"));
		String sResp = U.HTTP.post(sFullURL, hReqParams, charset);
		return sResp;
	}

	@Override
	public String userInfHandler(Map<String, String> hReqParams) {
		String sFullURL = fullUrl (hUrls.get("userInfo"));
		String sResp = U.HTTP.post(sFullURL, hReqParams, charset);
		return sResp;
	}

	@Override
	public String checkFPwdHandler(Map<String, String> hReqParams) {
		String sFullURL = fullUrl (hUrls.get("checkFPwd"));
		String sResp = U.HTTP.post(sFullURL, hReqParams, charset);
		return sResp;
	}

	@Override
	public String checkTPwdHandler(Map<String, String> hReqParams) {
		String sFullURL = fullUrl (hUrls.get("checkTPwd"));
		String sResp = U.HTTP.post(sFullURL, hReqParams, charset);
		return sResp;
	}

	@Override
	public String checkTPwdByGDHHandler(Map<String, String> hReqParams) {
		String sFullURL = fullUrl (hUrls.get("checkTPwdByGDH"));
		String sResp = U.HTTP.post(sFullURL, hReqParams, charset);
		return sResp;
	}

	@Override
	public String modiTPwdHandler(Map<String, String> hReqParams) {
		String sFullURL = fullUrl (hUrls.get("modiTPwd"));
		String sResp = U.HTTP.post(sFullURL, hReqParams, charset);
		return sResp;
	}

	@Override
	public String modiFPwdHandler(Map<String, String> hReqParams) {
		String sFullURL = fullUrl (hUrls.get("modiFPwd"));
		String sResp = U.HTTP.post(sFullURL, hReqParams, charset);
		return sResp;
	}
	@Override
	public String quoteHandler(Map<String, String> hReqParams) {
		String sFullURL = fullUrl (hUrls.get("quote"));
		String sResp = U.HTTP.post(sFullURL, hReqParams, charset);
		return sResp;
	}
	@Override
	public String zjNZBiz(Map<String, String> hReqParams) {
		String sFullURL = fullUrl (hUrls.get("fundsNZ"));
		String sResp = U.HTTP.post(sFullURL, hReqParams, charset);
		return sResp;
	}
	@Override
	public String gdhQuery(Map<String, String> hReqParams) {
		String sFullURL = fullUrl (hUrls.get("gdhQuery"));
		String sResp = U.HTTP.post(sFullURL, hReqParams, charset);
		return sResp;
	}
	@Override
	public String queryCancleOrderHandler(Map<String, String> hReqParams) {
		String sFullURL = fullUrl (hUrls.get("queryCancleOrder"));
		String sResp = U.HTTP.post(sFullURL, hReqParams, charset);
		return sResp;
	}
	@Override
	public String queryPeihao(Map<String, String> hReqParams) {
		String sFullURL = fullUrl (hUrls.get("queryPeihao"));
		String sResp = U.HTTP.post(sFullURL, hReqParams, charset);
		return sResp;
	}

	@Override
	public String queryBallot(Map<String, String> hReqParams) {
		String sFullURL = fullUrl (hUrls.get("queryBallot"));
		String sResp = U.HTTP.post(sFullURL, hReqParams, charset);
		return sResp;
	}
	/*
	 * Return the completed URL.
	 * 
	 * @param sPartUrl
	 * @return
	 */
	private String fullUrl(String sPartUrl) {
		StringBuffer sFullUrl = new StringBuffer("http://")
		.append(Constants.tradingHost)
		.append(":")
		.append(Constants.tradingPort)
		.append(sPartUrl.startsWith("/") ? sPartUrl : ("/" + sPartUrl));
		return sFullUrl.toString();
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public void sethUrls(Map<String, String> hUrls) {
		this.hUrls = hUrls;
	}

	@Override
	public String queryHistoryZJHandler(Map<String, String> hReqParams) {
		String sFullURL = fullUrl (hUrls.get("queryHistoryZJ"));
		String sResp = U.HTTP.post(sFullURL, hReqParams, charset);
		return sResp;
	}
}
